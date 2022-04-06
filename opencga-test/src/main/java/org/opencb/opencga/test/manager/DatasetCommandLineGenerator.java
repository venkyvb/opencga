/*
 * Copyright 2015-2020 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.opencga.test.manager;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.opencb.commons.utils.PrintUtils;
import org.opencb.opencga.test.config.Caller;
import org.opencb.opencga.test.config.Configuration;
import org.opencb.opencga.test.config.Environment;
import org.opencb.opencga.test.execution.DataSetExecutionCommand;
import org.opencb.opencga.test.execution.DatasetExecutionPlan;
import org.opencb.opencga.test.utils.DatasetTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class DatasetCommandLineGenerator {


    private Configuration configuration;

    public DatasetCommandLineGenerator(Configuration configuration) {
        this.configuration = configuration;
    }

    public static List<String> findAllFileNamesInFolder(File folder) {
        List<String> res = new ArrayList<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.isDirectory()) {
                res.add(file.getName());
            }
        }
        return res;
    }

    /**
     * Allows to only process a list of given environments.
     *
     * @return CLIs Map as a list of command lines by environment
     * @throws IOException
     */
    public List<DatasetExecutionPlan> generateCommandLines() throws IOException {
        //logger.debug("Processing the following environments: {}", environments.toString())
        List<Environment> environments = configuration.getEnvs();
        List<DatasetExecutionPlan> datasetPlanExecutions = new ArrayList<>();
        for (Environment environment : environments) {
            DatasetExecutionPlan datasetPlanExecution = new DatasetExecutionPlan(environment);
            Map<String, List<DataSetExecutionCommand>> commands = new HashMap<>();
            File datasetDir = new File(environment.getDataset().getPath() + File.separator + "fastq");
            List<String> filenames = findAllFileNamesInFolder(datasetDir);
            Collections.sort(filenames);
            for (int i = 0; i < filenames.size(); i++) {
                List<DataSetExecutionCommand> commandLines = new LinkedList<>();
                String filename = filenames.get(i).substring(0, filenames.get(i).indexOf('.'));
                filename = filename.replaceAll(" ", "_");
                List<Caller> callers = environment.getCallers();
                //If all callers are skipped should not execute the aligner
                if (CollectionUtils.isNotEmpty(callers) && !areSkippedAllCallers(callers)) {
                    if (environment.getAligner() != null && !environment.getAligner().isSkip()) {
                        // Generate command line for the Aligner, WARNING!! filenames index is incremented two times if is Paired-End enabled
                        String command = getAlignerCommandLine(environment, filename).replace("${FASTQ1}", environment.getDataset().getPath() + "fastq/" + filenames.get(i));
                        if (environment.getDataset().isPaired()) {
                            command = command.replace("${FASTQ2}", environment.getDataset().getPath() + "fastq/" + filenames.get(++i));
                        }

                        commandLines.add(new DataSetExecutionCommand().setCommandLine(command).setImage(environment.getAligner().getImage()));
                        // Adding samtools command lines
                        List<String> samtoolsCommand = DatasetTestUtils.getSamtoolsCommands(DatasetTestUtils.getBamDirPath(environment) + filename);
                        for (String c : samtoolsCommand) {
                            commandLines.add(new DataSetExecutionCommand().setCommandLine(c).setImage(environment.getAligner().getImage()));
                        }
                    } else {
                        //If aligner are disabled input bam folder must exist
                        File bamDir = Paths.get(environment.getDataset().getPath(), "bam").toFile();
                        if (bamDir.exists()) {
                            FileUtils.copyDirectory(bamDir, Paths.get(DatasetTestUtils.getBamDirPath(environment)).toFile());
                        } else {
                            PrintUtils.printError("Directory " + bamDir.getAbsolutePath() + " not exists.");
                            System.exit(0);
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(callers) && !areSkippedAllCallers(callers)) {
                    // Adding caller command lines
                    for (Caller caller : callers) {
                        String callerCommand = getVariantCallerCommandLine(environment, caller.getCommand(), caller.getParams(), filename, caller.getName().replaceAll(" ", "_"));
                        commandLines.add(new DataSetExecutionCommand().setCommandLine(callerCommand).setImage(caller.getImage()));
                    }
                } else {
                    //If callers are disabled input vcf folder must exist
                    File vcfDir = Paths.get(environment.getDataset().getPath(), "vcf").toFile();
                    if (vcfDir.exists()) {
                        FileUtils.copyDirectory(vcfDir, Paths.get(DatasetTestUtils.getEnvironmentOutputDir(environment), "vcf").toFile());
                    } else {
                        PrintUtils.printError("Directory " + vcfDir.getAbsolutePath() + " not exists.");
                        System.exit(0);
                    }
                }
                commands.put(filename, commandLines);
            }
            datasetPlanExecution.setCommands(commands);
            datasetPlanExecutions.add(datasetPlanExecution);
        }
        return datasetPlanExecutions;
    }

    private boolean areSkippedAllCallers(List<Caller> callers) {
        for (Caller caller : callers) {
            if (!caller.isSkip()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generate the command lines for Aligner adding params to command and replacing environment variables.
     *
     * @param environment
     * @param command
     * @param params
     * @param filename
     * @param name
     * @return
     */
    private String getVariantCallerCommandLine(Environment environment, String command, List<String> params, String filename, String name) {
        String param = String.join(" ", params);
        command = command.replace("${PARAMS}", param);
        command = command.replace("${INDEX}", environment.getReference().getIndex());
        String output = DatasetTestUtils.getEnvironmentOutputDir(environment);
        command = command.replace("${OUTPUT}", DatasetTestUtils.getVCFDirPath(environment) + name + "_" + filename + ".vcf");
        command = command.replace("${BAM}", DatasetTestUtils.getBamDirPath(environment) + filename + ".sorted.bam");
        command = command.replace("${REFERENCE.PATH}", environment.getReference().getPath());
        return command;
    }


    /**
     * Generate the command lines for Aligner adding params to command and replacing environment variables.
     *
     * @param environment
     * @return
     */
    private String getAlignerCommandLine(Environment environment, String filename) {
        String param = String.join(" ", environment.getAligner().getParams());
        String command = environment.getAligner().getCommand();
        command = command.replace("${PARAMS}", param);
        command = command.replace("${INDEX}", environment.getReference().getIndex());
        command = command.replace("${OUTPUT}", DatasetTestUtils.getBamDirPath(environment) + "${FASTQNAME}.sam");
        command = command.replace("${FASTQNAME}", filename);
        return command;
    }
}

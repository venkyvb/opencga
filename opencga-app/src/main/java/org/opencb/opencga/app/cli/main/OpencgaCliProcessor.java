package org.opencb.opencga.app.cli.main;

import com.beust.jcommander.ParameterException;
import org.fusesource.jansi.Ansi;
import org.opencb.opencga.app.cli.CommandExecutor;
import org.opencb.opencga.app.cli.GeneralCliOptions;
import org.opencb.opencga.app.cli.main.executors.*;
import org.opencb.opencga.core.common.GitRepositoryState;

import java.io.Console;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

public class OpencgaCliProcessor {

    private static Console console;
    private static OpencgaCliShellExecutor shell = new OpencgaCliShellExecutor(new GeneralCliOptions.CommonCommandOptions());
    private static OpencgaCliOptionsParser cliOptionsParser;

    private static Console getConsole() {
        if (console == null) {
            console = System.console();
        }
        return console;
    }

    private static OpencgaCliOptionsParser getCliOptionsParser() {
        if (cliOptionsParser == null) {
            cliOptionsParser = new OpencgaCliOptionsParser();
        }
        return cliOptionsParser;
    }

    public static void process(String[] args) {

        console = getConsole();
        boolean invalidInput = false;
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        if (args.length == 1 && "exit".equals(args[0])) {
            System.out.println(String.valueOf(ansi().fg(Ansi.Color.YELLOW).a("\nThanks for using OpenCGA. See you soon.\n\n").reset()));
            System.exit(0);
        }
      /*  if (args.length == 1 && "--shell".equals(args[0])) {
            try {
                shell.execute();
            } catch (Exception e) {
                shell.printlnRed(e.getMessage());
            }
        }

*/
        if (args.length > 2 && "use".equals(args[0]) && "study".equals(args[1])) {

            shell.setCurrentStudy(args[2]);
            return;
        }

        //login The first if clause is for scripting login method and the else clause is for the shell login
        if (args.length > 3 && "users".equals(args[0]) && "login".equals(args[1]) && argsContains(args, "--user-password")) {
            if (!argsContains(args, "--help") && !argsContains(args, "-h")) {
                args = getUserPasswordArgs(args, "--user-password");
            }
        } else if (args.length > 3 && "users".equals(args[0]) && "login".equals(args[1])) {
            if (!argsContains(args, "--help") && !argsContains(args, "-h")) {
                char[] passwordArray = console.readPassword(String.valueOf(ansi().fg(GREEN).a("\nEnter your secret password: ").reset()));
                args = appendArgs(args, new String[]{"--password", new String(passwordArray)});
            }
        }

        if (!invalidInput) {
            cliOptionsParser = getCliOptionsParser();

            try {
                cliOptionsParser.parse(args);

                String parsedCommand = cliOptionsParser.getCommand();
                if (parsedCommand == null || parsedCommand.isEmpty()) {
                    if (cliOptionsParser.getGeneralOptions().version) {
                        System.out.println("");
                        OpencgaCliShellExecutor.printGreen("\tOpenCGA CLI version: ");
                        OpencgaCliShellExecutor.printlnYellow("\t" + GitRepositoryState.get().getBuildVersion());
                        OpencgaCliShellExecutor.printGreen("\tGit version:");
                        OpencgaCliShellExecutor.printlnYellow("\t\t" + GitRepositoryState.get().getBranch() + " " + GitRepositoryState.get().getCommitId());
                        OpencgaCliShellExecutor.printGreen("\tProgram:");
                        OpencgaCliShellExecutor.printlnYellow("\t\tOpenCGA (OpenCB)");
                        OpencgaCliShellExecutor.printGreen("\tDescription: ");
                        OpencgaCliShellExecutor.printlnYellow("\t\tBig Data platform for processing and analysing NGS data");
                        System.out.println("");
                    } else if (cliOptionsParser.getGeneralOptions().help) {
                        cliOptionsParser.printUsage();
                        // System.exit(0);
                    } else {
                        cliOptionsParser.printUsage();
                        //  System.exit(1);
                    }
                } else {
                    CommandExecutor commandExecutor = null;

                    // Check if any command -h option is present
                    if (cliOptionsParser.isHelp()) {
                        cliOptionsParser.printUsage();
                        //System.exit(0);
                    } else {
                        String parsedSubCommand = cliOptionsParser.getSubCommand();
                        if (parsedSubCommand == null || parsedSubCommand.isEmpty()) {
                            cliOptionsParser.printUsage();
                        } else {
                            switch (parsedCommand) {
                                case "users":
                                    commandExecutor = new UsersCommandExecutor(cliOptionsParser.getUsersCommandOptions());
                                    break;
                                case "projects":
                                    commandExecutor = new ProjectsCommandExecutor(cliOptionsParser.getProjectsCommandOptions());
                                    break;
                                case "studies":
                                    commandExecutor = new StudiesCommandExecutor(cliOptionsParser.getStudiesCommandOptions());
                                    break;
                                case "files":

                                    commandExecutor = new FilesCommandExecutor(cliOptionsParser.getFilesCommandOptions());
                                    break;
                                case "jobs":
                                    commandExecutor = new JobsCommandExecutor(cliOptionsParser.getJobsCommandOptions());
                                    break;
                                case "individuals":
                                    commandExecutor = new IndividualsCommandExecutor(cliOptionsParser.getIndividualsCommandOptions());
                                    break;
                                case "samples":
                                    commandExecutor = new SamplesCommandExecutor(cliOptionsParser.getSamplesCommandOptions());
                                    break;
                                case "cohorts":
                                    commandExecutor = new CohortsCommandExecutor(cliOptionsParser.getCohortsCommandOptions());
                                    break;
                                case "panels":
                                    commandExecutor = new DiseasePanelsCommandExecutor(cliOptionsParser.getDiseasePanelsCommandOptions());
                                    break;
                                case "families":
                                    commandExecutor = new FamiliesCommandExecutor(cliOptionsParser.getFamiliesCommandOptions());
                                    break;
                                case "alignments":
                                    commandExecutor =
                                            new AnalysisAlignmentCommandExecutor(cliOptionsParser.getAnalysisAlignmentCommandOptions());
                                    break;
                                case "variant":
                                    commandExecutor =
                                            new AnalysisVariantCommandExecutor(cliOptionsParser.getAnalysisVariantCommandOptions());
                                    break;
                                case "clinical":
                                    commandExecutor =
                                            new AnalysisClinicalCommandExecutor(cliOptionsParser.getAnalysisClinicalCommandOptions());
                                    break;
                                case "operations":
                                    commandExecutor =
                                            new OperationsVariantStorageCommandExecutor(cliOptionsParser.getOperationsVariantStorageCommandOptions());
                                    break;
                                case "meta":
                                    commandExecutor = new MetaCommandExecutor(cliOptionsParser.getMetaCommandOptions());
                                    break;
                                default:
                                    OpencgaCliShellExecutor.printlnRed("ERROR: not valid command passed: '" + parsedCommand + "'");
                                    break;
                            }

                            executeCommand(commandExecutor);
                        }
                    }
                }
            } catch (ParameterException e) {
                OpencgaCliShellExecutor.printlnRed("\n" + e.getMessage());
                cliOptionsParser.printUsage();
                // e.printStackTrace();
                //System.exit(1);
            }
        }
    }

    private static String[] getUserPasswordArgs(String[] args, String s) {
        for (int i = 0; i < args.length; i++) {
            if (s.equals(args[i])) {
                args[i] = "--password";
                break;
            }
        }
        return args;
    }

    private static void executeCommand(CommandExecutor commandExecutor) {
        if (commandExecutor != null) {
            try {
                commandExecutor.execute();
            } catch (Exception e) {
                shell.printlnRed(e.getMessage());
                //    e.printStackTrace();
                //System.exit(1);
            }
        } else {
            cliOptionsParser.printUsage();
            // System.exit(1);
        }
    }

    private static String[] appendArgs(String[] args, String[] strings) {
        String[] res = new String[args.length + strings.length];
        for (int i = 0; i < res.length; i++) {
            if (i < args.length) {
                res[i] = args[i];
            } else {
                res[i] = strings[i - args.length];
            }
        }
        return res;
    }

    private static boolean argsContains(String[] args, String s) {
        for (String arg : args) {
            if (arg.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static void forceLogin() {
        console = getConsole();
        String[] args = new String[2];
        String user = console.readLine(String.valueOf(ansi().fg(GREEN).a("\nEnter your user: ").reset()));
        char[] passwordArray = console.readPassword(String.valueOf(ansi().fg(GREEN).a("\nEnter your password: ").reset()));
        args[0] = "users";
        args[1] = "login";
        args = appendArgs(args, new String[]{"-u", user});
        args = appendArgs(args, new String[]{"--password", new String(passwordArray)});
        cliOptionsParser = getCliOptionsParser();
        cliOptionsParser.parse(args);
        CommandExecutor commandExecutor = new UsersCommandExecutor(cliOptionsParser.getUsersCommandOptions());
        executeCommand(commandExecutor);
    }
}

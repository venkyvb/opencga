package org.opencb.opencga.storage.hadoop.variant.executors;

import org.junit.Before;
import org.junit.Test;
import org.opencb.commons.datastore.core.ObjectMap;
import org.opencb.opencga.storage.core.exceptions.StorageEngineException;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created on 14/02/19.
 *
 * @author Jacobo Coll &lt;jacobo167@gmail.com&gt;
 */
public class SshMRExecutorTest {

    private ObjectMap options;

    @Before
    public void setUp() throws Exception {
        System.setProperty("app.home", "/opt/opencga");
        options = new ObjectMap(MRExecutorFactory.MR_EXECUTOR, "ssh");
        options.put(SshMRExecutor.SSH_USER, "test_user");
        options.put(SshMRExecutor.SSH_HOST, "test_host");

    }

    @Test
    public void testFactory() throws StorageEngineException {
        MRExecutor mrExecutor = MRExecutorFactory.getMRExecutor(options);
        assertThat(mrExecutor, instanceOf(SshMRExecutor.class));
    }

    @Test
    public void testRun() throws StorageEngineException {
        SshMRExecutor sshMRExecutor = new SshMRExecutor();
        sshMRExecutor.init(options);

        String cmd = sshMRExecutor.buildCommand("echo", "hello world");
        assertEquals("/opt/opencga/conf/hadoop/hadoop-ssh.sh echo hello world", cmd);

        List<String> env = sshMRExecutor.buildEnv();

        assertThat(env, hasItem("HADOOP_SSH_USER=test_user"));
        assertThat(env, hasItem("HADOOP_SSH_HOST=test_host"));
    }

    @Test
    public void testChangeRemoteOpenCGAHome() throws StorageEngineException {
        SshMRExecutor sshMRExecutor = new SshMRExecutor();
        sshMRExecutor.init(options.append(SshMRExecutor.REMOTE_OPENCGA_HOME, "/home/user/opencga"));

        String hadoopClasspath = "/opt/opencga/libs/myLib.jar::/opt/opencga/libs/myLibOther.jar:/opt/opencga/conf/hadoop";
        String expectedHadoopClasspath = "/home/user/opencga/libs/myLib.jar:/home/user/opencga/libs/myLibOther.jar:/home/user/opencga/conf/hadoop";
        String actualHadoopClasspath = sshMRExecutor.replaceOpencgaHome("/opt/opencga", "/home/user/opencga", hadoopClasspath);

        assertEquals(expectedHadoopClasspath, actualHadoopClasspath);
    }
}
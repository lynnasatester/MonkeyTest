package utils;

import junit.framework.Assert;

import java.io.DataOutputStream;
import java.io.File;

/**
 * Created by linmiao on 16/11/29.
 */
public class CommandUtil {
    public CommandUtil() {
    }

    public Process execAsRoot(String command) {
        Process ps = null;

        try {
            ps = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(ps.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();

            try {
                if(ps.waitFor() != 0) {
                    System.err.println("exit value = " + ps.exitValue());
                }
            } catch (InterruptedException var5) {
                System.err.println(var5);
                Assert.fail("InterruptedException: Run adb command: " + command + " as root failed!");
            }
        } catch (Exception var6) {
            System.err.println(var6);
            Assert.fail("Exception: Run adb command: " + command + " as root failed!");
        }

        return ps;
    }

    public Process execAsRootWithoutWait(String command) {
        Process ps = null;

        try {
            ps = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(ps.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (Exception var5) {
            System.err.println(var5);
            Assert.fail("Exception: Run adb command: " + command + " as root failed!");
        }

        return ps;
    }

    public Process execAsNormal(String command) {
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(command);
            try {
                if(ps.waitFor() != 0) {
                    System.err.println("exit value = " + ps.exitValue());
                }
            } catch (InterruptedException var4) {
                System.err.println(var4);
                Assert.fail("InterruptedException: Run adb command: " + command + " as normal user failed!");
            }
        } catch (Exception var5) {
            System.err.println(var5);
            Assert.fail("Exception: Run adb command: " + command + " as normal user failed!");
        }

        return ps;
    }

    public Process execAsNormalWithoutWait(String command) {
        Process ps = null;

        try {
            ps = Runtime.getRuntime().exec(command);
        } catch (Exception var4) {
            System.err.println(var4);
            Assert.fail("Exception: Run adb command: " + command + " as normal user failed!");
        }

        return ps;
    }

    public Process startLogcat(File file) throws Exception {
        return this.execAsNormalWithoutWait("logcat -f " + file + " -v time");
    }

    public Process startStepLog(File file, String logTag) throws Exception {
        return this.execAsNormalWithoutWait("logcat -f " + file + " -v time -s " + logTag);
    }

    public Process startRecordMemory(File file) throws Exception {
        return this.execAsNormalWithoutWait("logcat -f " + file + " -v time -s MemoryRecorder");
    }

    public void stopLog(Process logProcess) throws Exception {
        if(logProcess != null) {
            logProcess.destroy();
        }

    }
}

package com.letv.monkeytest1127_1830;

import android.app.Instrumentation;
import android.content.pm.PackageManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import monkey.MonkeyReal;
import utils.CommandUtil;
import utils.Config;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Process process = null;
    private CommandUtil commandUtil=new CommandUtil();
    private SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
    private String time;


    MonkeyReal monkey=null;
    public ApplicationTest(){
        super(Config.TARGET_PACKAGE, MainActivity.class);
    }

    public void testMonkeyEvents(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Instrumentation inst = getInstrumentation();
        PackageManager pm = getActivity().getPackageManager();
        monkey= new MonkeyReal(inst,display, pm, Config.TARGET_PACKAGE);
        monkey.run(new String[]{"-p",Config.TARGET_PACKAGE,"--throttle","500","100"});
    }

    @Override
    protected void setUp()throws Exception{
        // TODO Auto-generated method stub
        if(Config.LogFolder.exists()) {
            deleteDir(Config.LogFolder);
            Log.d(Config.LOG_TAG,"成功删除LogFolder中内容");
        }else {
            System.out.println("LogFolder不存在");
            if(Config.LogFolder.mkdir())
                Log.d(Config.LOG_TAG,"成功创建LogFolder");
            else
                Log.e(Config.LOG_TAG,"创建LogFolder失败!!!!!!!!!!!!!");
        }

        if(Config.ScreenshotFolder.exists()) {
            deleteDir(Config.ScreenshotFolder);
            Log.d(Config.LOG_TAG,"成功删除LogFolder中内容");
        }else {
            System.out.println("LogFolder不存在");
            if(Config.ScreenshotFolder.mkdir())
                Log.d(Config.LOG_TAG,"成功创建LogFolder");
            else
                Log.e(Config.LOG_TAG,"创建LogFolder失败!!!!!!!!!!!!!");
        }



        this.time = this.timeStamp.format(new Date()).toString();
        setActivityInitialTouchMode(false);
        try {
            process = commandUtil.startStepLog(new File(Config.LogFolder + File.separator + this.time + "_monkeystep.log"), Config.LOG_TAG);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        Log.d("monkeylm","finish");
        commandUtil.stopLog(process);
        super.tearDown();
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
//		dir.delete();// 删除目录本身
    }

}
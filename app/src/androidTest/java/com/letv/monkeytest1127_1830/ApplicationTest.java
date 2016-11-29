package com.letv.monkeytest1127_1830;

import android.app.Application;
import android.app.Instrumentation;
import android.content.pm.PackageManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.app.IActivityController;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import monkey.MonkeyReal;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final String packageToTest = "com.letv.monkeytest1127_1830";

    public ApplicationTest(){
        super(packageToTest, MainActivity.class);
    }

    public void testMonkeyEvents(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Instrumentation inst = getInstrumentation();
        PackageManager pm = getActivity().getPackageManager();
        MonkeyReal monkey = new MonkeyReal(inst,display, pm, packageToTest);
        monkey.run(new String[]{"-p",packageToTest,"--throttle","1000","100"});
    }

    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();

        setActivityInitialTouchMode(false);
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        Log.d("monkeylm","finish");
        super.tearDown();
    }



}
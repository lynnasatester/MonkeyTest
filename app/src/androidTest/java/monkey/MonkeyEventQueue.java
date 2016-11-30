/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monkey;

import android.util.Log;

import java.util.LinkedList;
import java.util.Random;

import utils.ActivityUtil;
import utils.Config;
import utils.TakeScreenshot;

/**
 * class for keeping a monkey event queue
 */
@SuppressWarnings("serial")
public class MonkeyEventQueue extends LinkedList<MonkeyEvent> {

    private Random mRandom;
    private long mThrottle;
    private boolean mRandomizeThrottle;
    private ActivityUtil activityUtil=new ActivityUtil();

    public MonkeyEventQueue(Random random, long throttle, boolean randomizeThrottle) {
        super();
        mRandom = random;
        mThrottle = throttle;
        mRandomizeThrottle = randomizeThrottle;
    }

    /*@Override
    public void addLast(MonkeyEvent e) {
        long screenshotTime=0;
        try{
            long startTime=System.currentTimeMillis();
            TakeScreenshot.takeScreenshot(activityUtil.getActivity(),System.currentTimeMillis()+"");
            screenshotTime=System.currentTimeMillis()-startTime;
            Log.d(Config.LOG_TAG,e.getEventType()+"--截图时间"+screenshotTime);
        }catch (Exception exception){
            exception.printStackTrace();
            Log.e(Config.LOG_TAG,"截图出错!!!!!!!!!!!!!!!!");
        }
        super.add(e);
        if (e.isThrottlable()) {
            System.out.println(e.getEventType()+"==========截图=============");
            long throttle = mThrottle;
            if (mRandomizeThrottle && (mThrottle > 0)) {
                throttle = mRandom.nextLong();
                if (throttle < 0) {
                    throttle = -throttle;
                }
                throttle %= mThrottle;
                ++throttle;
            }
            super.add(new MonkeyThrottleEvent(throttle-screenshotTime));
            Log.d(Config.LOG_TAG,"休眠时间"+(throttle-screenshotTime));
        }
    }*/
    @Override
    public void addLast(MonkeyEvent e) {
        super.add(e);
        if (e.isThrottlable()) {
            long throttle = mThrottle;
            if (mRandomizeThrottle && (mThrottle > 0)) {
                throttle = mRandom.nextLong();
                if (throttle < 0) {
                    throttle = -throttle;
                }
                throttle %= mThrottle;
                ++throttle;
            }
            super.add(new MonkeyThrottleEvent(throttle));
        }
    }
}

/*
 * Copyright (C) 2012 Google Inc.
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
import android.app.Instrumentation;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import utils.Config;

/**
 * monkey screen rotation event
 */
public class MonkeyRotationEvent extends MonkeyEvent {

    private final int mRotationDegree;
    private final boolean mPersist;

    /**
     * Construct a rotation Event.
     *
     * @param degree Possible rotation degrees, see constants in
     * anroid.view.Suface.
     * @param persist Should we keep the rotation lock after the orientation
     * change.
     */
    public MonkeyRotationEvent(int degree, boolean persist) {
        super(EVENT_TYPE_ROTATION);
        mRotationDegree = degree;
        mPersist = persist;
    }

    @Override
    public int injectEvent(Instrumentation instrumentation, int verbose) {
        if (verbose > 0) {
            Log.d(Config.LOG_TAG,":Sending rotation degree=" + mRotationDegree +
                               ", persist=" + mPersist);
        }
        // inject rotation event
       /* try {
            System.out.println("rotation===============");
            iwm.freezeRotation(mRotationDegree);
            if (!mPersist) {
                iwm.thawRotation();
            }
            return MonkeyEvent.INJECT_SUCCESS;
        } catch (Exception ex) {
            return MonkeyEvent.INJECT_ERROR_REMOTE_EXCEPTION;
        }*/


        try {
            System.out.println("需要在android manifest.XML中加入横竖屏权限");
            Class<?> WindowManagerGlobal = Class.forName("android.view.WindowManagerGlobal");
            Method getWindowManagerService = WindowManagerGlobal.getMethod("getWindowManagerService", null);
            Object wm=getWindowManagerService.invoke(WindowManagerGlobal,null);
            Class<?> IWindowManager = Class.forName("android.view.IWindowManager");
            Method freezeRotation = IWindowManager.getMethod("freezeRotation",int.class);
//            freezeRotation.invoke(wm, Surface.ROTATION_0); 加入权限后此句取消注视
            //<uses-permission android:name="android.permission.SET_ORIENTATION"/>
            return MonkeyEvent.INJECT_SUCCESS;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return MonkeyEvent.INJECT_ERROR_REMOTE_EXCEPTION;
    }
}

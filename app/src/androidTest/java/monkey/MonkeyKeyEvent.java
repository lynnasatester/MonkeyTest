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

import android.app.Instrumentation;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import utils.Config;

/**
 * monkey key event
 */
public class MonkeyKeyEvent extends MonkeyEvent {
    private int mDeviceId;
    private long mEventTime;
    private long mDownTime;
    private int mAction;
    private int mKeyCode;
    private int mScanCode;
    private int mMetaState;
    private int mRepeatCount;

    private KeyEvent mKeyEvent;

    public MonkeyKeyEvent(int action, int keyCode) {
        this(-1, -1, action, keyCode, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0);
    }

    public MonkeyKeyEvent(long downTime, long eventTime, int action,
            int keyCode, int repeatCount, int metaState,
            int device, int scanCode) {
        super(EVENT_TYPE_KEY);
        mDownTime = downTime;
        mEventTime = eventTime;
        mAction = action;
        mKeyCode = keyCode;
        mRepeatCount = repeatCount;
        mMetaState = metaState;
        mDeviceId = device;
        mScanCode = scanCode;
    }

    public MonkeyKeyEvent(KeyEvent e) {
        super(EVENT_TYPE_KEY);
        mKeyEvent = e;
    }

    public int getKeyCode() {
        return mKeyEvent != null ? mKeyEvent.getKeyCode() : mKeyCode;
    }

    public int getAction() {
        return mKeyEvent != null ? mKeyEvent.getAction() : mAction;
    }

    public long getDownTime() {
        return mKeyEvent != null ? mKeyEvent.getDownTime() : mDownTime;
    }

    public long getEventTime() {
        return mKeyEvent != null ? mKeyEvent.getEventTime() : mEventTime;
    }

    public void setDownTime(long downTime) {
        if (mKeyEvent != null) {
            throw new IllegalStateException("Cannot modify down time of this key event.");
        }
        mDownTime = downTime;
    }

    public void setEventTime(long eventTime) {
        if (mKeyEvent != null) {
            throw new IllegalStateException("Cannot modify event time of this key event.");
        }
        mEventTime = eventTime;
    }

    @Override
    public boolean isThrottlable() {
        return (getAction() == KeyEvent.ACTION_UP);
    }

    @Override
    public int injectEvent(Instrumentation instrumentation,int verbose) {
        String note="";
        if (verbose >=0) {
            if (mAction == KeyEvent.ACTION_UP) {
                note = "ACTION_UP";
            } else {
                note = "ACTION_DOWN";
            }

            try {
                Log.d(Config.LOG_TAG,":Sending Key (" + note + "): "
                        + mKeyCode + "    // "
                        + MonkeySourceRandom.getKeyName(mKeyCode));
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d(Config.LOG_TAG,":Sending Key (" + note + "): "
                        + mKeyCode + "    // Unknown key event");
            }
        }


        try {
            instrumentation.sendKeyDownUpSync(mKeyCode);
        } catch (Exception e) {
            Log.d(Config.LOG_TAG,"Failed to send key (" + note + "): " + mKeyCode + "    // ");
            return MonkeyEvent.INJECT_FAIL;
        }
        return MonkeyEvent.INJECT_SUCCESS;
    }
}

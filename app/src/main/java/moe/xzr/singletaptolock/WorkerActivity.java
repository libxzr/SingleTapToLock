/*
 * Copyright (C) 2021 LibXZR <i@xzr.moe>
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
 
package moe.xzr.singletaptolock;

import android.app.Activity;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

public class WorkerActivity extends Activity {

    private InputManager mInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputManager = InputManager.getInstance();

        sendKeyEvent(KeyEvent.KEYCODE_POWER);

        finish();
    }

    private void injectKeyEvent(KeyEvent event) {
        mInputManager.injectInputEvent(event,
                InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
    }

    private void sendKeyEvent(int keyCode) {
        final long now = SystemClock.uptimeMillis();

        KeyEvent event = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyCode, 0,
                0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_UNKNOWN);

        injectKeyEvent(event);
        injectKeyEvent(KeyEvent.changeAction(event, KeyEvent.ACTION_UP));
    }
}

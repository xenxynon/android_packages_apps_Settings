/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.android.settings.bluetooth;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import android.content.Context;
import android.content.res.Resources;
import android.os.UserManager;
import android.support.v7.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST_PATH, sdk = TestConfig.SDK_VERSION)
public class BluetoothSettingsTest {
    private static final String FOOTAGE_MAC_STRING = "Bluetooth mac: xxxx";

    @Mock
    private UserManager mUserManager;
    @Mock
    private Resources mResource;
    @Mock
    private Context mContext;
    @Mock
    private LocalBluetoothAdapter mLocalAdapter;
    private BluetoothSettings mFragment;
    private Preference mMyDevicePreference;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mFragment = spy(new BluetoothSettings());
        doReturn(mContext).when(mFragment).getContext();
        doReturn(mResource).when(mFragment).getResources();

        mMyDevicePreference = new Preference(RuntimeEnvironment.application);

        mFragment.setLocalBluetoothAdapter(mLocalAdapter);
    }

    @Test
    public void setTextSpan_notSpannable_shouldNotCrash() {
        final String str = "test";
        mFragment.setTextSpan(str, "hello");
    }

    @Test
    public void setUpdateMyDevicePreference_setTitleCorrectly() {
        doReturn(FOOTAGE_MAC_STRING).when(mFragment).getString(
                eq(R.string.bluetooth_footer_mac_message), any());

        mFragment.updateMyDevicePreference(mMyDevicePreference);

        assertThat(mMyDevicePreference.getTitle()).isEqualTo(FOOTAGE_MAC_STRING);
    }

}

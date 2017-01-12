/*
 * Copyright (c) 2017  Ni YueMing<niyueming@163.com>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package net.nym.permissionlibrary.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.nym.permissionlibrary.R;
import net.nym.permissionlibrary.permission.IPermissionConstant;
import net.nym.permissionlibrary.permission.NPermissionListener;
import net.nym.permissionlibrary.permission.NPermissionResult;

import java.util.List;
import java.util.Locale;

/**
 * @author niyueming
 * @date 2017-01-11
 * @time 15:33
 */

public class NPermissionActivity extends AppCompatActivity implements IPermissionConstant,NPermissionListener,ActivityCompat.OnRequestPermissionsResultCallback{
    private final static String TAG = NPermissionActivity.class.getSimpleName();
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        NPermissionResult.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    @Override
    public void onPermissionSucceed(int requestCode, List<String> grantPermissions) {
        Log.i(TAG,String.format(Locale.getDefault(),"succeed,requestCode=%d,permissions=%s",requestCode,grantPermissions));
    }

    @Override
    public void onPermissionFailed(int requestCode, List<String> deniedPermissions) {
        String permission = "";
        switch (requestCode) {
            case REQUEST_CAMERA:
                permission = getString(R.string.n_permission_camera);
                break;
            case REQUEST_CONTACTS:
                permission = getString(R.string.n_permission_contacts);
                break;
            case REQUEST_CALENDAR:
                permission = getString(R.string.n_permission_calendar);
                break;
            case REQUEST_LOCATION:
                permission = getString(R.string.n_permission_location);
                break;
            case REQUEST_STORAGE:
                permission = getString(R.string.n_permission_storage);
                break;
            case REQUEST_MICROPHONE:
                permission = getString(R.string.n_permission_microphone);
                break;
            case REQUEST_SENSORS:
                permission = getString(R.string.n_permission_sensors);
                break;
            case REQUEST_PHONE:
                permission = getString(R.string.n_permission_phone);
                break;
            case REQUEST_SMS:
                permission = getString(R.string.n_permission_sms);
                break;
            case REQUEST_CAMERA_AND_STORAGE:
                permission = getString(R.string.n_permission_camera_and_storage);
                break;
            default:
                break;
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.n_failed_title)
                .setMessage(getString(R.string.n_failed_message,permission))
                .setPositiveButton(R.string.n_failed_conform, null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NPermissionResult.removeListener(this);
    }
}

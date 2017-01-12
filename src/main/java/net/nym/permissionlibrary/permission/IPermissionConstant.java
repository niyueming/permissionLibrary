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

package net.nym.permissionlibrary.permission;

import android.Manifest;

/**
 * @author niyueming
 * @date 2017-01-11
 * @time 15:40
 */

public interface IPermissionConstant {

    int REQUEST_INVALID = -1;
    int REQUEST_CAMERA = 0;
    int REQUEST_CONTACTS = 1;
    int REQUEST_CALENDAR = 2;
    int REQUEST_LOCATION = 3;
    int REQUEST_STORAGE = 4;
    int REQUEST_MICROPHONE = 5;
    int REQUEST_SENSORS = 6;
    int REQUEST_PHONE = 7;
    int REQUEST_SMS = 8;
    int REQUEST_CAMERA_AND_STORAGE = 9;
    /**
     * Permissions required to read and write contacts
     * 通讯录
     */
    String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS, Manifest.permission.GET_ACCOUNTS};
    /**
     * 日历
     */
    String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    /**
     * 摄像头
     */
    String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    /**
     * 地理位置
     */
    String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 存储空间
     */
    String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    /**
     * 存储空间和摄像头
     */
    String[] PERMISSIONS_CAMERA_AND_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    /**
     * 麦克风
     */
    String[] PERMISSIONS_MICROPHONE = {Manifest.permission.RECORD_AUDIO};
    /**
     * 身体传感器
     */
    String[] PERMISSIONS_SENSORS = {Manifest.permission.BODY_SENSORS};
    /**
     * 电话
     */
    String[] PERMISSIONS_PHONE = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
//            ,
//            Manifest.permission.READ_CALL_LOG,
//            Manifest.permission.WRITE_CALL_LOG,
//            Manifest.permission.USE_SIP,
//            Manifest.permission.PROCESS_OUTGOING_CALLS,
//            Manifest.permission.ADD_VOICEMAIL
    };
    /**
     * 短信
     */
    String[] PERMISSIONS_SMS = {Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
//            "android.permission.READ_CELL_BROADCASTS",
            Manifest.permission.ADD_VOICEMAIL
    };
}

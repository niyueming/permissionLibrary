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

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author niyueming
 * @date 2017-01-11
 * @time 14:56
 */

public class NPermissionResult {
    private final static String TAG = NPermissionResult.class.getSimpleName();

    private final static SparseArray<NPermissionListener> mListenerMap = new SparseArray<NPermissionListener>();
    public final static NPermissionListener defaultListener = new NPermissionListener() {
        @Override
        public void onPermissionSucceed(int requestCode, List<String> grantPermissions) {
            Log.i(TAG,String.format(Locale.getDefault(),"succeed,requestCode=%d,permissions=%s",requestCode,grantPermissions));
        }

        @Override
        public void onPermissionFailed(int requestCode, List<String> deniedPermissions) {
            Log.i(TAG,String.format(Locale.getDefault(),"failed,requestCode=%d,permissions=%s",requestCode,deniedPermissions));
        }
    };

    public static void addListener(NPermissionListener listener,int requestCode){
        mListenerMap.append(requestCode,listener);
    }

    public static void removeListener(int requestCode){
        mListenerMap.remove(requestCode);
    }

    public static void removeListener(NPermissionListener listener){
        while (true){
            int index = mListenerMap.indexOfValue(listener);
            if (index == - 1){
                return;
            }
            mListenerMap.removeAt(index);
        }

    }

    public static void clear(){
        mListenerMap.clear();
    }
    /**
     * Parse the request results.
     *
     * @param requestCode  request code.
     * @param permissions  one or more permissions.
     * @param grantResults results.
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[]
            grantResults) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedList.add(permissions[i]);
            else
                deniedList.add(permissions[i]);
        }

        NPermissionListener listener = mListenerMap.get(requestCode,defaultListener);
        if (listener != null){
            if (deniedList.isEmpty()){
                listener.onPermissionSucceed(requestCode,grantedList);
            }
            else{
                listener.onPermissionFailed(requestCode, deniedList);
            }
        }
    }
}

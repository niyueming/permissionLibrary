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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import net.nym.permissionlibrary.R;
import net.nym.permissionlibrary.utils.NPermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niyueming
 * @date 2017-01-11
 * @time 14:01
 */

public class NPermission {
    private final static String TAG = NPermission.class.getSimpleName();
    private Object mContext;
    private NPermissionListener mListener;
    private String[] permissions;
    private int mRequestCode;
    private NRationaleListener mRationaleListener;

    private NPermission(){

    }

    private void setContext(Object context) {
        this.mContext = context;
    }

    private void setPermissionListener(NPermissionListener listener) {
        this.mListener = listener;
    }

    private void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    private void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
    }

    private void setRationaleListener(NRationaleListener rationaleListener) {
        this.mRationaleListener = rationaleListener;
    }

    public void request(){
        NPermissionResult.addListener(mListener,mRequestCode);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Context context = NPermissionUtils.getContext(mContext);

            final int[] grantResults = new int[permissions.length];
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();

            final int permissionCount = permissions.length;
            for (int i = 0; i < permissionCount; i++) {
                grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
            }
            onRequestPermissionsResult(mContext, mRequestCode, permissions, grantResults);
        } else {
            String[] deniedPermissions = getDeniedPermissions(mContext, permissions);
            // Denied permissions size > 0.
            if (deniedPermissions.length > 0) {
                // Remind users of the purpose of permissions.
                boolean showRationale = NPermissionUtils.shouldShowRationalePermissions(mContext, deniedPermissions);
                if (showRationale && mRationaleListener != null){
                    mRationaleListener.showRequestPermissionRationale(mRequestCode, rationale);
                }
                else{
                    rationale.resume();
                }
            } else { // All permission granted.
                final int[] grantResults = new int[permissions.length];

                final int permissionCount = permissions.length;
                for (int i = 0; i < permissionCount; i++) {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                }
                onRequestPermissionsResult(mContext, mRequestCode, permissions, grantResults);
            }
        }
    }

    /**
     * Rationale.
     */
    private NRationale rationale = new NRationale() {
        @Override
        public void cancel() {
            int[] results = new int[permissions.length];
            Context context = NPermissionUtils.getContext(mContext);
            for (int i = 0; i < results.length; i++) {
                results[i] = ActivityCompat.checkSelfPermission(context, permissions[i]);
            }
            onRequestPermissionsResult(mContext, mRequestCode, permissions, results);
        }

        @Override
        public void resume() {
            requestPermissions(mContext, mRequestCode, getDeniedPermissions(permissions));
        }
    };

    private static String[] getDeniedPermissions(Object o, String... permissions) {
        Context context = NPermissionUtils.getContext(o);
        List<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions)
            if (!NPermissionUtils.hasPermission(context, permission)){
                deniedList.add(permission);
            }
        return deniedList.toArray(new String[deniedList.size()]);
    }

    private static void requestPermissions(Object o, int requestCode, String... permissions) {
        if (o instanceof Activity){
            ActivityCompat.requestPermissions(((Activity) o), permissions, requestCode);
        }
        else if (o instanceof android.support.v4.app.Fragment){
            ((android.support.v4.app.Fragment) o).requestPermissions(permissions, requestCode);
        }
        else if (o instanceof android.app.Fragment) {
            FragmentCompat.requestPermissions((android.app.Fragment)o,permissions, requestCode);
        }else {
            Log.e(TAG, "The " + o.getClass().getName() + " is not support " + "requestPermissions()");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void onRequestPermissionsResult(Object o, int requestCode, @NonNull String[] permissions,
                                                   @NonNull int[] grantResults) {
        if (o instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ((Activity) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
            else if (o instanceof ActivityCompat.OnRequestPermissionsResultCallback)
                ((ActivityCompat.OnRequestPermissionsResultCallback) o).onRequestPermissionsResult(requestCode,
                        permissions, grantResults);
            else{
                Log.e(TAG, "The " + o.getClass().getName() + " is not support " + "onRequestPermissionsResult()");
            }
        } else if (o instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) o).onRequestPermissionsResult(requestCode, permissions,
                    grantResults);
        } else if (o instanceof android.app.Fragment) {
            ((android.app.Fragment) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    public static class Builder{
        private Object mContext;
        private int mRequestCode = IPermissionConstant.REQUEST_INVALID;
        private NPermissionListener mListener = NPermissionResult.defaultListener;
        private NRationaleListener mRationaleListener;
        private String[] permissions;
        public Builder(Activity activity){
            mContext = activity;
            mRationaleListener = new DefaultRationaleListener(activity);
            if (activity instanceof NPermissionListener){
                mListener = (NPermissionListener) activity;
            }
        }

        public Builder(Fragment fragment){
            mContext = fragment;
            mRationaleListener = new DefaultRationaleListener(fragment.getContext());
            if (fragment instanceof NPermissionListener){
                mListener = (NPermissionListener) fragment;
            }
        }

        public Builder(android.app.Fragment fragment){
            mContext = fragment;
            mRationaleListener = new DefaultRationaleListener(fragment.getActivity());
            if (fragment instanceof NPermissionListener){
                mListener = (NPermissionListener) fragment;
            }
        }

        public Builder requestCode(int requestCode){
            mRequestCode = requestCode;
            return this;
        }

        public Builder permissions(String... permissions){
            this.permissions = permissions;
            return this;
        }

        public Builder permissionListener(NPermissionListener listener){
            mListener = listener;
            return this;
        }

        public Builder rationaleListener(NRationaleListener rationaleListener){
            mRationaleListener = rationaleListener;
            return this;
        }


        public NPermission build(){
            if (permissions == null){
                permissions = new String[]{};
            }
            NPermission permission = new NPermission();
            permission.setContext(mContext);
            permission.setRequestCode(mRequestCode);
            permission.setPermissions(permissions);
            permission.setPermissionListener(mListener);
            permission.setRationaleListener(mRationaleListener);

            return permission;
        }


        public class DefaultRationaleListener implements NRationaleListener {

            Context mContext;
            public DefaultRationaleListener(Context context){
                mContext = context;
            }
            @Override
            public void showRequestPermissionRationale(int requestCode, final NRationale rationale) {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.n_rationale_title)
                        .setMessage(R.string.n_rationale_message)
                        .setPositiveButton(R.string.n_rationale_conform, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rationale.resume();
                            }
                        })
                        .setNegativeButton(R.string.n_rationale_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rationale.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        };
    }
}

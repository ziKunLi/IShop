package com.study.newbies.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.study.newbies.common.app.ThisApp;

/**
 *
 * @author NewBies
 * @date 2018/9/24
 */

public class PermissionUtil {

    /**
     * 获取文件访问与编辑权限
     * @param activity
     */
    public static void getFilePermission(Activity activity){
        //动态获取创建和删除文件的权限
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //判断SDK的版本是否大于6.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
        //动态获取向SD卡写入文件的权限
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},2);
            }
        }
    }

    /**
     * 获得相机权限
     * @param activity
     */
    public static void getCameraPermission(Activity activity){
        if(ContextCompat.checkSelfPermission(ThisApp.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //判断SDK的版本是否大于6.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},1);
            }
        }
    }

    public static void getReadContactsPermission(Activity activity, int requestCode){
        //动态获取读取联系人权限
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},requestCode);
            }
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},requestCode);
            }
        }
    }

    /**
     * 拨打电话权限
     * @param activity
     * @param requestCode
     */
    public static void getCallPhonePermission(Activity activity, int requestCode){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},requestCode);
            }
        }
    }

}

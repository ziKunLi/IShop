package com.study.newbies.common.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

/**
 * 用于加载文件的工具类
 * @author NewBies
 * @date 2018/1/11
 */
public class FileLoadUtil {

    /**
     * 判断并得到SD卡的路径
     * @return 如果存在SD卡则返回SD卡路径，如果不存在则返回null
     */
    public static String getSdCardPath() {
        //判断手机是否存在SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //返回SD卡路径
            return  Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 动态获取创建和删除文件夹，向SD卡写入文件的权限
     */
    public static void getPermissions(AppCompatActivity context){
        //动态获取创建和删除文件的权限
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //判断SDK的版本是否大于6.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
        //动态获取向SD卡写入文件的权限
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},2);
            }
        }
    }

    /**
     * 在指定路径处新建指定文件夹
     * @return
     */
    public static boolean makirFile(String path, String fileName) {
        File file = new File(path + fileName);
        try {
            //如果文件夹不存在，那么创建文件夹
            if(!file.exists()){
                file.mkdirs();
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //出现异常，返回FALSE
            return false;
        }
        return false;
    }
}

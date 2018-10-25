package com.study.newbies.common.util.picture;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 *
 * @author NewBies
 * @date 2018/9/25
 */
public class PhotoUtil {

    public static void takePhoto(Activity activity,String imagePath, int requestCode){
        Uri imageUri = null;
        File outputImage = new File(imagePath);
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(activity, "com.example.newbies.solutionrgbresolution.fileProvider", outputImage);
        }
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 更改系统默认存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void getPicture(Activity activity, int requestCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 得到从系统文件夹中选取的文件的路径
     * @param data
     * @return
     */
    public static String getUriPath(Activity activity, Intent data){
        Uri uri = data.getData();
        String path = "";
        String scheme = uri.getScheme();
        //得到文件的真实路径
        if (scheme == null) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        path = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return path;
    }
}

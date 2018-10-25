package com.study.newbies.common.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.study.newbies.common.app.ThisApp;
import com.study.newbies.common.net.callback.IRequest;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.util.FileUtil;
import com.study.newbies.common.util.StringUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 *
 * @author NewBies
 * @date 2018/9/12
 */

public class SaveFileTask extends AsyncTask<Object, Void, File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {

        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream inputStream = body.byteStream();
        if(StringUtil.isNull(downloadDir)){
            downloadDir = "shopDownload";
        }
        if(StringUtil.isNull(extension)){
            extension = "";
        }
        if(name == null){
            return FileUtil.writeToDisk(inputStream, downloadDir, extension.toUpperCase(), extension);
        }
        else {
            return FileUtil.writeToDisk(inputStream, downloadDir, name);
        }

    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    /**
     * 自动安装APK
     * @param file
     */
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            ThisApp.getApplicationContext().startActivity(install);
        }
    }
}

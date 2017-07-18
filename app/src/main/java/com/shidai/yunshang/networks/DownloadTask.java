package com.shidai.yunshang.networks;


import android.os.AsyncTask;

import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.intefaces.DownLoadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 创建时间： 2017/7/3.
 * 作者：黄如辉
 * 功能描述：
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private int STAEOFNETWORK = 1;
    private InputStream is;
    int filelen = 0;
    private FileOutputStream fos;
    private DownLoadListener downLoadListener;
    private int downLength = 0;

    public DownloadTask(int filelen,  DownLoadListener downLoadListener) {
        this.filelen = filelen;
        this.downLoadListener = downLoadListener;
    }

    @Override
    protected void onPreExecute() {
        downLoadListener.shownotifi();
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        downLength += values[0];
        downLoadListener.refreshView(filelen, values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        if (downLength == filelen) {
        downLoadListener.finishNotify();
        // 安装文件
        downLoadListener.installApk();
        //通知条消失
        downLoadListener.dismisNotification();
//        }else{
//            downLoadListener.cancleNotification();
//        }

    }

    @Override
    protected String doInBackground(String... params) {
        File file = new File(Constant.SAVEAPPFILEPATH);
        // 第一次新建目录
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }

        try {
            URL url = new URL(params[0]);
            HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
            if (openConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                // 提示用户,或程序内部处理
                return null;
            }
            is = openConnection.getInputStream();
            fos = new FileOutputStream(file);
            int len = 0;
            int loadlen = 0;
            int num = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                loadlen = loadlen + len;
                // 每百分之7更新一次
                // 降低更新频率
                int progress = loadlen * 100 / filelen;
                if (progress > 2 * num )
                {
                    num++;
                    //把进度条的值传过去
                    publishProgress(loadlen);
                }
                fos.flush();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
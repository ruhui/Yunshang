package com.shidai.yunshang.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.shidai.yunshang.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    // 递归删除目录中的子目录下
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static File getFile(String path) {
        return new File(path);
    }

    public static boolean isFileExists(String path) {
        return new File(path).exists();
    }

    public static long getFileSize(File f) throws Exception {
        long s = 0;
        FileInputStream fis = null;
        if (f.exists()) {
            fis = new FileInputStream(f);
            fis.close();
            s = fis.available();
        } else {
            f.createNewFile();
        }
        fis.close();
        fis = null;
        return s;
    }

    public static String getFloderSize(File f) {
        long size = 0;
        if (f.exists()) {
            File flists[] = f.listFiles();
            for (File flist : flists) {
                if (flist.isDirectory()) {
                    try {
                        size = size + getFileSize(flist);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    size = size + flist.length();
                }
            }
        }
        return FormetFileSize(size);
    }

    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        if (fileSizeString.startsWith(".")) {
            fileSizeString = "0" + fileSizeString;
        }
        return fileSizeString;
    }

    public static File createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        return file;
    }

    public static File mkdirs(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    public static boolean deleteFile(String filePath) {
        boolean result = false;
        File file = new File(filePath);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

   /*图片压缩并获取新的地址*/
    public static String scal(String filepath){
        File outputFile = new File(filepath);
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filepath, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);
            outputFile = new File(createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }else{
                File tempFile = outputFile;
                outputFile = new File(createImageFile().getPath());
                copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile.getPath();

    }

    public static Uri createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,// prefix
                    ".jpg",         /* suffix */
                    storageDir      //* directory */
            );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents


        Uri mOriginUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mOriginUri = FileProvider.getUriForFile(MyApplication.getInstance(), MyApplication.getInstance().getPackageName() + ".FileProvider", image);
        } else {
            mOriginUri = Uri.fromFile(image);
        }
        return mOriginUri;
    }


    public static void copyFileUsingFileChannels(File source, File dest){
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

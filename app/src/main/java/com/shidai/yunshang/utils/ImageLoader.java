package com.shidai.yunshang.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.shidai.yunshang.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ImageLoader {

    private final static int DEFAULT_ICON_RESID =
            R.drawable.nav_icon_head_default;

    //加载本地图片
    public static void loadLocalImage(File file, ImageView imageView, int radio){
        Glide.with(imageView.getContext()).
                load(file).
                asBitmap().
                transform(new GlideRoundTransform(imageView.getContext(), radio)).
                into(imageView);
    }

    //加载资源图片
    public static void loadResourceImage(int drawable,ImageView imageView,int radio, int width, int height){
        Glide.with(imageView.getContext()).
                load(drawable).
                asBitmap().
                transform(new GlideRoundTransform(imageView.getContext(), radio)).
                override(width, height).
                into(imageView);
    }


    //加载资源图片

    public static void getResourceBitmap(final int drawable, final ImageView imageView) {
       new ImageSynTask(imageView.getContext(), drawable, imageView).execute("");
    }

    static class ImageSynTask extends AsyncTask<String, Void,String>{
        Bitmap bitmap = null;
        Context context;
        ImageView imageView;
        int drawable;

        public ImageSynTask(Context context, int drawable,ImageView imageView){
            this.context = context;
            this.drawable =drawable;
            this.imageView = imageView;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                bitmap = Glide.with(context).load(drawable).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageView.setImageBitmap(bitmap);
        }
    }

    //加载资源图片
    public static void loadResourceImage(int drawable,ImageView imageView,int radio){
        Glide.with(imageView.getContext()).
                load(drawable).
                asBitmap().
                transform(new GlideRoundTransform(imageView.getContext(), radio)).
                into(imageView);
    }

    public static void loadCircleResourceImage(int drawable, ImageView imageView) {
        Context context = imageView.getContext();
        BitmapPool pool = Glide.get(context).getBitmapPool();
        Glide.with(context).load(drawable).fitCenter().bitmapTransform(new CropCircleTransformation(pool)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Uri uri, ImageView imageView) {
        Glide.with(imageView.getContext()).loadFromMediaStore(uri).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    public static void loadImageRadio(String url, ImageView imageView,int radio) {
        if (TextUtils.isEmpty(url) || imageView == null) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(trim(url))
                .transform(new GlideRoundTransform(imageView.getContext(), radio))
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    private static String trim(String url) {
        return url != null ? url.trim() : url;
    }

    //现实占位符（网络不好时现实placeHolderImgRes）
    public static void loadImage(String url, ImageView imageView, int placeHolderImgRes) {
        Glide.with(imageView.getContext()).load(trim(url)).fitCenter().placeholder(placeHolderImgRes).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    //现实圆形图片
    public static void loadCircleImage(String url, ImageView imageView, int defaultdrable) {
        Context context = imageView.getContext();
        BitmapPool pool = Glide.get(context).getBitmapPool();
        Glide.with(context).load(trim(url)).placeholder(defaultdrable).fitCenter()
                .bitmapTransform(new CropCircleTransformation(pool)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    //获取网络图片返回Bitmap
    public static Bitmap getImage(Context context, String url) {
        try {
            return Glide.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadImageToDisk(Context context, final String url, final String dicPath, final String fileName) {
        loadImageToDisk(context, url, dicPath, null);
    }

    public static void loadImageToDisk(Context context, final String url, final String dicPath, final String fileName, final DownloadCompleteCallback callback) {
        final Context ctx = context.getApplicationContext();
        Glide.with(ctx).load(url)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(final File bitmapFile, GlideAnimation anim) {
                        new AsyncTask<File, Void, File>() {

                            @Override
                            protected File doInBackground(File... params) {
                                String title = fileName;
//                                String dicPath = LocalWallpaegrManager.WALLPAGER_DISK;
                                File dic = new File(dicPath);
                                if (!dic.exists()) {
                                    dic.mkdirs();
                                }
                                File dest = new File(dic, title);
                                try {
                                    FileUtil.copyFile(params[0], dest);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return dest;
                            }

                            @Override
                            protected void onPostExecute(File destFile) {
                                try {
                                    if (callback != null) {
                                        callback.onComplete(destFile.getPath());
                                    }
//                                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                                    scanIntent.setData(Uri.fromFile(destFile));
//                                    ctx.sendBroadcast(scanIntent);
//                                    SecurePreferences.getInstance().edit()
                                } catch (Exception e) {

                                }
//                                ToastHelp.getInstance().showToast(R.string.save_success);
                            }
                        }.execute(bitmapFile);
                    }
                });
    }


    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public static class CropCircleTransformation implements Transformation<Bitmap> {

        private BitmapPool mBitmapPool;

        public CropCircleTransformation(BitmapPool pool) {
            this.mBitmapPool = pool;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();
            int size = Math.min(source.getWidth(), source.getHeight());

            int width = (source.getWidth() - size) / 2;
            int height = (source.getHeight() - size) / 2;

            Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                    BitmapShader.TileMode.CLAMP);
            if (width != 0 || height != 0) {
                Matrix matrix = new Matrix();
                matrix.setTranslate(-width, -height);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override
        public String getId() {
            return "CropCircleTransformation()";
        }
    }

    public interface DownloadCompleteCallback {
        void onComplete(String path);
    }


    public static class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}

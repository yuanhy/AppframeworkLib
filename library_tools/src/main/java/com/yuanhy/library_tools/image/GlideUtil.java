package com.yuanhy.library_tools.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/4/9.
 */

public class GlideUtil {
   private static  String TAG="GlideUtil";


    private static GlideUtil glideImageViewUtil;
    private static RequestOptions options;
    private static RequestOptions optionsUserIco;

    private GlideUtil() {
        options = new RequestOptions()
//                .placeholder(R.drawable.glidle_loadgif)
                .error(R.drawable.icon_errorimg).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        optionsUserIco = new RequestOptions()
                .circleCropTransform()
                .placeholder(R.drawable.login_head)
                .error(R.drawable.login_head).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public static GlideUtil getGlideImageViewUtil() {
        if (glideImageViewUtil == null) {
            glideImageViewUtil = new GlideUtil();
        }
        return glideImageViewUtil;
    }

    public void setUserIco(Context context, String urlpath, ImageView imageView) {
        if (context == null)
            return;
        Glide.with(context).load(urlpath).apply(optionsUserIco).into(imageView);
    }

    public void setUserIco(Context context, int drawable, ImageView imageView) {

        Glide.with(context).load(context.getResources().getDrawable(drawable)).apply(optionsUserIco).into(imageView);
    }

    public void setImageView(Context context, String urlpath, ImageView imageView) {
        Glide.with(context).load(urlpath).apply(options).into(imageView);
    }
    public void setImageViewBitmap(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).apply(options).into(imageView);
    }

    public void setImageView(Context context, int drawable, ImageView imageView) {
        Glide.with(context).load(context.getResources().getDrawable(drawable)).apply(options).into(imageView);

    }
    public void setGifImageView(Context context, String urlpath, ImageView imageView) {
        Glide.with(context).asGif().load(urlpath).apply(options).into(imageView);
    }


    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }


    //清除内存缓存
    public void clearMemory(Context context) {
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();
    }

    /**
     * 绘制圆角图片
     * @param context
     * @param path
     * @param imageView
     * @param dpValue dp单位
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    public   void setImageTransform(Context context, String path, ImageView imageView, float dpValue,boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom){
    CornerTransform transformation = new CornerTransform(context, dip2px(context, dpValue));
//只是绘制左上角和右上角圆角
    transformation.setExceptCorner(leftTop, rightTop, leftBottom, rightBottom);

    Glide.with(context)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false)
            .placeholder(R.drawable.load_img)
            .error(R.drawable.icon_errorimg)
            .transform(transformation)
            .into(imageView);
}

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
        /**
         * Glide 加载图片保存到本地
         * @param context
         * @param imgUrl 图片地址
         * @param imgFileName 图片文件绝对地址
         */
    public static void savaImage(final Context context, String imgUrl, final String  imgFileName, final YCallBack callBack){
//        File sourceFile = Glide.with(context).asFile().load(imgUrl).submit().get();
//        Glide.with(context).load(imgUrl).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                callBack.requestFail(false);
//                LogCatUtil.getInstance(context)
//                        .i("savaImage",  imgFileName+" 图片保存失败："+e.getMessage());
//                super.onLoadFailed(e, errorDrawable);
//            }
//
//            @Override
//            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
//                try {
//                    if (bytes==null||bytes.length==0){
//                        callBack.requestFail(false);
//                        return;
//                    }
//                    boolean   isSuccess=   savaBitmap(imgFileName, bytes);
//                    if (isSuccess){
//                        callBack.requestSuccessful(isSuccess);
//                    }else {
//                        callBack.requestFail(isSuccess);
//                    }
//                } catch (Exception e) {
//                    callBack.requestFail(null);
//                    e.printStackTrace();
//                }
//            }
//        });
    }
    // 保存图片到手机指定目录
    public static boolean savaBitmap(String imgFileName, byte[] bytes) {
        boolean   isSuccess = false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgFileName);
            fos.write(bytes);
            isSuccess =true;
           AppFramentUtil.logCatUtil.v ("SavaImageFilePath","成功--->"+imgFileName);
        } catch (IOException e) {
            AppFramentUtil.logCatUtil.v("SavaImageFilePath","失败--->"+imgFileName);
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
    /**
     * Glide 加载图片保存到本地
     * @param context
     * @param imgUrl 图片地址
     * @param imgFileName 图片文件绝对地址
     */
    public static void savaImage(final Context context,String imgUrl, final String  imgFileName, String directoryPath,
                                 final YCallBack callBack){
       File file= ImageUtil. newImageFile(directoryPath,imgFileName,imgUrl);
      if (file==null||!file.exists()){
          callBack.requestFail(false);
          LogCatUtil.getInstance(context)
                  .i(TAG,  imgFileName+" 图片创建失败：");
          return;
      }
//        Glide.with(context).load(imgUrl).into(new SimpleTarget<byte[]>() {
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                callBack.requestFail(false);
//                LogCatUtil.getInstance(context)
//                        .i("savaImage",  imgFileName+" 图片保存失败：");
//                super.onLoadFailed(e, errorDrawable);
//            }
//
//            @Override
//            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
//                try {
//                    if (bytes==null||bytes.length==0){
//                        callBack.requestFail(false);
//                        return;
//                    }
//                    boolean   isSuccess=   savaBitmap(directoryPath+imgFileName, bytes);
//                    if (isSuccess){
//                        callBack.requestSuccessful(isSuccess);
//                    }else {
//                        callBack.requestFail(isSuccess);
//                    }
//                } catch (Exception e) {
//                    callBack.requestFail(null);
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void setGifImageView2(Context context, int drawId, ImageView imageView) {

            RequestOptions      optionsGif = new RequestOptions()
//                .placeholder(R.drawable.load_img)
//                    .error(R.drawable.icon_errorimg)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).asGif()   .centerCrop().load(drawId).apply(optionsGif).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {

                if (resource instanceof GifDrawable) {
                    //加载一次
                    ((GifDrawable)resource).setLoopCount(1);
                }
                return false;
            }
        }).into(imageView );

    }
}

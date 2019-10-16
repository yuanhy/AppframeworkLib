package cc.zenking.edu.zhjx.view.imge;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.yuanhy.library_tools.image.GlideUtil;

import java.io.File;

import cc.zenking.edu.zhjx.R;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideUtil.getGlideImageViewUtil().setImageView(activity,path,imageView);
//        Picasso.with(activity)//
//                   .load(Uri.fromFile(new File(path)))//
//                .placeholder(R.mipmap.default_image)//
//                .error(R.mipmap.default_image)//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideUtil.getGlideImageViewUtil().setImageView(activity,path,imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
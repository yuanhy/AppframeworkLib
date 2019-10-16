package cc.zenking.edu.zhjx.utils;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;

import cc.zenking.edu.zhjx.view.imge.PicassoImageLoader;

public class ImagePickerUtils {
	private static ImagePickerUtils imagePickerUtils;

	private ImagePickerUtils() {
		initImagePic();
	}

	public static ImagePickerUtils getInstance() {
		synchronized (ImagePickerUtils.class) {
			if (imagePickerUtils == null) {
				imagePickerUtils = new ImagePickerUtils();
			}
		}
		return imagePickerUtils;
	}

	public ImagePicker getImagePicker() {
		return imagePicker;
	}

	private	ImagePicker imagePicker;

	private void initImagePic() {
		imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);  //显示拍照按钮
		imagePicker.setCrop(true);        //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true); //是否按矩形区域保存
		imagePicker.setSelectLimit(9);    //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
	}
}

package com.lq.ren.crazynotes.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

/**
 * Author lqren on 17/3/14.
 */

public class photosUtil {

    /** 相机选择照片 */
    private void takePictureByCamera(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }

    /** 从图库选择照片 */
    public void choosePictureFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 从图库选择照片
     */
    public static Uri cropPicture(Activity activity, int requestCode, Intent data) {
        Uri imageUri = data.getData();
        Uri fileUri = null;
        String picturePath = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                picturePath = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, "", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            picturePath = "";
        }
        if (picturePath != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                fileUri = Uri.parse(picturePath);
            } else {
                fileUri = Uri.fromFile(new File(picturePath));
            }
            File cropTempFile = new File("");
            if (cropTempFile != null) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(fileUri, "image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("noFaceDetection", true);
                intent.putExtra("return-data", false);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropTempFile));
                activity.startActivityForResult(intent, requestCode);
            }
        }
        return fileUri;
    }
}

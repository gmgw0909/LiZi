package com.fancy.learncenter.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by XINDU_L_011 on 2018/1/19.
 */

public class MyCamParaUtil {
    private final CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private static MyCamParaUtil myCamPara = null;

    private MyCamParaUtil() {

    }

    public static MyCamParaUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new MyCamParaUtil();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    public Camera.Size getPreviewSize(List<Camera.Size> list, int th) {
        Collections.sort(list, sizeComparator);
        Camera.Size size = null;
        for (int i = 0; i < list.size(); i++) {
            size = list.get(i);
            if ((size.width > th) && equalRate(size, 1.3f)) {
                break;
            }
        }
        return size;
    }

    public Camera.Size getPictureSize(List<Camera.Size> list, int th) {
        Collections.sort(list, sizeComparator);
        Camera.Size size = null;
        for (int i = 0; i < list.size(); i++) {
            size = list.get(i);
            if ((size.width > th) && equalRate(size, 1.3f)) {
                break;
            }
        }
        return size;

    }

    public boolean equalRate(Camera.Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        if (Math.abs(r - rate) <= 0.2) {
            return true;
        } else {
            return false;
        }
    }

    public class CameraSizeComparator implements Comparator<Camera.Size> {
        //按升序排列
        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    //前置摄像头录完后视频倒置旋转
    public static byte[] rotateYUV420Degree180(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
        int i = 0;
        int count = 0;

        for (i = imageWidth * imageHeight - 1; i >= 0; i--) {
            yuv[count] = data[i];
            count++;
        }

        i = imageWidth * imageHeight * 3 / 2 - 1;
        for (i = imageWidth * imageHeight * 3 / 2 - 1; i >= imageWidth
                * imageHeight; i -= 2) {
            yuv[count++] = data[i - 1];
            yuv[count++] = data[i];
        }
        return yuv;
    }

    /**
     * 获取屏幕的宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}

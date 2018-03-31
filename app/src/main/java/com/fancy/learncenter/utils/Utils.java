package com.fancy.learncenter.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.fancy.learncenter.common.Constant;
import com.jph.takephoto.model.CropOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hyy on 2016/11/2.
 */
public class Utils {
    public static Uri getCutPicPath() {
        File tfile = new File(Constant.PIC_PATH);
        if (!tfile.exists()) {
            try {
                tfile.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
        return Uri.fromFile(new File(Constant.PIC_PATH + System.currentTimeMillis() + ".jpg"));
    }

    public static CropOptions getCutPicCropOptions() {
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");
        boolean withWonCrop = false;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(withWonCrop);

        return builder.create();
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) {

        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2pix(Context context, int dip) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (dm.density * (float) dip);
    }

    public static File getLiZiPicFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "/LiZi/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}

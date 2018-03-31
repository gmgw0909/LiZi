package com.fancy.learncenter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

import fancy.hyypaysdk.pay.Constant;

/**
 * Created by sww on 2016/10/24 09:23.
 */

public class WXShareUtil {

    private static IWXAPI api;
    private static WXShareUtil single = null;
    private static final int THUMB_SIZE = 150;
    private static Context context;

    private WXShareUtil(Context context) {
        initWX(context);
    }

    public static WXShareUtil getInstance(Context context) {

        if (single == null) {
            single = new WXShareUtil(context);
        }
        return single;
    }

    public void initWX(Context context) {

        api = WXAPIFactory.createWXAPI(context, Constant.APP_ID);
        api.registerApp(Constant.APP_ID);

    }

    /**
     * 分享网页
     * @param url
     * @param title
     * @param description
     * @param thumb 缩略图
     * @param isCircle 是：分享到朋友圈，否：分享给好友
     */

    public void shareWebPage(String url, String title, String description, Bitmap thumb,boolean isCircle) {



        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);

    }


    /**
     * 分享文本
     *
     * @param text
     * @param isCircle
     */
    public void shareText(String text, boolean isCircle) {
        WXTextObject object = new WXTextObject();
        object.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = object;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction(text);
        //分享到朋友圈，或是给好友
        req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }



    /**
     * 分享图片url
     *
     * @param url
     * @param isCircle
     */
    public void shareImageUrl(final String url, final boolean isCircle) {

        FrescoLoadUtil.getInstance().loadImageBitmap(url, new FrescoLoadUtil.FrescoBitmapCallback<Bitmap>() {
            @Override
            public void onSuccess(Uri uri, Bitmap result) {

                    WXImageObject imgObj = new WXImageObject(result);
                    imgObj.imageUrl = url;
                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;

                    Bitmap thumbBmp = Bitmap.createScaledBitmap(result, THUMB_SIZE, THUMB_SIZE, true);
                    result.recycle();

                    msg.thumbData = bmpToByteArray(thumbBmp, true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("img");
                    req.message = msg;
                    req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

                    api.sendReq(req);

            }

            @Override
            public void onFailure(Uri uri, Throwable throwable) {
                

            }

            @Override
            public void onCancel(Uri uri) {

            }
        });

    }




    /**
     * 加载图片id
     *
     * @param context
     * @param resId
     * @param isCircle
     */

    public void shareImageLocal(Context context, int resId, boolean isCircle) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();

        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    /**
     * 加载本地图片
     * @param context
     * @param path
     * @param isCircle
     */

    public void shareImagePath(Context context, String path, boolean isCircle) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtil.show("文件路径不存在！");
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imag");
        req.message = msg;
        req.scene = isCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }



    public byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}

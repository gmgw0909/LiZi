package com.fancy.learncenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.fancy.learncenter.adapter.GraphicAdapter;
import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.utils.DimenUtil;
import com.fancy.learncenter.utils.PermissionUtils;
import com.fancy.learncenter.utils.Utils;
import com.fancy.learncenter.view.LiZhiDialog;
import com.fancy.lizi.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseGraphicActivity extends TakePhotoActivity {

    private static final int ACCESS_LOCATION_RESULT_CODE = 1001;

    @Bind(R.id.next)
    TextView next;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.yibaocun)
    TextView yibaocun;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.biaoqing)
    ImageView biaoqing;
    @Bind(R.id.tupian)
    ImageView tupian;
    @Bind(R.id.paizhao)
    ImageView paizhao;
    @Bind(R.id.weizhi)
    ImageView weizhi;
    @Bind(R.id.jianpan)
    ImageView jianpan;
    @Bind(R.id.bottom_bar)
    RelativeLayout bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasegraphic);
        ButterKnife.bind(this);
        next.setText("下一步");

        initRecycle();
    }


    ArrayList<String> itemDatas;
    GraphicAdapter graphicAdapter;

    private void initRecycle() {
        itemDatas = new ArrayList<>();
        itemDatas.add("");
        graphicAdapter = new GraphicAdapter(this, itemDatas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(graphicAdapter);
        graphicAdapter.setPicCallBack(new GraphicAdapter.SetPicCallBack() {
            @Override
            public void picCallBack(int focusPosition, int position) {

            }
        });

        takePhoto = getTakePhoto();
    }

    TakePhoto takePhoto;

    @OnClick({R.id.back, R.id.next, R.id.tupian, R.id.weizhi, R.id.paizhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                LiZhiDialog.show(this, R.mipmap.bg_tuxing, "退出前，您需要保存吗？",
                        true, "不保存", "保存", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                LiZhiDialog.dismissDialog();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                LiZhiDialog.dismissDialog();
                            }
                        });
                break;
            case R.id.next:
                startActivity(new Intent(ReleaseGraphicActivity.this, ReleaseGraphicNextActivity.class));
                break;
            case R.id.tv_location:
                tvLocation.setVisibility(View.GONE);
                tvLocation.setText("");
                break;
            case R.id.delete:
//                imageTemp.setVisibility(View.GONE);
                break;
            case R.id.tuozhuai:
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.dip2px(180));
                layoutParams.setMargins(0, 0, 0, DimenUtil.dip2px(-90));//4个参数按顺序分别是左上右下
//                image.setLayoutParams(layoutParams);
                break;
            case R.id.biaoqing:
                break;
            case R.id.tupian:
                Uri imageUri = Uri.fromFile(Utils.getLiZiPicFile());
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                break;
            case R.id.paizhao:
                Uri uri = Uri.fromFile(Utils.getLiZiPicFile());
                takePhoto.onPickFromCaptureWithCrop(uri, getCropOptions());
                break;
            case R.id.weizhi:
                graphicAdapter.notifyDataSetChanged();
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);
                break;
            case R.id.jianpan:
                closeKeyBord(etTitle, this);
                break;
        }
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    //权限被允许（如果被拒绝的话就会提示去设置中打开权限）
                    startActivityForResult(new Intent(ReleaseGraphicActivity.this, MapLocationActivity.class), ACCESS_LOCATION_RESULT_CODE);
                    break;
            }
        }
    };

    /**
     * 定位权限授权回调
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    /**
     * 获取地图定位后回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACCESS_LOCATION_RESULT_CODE && resultCode == RESULT_OK) {
            String locationStr = data.getStringExtra(Constant.LOCATION_STRING);
            if (!TextUtils.isEmpty(locationStr)) {
                tvLocation.setVisibility(View.VISIBLE);
                tvLocation.setText(locationStr);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 关闭键盘
     */
    public static void closeKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    private CropOptions getCropOptions() {

        CropOptions.Builder builder = new CropOptions.Builder();
//        builder.setWithOwnCrop(false);
        return builder.create();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result.getImage() != null && !TextUtils.isEmpty(result.getImage().getPath())) {
            if (graphicAdapter.focusPosition == itemDatas.size()) {
                itemDatas.add(result.getImage().getPath());
            } else {
                itemDatas.add(graphicAdapter.focusPosition, result.getImage().getPath());
            }

            graphicAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 通过imageWidth 的宽度，自动适应高度
     * * @param simpleDraweeView view
     * * @param imagePath  Uri
     * * @param imageWidth width
     */
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
                layoutParams.width = imageWidth;
                layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
                simpleDraweeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).setUri(Uri.parse(imagePath)).build();
        simpleDraweeView.setController(controller);
    }
}

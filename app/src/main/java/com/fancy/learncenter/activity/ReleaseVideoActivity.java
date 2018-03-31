package com.fancy.learncenter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.common.Constant;
import com.fancy.learncenter.utils.PermissionUtils;
import com.fancy.learncenter.view.LiZhiDialog;
import com.fancy.learncenter.view.PopupWindowBiaoQian;
import com.fancy.lizi.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseVideoActivity extends BaseActivity {

    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.next)
    TextView next;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.image_temp)
    FrameLayout imageTemp;
    @Bind(R.id.video_img)
    ImageView videoImg;
    @Bind(R.id.add_video)
    ImageView addVideo;
    @Bind(R.id.framelayout_contain)
    FrameLayout framelayoutContain;
    @Bind(R.id.biaoqian)
    TextView tvBiaoQian;

    String videoPath;
    private static final int ACCESS_LOCATION_RESULT_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasevideo, Status.Default);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPath = getIntent().getStringExtra(Constant.VIDEO_PATH);
        if (!TextUtils.isEmpty(videoPath)) {
            imageTemp.setVisibility(View.VISIBLE);
            addVideo.setVisibility(View.GONE);
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(videoPath);
            Bitmap bitmap = media.getFrameAtTime();
            videoImg.setImageBitmap(bitmap);//显示视频第一帧
        }
    }

    @OnClick({R.id.back, R.id.next, R.id.tv_location, R.id.delete,
            R.id.video_img, R.id.biaoqian, R.id.add_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                LiZhiDialog.showWithOneBtn(this, R.mipmap.bg_chenggong, "恭喜您，发布成功！",
                        "立即查看", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                LiZhiDialog.dismissDialog();
                            }
                        });
                break;
            case R.id.add_video:
                startActivity(new Intent(ReleaseVideoActivity.this, VideoRecorderActivity.class));
                break;
            case R.id.delete:
                imageTemp.setVisibility(View.GONE);
                addVideo.setVisibility(View.VISIBLE);
                break;
            case R.id.video_img:
                startActivity(new Intent(ReleaseVideoActivity.this, PreviewVideoActivity.class)
                        .putExtra(Constant.VIDEO_PATH, videoPath));
                break;
            case R.id.tv_location:
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);
                break;
            case R.id.biaoqian:
                show();
                break;
        }
    }

    /**
     * 获取地图定位后回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESS_LOCATION_RESULT_CODE && resultCode == RESULT_OK) {
            String locationStr = data.getStringExtra(Constant.LOCATION_STRING);
            if (!TextUtils.isEmpty(locationStr)) {
                tvLocation.setText(locationStr);
            }
        }
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    //权限被允许（如果被拒绝的话就会提示去设置中打开权限）
                    startActivityForResult(new Intent(ReleaseVideoActivity.this, MapLocationActivity.class), ACCESS_LOCATION_RESULT_CODE);
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

    private void show() {
        PopupWindowBiaoQian biaoQian = new PopupWindowBiaoQian(this, framelayoutContain);
        biaoQian.showPopupWindow();
        biaoQian.setOnChooseItem(new PopupWindowBiaoQian.OnChooseItem() {
            @Override
            public void getItems(List<String> list) {
                if (list != null && list.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1) {
                            stringBuffer.append(list.get(i));
                        } else {
                            stringBuffer.append(list.get(i) + " | ");
                        }
                    }
                    tvBiaoQian.setText(stringBuffer.toString());
                } else {
                    tvBiaoQian.setText("请选择标签  >");
                }
            }
        });
    }
}

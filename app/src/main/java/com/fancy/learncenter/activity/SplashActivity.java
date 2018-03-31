package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.utils.TimeUtil;
import com.fancy.lizi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.backgroup)
    LinearLayout backgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash, Status.NAVIGATION);
        ButterKnife.bind(this);

        TimeUtil.interval(3, new TimeUtil.TimeCallBack() {
            @Override
            public void nextCallBack(long time) {
                timeTv.setText("跳过：" + time);

            }

            @Override
            public void completeCallBack() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void startCallBack() {

            }
        });

    }

//    @OnClick(R.id.backgroup)
//    public void onViewClicked() {
//
//    }

    /*
    *   Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("bizCode", "RoastingClubBanner");
        dataMap.put("appNo", "1001");
        dataMap.put("libraryNo", " ");
        dataMap.put("regionId", " ");
        dataMap.put("longitude", " ");
        dataMap.put("latitude", " ");
        HttpMehtod.getInstance().testNet(new IdeaObserver<BaseDataBean<Student>>(this, true) {


            @Override
            public void onSuccess(BaseDataBean<Student> studentBaseDataBean) {
                Student student = studentBaseDataBean.getData();

                ToastUtil.show("==我成功请求了==");
                LogUtil.MyLog("onSuccess", "======" + student.getAdvert().size());
            }

            @Override
            public void onFail(String code) {
                ToastUtil.show("==我请求错误==" + code);
            }
        }, dataMap);
    * */
}

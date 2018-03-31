package com.fancy.learncenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fancy.learncenter.activity.base.BaseActivity;
import com.fancy.learncenter.utils.DimenUtil;
import com.fancy.learncenter.utils.ToastUtil;
import com.fancy.learncenter.view.CircleImageView;
import com.fancy.learncenter.view.LPopWindow;
import com.fancy.lizi.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by LeeBoo on 2018/1/22.
 */

public class EditInfoActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iamge_user)
    CircleImageView iamgeUser;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.birthday)
    TextView birthday;

    SinglePicker<String> xingbie_picker;//性别选择器
    DatePicker datePicker;//生日选择器
    LPopWindow lPopWindow;//头像弹出框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ButterKnife.bind(this);
        title.setText("编辑个人资料");
        initPicker();
        initBirthDayPicker();
    }

    @OnClick({R.id.back, R.id.touxiang, R.id.xingming, R.id.lvnian,
            R.id.phone, R.id.qianming, R.id.xingbie, R.id.shengri})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.touxiang:
                initPicPopWindow(view);
                break;
            case R.id.xingming:
                break;
            case R.id.lvnian:
                break;
            case R.id.phone:
                break;
            case R.id.qianming:
                Intent intent = new Intent(EditInfoActivity.this, EditQianMingActivity.class);
                startActivity(intent);
                break;
            case R.id.xingbie:
                xingbie_picker.show();
                break;
            case R.id.shengri:
                datePicker.show();
                break;
        }
    }

    //头像弹出框
    private void initPicPopWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pupopwinodw_take_or_pick_pic, null);
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lPopWindow.dismiss();
            }
        });
        TextView choose = (TextView) contentView.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("选择相册");
            }
        });
        TextView camera = (TextView) contentView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("拍照");
            }
        });
        lPopWindow = new LPopWindow.Builder(EditInfoActivity.this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, DimenUtil.dip2px(220))
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .build()
                .showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //初始化选择器
    public void initPicker() {
        xingbie_picker = new SinglePicker<>(this, new String[]{"男", "女"});
        xingbie_picker.setCanLoop(true);
        xingbie_picker.setTopBackgroundColor(0xFFEEEEEE);
        xingbie_picker.setTopHeight(50);
        xingbie_picker.setTopLineColor(0xFF33B5E5);
        xingbie_picker.setTopLineHeight(1);
        xingbie_picker.setTitleText("请选择性别");
        xingbie_picker.setTitleTextColor(0xFF999999);
        xingbie_picker.setTitleTextSize(14);
        xingbie_picker.setOffset(1);
        xingbie_picker.setCancelTextColor(0xFF33B5E5);
        xingbie_picker.setCancelTextSize(15);
        xingbie_picker.setSubmitTextColor(0xFF33B5E5);
        xingbie_picker.setSubmitTextSize(15);
        xingbie_picker.setSelectedTextColor(getResources().getColor(R.color.colorPrimary));
        xingbie_picker.setUnSelectedTextColor(0xFF999999);
        LineConfig config = new LineConfig();
        config.setColor(getResources().getColor(R.color.colorPrimary));//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        xingbie_picker.setLineConfig(config);
        xingbie_picker.setItemWidth(200);
        xingbie_picker.setBackgroundColor(0xFFE1E1E1);
        final String sexStr = sex.getText().toString();
        if (!TextUtils.isEmpty(sexStr)) {
            xingbie_picker.setSelectedItem(sexStr);
        }
        xingbie_picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                sex.setText(item);
            }
        });
    }

    public void initBirthDayPicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        datePicker = new DatePicker(this);
        datePicker.setCanLoop(false);
        datePicker.setTopHeight(50);
        datePicker.setTopLineColor(0xFF33B5E5);
        datePicker.setTopLineHeight(1);
        datePicker.setCancelTextColor(0xFF33B5E5);
        datePicker.setCancelTextSize(15);
        datePicker.setSubmitTextColor(0xFF33B5E5);
        datePicker.setSubmitTextSize(15);
        datePicker.setWheelModeEnable(true);
        datePicker.setHeight(DimenUtil.dip2px(250));
        datePicker.setTopPadding(25);
        datePicker.setRangeStart(1899, 1, 1);
        datePicker.setRangeEnd(year, month, date);
        datePicker.setSelectedItem(1999, 9, 9);
        datePicker.setWeightEnable(true);
        datePicker.setBackgroundColor(0xFFE1E1E1);
        datePicker.setSelectedTextColor(getResources().getColor(R.color.colorPrimary));
        LineConfig config = new LineConfig();
        config.setColor(getResources().getColor(R.color.colorPrimary));//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        datePicker.setLineConfig(config);
        datePicker.setTitleTextColor(0xFF999999);
        datePicker.setTitleTextSize(14);
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                birthday.setText(year + "-" + month + "-" + day);
            }
        });
        datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                datePicker.setTitleText(year + "-" + datePicker.getSelectedMonth() + "-" + datePicker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                datePicker.setTitleText(datePicker.getSelectedYear() + "-" + month + "-" + datePicker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                datePicker.setTitleText(datePicker.getSelectedYear() + "-" + datePicker.getSelectedMonth() + "-" + day);
            }
        });
    }
}

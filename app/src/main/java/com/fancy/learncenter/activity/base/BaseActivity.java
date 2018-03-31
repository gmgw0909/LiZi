package com.fancy.learncenter.activity.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fancy.learncenter.common.StatusBarCompat;
import com.fancy.lizi.R;


/**
 * Created by Hyy on 2016/9/18.
 */
public class BaseActivity extends AppCompatActivity {
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutResID, int colot) {
        super.setContentView(layoutResID);
        StatusBarCompat.compat(this);
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setContentView(int layoutResID, Status Type) {
        this.setContentView(layoutResID);
        setType(Type);
    }


    public void setTitleName(String titleName) {
        toolbar.setVisibility(View.VISIBLE);
        if (titleTv != null) {
            titleTv.setText(titleName);
        }
    }

    TextView titleTv;
    Toolbar toolbar;

    public void initToolbar(String Title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            ImageView back = (ImageView) findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            titleTv = (TextView) findViewById(R.id.title);
            titleTv.setText(Title);
        }

    }

    public void toobarGone() {
        toolbar.setVisibility(View.GONE);
    }

    public void setType(Status type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            switch (type) {
                case NAVIGATION:
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    break;
                case STATUS:
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    break;
            }
        }
    }

    public enum Status {
        NAVIGATION, STATUS, Default
    }

    public TextView showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.dialog_loading, null);
        builder.setView(view);
        ProgressBar pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        TextView tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pb_loading.setIndeterminateTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        }
        tv_hint.setText("视频编译中");
        progressDialog = builder.create();
        progressDialog.show();
        return tv_hint;
    }

    public void closeProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

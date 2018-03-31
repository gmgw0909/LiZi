package com.fancy.learncenter.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fancy.learncenter.adapter.base.CommonRecycleViewAdapter;
import com.fancy.learncenter.adapter.base.CustomViewHold;
import com.fancy.learncenter.bean.Student;
import com.fancy.lizi.R;

import java.util.ArrayList;

//import com.umeng.soexample.R;

/**
 * Created by Hyy on 2016/9/18.
 */
public class TestAdapter extends CommonRecycleViewAdapter<Student> {
    Activity mContext;


    public TestAdapter(Activity mContext, int resource, ArrayList<Student> itemDatas) {
        super(mContext, resource, itemDatas);
        this.mContext = mContext;
    }

    // 根据初始化的layout ID，实现内部控件赋值以及功能
    @Override
    public void bindView(CustomViewHold customViewHold, Student item, int position) {

        RelativeLayout rl = customViewHold.getView(R.id.cardview);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showShareDilaog(mContext);
//                //扫描二维码
//                IntentIntegrator integrator = new IntentIntegrator(mContext);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//                integrator.setPrompt("测试");
//                integrator.setCameraId(0);  // Use a specific camera of the device
//                integrator.setBeepEnabled(false);
//                integrator.setBarcodeImageEnabled(true);
//                integrator.initiateScan();

            }
        });
    }



}

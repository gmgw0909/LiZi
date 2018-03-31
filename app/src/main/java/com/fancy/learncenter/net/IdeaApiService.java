package com.fancy.learncenter.net;

import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.bean.RegistDataBean;
import com.fancy.learncenter.bean.Student;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by hyy on 2017/12/1.
 * describe as
 */

public interface IdeaApiService {
    @POST("api/advert/list")
    Observable<BaseDataBean<Student>> getShowBanner(@QueryMap Map<String, Object> imgs);

    @POST("app/sms/register")
    Observable<BaseDataBean<String>> getYZM(@QueryMap Map<String, Object> imgs);

    @POST("app/doRegister")
    Observable<BaseDataBean<RegistDataBean>> register(@QueryMap Map<String, Object> imgs);

    @POST("app/login")
    Observable<BaseDataBean> login(@QueryMap Map<String, Object> imgs);

    @POST("app/index/posts")
    Observable<BaseDataBean> getPostDatas(@QueryMap Map<String, Object> imgs);
}

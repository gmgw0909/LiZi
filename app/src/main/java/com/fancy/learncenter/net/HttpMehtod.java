package com.fancy.learncenter.net;

import com.fancy.learncenter.bean.BaseDataBean;
import com.fancy.learncenter.common.Constant;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hyy on 2016/9/29.
 */
public class HttpMehtod {

    private Retrofit retrofit;
    private static HttpMehtod instance;

    //构造方法私有
    private HttpMehtod() {
    }

    //获取单例
    public static HttpMehtod getInstance() {
        if (instance == null) {
            instance = new HttpMehtod();
        }
        return instance;
    }


    public void testNet(IdeaObserver ideaObserver, Map map) {
        Observable observable = IdeaApi.getApiService().getShowBanner(map);
        subscibe(observable, ideaObserver);
    }


    private void subscibe(Observable observable, Observer ideaObserver) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ideaObserver);
    }

    /**
     * 获取验证码
     * mobile
     *
     * @param ideaObserver
     */
    public void getYZM(String mobile, IdeaObserver ideaObserver) {
        Map map = new HashMap();
        map.put("mobile", mobile);
        Observable observable = IdeaApi.getApiService().getYZM(map);
        subscibe(observable, ideaObserver);
    }


    public void register(Map map, IdeaObserver ideaObserver) {
        Observable observable = IdeaApi.getApiService().register(map);
        subscibe(observable, ideaObserver);
    }

    public void login(Map map, IdeaObserver ideaObserver) {
        Observable observable = IdeaApi.getApiService().login(map);
        subscibe(observable, ideaObserver);
    }

    public void getPost(Map map, IdeaObserver ideaObserver) {
        Observable observable = IdeaApi.getApiService().getPostDatas(map);
        subscibe(observable, ideaObserver);
    }

    public void getUser(IdeaObserver ideaObserver) {
        Observable observable = IdeaApi.getApiService().getUser();
        subscibe(observable, ideaObserver);
    }
}

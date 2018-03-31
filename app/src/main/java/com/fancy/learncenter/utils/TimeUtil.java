package com.fancy.learncenter.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by hyy on 2017/10/19.
 * describe as  计时器工具类
 */

public class TimeUtil {

    public static Disposable disposables;

    /**
     * 轮询器
     *
     * @param seconds
     */
    public static void interval(final long seconds, final TimeCallBack timeCallBack) {
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(seconds)
                .map(new Function<Long, Long>() {
                    /**
                     * Apply some calculation to the input value and return some other value.
                     *
                     * @param aLong the input value
                     * @return the output value
                     * @throws Exception on error
                     */
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return seconds - aLong - 1;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        timeCallBack.nextCallBack(seconds);
                        disposables = disposable;
                        timeCallBack.startCallBack();
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        timeCallBack.nextCallBack(number);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancleTime();
                        timeCallBack.completeCallBack();
                    }

                    @Override
                    public void onComplete() {
                        cancleTime();
                        timeCallBack.completeCallBack();
                    }
                });
    }

    /**
     * 倒计时
     *
     * @param seconds
     */
    public static void timer(long seconds, final TimeCallBack timeCallBack) {
        Observable.timer(seconds, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                        disposables = disposable;
                        timeCallBack.startCallBack();
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        timeCallBack.nextCallBack(number);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancleTime();
                        timeCallBack.completeCallBack();
                    }

                    @Override
                    public void onComplete() {
                        timeCallBack.completeCallBack();
                        cancleTime();
                    }
                });
    }

    public interface TimeCallBack {
        /**
         * 计时中
         */
        abstract void nextCallBack(long time);

        /**
         * 计时完成
         */
        abstract void completeCallBack();

        /**
         * 计时开始
         */
        abstract void startCallBack();
    }

    /**
     * 取消计时
     */
    public static void cancleTime() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}

package com.shidai.yunshang.networks;
import com.shidai.yunshang.intefaces.ActivityFinish;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/8.
 */

public class PosetSubscriber<T> {

    public Subscriber getSubscriber(final ResponseResultListener<T> listener){
        Subscriber subscriber = new Subscriber<ResponseParent<T>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.fialed("",e.getMessage());
            }

            @Override
            public void onNext(ResponseParent<T> httpResult) {
                if (httpResult.result_code.equals("1000")){
                    //成功
                    listener.success(httpResult.data);
                }else if (httpResult.result_code.equals("401")){
                    //身份验证失败
                    ToastUtil.showToast(httpResult.result_msg);
                    EventBus.getDefault().post(new ActivityFinish(true));
                }else{
                    ToastUtil.showToast(httpResult.result_msg);
                    listener.fialed(httpResult.result_code, httpResult.result_msg);
                }
            }
        };
        return subscriber;
    }
}

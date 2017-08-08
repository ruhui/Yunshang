package com.shidai.yunshang.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.RecommenderMsgResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBar;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：推荐人
 */
@EFragment(R.layout.fragment_recommend)
public class RecommendFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.edtPhone)
    EditText edtPhone;

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("推荐人");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

        });

        String recommener = SecurePreferences.getInstance().getString("USERRRECOMMENDER", "");
        edtPhone.setText(recommener);
    }



    @Click(R.id.button2)
    void submitData(){
        String phoneNum = edtPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNum) || !Tool.checkPhoneNum(phoneNum)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }

        showProgress();
        Subscriber subscriber = new PosetSubscriber<RecommenderMsgResponse>().getSubscriber(callback_recommend);
        UserManager.saveRecommender(phoneNum, subscriber);
    }

    ResponseResultListener callback_recommend = new ResponseResultListener<Integer>() {
        @Override
        public void success(Integer returnMsg) {
            closeProgress();
            ToastUtil.showToast("修改成功");
            /*重置用户信息*/
            EventBus.getDefault().post(new RefreshListener(true, "userrefresh"));
            finishFragment();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}

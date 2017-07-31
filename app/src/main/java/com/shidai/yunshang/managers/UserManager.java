package com.shidai.yunshang.managers;

import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.ZZCHeaders;
import com.shidai.yunshang.networks.requests.BandDeleteRequest;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.requests.RegistRequest;
import com.shidai.yunshang.networks.requests.SaveCreditResquest;
import com.shidai.yunshang.networks.requests.SendRegsmsRequest;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.networks.responses.BillprofitResponse;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.RegistResponse;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.networks.responses.SortResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import com.shidai.yunshang.utils.SecurePreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
    *
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/10 11:57
   **/

public class UserManager {


    /**
     * 登录接口
     */
    public static void login(String teltphone, String password, Subscriber<ResponseParent<LoginResponse>> subscriber){
        LoginRequest loginResult = new LoginRequest(teltphone,password);

        ZZCHeaders zzcHeaders = new ZZCHeaders(loginResult);
        ApiClient.getApiService().login(loginResult, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取验证码
     * getregsms
     */
    public static void getRegsms(String mobile, EnumSendUserType userType, Subscriber<ResponseParent<Boolean>> subscriber){
        SendRegsmsRequest sandCode = new SendRegsmsRequest(mobile, userType);

        ZZCHeaders zzcHeaders = new ZZCHeaders( sandCode);
        ApiClient.getApiService().getregsms(sandCode, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 注册
     * @param request
     * @param subscriber
     */
    public static void RegistUser(RegistRequest request, Subscriber<ResponseParent<RegistResponse>> subscriber) {

        ZZCHeaders zzcHeaders = new ZZCHeaders(request);
        ApiClient.getApiService().register(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取用户资料
     * @param subscriber
     */
    public static void getUsermsg(Subscriber<ResponseParent<UsermsgResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getusermsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 公告总数和第一条
     * @param subscriber
     */
    public static void getBulletin(Subscriber<ResponseParent<BulletinResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getbulletin(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 系统消息
     * @param subscriber
     */
    public static void getsystemmsg(int curturnpage, Subscriber<ResponseParent<SystemResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(curturnpage));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getsystemmsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取银行卡信息
     * @param CardType 1信用卡  2借记卡
     * @param curturnpage 当前页面
     * @param subscriber
     */
    public static void getbankmsg(int CardType, int curturnpage, Subscriber<ResponseParent<BankmsgResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("CardType", String.valueOf(CardType));
        hashmap.put("page", String.valueOf(curturnpage));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getbankmsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除银行卡
     * @param bankid 卡id
     * @param subscriber
     */
    public static void deleteBank(int bankid, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        BandDeleteRequest request = new BandDeleteRequest(bankid);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().bankDelete(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取钱包
     * @param subscriber
     */
    public static void getBillbag(Subscriber<ResponseParent<BulletinResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBillbag(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 升级浏览等级的费率和结算
     * @param subscriber
     */
    public static void getShowup(Subscriber<ResponseParent<List<ShowupResponse>>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getShowup(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 收益
     * @param subscriber
     */
    public static void getBillprofit(Subscriber<ResponseParent<BillprofitResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBillprofit(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 排行榜 1同级排行，2江湖排行.
     * @param id
     * @param subscriber
     */
    public static void getSorter(String id, Subscriber<ResponseParent<List<SortResponse>>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getSorter(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 根据银行卡好获取银行信息
     * @param account_no 卡号
     * @param subscriber
     */
    public static void getBankMsg(String account_no, Subscriber<ResponseParent<BankCodeAndNameResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("account_no", account_no);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBankMsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 编辑添加信用卡
     * @param resquest
     * @param subscriber
     */
    public static void saveCredit(SaveCreditResquest resquest, Subscriber<ResponseParent<LoginResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, resquest);
        ApiClient.getApiService().saveCredit(resquest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

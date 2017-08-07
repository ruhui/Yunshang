package com.shidai.yunshang.managers;

import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.ZZCHeaders;
import com.shidai.yunshang.networks.requests.BandDeleteRequest;
import com.shidai.yunshang.networks.requests.IdRequest;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.requests.PhotoRequest;
import com.shidai.yunshang.networks.requests.RefreshUserResquest;
import com.shidai.yunshang.networks.requests.RegistRequest;
import com.shidai.yunshang.networks.requests.SaveCreditResquest;
import com.shidai.yunshang.networks.requests.SaveDebitRequest;
import com.shidai.yunshang.networks.requests.SendRegsmsRequest;
import com.shidai.yunshang.networks.requests.TransferRequest;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.networks.responses.BillprofitResponse;
import com.shidai.yunshang.networks.responses.BranchBankResponse;
import com.shidai.yunshang.networks.responses.BulletinDataResponst;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.CityResponse;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.MechantListResponse;
import com.shidai.yunshang.networks.responses.MerchantDetailResponse;
import com.shidai.yunshang.networks.responses.RegistResponse;
import com.shidai.yunshang.networks.responses.SettletypeResponse;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.networks.responses.SortResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import com.shidai.yunshang.networks.responses.VersionResponst;
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
     * 刷新登录
     * @param subscriber
     */
    public static void refreshLogin(Subscriber<ResponseParent<LoginResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        RefreshUserResquest request = new RefreshUserResquest(Authorization);

        ZZCHeaders zzcHeaders = new ZZCHeaders(request);
        ApiClient.getApiService().refreshLogin(request, zzcHeaders.getHashMap())
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
     * @param cardType 1信用卡  2借记卡  卡类型 借记（储蓄卡）：DC；贷记（信用卡）：CC
     * @param curturnpage 当前页面
     * @param subscriber
     */
    public static void getbankmsg(int cardType, int curturnpage, Subscriber<ResponseParent<BankmsgResponse>> subscriber) {
        String card_type = "CC";
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        if (cardType == 1){
            card_type = "CC";
        }else{
            card_type = "DC";
        }
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("card_type", card_type);
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

    /**
     * 编辑添加银行卡
     * @param resquest
     * @param subscriber
     */
    public static void saveDebit(SaveDebitRequest resquest, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, resquest);
        ApiClient.getApiService().saveDebit(resquest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取省市县
     * @param subscriber
     */
    public static void getRegions(Subscriber<ResponseParent<List<CityResponse>>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getRegions(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取银行卡支行
     * @param bank_code
     * @param name
     * @param province_id
     * @param city_id
     * @param page
     * @param subscriber
     */
    public static void getBranchbank(String bank_code, String name, String province_id, String city_id, int page, Subscriber<ResponseParent<BranchBankResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("bank_code", bank_code);
        hashmap.put("name", name);
        hashmap.put("province_id", province_id);
        hashmap.put("city_id", city_id);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBranchbank(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 保存图片
     * @param photo
     * @param subscriber
     */
    public static void saveHeadPhoto(String photo, Subscriber<ResponseParent<LoginResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        PhotoRequest resquest = new PhotoRequest(photo);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, resquest);
        ApiClient.getApiService().saveHeadPhoto(resquest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**getsystemmsg
     * @param curturnPage
     * @param subscriber
     */
    public static void getBulletins(int curturnPage, Subscriber<ResponseParent<BulletinDataResponst>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(curturnPage));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBulletins(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置已读
     * @param ids
     * @param subscriber
     */
    public static void setread(List<Integer> ids, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        IdRequest addCarFrom = new IdRequest(ids);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().setRead(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除消息
     * @param ids
     * @param subscriber
     */
    public static void deleteMessage(List<Integer> ids, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        IdRequest addCarFrom = new IdRequest(ids);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().deleteMessage(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 相关版本
     * @param subscriber
     */
    public static void getVersion(Subscriber<ResponseParent<VersionResponst>> subscriber) {
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        ApiClient.getApiService().getVersion(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 钱包提现的结算方式
     * @param subscriber
     */
    public static void getSettletype(Subscriber<ResponseParent<List<SettletypeResponse>>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getSettletype(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 余额、收益提现
     * @param amount 提现金额
     * @param settle_type 结算类型：T0,T1,默认用T1
     * @param type 提现类型：1余额，2分润
     * @param subscriber
     */
    public static void setTransfer(double amount, String settle_type, int type, Subscriber<ResponseParent<TransferResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        TransferRequest addCarFrom = new TransferRequest(amount, settle_type, type);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().setTransfer(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的商户列表
     * @param grade_id
     * @param username
     * @param type
     * @param curturnpage
     * @param subscriber
     */
    public static void getMechantList(String grade_id, String username, String type, int curturnpage, Subscriber<ResponseParent<MechantListResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("grade_id", grade_id);
        hashmap.put("q", username);
        hashmap.put("type", type);
        hashmap.put("page", String.valueOf(curturnpage));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getMechantList(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取商家详情
     * @param mechantid
     * @param subscriber
     */
    public static void getMerchantDetail(int mechantid, Subscriber<ResponseParent<MerchantDetailResponse>> subscriber) {
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(mechantid));

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getMerchantDetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

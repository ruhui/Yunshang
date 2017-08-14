package com.shidai.yunshang.managers;

import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.requests.BandDeleteRequest;
import com.shidai.yunshang.networks.requests.CreatOrderRequest;
import com.shidai.yunshang.networks.requests.ForgetPwdRequest;
import com.shidai.yunshang.networks.requests.IdRequest;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.requests.PhotoRequest;
import com.shidai.yunshang.networks.requests.QuickPayCodeRequest;
import com.shidai.yunshang.networks.requests.RefreshUserResquest;
import com.shidai.yunshang.networks.requests.RegistRequest;
import com.shidai.yunshang.networks.requests.SaveCreditResquest;
import com.shidai.yunshang.networks.requests.SaveDebitRequest;
import com.shidai.yunshang.networks.requests.SelectCardRequest;
import com.shidai.yunshang.networks.requests.SelectchannelRequest;
import com.shidai.yunshang.networks.requests.SendRegsmsRequest;
import com.shidai.yunshang.networks.requests.TransferRequest;
import com.shidai.yunshang.networks.requests.UpdateGradeSelectCard;
import com.shidai.yunshang.networks.responses.BillListResponse;
import com.shidai.yunshang.networks.responses.CreatOrderResponse;
import com.shidai.yunshang.networks.responses.CreatQcodeResponse;
import com.shidai.yunshang.networks.responses.HomeAdResponse;
import com.shidai.yunshang.networks.responses.MechantListResponse;
import com.shidai.yunshang.networks.responses.MerchantDetailResponse;
import com.shidai.yunshang.networks.responses.RecommenderMsgResponse;
import com.shidai.yunshang.networks.responses.RecommenderRequest;
import com.shidai.yunshang.networks.responses.SelectCardResponse;
import com.shidai.yunshang.networks.responses.SettletypeResponse;
import com.shidai.yunshang.networks.responses.ShoukuanDetailResponse;
import com.shidai.yunshang.networks.responses.TixianDetailResponse;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.networks.responses.VersionResponst;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.networks.responses.BillbagResponse;
import com.shidai.yunshang.networks.responses.BillprofitResponse;
import com.shidai.yunshang.networks.responses.BranchBankResponse;
import com.shidai.yunshang.networks.responses.BulletinDataResponst;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.CityResponse;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.RegistResponse;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.networks.responses.SortResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:57
 **/
public interface ZZCService {
    /*登录*/
    @POST("account/login")
    Observable<ResponseParent<LoginResponse>> login(@Body LoginRequest httpClient, @HeaderMap Map<String, String> header);

    /*注册*/
    @POST("account/register")
    Observable<ResponseParent<RegistResponse>> register(@Body RegistRequest httpClient, @HeaderMap Map<String, String> header);

    /*刷新登录*/
    @POST("account/refresh")
    Observable<ResponseParent<LoginResponse>> refreshLogin(@Body RefreshUserResquest httpClient, @HeaderMap Map<String, String> header);


    /*获取验证码*/
    @POST("common/regsms")
    Observable<ResponseParent<Boolean>> getregsms(@Body SendRegsmsRequest sendCode, @HeaderMap Map<String, String> header);

    /*获取我的资料*/
    @GET("account/user")
    Observable<ResponseParent<UsermsgResponse>> getusermsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*公告总数和第一条*/
    @GET("msg/bulletin")
    Observable<ResponseParent<BulletinResponse>> getbulletin(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*系统消息*/
    @GET("msg/system")
    Observable<ResponseParent<SystemResponse>> getsystemmsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取我的银行卡信息*/
    @GET("account/bank")
    Observable<ResponseParent<BankmsgResponse>> getbankmsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*删除银行卡*/
    @POST("account/bank_delete")
    Observable<ResponseParent<Boolean>> bankDelete(@Body BandDeleteRequest request, @HeaderMap Map<String, String> header);

    /*钱包*/
    @GET("account/bill_bag")
    Observable<ResponseParent<BillbagResponse>> getBillbag(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*升级浏览等级的费率和结算*/
    @GET("account/show_up")
    Observable<ResponseParent<List<ShowupResponse>>> getShowup(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*收益*/
    @GET("account/bill_profit")
    Observable<ResponseParent<BillprofitResponse>> getBillprofit(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*排行*/
    @GET("account/sort_er")
    Observable<ResponseParent<List<SortResponse>>> getSorter(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*根据银行卡号获取银行信息*/
    @GET("account/get_bank")
    Observable<ResponseParent<BankCodeAndNameResponse>> getBankMsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*新增、编辑信用卡*/
    @POST(" account/credit_save")
    Observable<ResponseParent<Boolean>> saveCredit(@Body SaveCreditResquest httpClient, @HeaderMap Map<String, String> header);

    /*新增、编辑银行卡*/
    @POST(" account/debit_save")
    Observable<ResponseParent<Boolean>> saveDebit(@Body SaveDebitRequest httpClient, @HeaderMap Map<String, String> header);

    /*获取省市县*/
    @GET("common/regions")
    Observable<ResponseParent<List<CityResponse>>> getRegions(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取支行*/
    @GET("account/branch_bank")
    Observable<ResponseParent<BranchBankResponse>> getBranchbank(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*保存头像*/
    @POST("account/photo")
    Observable<ResponseParent<Boolean>> saveHeadPhoto(@Body PhotoRequest photo, @HeaderMap Map<String, String> header);

    /*公告*/
    @GET("msg/bulletins")
    Observable<ResponseParent<BulletinDataResponst>> getBulletins(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*设置已读*/
    @POST("msg/read")
    Observable<ResponseParent<Boolean>> setRead(@Body IdRequest request, @HeaderMap Map<String, String> header);

    /*删除消息*/
    @POST("msg/del")
    Observable<ResponseParent<Boolean>> deleteMessage(@Body IdRequest request, @HeaderMap Map<String, String> header);

    /*相关版本*/
    @GET("common/version")
    Observable<ResponseParent<VersionResponst>> getVersion(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*提现结算方式*/
    @GET("account/settle_type")
    Observable<ResponseParent<List<SettletypeResponse>>> getSettletype(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*余额、收益提现POST */
    @POST("account/transfer")
    Observable<ResponseParent<TransferResponse>> setTransfer(@Body TransferRequest request, @HeaderMap Map<String, String> header);

    /*获取我的商户列表*/
    @GET("account/merchant")
    Observable<ResponseParent<MechantListResponse>> getMechantList(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取商户详情*/
    @GET("account/get_merchant")
    Observable<ResponseParent<MerchantDetailResponse>> getMerchantDetail(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取推荐人*/
    @GET("account/recommender")
    Observable<ResponseParent<RecommenderMsgResponse>> getRecommender(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*保存推荐人*/
    @POST("account/save_recommender")
    Observable<ResponseParent<Boolean>> saveRecommender(@Body RecommenderRequest request, @HeaderMap Map<String, String> header);

    /*创建订单*/
    @POST("charge/create_order")
    Observable<ResponseParent<CreatOrderResponse>> createOrder(@Body CreatOrderRequest request, @HeaderMap Map<String, String> header);

    /*选择通道*/
    @POST("charge/select_channel")
    Observable<ResponseParent<Boolean>> selectChannel(@Body SelectchannelRequest request, @HeaderMap Map<String, String> header);

    /*选择银行卡*/
    @POST("charge/select_card")
    Observable<ResponseParent<SelectCardResponse>> selectCard(@Body SelectCardRequest request, @HeaderMap Map<String, String> header);

    /*支付获取验证码*/
    @POST("charge/get_code")
    Observable<ResponseParent<Boolean>> getCode(@HeaderMap Map<String, String> header);

    /*快捷支付*/
    @POST("charge/quick_pay")
    Observable<ResponseParent<Double>> quickPay(@Body QuickPayCodeRequest request, @HeaderMap Map<String, String> header);

    /*账单列表*/
    @GET("account/bills")
    Observable<ResponseParent<BillListResponse>> getBillList(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*提现详情*/
    @GET("account/transfer")
    Observable<ResponseParent<TixianDetailResponse>> getTixianDetail(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*收款详情*/
    @GET("account/receipt")
    Observable<ResponseParent<ShoukuanDetailResponse>> getReceiptDetail(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*忘记密码*/
    @POST("account/forget")
    Observable<ResponseParent<Boolean>> forgetPwd(@Body ForgetPwdRequest request, @HeaderMap Map<String, String> header);

    /*选择通道生成微信/支付宝二维码*/
    @POST("charge/create_qr")
    Observable<ResponseParent<CreatQcodeResponse>> createQrode(@Body SelectchannelRequest request, @HeaderMap Map<String, String> header);

    /*切换支付方式，二维码*/
    @GET("charge/select_pay")
    Observable<ResponseParent<String>> selectPay(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*升级选择银行卡*/
    @POST("upgrade/select_card")
    Observable<ResponseParent<SelectCardResponse>> upgradeSelectCard(@Body UpdateGradeSelectCard request, @HeaderMap Map<String, String> header);

    /*升级用到的发送验证码*/
    @POST("upgrade/get_code")
    Observable<ResponseParent<Boolean>> upgradeGetCode(@HeaderMap Map<String, String> header);

    /*升级的快捷支付*/
    @POST("upgrade/quick_pay")
    Observable<ResponseParent<Double>> upgradequickpay(@Body String sms_code, @HeaderMap Map<String, String> header);

    /*首页广告*/
    @GET("ad/gets")
    Observable<ResponseParent<List<HomeAdResponse>>> homeGets(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

}

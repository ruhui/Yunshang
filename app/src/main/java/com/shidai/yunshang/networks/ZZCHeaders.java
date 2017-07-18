package com.shidai.yunshang.networks;

import android.text.TextUtils;

import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.networks.requests.EmptyRequest;
import com.shidai.yunshang.utils.Tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:40
 **/
public class ZZCHeaders<T> {
    /*令牌,除注册、登录、退出、找回密码等开放接口外，均需传递本参数 Header中增加Authorization参数，值是Bearer access_token值（注意Bearer后面是一个空格是必须的）*/
    public String Authorization;
    /* 时间，格式为"2017-04-14 13:58:43"*/
    private String timestamp;
    /*随机数，8位*/
    private String nonce;
    /*合作号，公钥*/
    private String partner = Constant.PARTNER;
    /*签名ca0040d3c96fe869ee252f98ad46660a*/
    private String sign;
    /* ContentType方式application/json*/
    private String ContentType="";
    /*私钥*/
    private String privateKey = "5386f3b100713b5b4daf957a11f34743c77f522ffc353f6c3c4672274fd7b67d";

    private T reqData;
    /*头部需要的字段*/
    private Map<String,String> hashMap;
    /*get构造参数*/
    private Map<String,String> paraMap;

    /*post不需要授权*/
    public ZZCHeaders(T reqData) {
        ContentType = "application/json";
        hashMap = new HashMap<>();
        this.reqData =reqData;
        nonce = getStringRandom(8);
        timestamp = getDate();
        sign = getsignPost();

        hashMap.put("timestamp",timestamp);
        hashMap.put("nonce",nonce);
        hashMap.put("partner",partner);
        hashMap.put("sign",sign);
        hashMap.put("ContentType",ContentType);
    }

    /*post需要授权*/
    public ZZCHeaders(String authorization,T reqData) {
        ContentType = "application/json";
        hashMap = new HashMap<>();
        this.Authorization = "Bearer "+authorization;
        this.reqData =reqData;
        nonce = getStringRandom(8);
        timestamp = getDate();
        sign = getsignPost();

        hashMap.put("timestamp",timestamp);
        hashMap.put("nonce",nonce);
        hashMap.put("partner",partner);
        hashMap.put("sign",sign);
        hashMap.put("ContentType",ContentType);
        hashMap.put("Authorization",Authorization);
    }

    /*get授权*/
    public ZZCHeaders(String authorization, Map<String,String> parater) {
        paraMap = parater;
        hashMap = new HashMap<>();
        this.Authorization = "Bearer "+authorization;
        nonce = getStringRandom(8);
        timestamp = getDate();

        sign = getsignPost();

        hashMap.put("Authorization",Authorization);
        hashMap.put("timestamp",timestamp);
        hashMap.put("nonce",nonce);
        hashMap.put("partner",partner);
        hashMap.put("sign",sign);
    }


    /*get不需要授权*/
    public ZZCHeaders(Map<String,String> parater) {
        paraMap = parater;
        hashMap = new HashMap<>();
        nonce = getStringRandom(8);
        timestamp = getDate();
        sign = getsignPost();

        hashMap.put("timestamp",timestamp);
        hashMap.put("nonce",nonce);
        hashMap.put("partner",partner);
        hashMap.put("sign",sign);
    }



    public Map<String, String> getHashMap() {
        return hashMap;
    }

    /**
     * 获取随机数
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 获取当前日期
     */
    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取签名,post方式
     * @return
     */
    public String getsignPost() {
        String md5sign = "";
        if (!TextUtils.isEmpty(ContentType) || ContentType.equals("application/json")){
            //post方式传送，用json格式
            if (reqData instanceof EmptyRequest){
                md5sign += privateKey+timestamp+nonce;
            }else{
                md5sign += Tool.getGson(reqData)  + privateKey+timestamp+nonce;
            }
        }else{
           if (paraMap == null){
               return "";
           }
            LinkedHashMap<Object,String> sortpara = new LinkedHashMap<>();
            Object[] key_arr =  paraMap.keySet().toArray();
            Arrays.sort(key_arr);
            for  (Object key : key_arr) {
                sortpara.put(key, paraMap.get(key));
            }

            Iterator iter = sortpara.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                if (TextUtils.isEmpty(md5sign)){
                    md5sign = key+"="+val;
                }else{
                    md5sign += "&" + key+"="+val;
                }
            }
            md5sign += privateKey+timestamp+nonce;
        }

        return getMd5(md5sign);
    }

    //md5加
    private static String getMd5(String plainText) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());
            // 获得密文
            byte b[] = md.digest();
            // 把密文转换成十六进制的字符串形式
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

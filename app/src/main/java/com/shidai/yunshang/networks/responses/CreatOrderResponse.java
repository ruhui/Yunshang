package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.ChannelModel;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 10:50
 **/
public class CreatOrderResponse implements Serializable{
    private String order_no;
    private double amount;
    private int pay_type;
    private String pay_code;
    private String pay_name;
    private List<ChannelModel> channel;
    private String notify;
    private int pay_channel;
    private String settle_type;
    private List<String> props;

    public CreatOrderResponse(String order_no, double amount, int pay_type, String pay_code, String pay_name, List<ChannelModel> channel, String notify, int pay_channel, String settle_type, List<String> props) {
        this.order_no = order_no;
        this.amount = amount;
        this.pay_type = pay_type;
        this.pay_code = pay_code;
        this.pay_name = pay_name;
        this.channel = channel;
        this.notify = notify;
        this.pay_channel = pay_channel;
        this.settle_type = settle_type;
        this.props = props;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public List<ChannelModel> getChannel() {
        return channel;
    }

    public void setChannel(List<ChannelModel> channel) {
        this.channel = channel;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public int getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(int pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(String settle_type) {
        this.settle_type = settle_type;
    }

    public List<String> getProps() {
        return props;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }
}
//"order_no": "sample string 1",
//        "amount": 2,
//        "pay_type": 1,
//        "pay_code": "sample string 3",
//        "pay_name
//"notify": "sample string 4",
//        "pay_channel": 0,
//        "settle_type": "sample string 5",
//        "props":
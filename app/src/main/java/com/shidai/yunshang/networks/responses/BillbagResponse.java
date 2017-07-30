package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.BillbagModel;

import java.util.List;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class BillbagResponse  {
    private double deposit;//余额可提现
    private double total_receipt;
    private List<BillbagModel> payments;

    public BillbagResponse(double deposit, double total_receipt, List<BillbagModel> payments) {
        this.deposit = deposit;
        this.total_receipt = total_receipt;
        this.payments = payments;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getTotal_receipt() {
        return total_receipt;
    }

    public void setTotal_receipt(double total_receipt) {
        this.total_receipt = total_receipt;
    }

    public List<BillbagModel> getPayments() {
        return payments;
    }

    public void setPayments(List<BillbagModel> payments) {
        this.payments = payments;
    }
}

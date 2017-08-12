package com.shidai.yunshang.networks.requests;

/**
 * 创建时间： 2017/8/12.
 * 作者：黄如辉
 * 功能描述：
 */

public class UpdateGradeSelectCard {
    private String grade_id;
    private String bank_id;
    private String amount;

    public UpdateGradeSelectCard(String grade_id, String bank_id, String amount) {
        this.grade_id = grade_id;
        this.bank_id = bank_id;
        this.amount = amount;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

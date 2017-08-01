package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.BranchbrankModel;

import java.util.List;

/**
 * 创建时间： 2017/8/1.
 * 作者：黄如辉
 * 功能描述：
 */

public class BranchBankResponse {
    private int page;
    private int size;
    private double total_pages;
    private double total_result;
    private List<BranchbrankModel> rows;

    public BranchBankResponse(int page, int size, double total_pages, double total_result, List<BranchbrankModel> rows) {
        this.page = page;
        this.size = size;
        this.total_pages = total_pages;
        this.total_result = total_result;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(double total_pages) {
        this.total_pages = total_pages;
    }

    public double getTotal_result() {
        return total_result;
    }

    public void setTotal_result(double total_result) {
        this.total_result = total_result;
    }

    public List<BranchbrankModel> getRows() {
        return rows;
    }

    public void setRows(List<BranchbrankModel> rows) {
        this.rows = rows;
    }
}
//"page": 1,
//        "size": 2,
//        "total_pages": 2,
//        "total_result": 3,
//        "rows":
package com.shidai.yunshang.networks.requests;

import java.util.List;

/**
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */

public class IdRequest {
    public List<Integer> ids;

    public IdRequest(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}

package com.shidai.yunshang.managers;

import com.shidai.yunshang.R;

public class BankManager {

    public static int getDrawable(String bankCode){
        if (bankCode.equals("ABC")){
            //中国农业银行
            return R.drawable.icon_ban_ny;
        }else if (bankCode.equals("BCM")){
            // 交通银行
            return R.drawable.icon_ban_jt;
        }else if (bankCode.equals("BOC")){
            // 中国银行
            return R.drawable.icon_ban_china;
        } else if (bankCode.equals("CCB")){
            // 中国建设银行
            return R.drawable.icon_ban_js;
        }else if (bankCode.equals("CEB")){
            // 中国光大银行
            return R.drawable.icon_ban_gd;
        }else if (bankCode.equals("CGB")){
            // 广东发展银行
            return R.drawable.icon_ban_gf;
        }else if (bankCode.equals("CIB")){
            // 兴业银行
            return R.drawable.icon_ban_xy;
        } else if (bankCode.equals("CITIC")){
            // 中信银行
            return R.drawable.icon_ban_xy;
        }else if (bankCode.equals("CMB")){
            // 招商银行
            return R.drawable.icon_ban_zs;
        }else if (bankCode.equals("CMBC")){
            // 中国民生银行
            return R.drawable.icon_ban_ms;
        }else if (bankCode.equals("HXB")){
            // 华夏银行
            return R.drawable.icon_ban_hx;
        }else if (bankCode.equals("ICBC")){
            //中国工商银行
            return R.drawable.icon_ban_gs;
        }else if (bankCode.equals("PSBC")){
            //中国邮政储蓄银行
            return R.drawable.icon_ban_yz;
        }else if (bankCode.equals("SPDB")){
            //上海浦东发展银行
            return R.drawable.icon_ban_pf;
        }else{
            //平安银行
            return R.drawable.icon_ban_pa;
        }
    }
}

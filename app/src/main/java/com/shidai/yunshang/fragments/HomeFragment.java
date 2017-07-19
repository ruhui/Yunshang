package com.shidai.yunshang.fragments;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.NavBar;
import com.shidai.yunshang.view.widget.PicTextView45;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/18.
 * 作者：黄如辉
 * 功能描述：
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar mNavbar;
    @ViewById(R.id.picText1)
    PicTextView45 picText1;
    @ViewById(R.id.picText2)
    PicTextView45 picText2;
    @ViewById(R.id.picText3)
    PicTextView45 picText3;
    @ViewById(R.id.picText4)
    PicTextView45 picText4;

    @ViewById(R.id.picText5)
    PicTextView45 picText5;
    @ViewById(R.id.picText6)
    PicTextView45 picText6;
    @ViewById(R.id.picText7)
    PicTextView45 picText7;
    @ViewById(R.id.picText8)
    PicTextView45 picText8;


    @AfterViews
    void initView(){
        mNavbar.setMiddleText(R.string.home_tabtitle);
        picText1.setTextView(R.string.home_sy_qywsc);picText1.setImageResource(R.drawable.sy_qywsc);
        picText2.setTextView(R.string.home_sy_zbsc);picText2.setImageResource(R.drawable.sy_zbsc);
        picText3.setTextView(R.string.home_sy_wyfs);picText3.setImageResource(R.drawable.sy_wyfs);
        picText4.setTextView(R.string.home_sy_zqjy);picText4.setImageResource(R.drawable.sy_zqjy);

        picText5.setTextView(R.string.home_sy_wysj);picText5.setImageResource(R.drawable.sy_wysj);
        picText6.setTextView(R.string.home_sy_syph);picText6.setImageResource(R.drawable.sy_syph);
        picText7.setTextView(R.string.home_sy_wdkf);picText7.setImageResource(R.drawable.sy_wdkf);
        picText8.setTextView(R.string.home_sy_gd);picText8.setImageResource(R.drawable.sy_gd);
    }
}

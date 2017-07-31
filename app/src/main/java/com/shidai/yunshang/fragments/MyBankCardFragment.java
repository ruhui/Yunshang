package com.shidai.yunshang.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.SelectBankCardAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.BankFragmentRefresh;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.intefaces.SelectBankListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BankmsgModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.pulltorefresh.PullToRefreshBase;
import com.shidai.yunshang.view.pulltorefresh.PullToRefreshScrollView;
import com.shidai.yunshang.view.widget.DefaultItemTouchHelpCallback;
import com.shidai.yunshang.view.widget.DefaultItemTouchHelper;
import com.shidai.yunshang.view.widget.NavBarSwitch;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：我的银行卡
 */
@EFragment(R.layout.fragment_mybankcrd)
public class MyBankCardFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarSwitch mNavbar;
    @ViewById(R.id.mSwipeMenuRecycleView)
    SwipeMenuRecyclerView recyclerView ;

    @ViewById(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;

    private SelectBankCardAdapter adapter_brankcard;
    private List<Object> listmodel = new ArrayList<>();
    private boolean switchBank = false;
    private int cardType = 1;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private int adapterPositions = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @AfterViews
    protected void initView() {
        mNavbar.setOnMenuClickListener(new NavBarSwitch.OnMenuClickListener() {

            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                finishFragment();
            }

            @Override
            public void onXyMenuClick(View view) {
                super.onXyMenuClick(view);
                switchBank = false;
                cardType = 1;
                adapter_brankcard.setBottom("+ 添加另一张信用卡", 0);
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getBankMsg();
            }

            @Override
            public void onBankMenuClick(View view) {
                super.onBankMenuClick(view);
                switchBank = true;
                cardType = 2;
                adapter_brankcard.setBottom("+ 添加另一张银行卡", 1);
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getBankMsg();
            }
        });

        adapter_brankcard = new SelectBankCardAdapter(getActivity(), listmodel, "+ 添加另一张信用卡", selectBankListener);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        recyclerView.setAdapter(adapter_brankcard);

        getBankMsg();

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                PullToRefreshBase.Mode mode = refreshView.getCurrentMode();
                if (mode == PullToRefreshBase.Mode.PULL_FROM_START){
                    //上拉刷新数据
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    listmodel.clear();
                    getBankMsg();
                }else{
                    CURTURNPAGE++;
                    getBankMsg();
                }

            }
        });
    }


    OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            adapterPositions = adapterPosition;
            // 滑动删除的时候，从数据源移除，并刷新这个Item。
            if (adapterPositions < listmodel.size()){
                BankmsgModel enjou = (BankmsgModel) listmodel.get(adapterPositions);
                deleteRemindSale(enjou);
            }
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            if (viewType == 1){
                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;

                int width = getResources().getDimensionPixelSize(R.dimen.dp_90);
                int height = getResources().getDimensionPixelSize(R.dimen.dp_126);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // 各种文字和图标属性设置。
                deleteItem.setImage(R.drawable.yhk_sc);
                deleteItem.setHeight(height);
                deleteItem.setWidth(width);
                // 在Item右侧添加一个菜单。
                swipeRightMenu.addMenuItem(deleteItem);
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        }
    };


    /*行点击事件*/
    SelectBankListener selectBankListener = new SelectBankListener() {
        @Override
        public void addBankListener(int bankType) {
            if (bankType == 0){
                //信用卡
                showFragment(getActivity(), BindCreditFragment_.builder().build());
            }else{
                //银行卡
            }
        }
    };

    /*删除银行卡*/
    private void deleteRemindSale(BankmsgModel enjou) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_bank_delete);
        UserManager.deleteBank(enjou.getId(), subscriber);
    }

    private void getBankMsg() {
        Subscriber subscriber = new PosetSubscriber<BankmsgResponse>().getSubscriber(callback_bank);
        UserManager.getbankmsg(cardType, CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_bank = new ResponseResultListener<BankmsgResponse>() {
        @Override
        public void success(BankmsgResponse returnMsg) {
            pullToRefreshScrollView.onRefreshComplete();
            closeProgress();

            String bottomDes;
            if (switchBank){
                bottomDes = "+ 添加另一张银行卡";
            }else{
                bottomDes = "+ 添加另一张信用卡";
            }

            listmodel.addAll(returnMsg.getRows());
            adapter_brankcard.setData(listmodel, bottomDes);
        }

        @Override
        public void fialed(String resCode, String message) {
            pullToRefreshScrollView.onRefreshComplete();
            closeProgress();
        }
    };

    ResponseResultListener callback_bank_delete = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            listmodel.remove(adapterPositions);
            adapter_brankcard.removeRecycle(adapterPositions);
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("删除成功");
            }else{
                ToastUtil.showToast("删除失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };


    @Subscribe
    public void refresh(BankFragmentRefresh refresh){
        if (refresh.isfresh){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            listmodel.clear();
            getBankMsg();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}

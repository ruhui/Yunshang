package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.MineMsgAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BankmsgModel;
import com.shidai.yunshang.models.BulletinMode;
import com.shidai.yunshang.models.SystemModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.pulltorefresh.PullToRefreshBase;
import com.shidai.yunshang.view.pulltorefresh.PullToRefreshScrollView;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.CustomTitle;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_minemsg)
public class MineMsgFragment extends BaseFragment {

    @ViewById(R.id.textView19)
    TextView txtContent;
    @ViewById(R.id.textView20)
    TextView txtCount;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @ViewById(R.id.mSwipeMenuRecycleView)
    SwipeMenuRecyclerView recyclerView ;

    @ViewById(R.id.pullToRefreshScrollView)
    PullToRefreshScrollView pullToRefreshScrollView;


    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private MineMsgAdapter adapter_msg;
    private int adapterPositions = -1;
    private List<SystemModel> listMode = new ArrayList<>();

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("消息");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        recyclerView.setAdapter(adapter_msg = new MineMsgAdapter(adapterListener));

        getBulletin();
        /*系统消息*/
        getSystemMsg();

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                PullToRefreshBase.Mode mode = refreshView.getCurrentMode();
                if (mode == PullToRefreshBase.Mode.PULL_FROM_START){
                    //上拉刷新数据
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    listMode.clear();
                    /*系统消息*/
                    getSystemMsg();
                }else{
                    CURTURNPAGE++;
                    getSystemMsg();
                }

            }
        });
    }


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

                int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
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

    OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            adapterPositions = adapterPosition;
            // 滑动删除的时候，从数据源移除，并刷新这个Item。
            if (adapterPositions < listMode.size()){
                SystemModel enjou = (SystemModel) listMode.get(adapterPositions);
                deleteRemindSale(enjou);
            }
        }
    };


    AdapterListener adapterListener = new AdapterListener<SystemModel>() {
        @Override
        public void setItemClickListener(SystemModel o, int position) {
            SystemModel mode = listMode.get(position);
            if (mode.getId() == o.getId()){
                mode.setIs_read(true);
                //设置已读
                List<Integer> list_Id = new ArrayList<>();
                list_Id.add(mode.getId());
                setRead(list_Id);
                adapter_msg.setData(listMode);
            }

            /*到详情界面*/
            MsgeDetailFragment fragment = MsgeDetailFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putString("title", o.getTitle());
            bundle.putString("content", o.getContent());
            bundle.putString("creattime", o.getCreate_time());
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }
    };

    /*设置已读*/
    private void setRead(List<Integer> list_Id){
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_read);
        UserManager.setread(list_Id, subscriber);
    }

    /*删除消息*/
    private void deleteRemindSale(SystemModel enjou) {
        showProgress();
        List<Integer> listId = new ArrayList<>();
        listId.add(enjou.getId());
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_delete);
        UserManager.deleteMessage(listId, subscriber);
    }

    /*公告*/
    @Click(R.id.relaBulletin)
    void bulletin(){
        showFragment(getActivity(), BulletinFragment_.builder().build());
    }


    private void getSystemMsg(){
        Subscriber subscriber = new PosetSubscriber<SystemResponse>().getSubscriber(callback_systemmsg);
        UserManager.getsystemmsg(CURTURNPAGE, subscriber);
    }

    private void getBulletin() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<BulletinResponse>().getSubscriber(callback_bulletin);
        UserManager.getBulletin(subscriber);
    }

    ResponseResultListener callback_bulletin = new ResponseResultListener<BulletinResponse>() {
        @Override
        public void success(BulletinResponse returnMsg) {
            closeProgress();
            txtContent.setText(returnMsg.getTitle());
            if (returnMsg.getNew_count() == 0){
                txtCount.setVisibility(View.GONE);
            }else{
                txtCount.setVisibility(View.VISIBLE);
                txtCount.setText(returnMsg.getNew_count());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_systemmsg = new ResponseResultListener<SystemResponse>() {
        @Override
        public void success(SystemResponse returnMsg) {
            pullToRefreshScrollView.onRefreshComplete();

            listMode.addAll(returnMsg.getRows());

//            for (int i=0;i<5;i++){
//                SystemModel model = new SystemModel(i, i, i, "标题"+i, "内容"+i, i+"时间", false);
//                listMode.add(model);
//            }

            adapter_msg.setData(listMode);
        }

        @Override
        public void fialed(String resCode, String message) {
            pullToRefreshScrollView.onRefreshComplete();
        }
    };

    ResponseResultListener callback_read = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
        }

        @Override
        public void fialed(String resCode, String message) {
        }
    };

    ResponseResultListener callback_delete = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                //删除成功
                ToastUtil.showToast("删除成功");
                listMode.remove(adapterPositions);
                adapter_msg.remove(adapterPositions);
            }else{
                ToastUtil.showToast("删除失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}

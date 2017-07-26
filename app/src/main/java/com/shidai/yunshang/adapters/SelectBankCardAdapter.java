package com.shidai.yunshang.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold_;
import com.shidai.yunshang.intefaces.SelectBankListener;
import com.shidai.yunshang.models.BankmsgModel;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */

public class SelectBankCardAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {

    private int ITEM_SAMPLE = 1, ITEM_LAST = 2;
    private Context mContext;
    private String bottomdes;
    private int bankType = 0;//0信用卡 1银行卡
    private SelectBankListener selectBankListener;
    private List<Object> mList = new ArrayList<>();

    public SelectBankCardAdapter( Context mContext, List<Object> mList, String bottomdes,  SelectBankListener selectBankListener){
        this.mContext =mContext;
        this.mList = mList;
        this.bottomdes = bottomdes;
        this.selectBankListener = selectBankListener;
    }

    public void setData( List<Object> mList, String bottomdes){
        this.mList = mList;
        this.bottomdes = bottomdes;
        notifyDataSetChanged();
    }

    public void setBottom(String bottomdes, int bankType) {
        this.bottomdes = bottomdes;
        this.bankType = bankType;
        notifyDataSetChanged();
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == ITEM_SAMPLE){
//            return new ItemOneViewHold(LayoutInflater.from(mContext).inflate(R.layout.adapter_select_bankcard, parent, false));
//        }else if(viewType == ITEM_LAST){
//            return new ItemSecondViewHold(LayoutInflater.from(mContext).inflate(R.layout.textview_addbank, parent, false));
//        }
//        return null;
//    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == ITEM_SAMPLE){
            return LayoutInflater.from(mContext).inflate(R.layout.adapter_select_bankcard, parent, false);
        }else if(viewType == ITEM_LAST){
            return LayoutInflater.from(mContext).inflate(R.layout.textview_addbank, parent, false);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        if (viewType == ITEM_SAMPLE){
            return new ItemOneViewHold(realContentView);
        }else if(viewType == ITEM_LAST){
            return new ItemSecondViewHold(realContentView);
        }
        return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemOneViewHold){
            BankmsgModel model = (BankmsgModel) mList.get(position);
            ((ItemOneViewHold) holder).txtCardNum.setText(model.getBank_name());
            ImageLoader.loadImage(Tool.getPicUrl(mContext, model.getImage_path()),  ((ItemOneViewHold) holder).imgCardIcon);

            ((ItemOneViewHold) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //一定要有监听，否则无法拉出菜单
                }
            });
        }else if (holder instanceof ItemSecondViewHold){
            ((ItemSecondViewHold) holder).txtAddCard.setText(bottomdes);
            ((ItemSecondViewHold) holder).txtAddCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectBankListener.addBankListener(bankType);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() > position){
            return ITEM_SAMPLE;
        }else{
            return ITEM_LAST;
        }
    }

    public class ItemOneViewHold extends RecyclerView.ViewHolder{

        public ImageView imgCardIcon;
        public TextView txtCardNum;

        public ItemOneViewHold(View itemView) {
            super(itemView);
            txtCardNum = (TextView) itemView.findViewById(R.id.txtCardNum);
            imgCardIcon = (ImageView) itemView.findViewById(R.id.imageView6);
        }
    }

    public class ItemSecondViewHold extends RecyclerView.ViewHolder{

        public TextView txtAddCard;

        public ItemSecondViewHold(View itemView) {
            super(itemView);
            txtAddCard = (TextView) itemView.findViewById(R.id.txtAddCard);
        }
    }

    public void removeRecycle(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}

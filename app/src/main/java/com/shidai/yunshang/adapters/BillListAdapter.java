package com.shidai.yunshang.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.intefaces.BillAdapterListener;
import com.shidai.yunshang.intefaces.EnumBillType;
import com.shidai.yunshang.models.BillModel;
import com.shidai.yunshang.models.BillTitleModel;
import com.shidai.yunshang.utils.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：账单列表
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 11:31
 **/
public class BillListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int VIEWTILE = 1, VIEWITEM = 2;
    private EnumBillType billType;
    private Context mContext;
    private List<Object> list_object = new ArrayList<>();
    private BillAdapterListener billAdalterListener;

    public BillListAdapter(Context context, List<Object> listObject, BillAdapterListener billAdalterListener){
        mContext = context;
        this.list_object = listObject;
        this.billAdalterListener = billAdalterListener;
    }

    public void setData(EnumBillType billType, List<Object> listObject){
        this.list_object = listObject;
        this.billType = billType;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTILE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_billtitle, parent, false);
            return new BillTitleViewHold(view);
        }else if (viewType == VIEWITEM){
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_billitem, parent, false);
            return new BillItemViewHold(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (list_object.get(position) instanceof BillTitleModel){
            final BillTitleModel billTitlmodel = (BillTitleModel) list_object.get(position);
            if (billType == EnumBillType.TYPE_TX){
                //提现，不显示单日收入
                ((BillTitleViewHold)holder).relaRight.setVisibility(View.INVISIBLE);
            }else{
                ((BillTitleViewHold)holder).relaRight.setVisibility(View.VISIBLE);
            }
            ((BillTitleViewHold)holder).txtAmount.setText("¥"+ Tool.formatPrice(billTitlmodel.getTotal()));
            ((BillTitleViewHold)holder).txtDate.setText(billTitlmodel.getDate());
            ((BillTitleViewHold)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    billAdalterListener.setOnTitleClickListener(billTitlmodel, position);
                }
            });
        }else if (list_object.get(position) instanceof BillModel){
            final BillModel billModel = (BillModel) list_object.get(position);
            ((BillItemViewHold)holder).txtAmount.setText("¥"+ Tool.formatPrice(billModel.getAmount()));
            ((BillItemViewHold)holder).txtOrderno.setText(billModel.getOrder_no());
            ((BillItemViewHold)holder).txtStatu.setText(billModel.getStatus_name());
            ((BillItemViewHold)holder).txtTime.setText(billModel.getTime());
            ((BillItemViewHold)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    billAdalterListener.setOnItemClickListener(billModel, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list_object.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list_object.get(position) instanceof BillTitleModel){
            return VIEWTILE;
        }else if (list_object.get(position) instanceof BillModel){
            return VIEWITEM;
        }
        return -1;
    }

    /*头部的viewhold*/
    class BillTitleViewHold extends RecyclerView.ViewHolder{

        public TextView txtDate, txtAmount;
        public RelativeLayout relaRight;

        public BillTitleViewHold(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.textView80);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            relaRight = (RelativeLayout) itemView.findViewById(R.id.relaRight);
        }
    }

    /*列表的viewhold*/
    class BillItemViewHold extends RecyclerView.ViewHolder{

        public TextView txtStatu, txtTime, txtAmount, txtOrderno;

        public BillItemViewHold(View itemView) {
            super(itemView);
            txtStatu = (TextView) itemView.findViewById(R.id.textView82);
            txtTime = (TextView) itemView.findViewById(R.id.textView84);
            txtAmount = (TextView) itemView.findViewById(R.id.textView83);
            txtOrderno = (TextView) itemView.findViewById(R.id.txtOrderno);
        }
    }
}

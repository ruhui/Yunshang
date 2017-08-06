package com.shidai.yunshang.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.MineMsgViewHold;
import com.shidai.yunshang.adapters.viewholders.MineMsgViewHold_;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.SystemModel;

import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */

public class MineMsgAdapter extends RecyclerView.Adapter<MineMsgAdapter.MyViewHold> {

    private AdapterListener adapterListener;
    private List<SystemModel> mlist = new ArrayList<>();

    public MineMsgAdapter(AdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    public void setData(List<SystemModel> mlist){
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_minemsg, parent, false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {
        final SystemModel model = mlist.get(position);
        if (model.is_read()){
            holder.imgFirst.setVisibility(View.GONE);
        }else{
            holder.imgFirst.setVisibility(View.VISIBLE);
        }

        holder.txtTitle.setText(model.getTitle());
        holder.txtTime.setText(model.getCreate_time());
        holder.txtContent.setText(model.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(model, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void remove(int adapterPositions) {
        notifyItemRemoved(adapterPositions);
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        public ImageView imgFirst;
        public TextView txtTitle, txtTime, txtContent;

        public MyViewHold(View itemView) {
            super(itemView);
            imgFirst = (ImageView) itemView.findViewById(R.id.imgFirst);
            txtTitle = (TextView) itemView.findViewById(R.id.textView25);
            txtTime = (TextView) itemView.findViewById(R.id.textView26);
            txtContent = (TextView) itemView.findViewById(R.id.textView27);
        }
    }
}

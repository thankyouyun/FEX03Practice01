package com.goodyun.fex03practice01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo6-19 on 2018-03-22.
 */

public class Recycler3Adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<FragmentItem> items;

    public Recycler3Adapter(Context context, ArrayList<FragmentItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item3, parent, false);
        VH holder = new VH(itemView);

        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        //현재번째 아이템 얻어오기
        FragmentItem item = items.get(position);
        vh.tvTitle.setText(item.getTitle());
        vh.tvDesc.setText(item.getDescription());

        
        //이미지 세팅
        if (item.getImgUrl() == null) {//이미지가 없는가?

            vh.iv.setVisibility(View.GONE);

        } else {
            vh.iv.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getImgUrl()).into(vh.iv);
        }

    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView iv;
        TextView tvDesc;

        public VH(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            iv = itemView.findViewById(R.id.iv);
            tvDesc = itemView.findViewById(R.id.tv_desc);

            //아이템클릭 했을때

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = items.get(getLayoutPosition()).getLink();

                    Intent intent = new Intent(context,ItemWebActivity.class);
                    intent.putExtra("Link",link);
                    context.startActivity(intent);

                }
            });

        }//public VH

    }
}

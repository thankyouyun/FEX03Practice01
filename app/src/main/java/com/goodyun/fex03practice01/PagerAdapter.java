package com.goodyun.fex03practice01;


import android.content.Context;

import android.content.Intent;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by alfo6-19 on 2018-03-20.
 */

public class PagerAdapter extends android.support.v4.view.PagerAdapter {


    LayoutInflater inflater;
    ArrayList<FragmentItem> items;
    int numPerPage = 3;
    int position;
    View page;
    ImageView iv01, iv02, iv03;
    TextView tvTitle01, tvTitle02, tvTitle03;
    TextView tvDate01, tvDate02, tvDate03;
    TextView tvDesc01, tvDesc02, tvDesc03;
    Context context;
    CardView cardView1, cardView2, cardView3;

    public PagerAdapter(Context context, LayoutInflater inflater, ArrayList<FragmentItem> items) {
        this.inflater = inflater;
        this.items = items;
        this.context = context;

    }

    @Override
    public int getCount() {


//        int n = items.size() % numPerPage == 0 ? items.size() / numPerPage : items.size() / numPerPage + 1;


        return items.size() / numPerPage;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        this.position = position;
        page = inflater.inflate(R.layout.page_adapter1, container, false);


        cardwrite();
        readCard();
        clickCard();

        container.addView(page);

        return page;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


    public void cardwrite() {


        iv01 = page.findViewById(R.id.iv01);
        tvTitle01 = page.findViewById(R.id.tv_title01);
        tvDate01 = page.findViewById(R.id.tv_date01);
        tvDesc01 = page.findViewById(R.id.tv_desc01);

        iv02 = page.findViewById(R.id.iv02);
        tvTitle02 = page.findViewById(R.id.tv_title02);
        tvDate02 = page.findViewById(R.id.tv_date02);
        tvDesc02 = page.findViewById(R.id.tv_desc02);

        iv03 = page.findViewById(R.id.iv03);
        tvTitle03 = page.findViewById(R.id.tv_title03);
        tvDate03 = page.findViewById(R.id.tv_date03);
        tvDesc03 = page.findViewById(R.id.tv_desc03);

        cardView1 = page.findViewById(R.id.card01);
        cardView2 = page.findViewById(R.id.card02);
        cardView3 = page.findViewById(R.id.card03);

    }

    public void readCard() {
        FragmentItem item;

        if (items.size() == 0) return;


        item = items.get(numPerPage * position);
        tvTitle01.setText(item.title);
        tvDate01.setText(item.date);
        tvDesc01.setText(item.description);
        if (item.getImgUrl() == null) {

            iv01.setVisibility(View.GONE);

        } else {
            iv01.setVisibility(View.VISIBLE);
            Glide.with(page).load(item.getImgUrl()).into(iv01);
        }


        item = items.get(numPerPage * position + 1);
        tvTitle02.setText(item.title);
        tvDate02.setText(item.date);
        tvDesc02.setText(item.description);
        if (item.getImgUrl() == null) {

            iv02.setVisibility(View.GONE);

        } else {
            iv02.setVisibility(View.VISIBLE);
            Glide.with(page).load(item.getImgUrl()).into(iv02);
        }


        item = items.get(numPerPage * position + 2);
        tvTitle03.setText(item.title);
        tvDate03.setText(item.date);
        tvDesc03.setText(item.description);
        if (item.getImgUrl() == null) {

            iv03.setVisibility(View.GONE);

        } else {
            iv03.setVisibility(View.VISIBLE);
            Glide.with(page).load(item.getImgUrl()).into(iv03);
        }

    }//readcard


    public void clickCard() {


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String link = items.get(numPerPage * position).getLink();

                Intent intent = new Intent(context, ItemWebActivity.class);
                intent.putExtra("Link", link);
                context.startActivity(intent);


            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String link = items.get(numPerPage * position + 1).getLink();

                Intent intent = new Intent(context, ItemWebActivity.class);
                intent.putExtra("Link", link);
                context.startActivity(intent);


            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String link = items.get(numPerPage * position + 2).getLink();

                Intent intent = new Intent(context, ItemWebActivity.class);
                intent.putExtra("Link", link);
                context.startActivity(intent);


            }
        });


    }//clickCard


}

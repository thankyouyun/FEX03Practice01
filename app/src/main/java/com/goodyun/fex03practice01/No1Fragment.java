package com.goodyun.fex03practice01;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by alfo6-19 on 2018-03-20.
 */

public class No1Fragment extends Fragment {


    ArrayList<FragmentItem> items = new ArrayList<>();
    ViewPager pager;
    PagerAdapter adapter;
    TextView tvCount;
    Context context;
    TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        try {
            context = getActivity();
            tvCount = view.findViewById(R.id.tv_count);
            tvTitle = view.findViewById(R.id.tv_title);
            pager = view.findViewById(R.id.pager);


            adapter = new PagerAdapter(context, getLayoutInflater(), items);
            pager.setAdapter(adapter);


            tvTitle.setText("주요뉴스 ▷▷▷");

            pager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    tvCount.setText((pager.getCurrentItem() + 1) + " / " + items.size() / 3);


                }
            });

            reedRSS();
        }catch (Exception e){

        }

        return view;
    }//onCreateView


    public void reedRSS() {
        try {
            URL url = new URL("http://rss.hankyung.com/new/news_main.xml");

            RssFeedTask task = new RssFeedTask();

            task.execute(url);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    class RssFeedTask extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... urls) {

            InputStream is;
            try {

                is = urls[0].openStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(is, "utf-8");

                int eventType = xpp.next();

                FragmentItem item = null;
                String tagName = null;


                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {

                        case XmlPullParser.START_DOCUMENT:


                            break;

                        case XmlPullParser.START_TAG:

                            tagName = xpp.getName();
                            if (tagName.equals("item")) {
                                item = new FragmentItem();
                            } else if (tagName.equals("title")) {
                                xpp.next();
                                if (item != null) item.setTitle(xpp.getText());
                            } else if (tagName.equals("link")) {
                                xpp.next();
                                if (item != null) item.setLink(xpp.getText());
                            } else if (tagName.equals("description")) {
                                xpp.next();
                                if (item != null) item.setDescription(xpp.getText());
                            } else if (tagName.equals("image")) {
                                xpp.next();
                                if (item != null) item.setImgUrl(xpp.getText());
                            } else if (tagName.equals("pubDate")) {
                                xpp.next();
                                if (item != null) item.setDate(xpp.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:

                            break;
                        case XmlPullParser.END_TAG:
                            tagName = xpp.getName();
                            if (tagName.equals("item")) {
                                items.add(item);
                                item = null;
                                publishProgress();
                            }

                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;

                    }

                    eventType = xpp.next();

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return "파싱종료";
        }

        //publishProgress()를 호출하면 실행되는 메소드 UI변경작업 가능
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }
    }//RssFeedTask


}

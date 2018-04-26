package com.goodyun.fex03practice01;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Tab2Activity extends AppCompatActivity {

    ArrayList<FragmentItem> items = new ArrayList<>();
    Recycler2Adapter adapter;
    RecyclerView recyclerView;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("스포츠");

        recyclerView = findViewById(R.id.recycler);
        adapter = new Recycler2Adapter(this, items);
        recyclerView.setAdapter(adapter);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        reedRss();

    }//onCreate

    void reedRss() {

        try {
            URL url = new URL("http://rss.hankyung.com/new/news_enter.xml");
            RssFeedTask task = new RssFeedTask();
            task.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }//reedRSS

    class RssFeedTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];


            try {
                InputStream is = url.openStream();
                XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();

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


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "파싱종료";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);
        }
    }//RssFeedTask


}

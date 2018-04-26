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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Tab4Activity extends AppCompatActivity {
    ArrayList<FragmentItem> items = new ArrayList<>();
    Recycler4Adapter adapter;
    RecyclerView recyclerView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("포토");


        recyclerView = findViewById(R.id.recycler);
        adapter = new Recycler4Adapter(this,items);
        recyclerView.setAdapter(adapter);

        //리사이클러뷰의 아이템 배치방법 적용
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        reedRSS();



    }


    void reedRSS() {

        try {
            URL url = new URL("http://rss.hankyung.com/new/news_photo.xml");

            //별도 스레드객체 생성 스레드의 백그라운드 작업과 UI작업을 같이 할 수 있는 객체 생성
            Tab4Activity.RssFeedTask task = new Tab4Activity.RssFeedTask();
            //doInBackground()메소드 실행시키는 명령(메소드)
            task.execute(url); //Thread의 start()와 같은 역할


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    //Rss XML문서를 네트워크로 읽어오는 작업 스레드 innerclass

    class RssFeedTask extends AsyncTask<URL, Void, String> {
        //Thread의 run() 메소드같은 역할과 같은 메소드


        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];

            try {
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                //utf-8은 한글도 읽기위한 인코딩 방식
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

//            Toast.makeText(MainActivity.this, "하이", Toast.LENGTH_SHORT).show();

            //리사이클러뷰의 갱신요청
            adapter.notifyDataSetChanged();

        }


        //doInBVackground()가 종료 되면 자동으로 실행되는 메소드 //이 메소드 안에서는 UI변경작업 가능


        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);

        }
    }//RssFeedTask


}

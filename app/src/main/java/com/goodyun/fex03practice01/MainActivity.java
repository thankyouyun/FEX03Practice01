package com.goodyun.fex03practice01;


import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    Toolbar toolbar;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawerLayout = findViewById(R.id.layout_drawer);
            navigationView = findViewById(R.id.nav);


            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
            drawerLayout.addDrawerListener(drawerToggle);

            drawerToggle.syncState();


            getSupportActionBar().setTitle("뉴스보기");


            tabLayout = findViewById(R.id.layout_tab);
            TabLayout.Tab tab = null;
            tab = tabLayout.newTab().setTag("tab1").setText("경제");
            tabLayout.addTab(tab);

            tab = tabLayout.newTab().setTag("tab2").setText("스포츠/연예");
            tabLayout.addTab(tab);

            tab = tabLayout.newTab().setTag("tab3").setText("산업/IT");
            tabLayout.addTab(tab);

            tab = tabLayout.newTab().setTag("tab4").setText("포토");
            tabLayout.addTab(tab);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getTag().equals("tab1")) {
                        Intent intent = new Intent(MainActivity.this, Tab1Activity.class);

                        startActivity(intent);
                    }
                    if (tab.getTag().equals("tab2")) {
                        Intent intent = new Intent(MainActivity.this, Tab2Activity.class);
                        startActivity(intent);
                    }
                    if (tab.getTag().equals("tab3")) {
                        Intent intent = new Intent(MainActivity.this, Tab3Activity.class);
                        startActivity(intent);
                    }

                    if (tab.getTag().equals("tab4")) {
                        Intent intent = new Intent(MainActivity.this, Tab4Activity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {

        }

    }//onCreate

    public void clickToolbar(View v) {
        toolbar.setTitle("뉴스를 보아요");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        sv = (SearchView)item.getActionView();
        sv.setQueryHint("검색어를 입력하시오");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "검색하라", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



}

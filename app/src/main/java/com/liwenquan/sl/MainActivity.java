package com.liwenquan.sl;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //private static final String TAG="MainActivity";

    public static ArrayList<String> list = new ArrayList<String>();
    SharedPreferences.Editor editor;
    StringBuffer sb;
    int count;
    private AlarmManager alarmManager;
    private TextView tvTime;
    private RecyclerView mRecyclerView;
    //public static ArrayList<String> listlable=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建一个线性布局
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        //创建Adapter，并指定数据集


        tvTime = (TextView) findViewById(R.id.tvTime);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_bug) {
                    startActivity(new Intent(MainActivity.this, PlayAlarmActivity.class));
                    return true;
                } else if (id == R.id.action_about) {
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    return true;
                }
                return true;
            }
        });
        toolbar.setTitle(R.string.home_page);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        findViewById(R.id.imgAdd).setOnClickListener(this);
        findViewById(R.id.action_setting).setOnClickListener(this);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        readSavedAlarmList();
        MyAdapter adapter = new MyAdapter(this, list);
        //设置Adapter
        mRecyclerView.setAdapter(adapter);
    }

    public void startAlarmDetailsActivity(String s) {
        Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
        intent.putExtra("打开相应的闹钟", s);
        startActivity(intent);
    }


    private void readSavedAlarmList() {
        SharedPreferences sp = getSharedPreferences(AddAlarmActivity.class.getName(), MODE_PRIVATE);
        String content = sp.getString(AddAlarmActivity.KEY, null);
        if (content != null) {
            String[] timeStrings = content.split(",");
            for (String string : timeStrings) {
                list.add(string);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAdd:
                startActivity(new Intent(MainActivity.this, AddAlarmActivity.class));

                break;
            case R.id.action_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }
    }


}

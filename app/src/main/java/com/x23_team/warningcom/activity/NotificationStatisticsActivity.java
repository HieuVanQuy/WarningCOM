package com.x23_team.warningcom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.x23_team.warningcom.Entity.Post;
import com.x23_team.warningcom.R;
import com.x23_team.warningcom.adapter.NotificationAdapter;

import java.util.ArrayList;

public class NotificationStatisticsActivity extends AppCompatActivity {
    private String[] typeOfWarning = {"1","2","3"};
    private String[] address = {"Phan Tu, Ngu Hanh Son, Da Nang","Nguyen Van Linh, Thanh Khe, Da Nang","Hoang Sa, Ngu Hanh Son, Da Nang"};
    private ListView listNotification;
    private NotificationAdapter adapterNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_statistics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.statisticsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Notification");


        listNotification = (ListView) findViewById(R.id.listNotification);
        ArrayList<Post> arrPost = new ArrayList<>();
        adapterNotification = new NotificationAdapter(NotificationStatisticsActivity.this,R.layout.notificationlistview_layout,arrPost);
        listNotification.setAdapter(adapterNotification);
        for(int i = 1;i<=3;i++){
            Post mypost = new Post();
            mypost.setType_id(typeOfWarning[i]);
            mypost.setAddress(address[i]);
            arrPost.add(mypost);
            adapterNotification.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Toast.makeText(NotificationStatisticsActivity.this,"Chuyen ve home activity",Toast.LENGTH_LONG).show();
            {
                Intent intent = new Intent(NotificationStatisticsActivity.this,RegisterActivity.class);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
}

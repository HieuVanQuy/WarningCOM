package com.x23_team.warningcom.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.x23_team.warningcom.R;

public class HelpMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FragmentMenu.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}

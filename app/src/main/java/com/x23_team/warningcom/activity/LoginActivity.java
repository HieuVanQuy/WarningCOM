package com.x23_team.warningcom.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.x23_team.warningcom.R;

public class LoginActivity extends AppCompatActivity {
    public static SQLiteDatabase database;
    public static String DATABASE_NAME = "WarningCOM.sqlite";
    EditText edtID,edtPass;
    Button btnLogin;

    public void init(){
        edtID = (EditText) findViewById(R.id.edtID);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        database = openOrCreateDatabase("WarningCOM.sqlite", MODE_PRIVATE, null);
        try{
            createTable();
        }
        catch (Exception e){
            Log.e("Error", "Table is exits")
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent();
            }
        });

    }
}

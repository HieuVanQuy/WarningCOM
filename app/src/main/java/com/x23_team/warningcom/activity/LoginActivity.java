package com.x23_team.warningcom.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.x23_team.warningcom.R;


public class LoginActivity extends AppCompatActivity {
    public static SQLiteDatabase database;
    EditText edtID,edtPass;
    Button btnLogin;
    TextView txtSignup;

    public void init(){
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        database = openOrCreateDatabase("WarningCOM.sqlite", MODE_PRIVATE, null);
        try{
            createTable();
        }
        catch (Exception e){
            Log.e("Error", "Table is exits");
        }
    }

    private void createTable() {
        String sql1 = "CREATE TABLE Account("+
                "[ID] TEXT Primary key,"+
                "[Pass] TEXT,"+
                "[name] TEXT,"+
                "[role] TEXT,"+
                "[phoneNumber] TEXT,"+
                "[EMAIL] TEXT";
        database.execSQL(sql1);

    }

    public void queryLogin(){
        String id = edtID.getText().toString();
        String pass = edtPass.getText().toString();
        String check = id+pass;
        String t = "";
        if(t.equals(check)){
            Intent map = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(map);
        }

//        Cursor c = database.query("Account", null, null, null, null, null, null);
//        c.moveToFirst();
//        String data = "";
//        while (c.isAfterLast()==false){
//            data+=c.getString(0)+c.getString(1);
//            if(check.equals(data)){
//                Intent map = new Intent(LoginActivity.this, RegisterActivity.class);
//                map.putExtra("ID", edtID.getText().toString());
//                startActivity(map);
//                break;
//            }
//            c.moveToNext();
//        }
        else
            Toast.makeText(getApplicationContext(),"Account do not exist yet",Toast.LENGTH_LONG).show();
        //c.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                queryLogin();
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}

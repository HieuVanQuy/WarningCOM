package com.x23_team.warningcom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.x23_team.warningcom.R;
import com.x23_team.warningcom.app.AppConfig;
import com.x23_team.warningcom.app.AppController;
import com.x23_team.warningcom.helper.SQLiteHandler;
import com.x23_team.warningcom.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtID,edtPass;
    private Button btnLogin;
    private TextView txtSignup;
    private ProgressDialog pDialog;
    private String ID,pass;
    private SessionManager session;
    private SQLiteHandler db;

    public void init(){
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        edtID = (EditText) findViewById(R.id.et_username);
        edtPass = (EditText) findViewById(R.id.et_password_l);
        btnLogin = (Button) findViewById(R.id.btnLogin);
//        database = openOrCreateDatabase("WarningCOM.sqlite", MODE_PRIVATE, null);
//        try{
//            createTable();
//        }
//        catch (Exception e){
//            Log.e("Error", "Table is exits");
//        }
//    }
//
//    private void createTable() {
//        String sql1 = "CREATE TABLE Account("+
//                "[ID] TEXT Primary key,"+
//                "[Pass] TEXT,"+
//                "[name] TEXT,"+
//                "[role] TEXT,"+
//                "[phoneNumber] TEXT,"+
//                "[EMAIL] TEXT";
//        database.execSQL(sql1);

    }

    public void checkLogin(final String id, final String pass){
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response.substring(3));
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String pass = jObj.getString("pass");
                        String role = jObj.getString("role");
                        String name = jObj.getString("name");
                        String email = jObj.getString("email");
                        String phonenumber = jObj.getString("phonenumber");


                        //session.setLogin(true);

                        // Inserting row in users table
                        db.addAccount(pass, name, email, role, phonenumber);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", ID );
                params.put("password", pass);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                ID = edtID.getText().toString().trim();
                pass = edtPass.getText().toString();
                // Check for empty data in the form
                if (!ID.isEmpty() && !pass.isEmpty()) {
                    // login user
                    checkLogin(ID, pass);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
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

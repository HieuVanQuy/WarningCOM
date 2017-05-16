package com.x23_team.warningcom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.x23_team.warningcom.R;
import com.x23_team.warningcom.app.AppConfig;
import com.x23_team.warningcom.app.AppController;
import com.x23_team.warningcom.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btnlogin;
    SQLiteDatabase database = null;
    private TextInputEditText edtEmail, edtPassword;
    private TextView txtRegister;
    private String email,password;
    public void init(){
        btnlogin = (Button) findViewById( R.id.btnLogin );
        edtEmail = (TextInputEditText) findViewById( R.id.edtemail );
        edtPassword = (TextInputEditText) findViewById( R.id.edtPassword );
        txtRegister = (TextView) findViewById( R.id.txtRegister );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        edtEmail.setText( "thanhtay212@gmail.com" );
        edtPassword.setText( "123123" );
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());
//        session.setLogin( false );
//        if (session.isLoggedIn()) {
//            API_KEY = session.getKey();
//            AppConfig.KEY_EMAIL = session.getEmail();
//            AppConfig.KEY_PASS = session.getPASS();
//            // User is already logged in. Take him to main activity
//            Intent intent = new Intent(LoginActivity.this, FragmentMenu.class);
//            startActivity(intent);
//            finish();
//        }

        btnlogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString();
                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText( getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        txtRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class);
                startActivity( intent );
                finish();
            }
        } );
    }

    private SessionManager session;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }
    private String Password,Email;
    private void checkLogin(final String email, final String password) {
        String tag_string_req = "req_login";

        pDialog.setMessage( "Logging in ..." );
        showDialog();

        StringRequest strReq = new StringRequest( Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d( TAG, "Login Response: " + response.toString() );
                hideDialog();

                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(response.substring( 0 ));
                    String id = jObj.getString( "id" );
                    String ttl = jObj.getString( "ttl" );
                    String creted = jObj.getString( "created" );
                    String userId = jObj.getString( "userId" );

                    AppConfig.API_KEY = id;
                    AppConfig.KEY_UserID = userId;
                    AppConfig.KEY_TTL = ttl;
                    AppConfig.KEY_CREATED = creted;
                    session.setLogin( true );
                    Intent intent = new Intent( LoginActivity.this, FragmentMenu.class );
                    startActivity( intent );
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( TAG, "Login Error: " + error.getMessage() );
                Toast.makeText( getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT ).show();
                hideDialog();
            }
        } ) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put( "email", email );
                params.put( "password", password );

                return params;
            }

        };
        AppController.getInstance( this ).getRequestQueue().add( strReq );
    }
}

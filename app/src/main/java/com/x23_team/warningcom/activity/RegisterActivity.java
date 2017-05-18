package com.x23_team.warningcom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.x23_team.warningcom.R;
import com.x23_team.warningcom.app.AppConfig;
import com.x23_team.warningcom.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText edtPassWord, edtConfirmPassword, edtFullName, edtPhoneNumber, edtEmailAddress;
    private Button btnSignUp;
    private String Password,ConfirmPassword,FullName,PhoneNumber,Email;

    //private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);
        edtPassWord = (EditText) findViewById(R.id.edtPassWord);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtEmailAddress = (EditText) findViewById(R.id.edtEmailAddress);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        //session = new SessionManager(getBaseContext())
    }

    private void submit() {
        if(!validatePassWord())
            return;
        if(!validateConfirmPassWord())
            return;
        if(!validateFullName())
            return;
        if(!validatePhoneNumber())
            return;
        if(!validateEmailAddress())
            return;
        onSignUp();
    }




    private boolean validatePassWord(){
        Password = edtPassWord.getText().toString().trim();
        if(Password.isEmpty()){
            edtPassWord.setError(edtPassWord.getHint()+" is required!");
            edtPassWord.requestFocus();
            return false;
        }
        else edtPassWord.setError(null);
        return true;
    }

    private boolean validateConfirmPassWord(){
        ConfirmPassword = edtConfirmPassword.getText().toString().trim();
        if(ConfirmPassword.isEmpty()){
            edtConfirmPassword.setError(edtConfirmPassword.getHint()+" is required!");
            edtConfirmPassword.requestFocus();
            return false;
        }
        if(!ConfirmPassword.equals(edtPassWord.getText().toString().trim())){
            edtConfirmPassword.setError("Your confirm password is wrong");
            edtConfirmPassword.requestFocus();
            return false;
        }
        else edtConfirmPassword.setError(null);
        return true;
    }

    private boolean validateFullName(){
        FullName = edtFullName.getText().toString().trim();
        if(FullName.isEmpty()){
            edtFullName.setError(edtFullName.getHint()+" is required!");
            edtFullName.requestFocus();
            return false;
        }
        else edtFullName.setError(null);
        return true;
    }

    private boolean validatePhoneNumber(){
        PhoneNumber = edtPhoneNumber.getText().toString().trim();
        if(PhoneNumber.isEmpty()){
            edtPhoneNumber.setError(edtPhoneNumber.getHint()+" is required!");
            edtPhoneNumber.requestFocus();
            return false;
        }
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(PhoneNumber);
        if(!matcher.matches() || PhoneNumber.length()>12 || PhoneNumber.length()<10){
            edtPhoneNumber.setError("Your Phone Number is wrong");
            edtPhoneNumber.requestFocus();
            return false;
        }
        else edtPhoneNumber.setError(null);
        return true;
    }

    private boolean validateEmailAddress(){
        Email = edtEmailAddress.getText().toString().trim();
        if(Email.isEmpty()){
            edtEmailAddress.setError(edtEmailAddress.getHint()+" is required!");
            edtEmailAddress.requestFocus();
            return false;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        if(!matcher.matches()){
            edtEmailAddress.setError("Your email is wrong");
            edtEmailAddress.requestFocus();
            return false;
        }
        else edtEmailAddress.setError(null);
        return true;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }
    private void onSignUp() {
        //String tag_string_req = "req_register";

        pDialog.setMessage("Registering...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                hideDialog();
                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(!error){
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(getApplicationContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response",error.getMessage());
                Toast.makeText(getApplicationContext(),"Error response",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Email);
                params.put("name", FullName);
                params.put("phone_number", PhoneNumber);
                params.put("password", Password);
                params.put("role_name", "user" );
                return params;
            }
        };
        AppController.getInstance(this).getRequestQueue().add(strReq);
    }
}

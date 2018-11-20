package com.example.user.cameraloo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText editEmail, editPassword, editName;
    Button btnSignIn, btnRegister;

    String URL= "http://3.0.101.196/SimpleLoginUpload/index.php";
    SessionHandler sharedP;

    JSONParser jsonParser=new JSONParser();
    String name = "";
    String pwd = "";
    String email = "";
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedP = new SessionHandler(getApplicationContext());
        editEmail=findViewById(R.id.editEmail);
        editName=findViewById(R.id.editName);
        editPassword=findViewById(R.id.editPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnRegister=findViewById(R.id.btnRegister);

        if(sharedP.isLoggedIn()){
            displayMain();
        }


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editName.getText().toString();
                pwd = editPassword.getText().toString();

                AttemptLogin attemptLogin= new AttemptLogin();
                attemptLogin.execute(name, pwd,"");
                Log.d("input utk reg",name);
                Log.d("input utk reg",pwd);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0)
                {
                    i=1;
                    editEmail.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    btnRegister.setText("CREATE ACCOUNT");
                }
                else{

                    btnRegister.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    i=0;
                    name = editName.getText().toString();
                    pwd = editPassword.getText().toString();
                    email = editEmail.getText().toString();
                    AttemptLogin attemptLogin= new AttemptLogin();
                    attemptLogin.execute(name, pwd,email);
                    Log.d("input utk reg",name);
                    Log.d("input utk reg",pwd);
                    Log.d("input utk reg",email);



                }

            }
        });


    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    Log.d("onPostExecute",result.getString("success"));
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                    Log.d("onPostExecute",result.getString("success"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            afterLogin(result);

        }


    }
    protected void afterLogin(JSONObject result){
       // Log.d("afterLogin","hubu asdawd");
        try {
            if (result.getString("success").equals("1")) {
                name = editName.getText().toString();
                if(email.length()>0)
                    email = editEmail.getText().toString();
                sharedP.loginUser(name);
                displayMain();
            }
        }catch (Exception e){

        }
    }
    protected void displayMain(){
        //Log.d("displayMain","huhu");

        Intent mainPage = new Intent(Login.this,MainForAll.class);
        mainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainPage);
        finish();
    }
}
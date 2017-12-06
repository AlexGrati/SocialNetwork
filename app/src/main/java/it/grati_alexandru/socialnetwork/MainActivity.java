package it.grati_alexandru.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.grati_alexandru.socialnetwork.Utils.FirebaseRestRequests;
import it.grati_alexandru.socialnetwork.Utils.JSONParser;
import it.grati_alexandru.socialnetwork.Utils.ResponseController;

public class MainActivity extends AppCompatActivity implements ResponseController{
    private TextView eTextUserLogin;
    private TextView eTextPassLogin;
    private SharedPreferences sharedPreferences;
    private String logedUser;
    private Intent intent;
    private String insertedUser;
    private String insertedPassword;
    private ProgressDialog progressDialog;
    private ResponseController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = this;
        eTextUserLogin = findViewById(R.id.eTextUserLogin);
        eTextPassLogin = findViewById(R.id.eTextPassLogin);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        intent = new Intent(getApplicationContext(),ComunityActivity.class);
        logedUser = sharedPreferences.getString("USER","");
        hasLoggedIn();
        progressDialog = new ProgressDialog(MainActivity.this);

    }

    public void hasLoggedIn(){
        if(!logedUser.equals("")){
            startActivity(intent);
            finish();
        }
    }

    public void onLoginButtonPressed(View v){
        if(isDataInserted()){
            progressDialog.setMessage("Controllo dati...");
            progressDialog.show();
            String url = "Users/"+insertedUser+"/Password/";
            FirebaseRestRequests.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode == 200){
                        String response = new String(responseBody);
                        if(!response.equals("null")){
                            if(insertedPassword.equals(response)){
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString("USER", insertedUser);
                                edit.apply();
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Benvenuto!!!",Toast.LENGTH_SHORT).show();
                                controller.respondOnRecevedData();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Password Sbagliata",Toast.LENGTH_SHORT).show();
                                controller.respondOnRecevedData();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Username non esiste",Toast.LENGTH_SHORT).show();
                            controller.respondOnRecevedData();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (statusCode >= 400 && statusCode < 500) {
                        controller.respondOnRecevedData();
                        Toast.makeText(getApplicationContext(),
                                "Server non disponibile.\nRiprova.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void respondOnRecevedData(){
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    public boolean isDataInserted(){
        insertedUser = eTextUserLogin.getText().toString();
        insertedPassword = eTextPassLogin.getText().toString();
        if(insertedUser.equals("")){
            Toast.makeText(getApplicationContext(), "Inserisci Login", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(insertedPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Inserisci Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

package it.grati_alexandru.socialnetwork;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.grati_alexandru.socialnetwork.Model.Comunity;
import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Utils.FileOperations;
import it.grati_alexandru.socialnetwork.Utils.FirebaseRestRequests;
import it.grati_alexandru.socialnetwork.Utils.JSONParser;
import it.grati_alexandru.socialnetwork.Utils.ResponseController;

public class ComunityActivity extends AppCompatActivity implements ResponseController{
    private ButtonAdapter buttonAdapter;
    private List<Gruppo> listaGruppi;
    private ResponseController responseController;
    private ProgressDialog progressDialog;
    private Comunity comunity;
    private SharedPreferences sharedPreferences;
    private String loggedInUser;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunity);
        responseController = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loggedInUser = sharedPreferences.getString("USER","");
        comunity = (Comunity) FileOperations.readObject(getApplicationContext(),"COMUNITY");
        if(comunity != null){
            listaGruppi = comunity.getListaGruppi();
            buttonAdapter = new ButtonAdapter(getApplicationContext(),R.layout.list_item,listaGruppi);
            listView = findViewById(R.id.groupeListViewId);
            listView.setAdapter(buttonAdapter);
        }else{
            progressDialog = new ProgressDialog(ComunityActivity.this);
            progressDialog.setMessage("Carricamento Dati");
            progressDialog.show();
            getGroupesRest();
        }
    }

    public void getGroupesRest(){
        String url = "Users/"+loggedInUser+"/Gruppi";
        FirebaseRestRequests.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String json = new String(responseBody);
                    List<Gruppo> listaGruppi2 = JSONParser.getListaGruppi(json);
                    comunity = new Comunity(listaGruppi2);
                    buttonAdapter = new ButtonAdapter(getApplicationContext(),R.layout.list_item,listaGruppi2);
                    listView = findViewById(R.id.groupeListViewId);
                    listView.setAdapter(buttonAdapter);
                    FileOperations.writeObject(getApplicationContext(),"COMUNITY",comunity);
                    Toast.makeText(getApplicationContext(),
                            "Caricamento riuscito.",
                            Toast.LENGTH_SHORT).show();
                    responseController.respondOnRecevedData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode >= 400 && statusCode < 500){
                    responseController.respondOnRecevedData();
                    Toast.makeText(getApplicationContext(),
                            "Server non disponibile.\nRiprova.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void respondOnRecevedData() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }
}

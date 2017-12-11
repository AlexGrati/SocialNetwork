package it.grati_alexandru.socialnetwork;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Date;

import cz.msebera.android.httpclient.Header;
import it.grati_alexandru.socialnetwork.Model.Comunity;
import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Model.Post;
import it.grati_alexandru.socialnetwork.Utils.DateConversion;
import it.grati_alexandru.socialnetwork.Utils.FileOperations;
import it.grati_alexandru.socialnetwork.Utils.FirebaseRestRequests;
import it.grati_alexandru.socialnetwork.Utils.ResponseController;

public class AddPostActivity extends AppCompatActivity implements ResponseController{
    private SharedPreferences sharedPreferences;
    private EditText editTextTitoloPost;
    private EditText editTextContenutoPost;
    private String username;
    private String groupeName;
    private ResponseController responseController;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        editTextContenutoPost = findViewById(R.id.editTextContenutoPost);
        editTextTitoloPost = findViewById(R.id.editTextTitoloPost);

        responseController = this;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = sharedPreferences.getString("USER","");
        groupeName = sharedPreferences.getString("GROUPE_NAME","");
    }

    public void onInviaButtonClicked(View view){
        String warning = "Inserisci";
        String titoloPost = editTextTitoloPost.getText().toString();
        String contenutoPost = editTextContenutoPost.getText().toString();
        if(titoloPost.equals("")){
            warning = warning + " il titolo";
        }
        if(contenutoPost.equals("")){
            if(warning.equals("Inserisci")){
                warning = warning + " il contenuto";
            }else{
                warning = warning + " e il contenuto";
            }
        }

        if(warning.equals("Inserisci")){
            String url = "https://socialnetwork-490d3.firebaseio.com/Communities/Gruppi/"+groupeName+"/Posts";
            String urlGroupe = "https://socialnetwork-490d3.firebaseio.com/Communities/Gruppi/"+groupeName;

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl(url);
            DatabaseReference urlGroupeReference = firebaseDatabase.getReferenceFromUrl(urlGroupe);
            urlGroupeReference.child("LastModDate").setValue(DateConversion.formatDateToString(new Date()));

            Post p = new Post(titoloPost,username,new Date(),contenutoPost);

            databaseReference.child(titoloPost).child("Titolo").setValue(p.getTitolo());
            databaseReference.child(titoloPost).child("Autore").setValue(p.getAutore());
            databaseReference.child(titoloPost).child("Contenuto").setValue(p.getContenuto());
            databaseReference.child(titoloPost).child("Data_Creazione").setValue(DateConversion.formatDateToString(p.getDataCreazeione()));

            Comunity comunity = (Comunity) FileOperations.readObject(getApplicationContext(),"COMUNITY");
            Gruppo gruppo = comunity.getGroupeByName(groupeName);
            gruppo.addPost(p);
            gruppo.setLastModDate(new Date());
            comunity.updateGroupe(gruppo);
            FileOperations.writeObject(getApplicationContext(),"COMUNITY",comunity);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void respondOnRecevedData() {
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog.cancel();
            finish();
        }
    }
}

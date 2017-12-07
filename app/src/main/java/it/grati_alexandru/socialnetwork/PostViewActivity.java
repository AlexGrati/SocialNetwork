package it.grati_alexandru.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.grati_alexandru.socialnetwork.Model.Comunity;
import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Model.Post;
import it.grati_alexandru.socialnetwork.Utils.FileOperations;
import it.grati_alexandru.socialnetwork.Utils.FirebaseRestRequests;
import it.grati_alexandru.socialnetwork.Utils.JSONParser;
import it.grati_alexandru.socialnetwork.Utils.ResponseController;

public class PostViewActivity extends AppCompatActivity implements ResponseController {
    private Comunity comunity;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private String currentGroupName;
    private List<Post> postList;
    private Gruppo currentGroupe;
    private ResponseController responseController;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        responseController = this;

        recyclerView = findViewById(R.id.reclerViewId);
        linearLayoutManager = new LinearLayoutManager(PostViewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        currentGroupName = getIntent().getStringExtra("GROUPE_NAME");
        comunity = (Comunity) FileOperations.readObject(getApplicationContext(),"COMUNITY");
        currentGroupe = comunity.getGroupeByName(currentGroupName);

        if(currentGroupe.getPostList().size() == 0) {
            postList = currentGroupe.getPostList();
            recyclerAdapter = new RecyclerAdapter(getApplicationContext(),postList);
        }else{
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("Caricamento Dati....");
            progressDialog.show();
            getAllPostsRestOperation();
        }

    }

    public void getAllPostsRestOperation(){

        FirebaseRestRequests.get("Communities/Gruppi" + currentGroupName + "/Posts", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    List<Post> tempPostList = JSONParser.getAllPosts(new String(responseBody));
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(),tempPostList);
                    comunity.updateGroupePostList(currentGroupe, tempPostList);
                    FileOperations.writeObject(getApplicationContext(),"COMUNITY", comunity);
                    responseController.respondOnRecevedData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode >= 400 && statusCode < 500){
                    responseController.respondOnRecevedData();
                    Toast.makeText(getApplicationContext(),"Server innacessibile!\nRiprova in una altro momento",
                            Toast.LENGTH_SHORT);
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

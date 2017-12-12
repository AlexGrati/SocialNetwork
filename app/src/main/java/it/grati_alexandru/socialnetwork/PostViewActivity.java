package it.grati_alexandru.socialnetwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.grati_alexandru.socialnetwork.Model.Comunity;
import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Model.Post;
import it.grati_alexandru.socialnetwork.Utils.DateConversion;
import it.grati_alexandru.socialnetwork.Utils.FileOperations;
import it.grati_alexandru.socialnetwork.Utils.FirebaseRestRequests;
import it.grati_alexandru.socialnetwork.Utils.JSONParser;
import it.grati_alexandru.socialnetwork.Utils.ResponseController;

public class PostViewActivity extends AppCompatActivity implements ResponseController,SwipeRefreshLayout.OnRefreshListener {
    private Comunity comunity;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private String currentGroupName;
    private Gruppo currentGroupe;
    private ResponseController responseController;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Date lastModDate;
    private Date localGroupeModDate;
    private Boolean upToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        currentGroupName = sharedPreferences.getString("GROUPE_NAME","");
        setTitle(currentGroupName);

        responseController = this;

        recyclerView = findViewById(R.id.reclerViewId);
        linearLayoutManager = new LinearLayoutManager(PostViewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        comunity = (Comunity) FileOperations.readObject(getApplicationContext(),"COMUNITY");
        currentGroupe = comunity.getGroupeByName(currentGroupName);

        if(currentGroupe.getPostList().size() != 0) {
            checkGroupeLastModDate();
        }else{
            getAllPostsRestOperation();
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        checkGroupeLastModDate();
    }

    public void checkGroupeLastModDate(){
        upToDate = false;
        FirebaseRestRequests.get("Communities/Gruppi/" + currentGroupName + "/LastModDate", null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String response = new String(responseBody);
                    lastModDate = DateConversion.formatStringToDate(response);
                    localGroupeModDate = currentGroupe.getLastModDate();
                    responseController.respondOnRecevedData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode >= 400 && statusCode < 500){
                    Toast.makeText(getApplicationContext(),"Server innacessibile!\nRiprova in una altro momento",
                            Toast.LENGTH_SHORT).show();
                    responseController.respondOnRecevedData();
                }
            }
        });
    }

    public void getAllPostsRestOperation(){
        progressDialog = new ProgressDialog(PostViewActivity.this);
        progressDialog.setTitle("Caricamento Dati....");
        progressDialog.show();

        FirebaseRestRequests.get("Communities/Gruppi/" + currentGroupName + "/Posts", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String jsonString = new String(responseBody);
                    List<Post> tempPostList = JSONParser.getAllPosts(jsonString);
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(),tempPostList);
                    currentGroupe.setPostList(tempPostList);
                    recyclerView.setAdapter(recyclerAdapter);
                    comunity.updateGroupePostList(currentGroupe, tempPostList);
                    FileOperations.writeObject(getApplicationContext(),"COMUNITY", comunity);
                    upToDate = true;
                    responseController.respondOnRecevedData();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode >= 400 && statusCode < 500){
                    responseController.respondOnRecevedData();
                    Toast.makeText(getApplicationContext(),"Server innacessibile!\nRiprova in una altro momento",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        comunity = (Comunity) FileOperations.readObject(getApplicationContext(), "COMUNITY");
        currentGroupe = comunity.getGroupeByName(currentGroupName);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), currentGroupe.getPostList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onAddFabClicked(View v){
        intent = new Intent(getApplicationContext(),AddPostActivity.class);
        startActivity(intent);
    }

    @Override
    public void respondOnRecevedData() {
        if(lastModDate != null && !upToDate){
            if (lastModDate.after(localGroupeModDate)) {
                getAllPostsRestOperation();
                currentGroupe.setLastModDate(new Date());
                Toast.makeText(getApplicationContext(), "Lista aggiornata.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Non ci sono elementi nuovi",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog.cancel();
        }

        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

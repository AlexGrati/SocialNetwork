package it.grati_alexandru.socialnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import it.grati_alexandru.socialnetwork.Model.Comunity;
import it.grati_alexandru.socialnetwork.Model.Post;
import it.grati_alexandru.socialnetwork.Utils.DateConversion;
import it.grati_alexandru.socialnetwork.Utils.FileOperations;

public class PostActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView textPostTitle;
    private TextView textPostAutore;
    private TextView textPostDataCreazione;
    private TextView textPostContenuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        textPostTitle = findViewById(R.id.textViewPostTitle);
        textPostAutore = findViewById(R.id.textViewAutorePost);
        textPostDataCreazione = findViewById(R.id.textViewDataCreazionePost);
        textPostContenuto = findViewById(R.id.textViewContenutoPost);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String groupeName = sharedPreferences.getString("GROUPE_NAME","");
        String postTitle = getIntent().getStringExtra("TITOLO_POST");

        Comunity comunity = (Comunity) FileOperations.readObject(getApplicationContext(),"COMUNITY");
        Post post = comunity.getGroupeByName(groupeName).getPostByTitle(postTitle);

        textPostTitle.setText(post.getTitolo());
        textPostAutore.setText("Publicato da " +post.getAutore());
        textPostDataCreazione.setText(", " + DateConversion.formatDateToString(post.getDataCreazeione()));
        textPostContenuto.setText(post.getContenuto());
    }
}

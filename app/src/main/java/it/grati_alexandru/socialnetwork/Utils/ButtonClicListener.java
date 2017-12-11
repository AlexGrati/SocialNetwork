package it.grati_alexandru.socialnetwork.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import it.grati_alexandru.socialnetwork.PostActivity;
import it.grati_alexandru.socialnetwork.PostViewActivity;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class ButtonClicListener implements View.OnClickListener {
    private Context context;
    public ButtonClicListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = new Intent(context, PostViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        editor.putString("GROUPE_NAME",b.getText().toString());
        editor.apply();
        Log.i("TAG","Button"+ b.getText());
        context.startActivity(intent);
    }
}

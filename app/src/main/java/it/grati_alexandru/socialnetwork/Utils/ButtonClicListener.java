package it.grati_alexandru.socialnetwork.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
        Intent intent = new Intent(v.getContext(), PostViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("GROUPE_NAME",b.getText());
        Log.i("TAG","Button"+ b.getText());
        context.startActivity(intent);
    }
}

package it.grati_alexandru.socialnetwork;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Utils.ButtonClicListener;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class ButtonAdapter extends ArrayAdapter<Gruppo> {
    private int resource;
    private List<Gruppo> groupeList;
    private LayoutInflater inflater;
    private Button mButton;
    private Context context;

    public ButtonAdapter(Context context, int resource, List<Gruppo> groupeList ){
        super(context, resource,groupeList);
        this.context = context;
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
        this.groupeList = groupeList;
    }

    @Override
    public int getCount() {
        return groupeList.size();
    }

    @Nullable
    @Override
    public Gruppo getItem(int position) {
        return groupeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            Log.d("DEBUG","INFLATING VIEW");
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        Gruppo buttonName = groupeList.get(position);
        mButton = convertView.findViewById(R.id.bItemId);
        mButton.setText(buttonName.getNome());
        mButton.setOnClickListener(new ButtonClicListener(context));
        return convertView;
    }
}
package it.grati_alexandru.socialnetwork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import it.grati_alexandru.socialnetwork.Model.Post;
import it.grati_alexandru.socialnetwork.Utils.DateConversion;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardHolder> {

    public static class CardHolder extends RecyclerView.ViewHolder{
        private Context context;
        private TextView textViewAutore;
        private TextView textViewTitolo;
        private TextView textViewDataCreazione;
        private CardView cardView;
        public CardHolder(View v, final Context context){
            super(v);
            cardView = v.findViewById(R.id.cardViewId);
            textViewAutore = v.findViewById(R.id.textViewAutore);
            textViewTitolo = v.findViewById(R.id.textViewTitolo);
            textViewDataCreazione = v.findViewById(R.id.textViewData);
            this.context = context;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("TITOLO_POST",textViewTitolo.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    private Context context;
    private List<Post> postList;
    private Post p;

    public RecyclerAdapter(Context context, List<Post> postList){
        this.context = context;
        this.postList = postList;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent,false);
        CardHolder cardHolder = new CardHolder(v,context);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        p = postList.get(position);
        holder.textViewTitolo.setText(p.getTitolo());
        holder.textViewAutore.setText(p.getAutore());
        Date date = p.getDataCreazeione();
        String s = DateConversion.formatDateToString(p.getDataCreazeione());
        holder.textViewDataCreazione.setText(s);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

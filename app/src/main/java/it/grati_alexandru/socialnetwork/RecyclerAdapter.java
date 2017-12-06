package it.grati_alexandru.socialnetwork;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.grati_alexandru.socialnetwork.Model.Post;

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
        public CardHolder(View v, Context context){
            super(v);
            cardView = (CardView)v.findViewById(R.id.cardViewId);
            textViewAutore = v.findViewById(R.id.textViewAutore);
            textViewTitolo = v.findViewById(R.id.textViewTitolo);
            textViewDataCreazione = v.findViewById(R.id.textViewData);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }

    private Context context;
    private List<Post> postList;

    public RecyclerAdapter(Context context, List<Post> postList){
        this.context = context;
        this.postList = postList;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        CardHolder cardHolder = new CardHolder(v,context);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        Post p = postList.get(position);
        holder.textViewTitolo.setText(p.getTitolo());
        holder.textViewAutore.setText(p.getAutore());
        holder.textViewDataCreazione.setText(formatDateToString(p.getDataCreazeione()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    public String formatDateToString(Date date){
        Format format2 = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
        return format2.format(date);
    }

}

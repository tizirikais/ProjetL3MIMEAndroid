package com.mydays.journal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

public class DairyAdapter extends RecyclerView.Adapter<DairyAdapter.ViewHolder> {

    private ArrayList<Billet> list;
    private final Date date;
    Activity activity;

    public DairyAdapter(Activity activity, String date) {
        this.activity = activity;
        this.date = Date.valueOf(date);
        list = (new DbHelper(activity)).getBillets(this.date);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.dairy_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void reload() {
        list = (new DbHelper(activity)).getBillets(date);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final RatingBar rating;
        private final ImageView image;
        private Billet billet;

        public ViewHolder(View itemView) {
            super(itemView);
             title = (TextView)itemView.findViewById(R.id.title);
             rating = (RatingBar)itemView.findViewById(R.id.ratingBar);
             image = (ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ViewEntryActivity.class);
                    intent.putExtra("id",billet.getId());
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Billet billet) {
            this.billet = billet;
            title.setText(billet.getTitle());
            rating.setRating(billet.getRating());
            image.setImageDrawable(Categorie.getCategies(image.getContext()).get(billet.getCatigorie()).getDrawable());
        }
    }
}

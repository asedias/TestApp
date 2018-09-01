package ru.asedias.testapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.asedias.testapp.R;
import ru.asedias.testapp.api.GetEventInfo;


/**
 * Created by rorom on 01.09.2018.
 */

public class EventViewAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final GetEventInfo.EventInfo event;
    private final String category;

    private final int TYPE_INFO = 0;
    private final int TYPE_ARTICLE = 1;
    private final int TYPE_PREDICTION = 2;

    public EventViewAdapter(Context context, GetEventInfo.EventInfo event, String category) {
        this.context = context;
        this.event = event;
        this.category = category;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_INFO: return new EventHolder(LayoutInflater.from(context).inflate(R.layout.event_info, null));
        }
        return new ArticleHolder(LayoutInflater.from(context).inflate(R.layout.article, null));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return TYPE_INFO;
        else if(position == event.articles.size() + 1) return TYPE_PREDICTION;
        return TYPE_ARTICLE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_INFO) {
            ((EventHolder)holder).bind(event);
        } else if(getItemViewType(position) == TYPE_ARTICLE) {
            String[] info = event.articles.get(position-1);
            ((ArticleHolder)holder).bind(info[0], info[1]);
        } else {
            ((ArticleHolder)holder).bind(context.getResources().getString(R.string.prediction), event.prediction);
        }
    }

    @Override
    public int getItemCount() {
        return 2 + event.articles.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        public TextView teams;
        public TextView tournament;
        public TextView time;
        public ImageView icon;

        public EventHolder(View itemView) {
            super(itemView);
            this.teams = itemView.findViewById(R.id.teams);
            this.tournament = itemView.findViewById(R.id.tournament);
            this.time = itemView.findViewById(R.id.time);
            this.icon = itemView.findViewById(R.id.icon);
        }

        public void bind(GetEventInfo.EventInfo event) {
            this.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.teams.setText(String.format("%s - %s", event.team1, event.team2));
            this.tournament.setText(event.tournament);
            this.time.setText(event.time);
            int icon = R.drawable.ic_football_24dp;
            switch (category.hashCode()) {
                case -1211969373: {
                    icon = R.drawable.ic_hockey_24dp;
                    break;
                }
                case -877324069: {
                    icon = R.drawable.ic_tennis_24dp;
                    break;
                }
                case 727149765: {
                    icon = R.drawable.ic_basketball_24dp;
                    break;
                }
                case -1160328212: {
                    icon = R.drawable.ic_volleyball_24dp;
                    break;
                }
                case -119971141: {
                    icon = R.drawable.ic_cybersport_24dp;
                    break;
                }
            }
            this.icon.setImageResource(icon);
        }
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {

        public TextView header;
        public TextView text;

        public ArticleHolder(View itemView) {
            super(itemView);
            this.header = itemView.findViewById(R.id.article_header);
            this.text = itemView.findViewById(R.id.article_text);
        }

        public void bind(String header, String text) {
            if(!header.isEmpty()) {
                this.header.setText(header);
            } else {
                this.header.setVisibility(View.GONE);
            }
            this.text.setText(text);
        }
    }
}

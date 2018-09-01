package ru.asedias.testapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.asedias.testapp.EventViewActivity;
import ru.asedias.testapp.R;
import ru.asedias.testapp.api.GetEvents;

/**
 * Created by rorom on 01.09.2018.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder> {

    private final Context context;
    private final ArrayList<GetEvents.Event> events;
    private final String category;

    public EventsAdapter(Context context, ArrayList<GetEvents.Event> events, String category) {
        this.context = context;
        this.events = events;
        this.category = category;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventHolder(LayoutInflater.from(context).inflate(R.layout.event, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    protected class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView preview;
        public TextView time;
        public TextView number;
        private GetEvents.Event info;

        public EventHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            preview = itemView.findViewById(R.id.preview);
            time = itemView.findViewById(R.id.time);
            number = itemView.findViewById(R.id.number);
        }

        public void bind(GetEvents.Event event) {
            this.info = event;
            this.title.setText(String.format("%s (%s)", event.title, event.place));
            this.preview.setText(event.preview);
            this.time.setText(event.time);
            this.number.setText(event.coefficient);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EventViewActivity.class);
            intent.putExtra("article", this.info.article);
            intent.putExtra("title", this.info.title);
            intent.putExtra("category", category);
            context.startActivity(intent);
        }
    }
}

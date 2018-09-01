package ru.asedias.testapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import ru.asedias.testapp.R;
import ru.asedias.testapp.api.Callback;
import ru.asedias.testapp.api.GetEvents;
import ru.asedias.testapp.ui.EventsAdapter;

/**
 * Created by rorom on 01.09.2018.
 */
public class EventsFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";

    private RecyclerView mList;
    private ProgressBar mLoading;
    private View mErrorView;
    private TextView mErrorDescription;
    private Button mErrorTryAgain;
    private boolean loaded = false;
    private ArrayList<GetEvents.Event> events;
    private View contentView;
    private String category = "";

    public EventsFragment() { }

    public static EventsFragment newInstance(String category) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.contentView == null) {
            this.contentView = inflater.inflate(R.layout.list, container, false);
            mList = contentView.findViewById(R.id.list);
            mList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            mLoading = contentView.findViewById(R.id.progressbar);
            mErrorView = contentView.findViewById(R.id.error);
            mErrorDescription = contentView.findViewById(R.id.error_description);
            mErrorTryAgain = contentView.findViewById(R.id.error_try_again);
        }
        return this.contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(!loaded) {
            runRequest();
        } else {
            mLoading.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
            mList.setAdapter(new EventsAdapter(getActivity(), EventsFragment.this.events, category));
        }
    }

    private void runRequest() {
        if (getArguments() != null) {
            this.category = getArguments().getString(ARG_CATEGORY);
        }
        new GetEvents(category).setCallback(new Callback<ArrayList<GetEvents.Event>>() {
            @Override public void onError(Exception e) {
                mLoading.setVisibility(View.GONE);
                mList.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                mErrorTryAgain.setOnClickListener(v ->  {
                    mLoading.setVisibility(View.VISIBLE);
                    mErrorView.setVisibility(View.GONE);
                    runRequest();
                });
                String name = e.getClass().getSimpleName();
                mErrorDescription.setText(name.equals("UnknownHostException") ? R.string.error_connection :
                        (name.equals("SocketTimeoutException") ? R.string.error_timeout : R.string.error_parse));
                Toast.makeText(getActivity(), String.format("%s: %s", name, e.getLocalizedMessage()) , Toast.LENGTH_SHORT).show();
            }

            @Override public void onUIThread(ArrayList<GetEvents.Event> events) {
                EventsFragment.this.events = events;
                EventsFragment.this.loaded = true;
                mLoading.setVisibility(View.GONE);
                mList.setVisibility(View.VISIBLE);
                mList.setAdapter(new EventsAdapter(getActivity(), EventsFragment.this.events, category));
            }
        }).run();
    }
}

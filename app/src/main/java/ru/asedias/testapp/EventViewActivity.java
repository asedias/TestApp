package ru.asedias.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.asedias.testapp.api.Callback;
import ru.asedias.testapp.api.GetEventInfo;
import ru.asedias.testapp.ui.EventViewAdapter;

public class EventViewActivity extends AppCompatActivity {

    private RecyclerView mList;
    private ProgressBar mLoading;
    private View mErrorView;
    private TextView mErrorDescription;
    private Button mErrorTryAgain;
    private String article;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        this.article = getIntent().getStringExtra("article");
        this.category = getIntent().getStringExtra("category");
        this.mList = findViewById(R.id.list);
        this.mList = findViewById(R.id.list);
        this.mList.setLayoutManager(new LinearLayoutManager(this));
        this.mLoading = findViewById(R.id.progressbar);
        this.mErrorView = findViewById(R.id.error);
        this.mErrorDescription = findViewById(R.id.error_description);
        this.mErrorTryAgain = findViewById(R.id.error_try_again);
        runRequest();
    }

    private void runRequest() {
        new GetEventInfo(this.article).setCallback(new Callback<GetEventInfo.EventInfo>() {
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
                Toast.makeText(EventViewActivity.this, String.format("%s: %s", name, e.getLocalizedMessage()) , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIThread(GetEventInfo.EventInfo document) {
                mLoading.setVisibility(View.GONE);
                mList.setVisibility(View.VISIBLE);
                mList.setAdapter(new EventViewAdapter(EventViewActivity.this, document, category));
            }
        }).run();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.wikipediaclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.rey.material.widget.ProgressView;
import com.wikipediaclient.entities.json.WikiImageDetails;
import com.wikipediaclient.network.WikipediaEndpoint;
import com.wikipediaclient.ui.CArrayAdapter;
import com.wikipediaclient.ui.TintAutoComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static butterknife.ButterKnife.findById;


public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://en.wikipedia.org/w/api.php/";
    private Activity mActivity;

    @BindView(R.id.tac_search_criteria)
    TintAutoComplete tac_search_criteria;

    @BindView(R.id.image_result)
    CircleImageView image_result;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progress_circular_search)
    ProgressView progress_circular_search;

    WikipediaEndpoint wikipediaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wikipediaService =
                retrofit.create(WikipediaEndpoint.class);

        Call<WikiImageDetails> call = wikipediaService.getImageDetails("File:Albert Einstein Head.jpg");
        call.enqueue(new Callback<WikiImageDetails>() {
            @Override
            public void onResponse(Call<WikiImageDetails> call, Response<WikiImageDetails> response) {
                int statusCode = response.code();
                WikiImageDetails wikiImageDetails = response.body();
            }

            @Override
            public void onFailure(Call<WikiImageDetails> call, Throwable t) {
                t.printStackTrace();
            }
        });


        List<CArrayAdapter.AdapterItem> items = new ArrayList<>();
        items.add(new CArrayAdapter.AdapterItem(1, "Arman"));
        items.add(new CArrayAdapter.AdapterItem(1, "Arman Saf"));
        items.add(new CArrayAdapter.AdapterItem(1, "Pejman"));
        items.add(new CArrayAdapter.AdapterItem(1, "Pejman Safi"));
        items.add(new CArrayAdapter.AdapterItem(1, "Haaaa"));
        items.add(new CArrayAdapter.AdapterItem(1, "reza"));
        CArrayAdapter arrayAdapter = new CArrayAdapter(this, R.layout.listitem_search_result, items);
        tac_search_criteria.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addUiListeners()
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tac_search_criteria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}


            private Timer timer = new Timer();
            private final long DELAY = 500; // in ms
            @Override
            public void afterTextChanged(Editable editable) {
                progress_circular_search.setVisibility(View.VISIBLE);
                progress_circular_search.start();

                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }, DELAY);

}

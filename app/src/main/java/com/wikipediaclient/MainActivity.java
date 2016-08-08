package com.wikipediaclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rey.material.widget.ProgressView;
import com.wikipediaclient.entities.json.GoogleSuggestion;
import com.wikipediaclient.entities.json.Item;
import com.wikipediaclient.entities.json.WikiImageDetails;
import com.wikipediaclient.network.GoogleEndpoint;
import com.wikipediaclient.network.WikipediaEndpoint;
import com.wikipediaclient.ui.CArrayAdapter;
import com.wikipediaclient.ui.TintAutoComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
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
    private static final String TAG = "MainAcitivty";
    public static final String WIKI_URL = "https://en.wikipedia.org/w/api.php/";
    public static final String GOOGLE_SUGGEST_URL = "https://www.googleapis.com/customsearch/";
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
    GoogleEndpoint googleService;
    Retrofit wikiRetrofit;
    Retrofit googleRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        wikiRetrofit = new Retrofit.Builder()
                .baseUrl(WIKI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wikipediaService =
                wikiRetrofit.create(WikipediaEndpoint.class);

        googleRetrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_SUGGEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        googleService =
                googleRetrofit.create(GoogleEndpoint.class);

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
        CArrayAdapter arrayAdapter = new CArrayAdapter(this, R.layout.listitem_search_result, items);
        tac_search_criteria.setAdapter(arrayAdapter);

        addUiListeners();
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

    private void addUiListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tac_search_criteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tac_search_criteria.getText().length() > 3)
                {

                }
            }
        });


        // Listener for Google Suggestions
        tac_search_criteria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            private Timer timer = new Timer();
            private final long DELAY = 1000; // in ms
            // this flag helps preventing multiple simultaneous timers running
            AtomicBoolean isTimerRunning = new AtomicBoolean(false);
            @Override
            public void afterTextChanged(Editable editable) {
                // show a progress view in UI
                showProgressView();

                // We don't want to run a new query everytime user types a single char,
                // so instead we trigger a timer and wait for DELAY millisecs, if any new requests came through,
                if (!isTimerRunning.get() && tac_search_criteria.getText().length() > 3) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    isTimerRunning.set(true);

                                    // create the request for current search criteria
                                    Call<GoogleSuggestion> call = googleService.getSuggestions(
                                            tac_search_criteria.getText().toString());

                                    // enquie the request to be executed asynchronously and update the
                                    // adapter when the response has been received
                                    call.enqueue(new Callback<GoogleSuggestion>() {
                                        @Override
                                        public void onResponse(Call<GoogleSuggestion> call, Response<GoogleSuggestion> response) {
                                            int code = response.code();
                                            Log.v(TAG, "Retrofit response code:" + code);

                                            GoogleSuggestion suggestion = response.body();
                                            CArrayAdapter tac_adapter = (CArrayAdapter) tac_search_criteria.getAdapter();
                                            tac_adapter.clear();
                                            // iterate through all the suggestions and add them in adapter's list
                                            try {
                                                int itemCounter = 0;
                                                for (Item item : suggestion.getItems()) {
                                                    // check for null results and prevent exception
                                                    if (item.getPagemap() != null) {
                                                        if (item.getPagemap().getHcard() != null
                                                                && item.getPagemap().getHcard().size() > 0) {
                                                            String suggested = item.getPagemap().getHcard().get(0).getFn();
                                                            // add the suggested item if only it contains the criteria
                                                            // believe me, it can get really irrelevant!
                                                            if (suggested.toLowerCase().contains(
                                                                    tac_search_criteria.getText().toString().toLowerCase()
                                                            )) {
                                                                // add the suggested string to adapter's list
                                                                tac_adapter.add(
                                                                        new CArrayAdapter.AdapterItem(++itemCounter,
                                                                                suggested));
                                                            }
                                                        }
                                                    }
                                                }

                                                // notify the adapter about the changes
                                                tac_adapter.notifyDataSetChanged();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                // we got an exception here but
                                                // notify the adapter about the changes, if any
                                                tac_adapter.notifyDataSetChanged();
                                            }

                                            // let another search request to run
                                            isTimerRunning.set(false);
                                            hideProgressView();
                                        }

                                        @Override
                                        public void onFailure(Call<GoogleSuggestion> call, Throwable t) {
                                            // WOW! that's a fatal exception, but not related to my code :-)
                                            t.printStackTrace();

                                            // let another search request to run
                                            isTimerRunning.set(false);
                                            hideProgressView();
                                        }
                                    });
                                }
                            });
                        }
                    }, DELAY);
                }

            }
        });
    }

    private void showProgressView()
    {
        progress_circular_search.setVisibility(View.VISIBLE);
        progress_circular_search.start();
    }

    private void hideProgressView()
    {
        progress_circular_search.setVisibility(View.INVISIBLE);
        progress_circular_search.stop();
    }
}

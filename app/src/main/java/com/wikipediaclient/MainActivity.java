package com.wikipediaclient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.wikipediaclient.entities.json.google.suggestion.GoogleSuggestion;
import com.wikipediaclient.entities.json.google.suggestion.Item;
import com.wikipediaclient.entities.json.wiki.imgdetails.WikiImageDetails;
import com.wikipediaclient.network.GoogleEndpoint;
import com.wikipediaclient.network.WikipediaEndpoint;
import com.wikipediaclient.ui.CArrayAdapter;
import com.wikipediaclient.ui.TintAutoComplete;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @BindView(R.id.progress_search_criteria)
    ProgressView progress_circular_search;

    @BindView(R.id.progress_img)
    ProgressView progress_img;

    @BindView(R.id.txtview_article_abstract)
    TextView txtview_article_abstract;

    @BindView(R.id.txtview_article_title)
    TextView txtview_article_title;

    @BindView(R.id.btn_search)
    Button btn_search;

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

        // initialize retrofit object
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



        // init the autocomplete textview (tac_search_criteria) with an empty listed array adapter
        List<CArrayAdapter.AdapterItem> items = new ArrayList<>();
        CArrayAdapter arrayAdapter = new CArrayAdapter(this, R.layout.listitem_search_result, items);
        tac_search_criteria.setAdapter(arrayAdapter);

        // add ui listeners
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

        tac_search_criteria.setImeActionLabel("Search", KeyEvent.KEYCODE_ENTER);

        tac_search_criteria.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String searchCriteria = tac_search_criteria.getText().toString();

                    // we don't wnat to search for criteria less than 3 character
                    if (searchCriteria.length() > 3)
                    {
                        searchAndShowArticleDetails(searchCriteria);
                        searchForBestArticleImage(searchCriteria);
                    }

                    return true;
                }
                return false;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchCriteria = tac_search_criteria.getText().toString();

                // we don't want to search for criteria less than 3 character
                if (searchCriteria.length() > 3)
                {
                    searchAndShowArticleDetails(searchCriteria);
                    searchForBestArticleImage(searchCriteria);
                }
            }
        });


        // triggers when user selects an item from suggestion list
        tac_search_criteria.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String searchCriteria = tac_search_criteria.getText().toString();

                // we don't want to search for criteria less than 3 character
                if (searchCriteria.length() > 3)
                {
                    searchAndShowArticleDetails(searchCriteria);
                    searchForBestArticleImage(searchCriteria);
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
                                    // show a progress view in UI
                                    showSuggestionProgressView();
                                    isTimerRunning.set(true);

                                    Log.i(TAG, "Loading suggestions for " + tac_search_criteria.getText());
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
                                            hideSuggestionProgressView();
                                        }

                                        @Override
                                        public void onFailure(Call<GoogleSuggestion> call, Throwable t) {
                                            // WOW! that's a fatal exception, but not related to my code :-)
                                            t.printStackTrace();

                                            // let another search request to run
                                            isTimerRunning.set(false);
                                            hideSuggestionProgressView();
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



    private void searchAndShowArticleDetails(String searchCriteria)
    {
        // show loading progress
        showImageProgressView();

        Log.i(TAG, "Loading article details for criteria " + searchCriteria);

        Call<ResponseBody> articleDetailsCall = wikipediaService.getArticleDetails(searchCriteria);
        articleDetailsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    // The response from Wikipedia API is a dynamic Json, so we can't use
                    // automatic deserialization to predefined object types,
                    // we need to parse the json manually
                    String body = response.body().string();
                    JsonElement jelement = new JsonParser().parse(body);
                    JsonObject  jobject = jelement.getAsJsonObject();
                    jobject = (JsonObject)jobject.get("query");
                    jobject = (JsonObject)jobject.get("pages");
                    Set<Map.Entry<String, JsonElement>> entries = jobject.entrySet();//will return members of your object
                    for (Map.Entry<String, JsonElement> entry: entries) {
                        jobject = (JsonObject)jobject.get(entry.getKey());

                        Log.i(TAG, "title: " + jobject.get("title").getAsString());
                        Log.i(TAG, "extract: " + jobject.get("extract").getAsString());

                        // show article detail on UI:
                        txtview_article_title.setText(jobject.get("title").getAsString());
                        txtview_article_abstract.setText(Html.fromHtml(jobject.get("extract").getAsString()));

                        // we only need the first elemnt
                        break;
                    }

                    Log.i(TAG, "code: " + statusCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void searchForBestArticleImage(final String searchCriteria)
    {
        Log.i(TAG, "Searching for original article image url for criteria: " + searchCriteria);

        Call<ResponseBody> imagesCall = wikipediaService.getImages(searchCriteria);
        imagesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    // The response from Wikipedia API is a dynamic Json, so we can't use
                    // automatic deserialization to predefined object types,
                    // we need to parse the json manually
                    String body = response.body().string();
                    JsonElement jelement = new JsonParser().parse(body);
                    JsonObject  jobject = jelement.getAsJsonObject();
                    jobject = (JsonObject)jobject.get("query");
                    jobject = (JsonObject)jobject.get("pages");
                    // will return members of "pages" object
                    Set<Map.Entry<String, JsonElement>> entries = jobject.entrySet();
                    for (Map.Entry<String, JsonElement> entry: entries) {
                        jobject = (JsonObject)jobject.get(entry.getKey());

                        jobject = (JsonObject)jobject.get("thumbnail");

                        // this is the actual image url that we are looking for
                        downloadAndShowImage(jobject.get("original").getAsString());

                        // we only need the first elemnt
                        break;
                    }

                    Log.i(TAG, "code: " + statusCode);
                } catch (Exception e) {
                    e.printStackTrace();

                    // Oops, we didn't find the article main page pic,
                    // try search in a more general way:
                    searchForAllArticleImages(searchCriteria);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

                hideImageProgressView(false);
            }
        });
    }


    private void searchForAllArticleImages(final String searchCriteria) {
        Log.i(TAG, "Searching for best image url for criteria: " + searchCriteria);
        Call<ResponseBody> imagesCall = wikipediaService.getImages(searchCriteria);
        imagesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    String body = response.body().string();
                    JsonElement jelement = new JsonParser().parse(body);
                    JsonObject jobject = jelement.getAsJsonObject();
                    jobject = (JsonObject) jobject.get("query");
                    jobject = (JsonObject) jobject.get("pages");
                    // will return members of "pages" object
                    Set<Map.Entry<String, JsonElement>> entries = jobject.entrySet();
                    for (Map.Entry<String, JsonElement> entry : entries) {
                        jobject = (JsonObject) jobject.get(entry.getKey());
                        JsonArray imgsJsonArray = (JsonArray) jobject.get("images");

                        String bestImgTitle = "";
                        for (int i = 0; i < imgsJsonArray.size(); i++) {
                            String imgTitle = imgsJsonArray.get(i).getAsJsonObject()
                                    .get("title").getAsString();
                            // The images that contain the search criteria are more probably
                            // relevant to the search subject
                            if (imgTitle.toLowerCase().contains(searchCriteria.toLowerCase())
                                    && !imgTitle.endsWith("svg")) {
                                Log.i(TAG, "Img title: " + imgTitle);
                                bestImgTitle = imgTitle;
                                break;
                            }
                        }
                        // we didn't find what we want, use the first image
                        if (bestImgTitle.equals("")) {
                            bestImgTitle = imgsJsonArray.get(0).getAsJsonObject().get("title").getAsString();
                        }
                        // Ok we got the image title, but it's not an http URL,
                        // we need to obtain the url and then show it on UI
                        searchAndGetImageUrl(bestImgTitle);

                        // we only need the first elemnt
                        break;
                    }

                    Log.i(TAG, "code: " + statusCode);
                } catch (Exception e) {
                    e.printStackTrace();

                    hideImageProgressView(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

                hideImageProgressView(false);
            }
        });

    }

    private void searchAndGetImageUrl(String imgTitle)
    {
        Call<WikiImageDetails> call = wikipediaService.getImageDetails(imgTitle);
        call.enqueue(new Callback<WikiImageDetails>() {
            @Override
            public void onResponse(Call<WikiImageDetails> call, Response<WikiImageDetails> response) {
                WikiImageDetails wikiImageDetails = response.body();
                String imgUrl = wikiImageDetails.getQuery().getPages().get1().getImageinfo().get(0).getUrl();
                downloadAndShowImage(imgUrl);
            }

            @Override
            public void onFailure(Call<WikiImageDetails> call, Throwable t) {
                t.printStackTrace();

                hideImageProgressView(false);
            }
        });
    }

    private void downloadAndShowImage(String imgUrl)
    {
        Log.i(TAG, "Downloading image: " + imgUrl);

        // downloading the article image asynchronously and then show on UI
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(imgUrl)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                mActivity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(image_result, "Error loading image", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();

                                hideImageProgressView(false);
                            }
                        }
                );
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        image_result.setImageBitmap(bitmap);

                        hideImageProgressView(true);
                    }
                });
            }
        });
    }

    private void showImageProgressView()
    {
        image_result.setVisibility(View.INVISIBLE);
        progress_img.setVisibility(View.VISIBLE);
        progress_img.start();
    }

    private void hideImageProgressView(boolean showImage)
    {
        if (showImage)
            image_result.setVisibility(View.VISIBLE);
        else
            image_result.setVisibility(View.INVISIBLE);

        progress_img.setVisibility(View.INVISIBLE);
        progress_img.stop();
    }

    private void showSuggestionProgressView()
    {
        progress_circular_search.setVisibility(View.VISIBLE);
        progress_circular_search.start();
    }

    private void hideSuggestionProgressView()
    {
        progress_circular_search.setVisibility(View.INVISIBLE);
        progress_circular_search.stop();
    }
}

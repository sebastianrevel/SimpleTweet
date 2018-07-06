package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;

    private View.OnClickListener postListen = new View.OnClickListener() {
        public void onClick(View v) {
            EditText simpleEditText = findViewById(R.id.etCompose);
            String strValue = simpleEditText.getText().toString();
            postTweet(strValue);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(29, 161, 242)));
        client = TwitterApp.getRestClient(this);
        Button button = (Button)findViewById(R.id.post_button);
        // Register the onClick listener with the implementation above
        button.setOnClickListener(postListen);
    }

    public void postTweet(final String tweetString) {
        client.sendTweet(tweetString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Make a tweet from object

                // Use an intent to go back to timeline Activity
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent i = new Intent();
                    i.putExtra("new_tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // send the tweet with the intents (use parceler with putExtra)
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("TwitterClient", responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
            }
        });
    }
}

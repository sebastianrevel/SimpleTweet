package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    // Tweet attributes
    String body;
    long uid;
    User user;
    String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {

        return user;
    }

    public String getBody() {

        return body;
    }

    public long getUid() {

        return uid;
    }

    // Deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // Extract value from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }
}

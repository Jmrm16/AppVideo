package com.example.videosuniguajira.Activities;

import androidx.annotation.NonNull;
import com.google.firebase.appcheck.AppCheckToken;

public class YourCustomAppCheckToken extends AppCheckToken {
    private String token;
    private long expiration;

    public YourCustomAppCheckToken(String token, long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    @NonNull
    @Override
    public String getToken() {
        return token;
    }

    @Override
    public long getExpireTimeMillis() {
        return expiration;
    }
}

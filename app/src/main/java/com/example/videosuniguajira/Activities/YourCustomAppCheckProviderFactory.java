package com.example.videosuniguajira.Activities;

import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProvider;
import com.google.firebase.appcheck.AppCheckProviderFactory;

public class YourCustomAppCheckProviderFactory implements AppCheckProviderFactory {
    @NonNull
    @Override
    public AppCheckProvider create(@NonNull FirebaseApp firebaseApp) {
        // Crear y devolver un objeto AppCheckProvider
        return new YourCustomAppCheckProvider(firebaseApp);
    }
}


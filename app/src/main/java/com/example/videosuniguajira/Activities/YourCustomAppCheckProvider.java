package com.example.videosuniguajira.Activities;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProvider;
import com.google.firebase.appcheck.AppCheckToken;

public class YourCustomAppCheckProvider implements AppCheckProvider {
    public YourCustomAppCheckProvider(FirebaseApp firebaseApp) {
        // Inicializar si es necesario
    }

    @NonNull
    @Override
    public Task<AppCheckToken> getToken() {
        // Lógica para intercambiar prueba de autenticidad por un token de App Check
        String tokenFromServer = "token"; // Obtén este token desde tu servidor
        long expirationFromServer = System.currentTimeMillis() / 1000L + 3600; // Por ejemplo, 1 hora desde ahora

        // Actualizar el token tempranamente para manejar desfases de reloj
        long expMillis = expirationFromServer * 1000L - 60000L;

        // Crear el objeto AppCheckToken con el formato correcto
        AppCheckToken appCheckToken = new YourCustomAppCheckToken(tokenFromServer, expMillis);

        return Tasks.forResult(appCheckToken);
    }
}



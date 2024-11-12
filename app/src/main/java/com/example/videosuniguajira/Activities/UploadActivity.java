package com.example.videosuniguajira.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.videosuniguajira.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST = 1;
    private Uri videoUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private VideoView videoViewPreview;
    private EditText editTextTitle;
    private Spinner spinnerGenre;
    private ProgressBar uploadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        storageReference = FirebaseStorage.getInstance().getReference("videos");
        db = FirebaseFirestore.getInstance();

        Button chooseVideoBtn = findViewById(R.id.chooseVideoBtn);
        Button uploadVideoBtn = findViewById(R.id.uploadVideoBtn);
        videoViewPreview = findViewById(R.id.videoViewPreview);
        editTextTitle = findViewById(R.id.editTextTitle);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        uploadProgressBar = findViewById(R.id.uploadProgressBar);

        // Configurar el Spinner con un adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        chooseVideoBtn.setOnClickListener(v -> openFileChooser());

        uploadVideoBtn.setOnClickListener(v -> {
            if (videoUri != null) {
                uploadVideo();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
            videoViewPreview.setVideoURI(videoUri);
            videoViewPreview.start();
        }
    }

    private void uploadVideo() {
        String title = editTextTitle.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString().trim();

        if (title.isEmpty() || genre.isEmpty()) {
            Log.d("UploadActivity", "Title or genre is empty");
            return;
        }

        uploadProgressBar.setVisibility(View.VISIBLE);

        StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                + "." + getFileExtension(videoUri));

        fileReference.putFile(videoUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d("UploadActivity", "Video uploaded successfully: " + uri.toString());
                    uploadProgressBar.setVisibility(View.GONE);
                    saveVideoInfo(title, genre, uri.toString());
                }))
                .addOnFailureListener(e -> {
                    Log.e("UploadActivity", "Error uploading video", e);
                    uploadProgressBar.setVisibility(View.GONE);
                });
    }

    private void saveVideoInfo(String title, String genre, String videoUrl) {
        Map<String, Object> video = new HashMap<>();
        video.put("title", title);
        video.put("genre", genre);
        video.put("videoUrl", videoUrl);

        db.collection("videos").add(video)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });
    }

    private String getFileExtension(Uri uri) {
        // Método para obtener la extensión del archivo (opcional)
        return "mp4"; // Por ejemplo
    }
}

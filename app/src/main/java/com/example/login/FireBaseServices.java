package com.example.login;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FireBaseServices {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    public FirebaseAuth getAuth() {
        return auth;
    }
    private Uri selectedImageURL;
    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public FirebaseFirestore getFire() {
        return firestore  ;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    private static FireBaseServices instance;
    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public FireBaseServices() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        selectedImageURL = null;
    }

    public static FireBaseServices getInstance() {
        if (instance == null) {
            instance = new FireBaseServices();
        }

        return instance;
    }
}

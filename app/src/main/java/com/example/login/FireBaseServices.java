package com.example.login;

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

    public FireBaseServices() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

    }

    public static FireBaseServices getInstance() {
        if (instance == null) {
            instance = new FireBaseServices();
        }

        return instance;
    }
}

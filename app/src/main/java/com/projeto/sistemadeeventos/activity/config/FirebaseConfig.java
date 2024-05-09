package com.projeto.sistemadeeventos.activity.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConfig {
    private static FirebaseFirestore firestore;
    private static FirebaseAuth auth;

    public static String getIdUsuario() {
        FirebaseAuth fireAuth = getAuth();
        return fireAuth.getCurrentUser().getUid();
    }

    public static FirebaseFirestore getFirestore() {
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }

    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}

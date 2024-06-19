package com.example.login;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FireBaseServices {
    private FirebaseAuth auth; // لإدارة المصادقة في Firebase
    private FirebaseFirestore firestore; // للوصول إلى Firestore لقاعدة البيانات
    private FirebaseStorage storage; // للوصول إلى Firebase Storage لتخزين الملفات

    // إعادة الحصول على كائن FirebaseAuth
    public FirebaseAuth getAuth() {
        return auth;
    }
    private Uri selectedImageURL;// رابط URL للصورة المحددة

    // إعادة الحصول على كائن FirebaseFirestore
    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public FirebaseFirestore getFire() {
        return firestore  ;
    }

    // إعادة الحصول على كائن FirebaseStorage
    public FirebaseStorage getStorage() {
        return storage;
    }

    private static FireBaseServices instance; // كائن من الفئة لضمان وجود كائن واحد فقط

    // الحصول على رابط URL للصورة المحددة
    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    // تعيين رابط URL للصورة المحددة
    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    // بناء الكائن وتهيئة متغيرات Firebase
    public FireBaseServices() {
        auth = FirebaseAuth.getInstance(); // الحصول على كائن FirebaseAuth
        firestore = FirebaseFirestore.getInstance(); // الحصول على كائن FirebaseFirestore
        storage = FirebaseStorage.getInstance(); // الحصول على كائن FirebaseStorage
        selectedImageURL = null; // إعداد الرابط URL للصورة المحددة على القيمة الافتراضية
    }

    public static FireBaseServices getInstance() {
        if (instance == null) {
            instance = new FireBaseServices(); // إنشاء كائن إذا كان غير موجود
        }

        return instance;
    }
}

package com.example.login; // تعريف الحزمة التي تحتوي على هذا الكود

import androidx.annotation.NonNull; // استيراد التعليق التوضيحي لضمان أن المعلمة أو القيمة غير null
import androidx.appcompat.app.AppCompatActivity; // استيراد الكلاس الأساسي لنشاط باستخدام ActionBar
import androidx.fragment.app.FragmentTransaction; // استيراد الكلاس لإدارة عمليات المعاملات للشظايا
import androidx.recyclerview.widget.LinearLayoutManager; // استيراد الكلاس الذي يوفر تخطيط العناصر في قائمة بشكل خطي
import androidx.recyclerview.widget.RecyclerView; // استيراد الكلاس الأساسي لإنشاء قائمة قابلة للتمرير بكفاءة

import android.content.Intent; // استيراد الكلاس الذي يستخدم لإرسال البيانات بين الأنشطة المختلفة
import android.os.Bundle; // استيراد الكلاس الذي يحتوي على بيانات الحالة المحفوظة للنشاط
import android.os.Handler; // استيراد الكلاس لتنفيذ الشفرات في وقت محدد
import android.util.Log; // استيراد الكلاس الذي يوفر قدرات التسجيل (logging)
import android.view.Gravity; // استيراد الكلاس لتحديد مواقع العرض
import android.view.KeyEvent; // استيراد الكلاس لمعالجة أحداث المفاتيح
import android.view.LayoutInflater; // استيراد الكلاس الذي يستخدم لتوسيع تخطيطات XML
import android.view.MenuItem; // استيراد الكلاس لتمثيل عنصر في قائمة
import android.view.MotionEvent; // استيراد الكلاس لمعالجة أحداث اللمس
import android.view.View; // استيراد الكلاس الأساسي لعرض واجهات المستخدم
import android.widget.ImageView; // استيراد الكلاس الذي يستخدم لعرض الصور
import android.widget.LinearLayout; // استيراد الكلاس الذي يستخدم لتخطيط العناصر بشكل خطي
import android.widget.PopupWindow; // استيراد الكلاس لعرض نافذة منبثقة
import android.widget.TextView; // استيراد الكلاس الذي يستخدم لعرض النصوص
import android.widget.Toast; // استيراد الكلاس الذي يستخدم لعرض رسائل قصيرة

import com.google.android.gms.tasks.OnCompleteListener; // استيراد الكلاس للاستماع لإتمام المهام
import com.google.android.gms.tasks.Task; // استيراد الكلاس لتمثيل المهام غير المتزامنة
import com.google.android.material.bottomappbar.BottomAppBar; // استيراد الكلاس لشريط التطبيق السفلي
import com.google.android.material.bottomnavigation.BottomNavigationView; // استيراد الكلاس لشريط التنقل السفلي
import com.google.android.material.floatingactionbutton.FloatingActionButton; // استيراد الكلاس للزر العائم
import com.google.android.material.navigation.NavigationBarView; // استيراد الكلاس لشريط التنقل

import com.google.firebase.firestore.QueryDocumentSnapshot; // استيراد الكلاس لتمثيل مستند من نتائج الاستعلام
import com.google.firebase.firestore.QuerySnapshot; // استيراد الكلاس لتمثيل نتائج الاستعلام

import java.util.ArrayList; // استيراد الكلاس لإنشاء قائمة ديناميكية
import java.util.List; // استيراد الكلاس لتمثيل قائمة من العناصر

// تعريف صف AllPet الذي يرث من AppCompatActivity
public class AllPet extends AppCompatActivity {
    private RecyclerView rvAllpet; // تعريف متغير لعرض قائمة الحيوانات الأليفة
    AdapterPet adapter; // تعريف متغير لمحول RecyclerView
    FireBaseServices fbs; // تعريف متغير لخدمات Firebase
    MyCallBack myCallback; // تعريف متغير لاستدعاء واجهة رد الاتصال
    ArrayList<Pet> pets; // تعريف قائمة لتخزين الحيوانات الأليفة
    View view; // تعريف متغير لمشاهدة العرض
    ImageView loader; // تعريف متغير لعرض مؤشر التحميل
    long itemCount = 0; // تعريف متغير لحساب عدد العناصر
    TextView error; // تعريف متغير لعرض رسائل الخطأ
    FloatingActionButton floatingActionButton; // تعريف متغير للزر العائم
    BottomAppBar bottomAppBar; // تعريف متغير لشريط التطبيق السفلي
    BottomNavigationView bottomNavigationView; // تعريف متغير لشريط التنقل السفلي
    PopupWindow popupWindow = null; // تعريف متغير لنافذة منبثقة

    // تعريف دالة لمعالجة الضغط على الأزرار في الجهاز
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            try {
                if (popupWindow == null) {
                    //dbMessage.Message(getApplicationContext(),"error");
                }
            } catch (Exception ex) {
                //dbMessage.Message(getApplicationContext(),"err");
            }
            // dbMessage.Message(getApplicationContext(),"dd");
            //return false;
        }
        return true; //super.onKeyDown(keyCode, event);
    }

    // تعريف دالة لتنفيذ حركة بسيطة على العنصر
    private void animate() {
        //error.setX(-1000);
        error.animate().translationX(-200).setDuration(600).translationX(0);
    }

    // تعريف دالة onCreate التي تنفذ عند إنشاء النشاط
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        popupWindow = null; // تهيئة النافذة المنبثقة
        setContentView(R.layout.activity_all_pet); // تعيين التخطيط للنشاط

        error = findViewById(R.id.error1); // العثور على عرض النص للخطأ
        // animate();
        loader = findViewById(R.id.loader); // العثور على مؤشر التحميل
        view = findViewById(R.id.viewadd); // العثور على العرض للإضافة
        floatingActionButton = findViewById(R.id.fab); // العثور على الزر العائم
        bottomAppBar = (BottomAppBar) findViewById(R.id.bottomAppBar); // العثور على شريط التطبيق السفلي
        bottomNavigationView = (BottomNavigationView) findViewById(R.id._bottomNavigationView); // العثور على شريط التنقل السفلي
        bottomNavigationView.setSelectedItemId(R.id.home); // تعيين العنصر المحدد افتراضيًا

        // تعيين مستمع للزر العائم
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllPet.this, PetAdd.class); // إنشاء نية للانتقال إلى نشاط إضافة الحيوانات الأليفة
                startActivity(intent); // بدء النشاط الجديد
            }
        });

        // تعيين مستمع لعناصر شريط التنقل السفلي
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                try {
                    switch (item.getItemId()) {
                        case R.id.profile: // في حالة تحديد عنصر "الملف الشخصي"
                            dbControls.hideControl(error); // إخفاء عنصر الخطأ
                            error.setTranslationX(-1000f); // تعيين موضع الخطأ
                            item.setChecked(true); // تعيين العنصر كمحدد
                            break;
                        case R.id.home: // في حالة تحديد عنصر "الصفحة الرئيسية"
                            if (itemCount == 0) {
                                dbControls.showControl(error); // عرض عنصر الخطأ
                                animate(); // تنفيذ الحركة على عنصر الخطأ
                            }
                            item.setChecked(true); // تعيين العنصر كمحدد
                            break;
                        case R.id.exit: // في حالة تحديد عنصر "الخروج"
                            item.setChecked(true); // تعيين العنصر كمحدد
                            Handler handler = new Handler(); // إنشاء معالج للتأخير
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(AllPet.this, MainActivity.class); // إنشاء نية للانتقال إلى النشاط الرئيسي
                                    startActivity(i); // بدء النشاط الجديد
                                }
                            }, 200); // تأخير لمدة 200 مللي ثانية
                            break;
                        case R.id.bluetooth: // في حالة تحديد عنصر "البلوتوث"
                            item.setChecked(true); // تعيين العنصر كمحدد
                            Intent i = new Intent(AllPet.this, BtControllerActivity.class); // إنشاء نية للانتقال إلى نشاط التحكم بالبلوتوث
                            startActivity(i); // بدء النشاط الجديد
                            break;
                    }
                } catch (Exception ex) {
                    dbMessage.Message(getApplicationContext(), ex.getMessage()); // عرض رسالة الخطأ
                }
                return false;
            }
        });

        try {
            fbs = FireBaseServices.getInstance(); // الحصول على مثيل لخدمات Firebase
            pets = new ArrayList<Pet>(); // تهيئة قائمة الحيوانات الأليفة
            readData(); // استدعاء الدالة لقراءة البيانات من Firebase

            myCallback = new MyCallBack() {
                @Override
                public void onCallback(List<Pet> restsList) {
                    dbControls.hideControl(loader); // إخفاء مؤشر التحميل
                    RecyclerView recyclerView = findViewById(R.id.rvRestsAllRest); // العثور على عنصر RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllPet.this)); // تعيين التخطيط الخطي
                    adapter = new AdapterPet(AllPet.this, pets); // تهيئة المحول
                    try {
                        recyclerView.setAdapter(adapter); // تعيين المحول لـ RecyclerView
                    } catch (Exception ex) {
                        dbMessage.Message(getApplicationContext(), ex.getMessage()); // عرض رسالة الخطأ
                    }
                }
            };
        } catch (Exception ex) {
            dbMessage.Message(getApplicationContext(), ex.getMessage()); // عرض رسالة الخطأ
        }
    }

    // دالة لقراءة البيانات من Firebase
    private void readData() {
        try {
            fbs.getFire().collection("pets")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            itemCount = task.getResult().size(); // الحصول على عدد العناصر
                            if (itemCount == 0)
                                animate(); // تنفيذ الحركة إذا لم تكن هناك عناصر
                            else
                                dbControls.hideControl(error); // إخفاء عنصر الخطأ

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try {
                                        pets.add(document.toObject(Pet.class)); // إضافة العناصر إلى قائمة الحيوانات الأليفة
                                    } catch (Exception ex) {
                                        dbMessage.Message(getApplicationContext(), ex.getMessage()); // عرض رسالة الخطأ
                                    }
                                }
                                myCallback.onCallback(pets); // استدعاء رد الاتصال بعد الحصول على البيانات
                            } else {
                                Log.e("AllPetActivity: readData()", "Error getting documents.", task.getException()); // تسجيل الخطأ في الحصول على المستندات
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show(); // عرض رسالة الخطأ
        }
    }
}

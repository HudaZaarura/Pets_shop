package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

// Activity لعرض تفاصيل حيوان أليف
public class PetDetailsActivity extends AppCompatActivity {
    private TextView detailsprice, detailslocation,detailsdescription,detailsage,detailsgender,detailscontactnum,detailscategory;
    private ImageView ivPhoto;
    private Button callbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
         loadProfilePet();

    }

    @SuppressLint("SuspiciousIndentation")
    // تحميل تفاصيل الحيوان الأليف
    private  void loadProfilePet(){
        connectComponents();
        Intent i = this.getIntent();
        Pet pet= (Pet) getIntent().getExtras().getSerializable("pet");
        detailsprice.setText(pet.getPrice());
        detailsdescription.setText(pet.getDescription());
        detailslocation.setText(pet.getLocation());
        detailscategory.setText(pet.getCategory());
        detailscontactnum.setText(pet.getContactnum());
        if(!pet.getPhoto().isEmpty())
        Picasso.get().load(pet.getPhoto()).into(ivPhoto);// تحميل الصورة باستخدام مكتبة Picasso
        detailsage.setText(pet.getAge());
        detailsgender.setText(pet.getGender());
    }
    // الرجوع إلى الشاشة السابقة عند الضغط على الزر الرجوع
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // ربط مكونات واجهة المستخدم بمتغيرات البرنامج
    private void connectComponents() {
        detailsprice = findViewById(R.id.pricedetails);
        detailsdescription = findViewById(R.id.descriptiondetails);
        detailslocation = findViewById(R.id.locationdetails);
        detailscategory = findViewById(R.id.categorydetails);
        detailscontactnum = findViewById(R.id.contactnumdetails);
        ivPhoto = findViewById(R.id.imagedetails3);
        detailsage=findViewById(R.id.agedetails);
        detailsgender=findViewById(R.id.genderdetails);
        callbtn=findViewById(R.id.callButton);

        // التصرف عند الضغط على زر الاتصال
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callContact();// استدعاء الدالة callContact() عند الضغط على زر الاتصال
            }
        });
    }

    // اتصال بالرقم المحدد عند الضغط على الزر "اتصال"
    public void callContact(){
        Uri number=Uri.parse("tel:"+detailscontactnum.getText().toString()); // إنشاء URI لرقم الهاتف
        Intent callIntent=new Intent(Intent.ACTION_DIAL,number);// إنشاء نية لفتح التطبيق للاتصالات مع الرقم
        startActivity(callIntent);// بدء النية للاتصال
    }
}
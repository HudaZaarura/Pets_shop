package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PetDetailsActivity extends AppCompatActivity {
    private TextView detailsprice, detailslocation,detailsdescription,detailsage,detailsgender,detailscontactnum,detailscategory;
    private ImageView ivPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
         loadProfilePet();

    }

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
        Picasso.get().load(pet.getPhoto()).into(ivPhoto);
        detailsage.setText(pet.getAge());
        detailsgender.setText(pet.getGender());
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void connectComponents() {
        detailsprice = findViewById(R.id.pricedetails);
        detailsdescription = findViewById(R.id.descriptiondetails);
        detailslocation = findViewById(R.id.locationdetails);
        detailscategory = findViewById(R.id.categorydetails);
        detailscontactnum = findViewById(R.id.contactnumdetails);
        ivPhoto = findViewById(R.id.imageViewrow);
        detailsage=findViewById(R.id.agedetails);
        detailsgender=findViewById(R.id.genderdetails);
    }
}
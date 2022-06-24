package com.example.login;

import static com.example.login.R.layout.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class PetAdd extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private String gender;

    private static final String TAG = "PetAddActivity";
    private EditText etlocation, etdiscreption, etage, etPhone, etprice;
    private TextView etgender;
    private Spinner spkind;
    private ImageView ivPhoto;
    private FireBaseServices fbs;
    private RadioButton rd1, rd2;
    private Uri filePath;
    private StorageReference storageReference;
    private String refAfterSuccessfullUpload = null;
    private String downloadableURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(activity_pet_add);
            //* addListenerOnButton();
            // dbMessage.Message(getApplicationContext(),"dd");
            getSupportActionBar().hide();
        }
        catch (Exception ex){
            dbMessage.Message(getApplicationContext(),ex.getMessage());
        }
        connectComponents();
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radiomale:
                if (checked)
                    gender="male";
                    // Pirates are the best
                    break;
            case R.id.radiofemale:
                if (checked)
                    gender="female";
                    // Ninjas rule
                    break;
        }
    }

    private void connectComponents() {
        etlocation = findViewById(R.id.etlocation);
        etprice = findViewById(R.id.etprice);
        etdiscreption = findViewById(R.id.etdisc);
        etage = findViewById(R.id.etage);
        etPhone = findViewById(R.id.etcontactnumber);
        spkind = findViewById(R.id.spkind);
        ivPhoto = findViewById(R.id.ivphotoPetAdd);
        fbs = FireBaseServices.getInstance();
        // android.R.layout.simple_list_item_1
        ArrayAdapter arrayAdapter=new ArrayAdapter<PetCategory>(this, text_spinner, PetCategory.values());

        arrayAdapter.setDropDownViewResource(spinner_drop_down);
        spkind.setAdapter(arrayAdapter);
        storageReference = fbs.getStorage().getReference();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = findViewById(R.id.btnDisplay);

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void add(View view) {
        // check if any field is empty
        try {
            String location, description, age, phone, kind, photo, price;
            String Gender = gender;
            location = etlocation.getText().toString();
            description = etdiscreption.getText().toString();
            price = etprice.getText().toString();
            age = etage.getText().toString();
            phone = etPhone.getText().toString();
            kind = spkind.getSelectedItem().toString();
            int radio=radioGroup.getCheckedRadioButtonId();
            if (ivPhoto.getDrawable() == null)
                photo = "no_image";
            else photo = downloadableURL;


          //  dbMessage.Message(getApplicationContext(),String.valueOf(photo));
            if (radio==-1|| location.trim().isEmpty() || description.trim().isEmpty() || age.trim().isEmpty() ||
                    phone.trim().isEmpty() || kind.trim().isEmpty() ) {
                Toast.makeText(this, R.string.err_fields_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
            gender=radioButton.getText().toString();
            Pet pet = new Pet(phone, price, location, PetCategory.valueOf(kind), photo, gender, age, description);
            fbs.getFire().collection("pets")
                    .add(pet)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            dbMessage.Message(getApplicationContext(),"New Pet Sotre");
                            /*
                            location = etlocation.getText().toString();
            description = etdiscreption.getText().toString();
            price = etprice.getText().toString();
            age = etage.getText().toString();
            phone = etPhone.getText().toString();
            kind = spkind.getSelectedItem().toString();
            int radio=radioGroup.getCheckedRadioButtonId();
                             */
                            etlocation.setText("");
                            etdiscreption.setText("");
                            etprice.setText("");
                            etage.setText("");
                            etPhone.setText("");
                            RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
                            radioButton.setChecked(false);
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

        }
        catch (Exception ex){
            dbMessage.Message(getApplicationContext(),ex.getMessage());
        }
    }

    public void selectPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 40);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                        filePath = data.getData();
                    Picasso.get().load(filePath).into(ivPhoto);

                        ivPhoto.setBackground(null);

                        uploadImage();


                }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            String fileNameStr = filePath.toString().substring(filePath.toString().lastIndexOf("/")+1);
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + filePath.getLastPathSegment());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(PetAdd.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    refAfterSuccessfullUpload = ref.toString();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(PetAdd.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }
}
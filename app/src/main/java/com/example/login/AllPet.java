package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllPet extends AppCompatActivity {
    private RecyclerView rvAllpet;
    AdapterPet adapter;
    FireBaseServices fbs;
    MyCallBack myCallback;
    ArrayList<Pet> pets;
    View view;
    ImageView loader;
    long itemCount=0;
    TextView error;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    PopupWindow popupWindow=null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            try{

            if(popupWindow==null){
                //dbMessage.Message(getApplicationContext(),"error");

            }}
            catch (Exception ex){
                //dbMessage.Message(getApplicationContext(),"err");
            }
           // dbMessage.Message(getApplicationContext(),"dd");
            //return false;
        }
        return true;//super.onKeyDown(keyCode, event);
    }

    private void animate(){
        //error.setX(-1000);
        error.animate().translationX(-200).setDuration(600).translationX(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        popupWindow=null;
        setContentView(R.layout.activity_all_pet);
        error=findViewById(R.id.error1);
       // animate();
        loader=findViewById(R.id.loader);
        view=findViewById(R.id.viewadd);
        floatingActionButton=findViewById(R.id.fab);
        bottomAppBar=(BottomAppBar)findViewById(R.id.bottomAppBar);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id._bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllPet.this,PetAdd.class);
                startActivity(intent);

//                try {
//                    view = getLayoutInflater().inflate(R.layout.activity_pet_add, null);
//                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//                }
//                catch (Exception ex){
//                    dbMessage.Message(getApplicationContext(),ex.getMessage());
//                }

     //          view.setElevation(4.1F);
       //         dbControls.showControl(view);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                try {

                    switch (item.getItemId()) {
                        case R.id.profile:
                            dbControls.hideControl(error);
                            error.setTranslationX(-1000f);
                            item.setChecked(true);

                          //  bottomNavigationView.getChildAt(0).setForeground(getDrawable(R.color.black));


                            break;
                        case R.id.home:
                            if(itemCount==0){
                                dbControls.showControl(error);
                                animate();
                                //error.animate().translationX(-1000f);
                            }
                            item.setChecked(true);

                            break;

                        case  R.id.exit:
                            item.setChecked(true);

                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i=new Intent(AllPet.this,MainActivity.class);

                                    startActivity(i);
                                }
                            },200);
                            break;


                        case R.id.bluetooth:
                            item.setChecked(true);
                            Intent i=new Intent(AllPet.this,BtControllerActivity.class);
                            startActivity(i);

                            break;

                    }
                }
                catch (Exception ex){
                    dbMessage.Message(getApplicationContext(),ex.getMessage());
                }
                return false;
            }
        });



        try {


            fbs = FireBaseServices.getInstance();
            pets = new ArrayList<Pet>();
              readData();
            myCallback = new MyCallBack() {
                @Override
                public void onCallback(List<Pet> restsList) {
                    dbControls.hideControl(loader);
                    //dbMessage.Message(getApplicationContext(),String.valueOf(restsList.size()));
                    RecyclerView recyclerView = findViewById(R.id.rvRestsAllRest);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AllPet.this));
                    adapter = new AdapterPet(AllPet.this, pets);
                    //dbMessage.Message(getApplicationContext(),String.valueOf(adapter.getItemCount()));
                   try{
                       recyclerView.setAdapter(adapter);
                   }
                   catch (Exception ex){
                     dbMessage.Message(getApplicationContext(),ex.getMessage());
                   }
                }
            };


            // set up the RecyclerView
        /*
        RecyclerView recyclerView = findViewById(R.id.rvRestsAllRest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRestaurant(this, rests);
        recyclerView.setAdapter(adapter);*/
        }
        catch (Exception ex){
            dbMessage.Message(getApplicationContext(),ex.getMessage());
        }
    }

    private void readData() {
        try {

            fbs.getFire().collection("pets")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            itemCount=task.getResult().size();
                            if(itemCount==0)
                                animate();
                            else
                                dbControls.hideControl(error);

                           // dbMessage.Message(getApplicationContext(),String.valueOf(task.getResult().size()));
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    try{
                                        pets.add(document.toObject(Pet.class));
                                      //  dbMessage.Message(getApplicationContext(),"d");
                                    }catch (Exception ex){
//                                        error.setTranslationX(0);
//                                        dbControls.showControl(error);
//                                        error.setText(ex.getMessage());
                                        dbMessage.Message(getApplicationContext(),ex.getMessage());
                                    }
                                  //
                                }

                               myCallback.onCallback(pets);
                            } else {
                                Log.e("AllPetActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
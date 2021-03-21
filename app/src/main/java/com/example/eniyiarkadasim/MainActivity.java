package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater(R.menu.mainmenu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getMenuInflater(int mainmenu) {
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case R.id.menuQuit :
                quit();



        }

        return super.onOptionsItemSelected(item);
    }

    private void quit() {

        FirebaseAuth.getInstance().signOut();

        Intent intentM = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intentM);
        intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

 @Override
          protected void onResume() {


       makeControl();
        super.onResume();
    }





  private void makeControl() {

        if(FirebaseAuth.getInstance() == null){


           Intent intentM = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentM);
            intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
    }
}
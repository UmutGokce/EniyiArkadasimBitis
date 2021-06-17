package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity{

  //  Button crash = (Button)findViewById(R.id.crash);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  /*    crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Test Crash");
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.optmenu:
               /* AlertDialog.Builder ask = new AlertDialog.Builder(MainActivity.this);
                ask.setMessage("Emin Misiniz ? ");
                ask.setCancelable(true);
                ask.setNegativeButton("Görüşürüz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intenty = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intenty);

                    }
                });
                ask.setPositiveButton("Hayır, Devam Etmek İstiyorum", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = ask.create();
                alertDialog.show();
*/
                FirebaseAuth.getInstance().signOut();
                Intent intenty = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intenty);

            case R.id.account :
                Intent intento = new Intent(MainActivity.this,AccountInfo.class);
                startActivity(intento);

            case R.id.menuChat :
                Intent intentc = new Intent(MainActivity.this,Chat.class);
                startActivity(intentc);
        }
       /* if(id==R.id.optmenu){
                AlertDialog.Builder ask = new AlertDialog.Builder(MainActivity.this);
                ask.setMessage("Emin Misiniz ? ");
                ask.setCancelable(true);
                ask.setNegativeButton("Görüşürüz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intenty = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intenty);

                    }
                });
                ask.setPositiveButton("Hayır, Devam Etmek İstiyorum", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
                    }
                });
AlertDialog alertDialog = ask.create();
alertDialog.show();
            } */
            return true;
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
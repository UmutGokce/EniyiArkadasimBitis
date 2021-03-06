package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eniyiarkadasim.dialogs.MailFrag;
import com.example.eniyiarkadasim.dialogs.PasswFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

   private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView logKayit = (TextView) findViewById(R.id.logKayit);
        Button logBtn = (Button) findViewById(R.id.btnLogin);
        ProgressBar pbarL = (ProgressBar) findViewById(R.id.pbarL);
        EditText logMail = (EditText)findViewById(R.id.logEmail);
        EditText logPassw = (EditText)findViewById(R.id.logPassw);
        TextView confMail2 = (TextView)findViewById(R.id.confMail2);
        TextView sendPassw2 = (TextView)findViewById(R.id.tvPassw);

            logKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterAct.class);
                startActivity(intent);

            }
        });
            confMail2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainCont, new MailFrag()).commit();
                }
            });
            sendPassw2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction ftpw = getSupportFragmentManager().beginTransaction();
                    ftpw.replace(R.id.mainCont,new PasswFragment()).commit();

                }
            });
            logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!logMail.getText().toString().isEmpty()&&!logPassw.getText().toString().isEmpty()){
                    progressBarLGoster();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(logMail.getText().toString(),logPassw.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                       Toast.makeText(LoginActivity.this, "Ba??ar??yla Giri?? Yap??ld??."+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                       Intent gecis = new Intent(LoginActivity.this,MainActivity.class);
                                       startActivity(gecis);
                                   }
                                   else{
                                       Toast.makeText(LoginActivity.this, "L??tfen Mail Adresinizi Onaylay??n", Toast.LENGTH_SHORT).show();
                                       FirebaseAuth.getInstance().signOut();
                                   }

                                   progressBarLGizle();

                               }else{
                                   progressBarLGizle();
                                   Toast.makeText(LoginActivity.this, "Maalesef Bir ??eyler Yanl???? Gitti"+FirebaseAuth.getInstance().getCurrentUser().getEmail() + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                                }
                            });

                }else{
                    Toast.makeText(LoginActivity.this, "L??tfen Bo?? Alanlar?? Doldurunuz", Toast.LENGTH_SHORT).show();
                }


            }

            private void progressBarLGoster() {
                pbarL.setVisibility(View.VISIBLE);
            }
            private void progressBarLGizle(){
                pbarL.setVisibility(View.INVISIBLE);
            }

            private void myAuthStateListener (){
            mAuthStateListener= new FirebaseAuth.AuthStateListener() {


                      @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            if(firebaseAuth.getCurrentUser() != null){
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "Ba??ar??l?? Giri??", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(LoginActivity.this, "L??tfen Mail Adresinizi Onaylay??n", Toast.LENGTH_SHORT).show();
                                }

                            }
                    }
                };

            }
            protected void onStart(){
               LoginActivity.super.onStart();
                FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);


            }
            protected void onStop(){

                LoginActivity.super.onStop();

                FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
            }
        });




    }

}
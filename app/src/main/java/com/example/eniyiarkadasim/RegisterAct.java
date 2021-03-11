package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterAct extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button btnKayit = (Button)findViewById(R.id.btnKayit);
        EditText eMail = (EditText) findViewById(R.id.eMail);
        EditText passw = (EditText) findViewById(R.id.passw);
        EditText passw2 = (EditText) findViewById(R.id.passw2);
        ProgressBar pbar = (ProgressBar)findViewById(R.id.pbar);
        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!eMail.getText().toString().isEmpty()&&!passw.getText().toString().isEmpty()&&!passw2.getText().toString().isEmpty()){

                    if(passw.getText().toString().equals(passw2.getText().toString())){
                        yeniUyeKayit(eMail.getText().toString(),passw.getText().toString());

                    }else{
                        Toast.makeText(RegisterAct.this, "Maalesef Parolalar Aynı Değil", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterAct.this, "Lütfen Aramıza Katılmak İçin Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show();




                }

            }

            private void yeniUyeKayit(String mail, String password) {
                progressBarGoster();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete( Task<AuthResult> task) {

                     if(task.isSuccessful()){
                         Toast.makeText(RegisterAct.this, "Uye Kaydı Yapıldı , Aramıza Hoşgeldin....", Toast.LENGTH_SHORT).show();

                     }else{
                         Toast.makeText(RegisterAct.this, "Uye Kaydı Yapılırken Bir Sorun Olustu :( , Lütfen " +
                                 "Tekrar Deneyin"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                     }
                     progressBarGizle();
                 }
             });
            }
            private void progressBarGoster(){
                pbar.setVisibility(ProgressBar.VISIBLE);


            }
            private void progressBarGizle(){
                pbar.setVisibility(ProgressBar.INVISIBLE);

            }
        });

    }
}
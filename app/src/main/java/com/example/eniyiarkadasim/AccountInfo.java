package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AccountInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        EditText accMail = (EditText)findViewById(R.id.accMail);
        EditText accId = (EditText)findViewById(R.id.accId);
        Button save = (Button)findViewById(R.id.save);
        Button passwZero = (Button)findViewById(R.id.passZero);

        accMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        accId.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        accMail.setEnabled(false);
        passwZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().sendPasswordResetEmail(accMail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AccountInfo.this, "Parola Sıfırlama Maili Gönderildi", Toast.LENGTH_SHORT).show();

                        }else{

                            Toast.makeText(AccountInfo.this, "Parola Sıfırlama Maili Gönderilirken Bir Hata Oluştu \n"+task.getException().getMessage()
                                    , Toast.LENGTH_SHORT).show();

                        }

                    }
                });



            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!accId.getText().toString().isEmpty()&& !accMail.getText().toString().isEmpty()){
                        if(!accId.getText().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString())){

                            UserProfileChangeRequest updateId = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(accId.getText().toString()).build();
                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(updateId);
                            accId.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(AccountInfo.this, "Değişiklik Yapıldı, Eyw", Toast.LENGTH_SHORT).show();



                        }else{
                            Toast.makeText(AccountInfo.this, "Değişiklik Yapılmadı", Toast.LENGTH_SHORT).show();

                        }

                }else {

                    Toast.makeText(AccountInfo.this, "Lütfen Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
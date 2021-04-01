package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);



        EditText accMail = (EditText)findViewById(R.id.accMail);
        EditText accId = (EditText)findViewById(R.id.accId);
        Button save = (Button)findViewById(R.id.save);
        Button passwZero = (Button)findViewById(R.id.passZero);
        EditText etPhone = (EditText)findViewById(R.id.etPhone);
        readUserInfo(etPhone,accMail,accId);
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
FirebaseDatabase.getInstance().getReference()
                                    .child("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("isim")
                                    .setValue(accId.getText().toString());
                            UserProfileChangeRequest updateId = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(accId.getText().toString()).build();
                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(updateId);


                            Toast.makeText(AccountInfo.this, "Değişiklik Yapıldı, Eyw", Toast.LENGTH_SHORT).show();




                        }else{
                            Toast.makeText(AccountInfo.this, "Değişiklik Yapılmadı\n", Toast.LENGTH_SHORT).show();

                        }

                }else {

                    Toast.makeText(AccountInfo.this, "Lütfen Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                }
                if(!etPhone.getText().toString().isEmpty()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("telefon")
                            .setValue(etPhone.getText().toString());


                }

            }
        });



    }



    private void readUserInfo(EditText phone, EditText mail ,EditText id) {



        DatabaseReference referans = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = referans.child("user")
                .orderByKey()
                .equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotx) {
                for(DataSnapshot snapshot : snapshotx.getChildren()){

                    User okunanKullanici = snapshot.getValue(User.class);
                    Toast.makeText(AccountInfo.this, "Adı : \n"+okunanKullanici.getIsim()+
                            "Telefonu :  \n"+okunanKullanici.getTelefon()+"User ID : \n"+okunanKullanici.getUser_id(), Toast.LENGTH_SHORT).show();
                    id.setText(okunanKullanici.getIsim());
                    phone.setText(okunanKullanici.getTelefon());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eniyiarkadasim.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AccountInfo extends AppCompatActivity {
private Uri imageUri;
    DatabaseReference db;
    FirebaseUser fuser;
    StorageReference storageReference;
    private StorageTask uploadTask;
Bitmap bitmap;
Boolean perm = false;
private static final int IMAGE_REQUEST =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);



        ImageView imgpp = (ImageView)findViewById(R.id.imgPp);
        EditText accMail = (EditText)findViewById(R.id.accMail);
        EditText accId = (EditText)findViewById(R.id.accId);
        Button save = (Button)findViewById(R.id.save);
        Button passwZero = (Button)findViewById(R.id.passZero);
        EditText etPhone = (EditText)findViewById(R.id.etPhone);
        readUserInfo(etPhone,accMail,accId);
        accMail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        accId.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        accMail.setEnabled(false);
        TextView getGallery = (TextView)findViewById(R.id.getGallery);
        TextView takePhoto = (TextView)findViewById(R.id.takePhoto);

        storageReference= FirebaseStorage.getInstance().getReference("uploads");







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

        imgpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            openImage();
                
            }



        });



    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));

    }

    private void uploadImage(){
      if(imageUri !=null){
          final StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
          uploadTask=fileReference.putFile(imageUri);
          uploadTask.continueWithTask(new Continuation< UploadTask.TaskSnapshot,Task<Uri>>() {
              @Override
              public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(task.isSuccessful()){

                        throw task.getException();

                    }
return fileReference.getDownloadUrl();
                                }
          }).addOnCompleteListener(new OnCompleteListener<Uri>() {
              @Override
              public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        Uri downloadUri=task.getResult();
                        String mUri = downloadUri.toString();
                        db=FirebaseDatabase.getInstance().getReference("user").child(fuser.getUid());
                        HashMap<String, Object> map=new HashMap<>();
                        map.put("imageUrl",mUri);
                        db.updateChildren(map);
                        
                    }
                    else{

                        Toast.makeText(AccountInfo.this, "Başarısız", Toast.LENGTH_SHORT).show();
                    }
              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                  Toast.makeText(AccountInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });

      }else {

          Toast.makeText(this, "Fotoğraf Seçilmedi", Toast.LENGTH_SHORT).show();
      }


        //return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST&& resultCode==RESULT_OK&&data != null&&data.getData()==null){
            imageUri=data.getData();


            if(uploadTask==null&& uploadTask.isInProgress()){
                Toast.makeText(this, "Upload is in progress", Toast.LENGTH_SHORT).show();


            }else{

                uploadImage();
            }
        }
    }

    private void readUserInfo(EditText phone, EditText mail , EditText id) {



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
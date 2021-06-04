package com.example.eniyiarkadasim.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eniyiarkadasim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MailFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        Button send = view.findViewById(R.id.cfBtn);
        Button cancel = view.findViewById(R.id.cancel);
        EditText mailCfControl = view.findViewById(R.id.cf2GetMail);
        EditText passwCfControl = view.findViewById(R.id.cf2GetPassword);

   cancel.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           getActivity().getSupportFragmentManager().beginTransaction().remove(MailFrag.this).commit();

       }
   });


       send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Oldu", Toast.LENGTH_SHORT).show();
                String cfMailx = mailCfControl.getText().toString();
                String cfPasswx = passwCfControl.getText().toString();
                confMailAgain();


            }

           private void confMailAgain() {
               FirebaseAuth.getInstance().signInWithEmailAndPassword(mailCfControl.getText().toString(),passwCfControl.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                          FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task2) {

                                  if(task2.isSuccessful()){
                                      Toast.makeText(getActivity(), "Başarıyla Gönderildi.", Toast.LENGTH_SHORT).show();
                                      getActivity().getSupportFragmentManager().beginTransaction().remove(MailFrag.this).commit();
                                      FirebaseAuth.getInstance().signOut();

                                  }
                                  else{
                                      Toast.makeText(getActivity(), "Mail Gönderilirken Bir Sorun Oluştu." +
                                              " \n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                  }

                              }
                          });

                           }
                       });
           }
       });


        return view;


    }



}
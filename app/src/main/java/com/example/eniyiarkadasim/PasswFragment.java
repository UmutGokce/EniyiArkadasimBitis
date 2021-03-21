package com.example.eniyiarkadasim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class PasswFragment extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_passw, container, false);
        Button sendP = view.findViewById(R.id.passw2Send);
        Button cancelP = view.findViewById(R.id.cancel2Passw);
        EditText userMail2Passw = view.findViewById(R.id.passw2Email);

        cancelP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(PasswFragment.this).commit();
            }
        });

        sendP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(userMail2Passw.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Parola Sıfırlama Maili Gönderildi", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().remove(PasswFragment.this).commit();
                        }else{

                            Toast.makeText(getActivity(), "Parola Sıfırlama Maili Gönderilirken Bir Hata Oluştu \n"+task.getException().getMessage()
                                    , Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        return view;
    }
}
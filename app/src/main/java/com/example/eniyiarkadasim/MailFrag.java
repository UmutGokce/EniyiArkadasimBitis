package com.example.eniyiarkadasim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MailFrag extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Button send = getView().findViewById(R.id.cfBtn);
        Button cfmail = getView().findViewById(R.id.cf2);
        Button cancel = getView().findViewById(R.id.cancel);
        getActivity().onBackPressed();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(MailFrag.this).commit();
            }

        });
        cfmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// conf mail gönderilecek
            }
        });
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        return view;
    }
}
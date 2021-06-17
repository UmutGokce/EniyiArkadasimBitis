package com.example.eniyiarkadasim.dialogs;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.eniyiarkadasim.Chat;
import com.example.eniyiarkadasim.R;
import com.example.eniyiarkadasim.model.ChatMsg;
import com.example.eniyiarkadasim.model.ChatRoom;
import com.example.eniyiarkadasim.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewChatRoomDialogFragment extends DialogFragment {
     int userLevel = 0;
    int mSeekProgress = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_new_chat_room_dialog, container, false);
       EditText newChatRoomName = view.findViewById(R.id.etNewChatName);
        SeekBar sbLevel = view.findViewById(R.id.seekBarLevel);
        Button create = view.findViewById(R.id.buttonCr);
        TextView tvLevel = view.findViewById(R.id.tvLevel);


          tvLevel.setText(String.valueOf(mSeekProgress));

        sbLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressChangedValue=progress;
                mSeekProgress=progress;
                tvLevel.setText(String.valueOf(progressChangedValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
            getUserLevel();

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(userLevel >= sbLevel.getProgress()){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        String crId = ref.child("chat_room").push().getKey();
                        ChatRoom cr = new ChatRoom();
                        cr.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        cr.setLevel(String.valueOf(mSeekProgress));
                        cr.setChatName(newChatRoomName.getText().toString());
                        cr.setRoomId(crId);
                        ref.child("chat_room").child(crId).setValue(cr);
//-----------------------------------------------------------------------------------------
                        String msgId = ref.child("chat_room").push().getKey();
                        ChatMsg msg = new ChatMsg();
                        msg.setMessage("Sohbet Odasına Hoşgeldiniz");
                        msg.setTimeStamp(getTime());
                        ref.child("chat_room")
                                .child(crId)
                                .child("chat_room_messages")
                                .child(msgId)
                                .setValue(msg);
                        ((Chat)getActivity()).init();
                        getDialog().dismiss();


                    }else{

                        Toast.makeText(getActivity(), "Seviyeniz Yeterli Değil", Toast.LENGTH_SHORT).show();
                    }



                }
            });


        return view;
    }

    private void getUserLevel() {
       DatabaseReference ref =  FirebaseDatabase.getInstance().getReference();
       Query query = ref.child("user").orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshotx) {
               for(DataSnapshot snapshot : snapshotx.getChildren()){
                   User okunanKullanici = snapshot.getValue(User.class);

                  userLevel=Integer.parseInt(okunanKullanici.getSeviye());

               }
           }


           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    private String getTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",new Locale("tr"));
        return sdf.format(new Date());
    }
}
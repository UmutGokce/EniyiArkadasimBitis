package com.example.eniyiarkadasim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eniyiarkadasim.dialogs.NewChatRoomDialogFragment;
import com.example.eniyiarkadasim.model.ChatMsg;
import com.example.eniyiarkadasim.model.ChatRoom;
import com.example.eniyiarkadasim.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
    FloatingActionButton fabRoom = (FloatingActionButton)findViewById(R.id.fabNewRoom);
    ArrayList<ChatRoom> allChatRooms = new ArrayList<ChatRoom>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();






    }

    public void init(){
        fabRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ftCr = getSupportFragmentManager().beginTransaction();
                ftCr.add(R.id.chatCont,new NewChatRoomDialogFragment());
                ftCr.addToBackStack(null);
                ftCr.commit();



               /* ft.replace(R.id.chatCont, new MailFrag()).commit();
                FragmentTransaction fm = manager.beginTransaction();
                fm.replace(R.id.)*/


            }
        });

    }
    private void getChatRooms(){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
     /*   Query query= ref.child("chat_rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshota : snapshot.getChildren()){

                    ChatRoom theRoom = snapshot.getValue(ChatRoom.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });
*/

        Query query = ref.child("chat_rooms");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshota : snapshot.getChildren()){



                   ChatRoom theRoom = snapshot.getValue(ChatRoom.class);

                    Map hMap = new HashMap<String,Object>();


                    hMap.put("chat_room",snapshota.getValue());
                    theRoom.setCreatorId(hMap.get("creatorId").toString());
                    theRoom.setChatName(hMap.get("chatName").toString());
                    theRoom.setLevel(hMap.get("level").toString());
                    theRoom.setRoomId(hMap.get("roomId").toString());


                    ArrayList <ChatMsg> allMessages = new ArrayList<ChatMsg>();
                    for(DataSnapshot messaggesX : snapshota.child("chat_room_messages").getChildren()){

                        ChatMsg readMsg= new ChatMsg();
                        readMsg.setTimeStamp(messaggesX.getValue(ChatMsg.class).getTimeStamp());
                        readMsg.setMessage(messaggesX.getValue(ChatMsg.class).getMessage());
                        readMsg.setName(messaggesX.getValue(ChatMsg.class).getName());
                        readMsg.setUserId(messaggesX.getValue(ChatMsg.class).getUserId());
                        readMsg.setPp(messaggesX.getValue(ChatMsg.class).getPp());

                        allMessages.add(readMsg);
                    }

theRoom.setChat_room_messages(allMessages);
allChatRooms.add(theRoom);
                    Toast.makeText(Chat.this, "Sohbet Odaları Sayisi : "+allChatRooms.size(), Toast.LENGTH_SHORT).show();
// vx = snapshota.getValue as HashMap<String,Object>(); I want to do something like this but couldn't figure it how

                }

            }
              /* theRoom.setCreatorId(snapshota.getValue(ChatRoom.class).getCreatorId());
                    theRoom.setLevel(snapshota.getValue(ChatRoom.class).getLevel());
                    theRoom.setChatName(snapshota.getValue(ChatRoom.class).getChatName());
                    theRoom.setRoomId(snapshota.getValue(ChatRoom.class).getRoomId());*/

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.example.groupmessage.ui.createmessage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupmessage.GroupModel;
import com.example.groupmessage.MessageModel;
import com.example.groupmessage.R;
import com.example.groupmessage.ui.creategroup.GroupAdapter;
import com.example.groupmessage.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateMessageFragment extends Fragment {
    TextView message_name,message_description;
    RecyclerView messageRv;
    Button create_message;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    ArrayList<MessageModel> messageModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_create_message, container, false);
        message_name=root.findViewById(R.id.message_name);
        message_description=root.findViewById(R.id.message_description);
        messageRv=root.findViewById(R.id.recyclerView_create_message);
        create_message=root.findViewById(R.id.create_message);
        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();

        messageModelArrayList=new ArrayList<>();

        create_message.setOnClickListener(view -> {
            String name=message_name.getText().toString();
            String description=message_description.getText().toString();
            if(name.isEmpty() || description.isEmpty()){
                Toast.makeText(getContext(),"Alanlar?? doldur",Toast.LENGTH_SHORT).show();
                return;
            }
            CreateMessage(name,description);
        });
        FetchMessage();
        return root;
    }
    private void CreateMessage(String messageName,String messageDescription){
        String userId=mAuth.getCurrentUser().getUid();
        mStore.collection("/userdata/"+userId+"/"+"messages").add(new HashMap<String, Object>(){{
            put("name",messageName);
            put("description",messageDescription);
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(),"Mesaj olu??turuldu",Toast.LENGTH_SHORT).show();
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                MessageModel messageModel=documentSnapshot.toObject(MessageModel.class);
                messageModelArrayList.add(messageModel);
                messageRv.getAdapter().notifyDataSetChanged();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(),"Mesaj olu??turulamad??",Toast.LENGTH_SHORT).show();
        });
    }
    private  void FetchMessage(){
        String userId=mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata/"+userId+"/"+"messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            messageModelArrayList.clear();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                MessageModel messageModel=new MessageModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getId());
                messageModelArrayList.add(messageModel);

                messageRv.setAdapter(new MessageAdapter(messageModelArrayList));
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                messageRv.setLayoutManager(linearLayoutManager);
            }
        });
    }
}
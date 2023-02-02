package com.example.groupmessage.ui.sendmessage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupmessage.MessageModel;
import com.example.groupmessage.OnClickitemEventListener;
import com.example.groupmessage.R;

import java.util.List;

public class SendMessageAdapter extends RecyclerView.Adapter<SendMessageAdapter.MessageViewHolder>{
    List<MessageModel> messageModelList;
    OnClickitemEventListener onClickitemEventListener;
    public SendMessageAdapter(List<MessageModel> messageModelList, OnClickitemEventListener onClickitemEventListener){
        this.messageModelList=messageModelList;
        this.onClickitemEventListener=onClickitemEventListener;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sendmessage_message,parent,false),onClickitemEventListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel messageModel=messageModelList.get(position);
        holder.setData(messageModel);
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageName,messageDesctription;
        OnClickitemEventListener onClickitemEventListener;
        public MessageViewHolder(View itemView,OnClickitemEventListener onClickitemEventListener){
            super(itemView);
            messageName=itemView.findViewById(R.id.item_sendmessage_nameTv);
            messageDesctription=itemView.findViewById(R.id.item_sendmessage_descriptionTv);
            this.onClickitemEventListener=onClickitemEventListener;

            itemView.setOnClickListener(this);
        }
        public void setData(MessageModel messageModel){
            messageName.setText(messageModel.getName());
            messageDesctription.setText(messageModel.getDescription());
        }

        @Override
        public void onClick(View view) {
            onClickitemEventListener.onClickItemEvent(getAdapterPosition());
        }
    }
}

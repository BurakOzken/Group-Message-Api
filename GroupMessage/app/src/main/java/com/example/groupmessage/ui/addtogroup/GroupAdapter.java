package com.example.groupmessage.ui.addtogroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupmessage.GroupModel;
import com.example.groupmessage.OnClickitemEventListener;
import com.example.groupmessage.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    List<GroupModel> groupModelList;
    OnClickitemEventListener onClickitemEventListener;
    public GroupAdapter (List<GroupModel> groupModelList, OnClickitemEventListener onClickitemEventListener){
        this.groupModelList=groupModelList;
        this.onClickitemEventListener=onClickitemEventListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder=new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addtogroup_group,parent,false),onClickitemEventListener);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel groupModel=groupModelList.get(position);
        holder.setData(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView groupName,groupDesctription;
        ImageView groupImageView;

        com.example.groupmessage.OnClickitemEventListener onClickitemEventListener;
        public GroupViewHolder(View itemView, com.example.groupmessage.OnClickitemEventListener onClickitemEventListener){
            super(itemView);
            groupImageView=itemView.findViewById(R.id.item_addtogroup_groupimage);
            groupName=itemView.findViewById(R.id.item_addtogroup_groupname);
            groupDesctription=itemView.findViewById(R.id.item_addtogroup_groupdescription);

            this.onClickitemEventListener=onClickitemEventListener;
            itemView.setOnClickListener(this);
        }
        public void setData(GroupModel groupModel){
            groupName.setText(groupModel.getName());
            groupDesctription.setText(groupModel.getDescription());
            if(groupModel.getImage() != null){
                Picasso.get().load(groupModel.getImage()).into(groupImageView);
            }
        }

        @Override
        public void onClick(View view) {
            onClickitemEventListener.onClickItemEvent(getAdapterPosition());
        }
    }
}
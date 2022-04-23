package com.example.hostelcomplaintforum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {
    private final Context mContext;
    private ArrayList<ComplaintItem> mFiles;

    public ComplaintAdapter(Context mContext, ArrayList<ComplaintItem> mFiles) {
        this.mContext = mContext;
        this.mFiles = mFiles;
    }

    public void updateData(ArrayList<ComplaintItem> newFiles){
        mFiles = newFiles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.complaint_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.mName.setText(mFiles.get(i).name);
        holder.mHostel.setText("Hostel - "+mFiles.get(i).hostel);
        holder.mRoom.setText(mFiles.get(i).room);
        holder.mSubject.setText(mFiles.get(i).subject);
        if(mFiles.get(i).reply==null) holder.mReply.setText("No replies yet.");
        else holder.mReply.setText("In progress");
    }
    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mSubject, mName, mRoom, mHostel, mDate, mReply;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSubject = itemView.findViewById(R.id.subject_item);
            mName = itemView.findViewById(R.id.name);
            mRoom = itemView.findViewById(R.id.room);
            mHostel = itemView.findViewById(R.id.hostel);
            mDate = itemView.findViewById(R.id.date);
            mReply = itemView.findViewById(R.id.reply_item);
        }
    }
}

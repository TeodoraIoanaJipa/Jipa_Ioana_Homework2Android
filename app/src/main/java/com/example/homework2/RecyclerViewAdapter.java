package com.example.homework2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mMarks = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mNames, ArrayList<String> mMarks, Context mContext) {
        this.mNames = mNames;
        this.mMarks = mMarks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTextName.setText(mNames.get(position));
        holder.mTextMarks.setText(mMarks.get(position));
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"onBindViewHolder"+ mMarks.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextMarks;
        TextView mTextName;
        RelativeLayout mParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName= itemView.findViewById(R.id.text_view_name);
            mTextMarks = itemView.findViewById(R.id.text_view_mark);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

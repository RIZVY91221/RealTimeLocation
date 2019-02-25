package com.example.realtimelocation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_Email;
    ItemClickLitener itemClickListener;

    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txt_Email=itemView.findViewById(R.id.userEmail);
    }

    public void setItemClickListener(ItemClickLitener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}

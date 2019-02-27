package com.example.shaqib.jubotserv.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaqib.jubotserv.R;
import com.example.shaqib.jubotserv.interfacee.ItemClickListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView mName, mPhone, mAddress, mComment, mDandT, mTotal;
    public Button btn;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        mName = itemView.findViewById(R.id.name);
        mPhone = itemView.findViewById(R.id.phone);
        mAddress = itemView.findViewById(R.id.address);
        mComment = itemView.findViewById(R.id.comment);
        mDandT = itemView.findViewById(R.id.dAndT);
        mTotal = itemView.findViewById(R.id.total);

        btn = itemView.findViewById(R.id.btn);



        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}


package com.madisoft.raj.titanquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madisoft.raj.titanquiz.Interface.ItemClickListener;
import com.madisoft.raj.titanquiz.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category_name;
    public TextView category_description;


    private ItemClickListener itemClickListener;


    public CategoryViewHolder(View itemView) {
        super(itemView);
        category_description = (TextView) itemView.findViewById(R.id.category_description);
        category_name=(TextView) itemView.findViewById(R.id.category_name);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}

package com.example.windows.webtech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windows.webtech.R;
import com.example.windows.webtech.model.Serachdata;

import java.util.ArrayList;

/**
 * Created by Windows on 12/22/2017.
 */

public class GetSearchAdapter extends RecyclerView.Adapter<GetSearchAdapter.MyViewHolder> {
    ArrayList<Serachdata.Brand> brandList;
    Context Mcontext;

    public GetSearchAdapter(ArrayList<Serachdata.Brand> productList, Context mcontext) {
        brandList = productList;
        Mcontext = mcontext;
        System.out.println("============in adapter FriendListAdapter=======");

    }


    @Override
    public GetSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailscard, parent, false);

        return new GetSearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GetSearchAdapter.MyViewHolder holder, int position) {
        holder.brandName.setText(brandList.get(position).getName());
        holder.desc_text.setText(brandList.get(position).getDescription());
        holder.id.setText(brandList.get(position).getId());
        holder.createdBy.setText(brandList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView brandName, desc_text, id, createdBy;

        public MyViewHolder(View view) {
            super(view);
            brandName = view.findViewById(R.id.brandTittle);
            desc_text = view.findViewById(R.id.descriptiondata);
            id = view.findViewById(R.id.productId);
            createdBy = view.findViewById(R.id.createdBy);
        }

    }
}
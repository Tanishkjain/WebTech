package com.example.windows.webtech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windows.webtech.R;
import com.example.windows.webtech.model.BrandData;
import com.example.windows.webtech.model.GetBrandData;

import java.util.ArrayList;

/**
 * Created by Windows on 12/21/2017.
 */

public class GetBrandAdapter extends RecyclerView.Adapter<GetBrandAdapter.MyViewHolder> {
    ArrayList<GetBrandData.BrandList> brandList;
    ArrayList<BrandData> brand1;
    Context Mcontext;

    public GetBrandAdapter(ArrayList<GetBrandData.BrandList> productList, ArrayList<BrandData> brand, Context mcontext) {
        brandList = productList;
        Mcontext = mcontext;
        brand1 = brand;
        System.out.println("============in adapter FriendListAdapter=======");

    }


    @Override
    public GetBrandAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailscard, parent, false);

        return new GetBrandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GetBrandAdapter.MyViewHolder holder, int position) {
        if (brandList.size() > 0) {
            holder.brandName.setText(brandList.get(position).getName());
            holder.desc_text.setText(brandList.get(position).getDescription());
            holder.id.setText(brandList.get(position).getId());
            holder.createdBy.setText(brandList.get(position).getCreatedAt());
        } else {
            holder.brandName.setText(brand1.get(position).getBrandName());
            holder.desc_text.setText(brand1.get(position).getDescreption());
            holder.id.setText(brand1.get(position).getId());
            holder.createdBy.setText(brand1.get(position).getCreatedAt());
        }
    }

    @Override
    public int getItemCount() {
        if(brandList.size()>0){
            return brandList.size();

        }
        else {
            return brand1.size();
        }

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

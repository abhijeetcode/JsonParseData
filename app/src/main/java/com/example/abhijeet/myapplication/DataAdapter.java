package com.example.abhijeet.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhijeet on 24/7/16.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder>
{
    private List<Data> DataList ;
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView item,price,image;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            item = (TextView)itemView.findViewById(R.id.name);
            price = (TextView)itemView.findViewById(R.id.price);
        }
    }
    public DataAdapter(List<Data> DataList)
    {
        this.DataList=DataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View vwinflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(vwinflate);
    }
    // To display the data at the specified position.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data=DataList.get(position);
        holder.item.setText(data.getItem());

        holder.price.setText("Price= "+data.getprice());
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }



}

package com.example.projek_imanage.fitur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.R;
import com.example.projek_imanage.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mcontext;
    private List<Item> mItems;
//    private OnItemClickListener mListener;

    public ItemAdapter (Context context, List<Item> items){
        mcontext = context;
        mItems = items;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.barang_row, parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item itemCurrent = mItems.get(position);
        holder.Namabarang.setText(itemCurrent.getNama_Barang());
        holder.detBarang.setText(itemCurrent.getDeskripsi());
        Glide.with(mcontext).load(itemCurrent.getGambar_Barang()).into(holder.imgBarang);
    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBarang;
        TextView Namabarang, detBarang;
        View mView;
        public ItemViewHolder(View itemvView){
            super(itemvView);
            mView = itemvView;
            Namabarang = (TextView) itemvView.findViewById(R.id.nama_barang);
            imgBarang = (ImageView) itemvView.findViewById(R.id.barang_img);
            detBarang = (TextView) itemvView.findViewById(R.id.detail_barang);

//            itemvView.setOnClickListener(this);
        }
    }

//    public interface OnItemClickListener{
//        void onItemClick(int position);
//
//        void onEditClick(int position);
//
//        void onDeleteClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        mListener = listener;
//    }







}





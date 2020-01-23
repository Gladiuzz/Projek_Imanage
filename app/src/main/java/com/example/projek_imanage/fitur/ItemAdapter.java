package com.example.projek_imanage.fitur;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    private OnItemClickListener mListener;

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


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
    , MenuItem.OnMenuItemClickListener {
        ImageView imgBarang;
        TextView Namabarang, detBarang;
        View mView;
        public ItemViewHolder(View itemvView){
            super(itemvView);
            mView = itemvView;
            Namabarang = (TextView) itemvView.findViewById(R.id.nama_barang);
            imgBarang = (ImageView) itemvView.findViewById(R.id.barang_img);
            detBarang = (TextView) itemvView.findViewById(R.id.detail_barang);

            itemvView.setOnClickListener(this);
            itemvView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem Detail = contextMenu.add(Menu.NONE, 1, 1, "Detail Item");
            MenuItem Edit = contextMenu.add(Menu.NONE, 2, 2, "Edit Item");
            MenuItem Delete = contextMenu.add(Menu.NONE, 3, 3, "Delete Item");

            Detail.setOnMenuItemClickListener(this);
            Edit.setOnMenuItemClickListener(this);
            Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
//                        case 1:
//                            mListener.onDetailClick(position);
//                            return true;
                        case 1:
                            mListener.onEditClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

//        void onDetailClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }







}





package com.example.projek_imanage.Adapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.R;
import com.example.projek_imanage.model.Item;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private Context mcontext;
    private List<Item> mItems;
    private List<Item> mItems_full;
    private OnItemClickListener mListener;

    public ItemAdapter (Context context, List<Item> items){
        mcontext = context;
        mItems = items;
        mItems_full = new ArrayList<>(mItems);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.barang_row, parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatrupiah = NumberFormat.getCurrencyInstance(localeID);

        final Item itemCurrent = mItems.get(position);
        holder.Namabarang.setText(itemCurrent.getNama_Barang());
        holder.detBarang.setText(itemCurrent.getDeskripsi());
        holder.hargaBarang.setText(formatrupiah.format(itemCurrent.getHarga()));
        Glide.with(mcontext).load(itemCurrent.getGambar_Barang()).into(holder.imgBarang);

    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void fiterList(ArrayList<Item> filterList) {
        mItems = filterList;
        notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return itemFilter;
//    }
//
//    private Filter itemFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<Item> filterdItem = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0){
//                filterdItem.addAll(mItems_full);
//            }
//            else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//        }
//    };




    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
    , MenuItem.OnMenuItemClickListener {
        ImageView imgBarang;
        TextView Namabarang, detBarang, hargaBarang;
        CardView barang_card;
        View mView;
        public ItemViewHolder(final View itemvView){
            super(itemvView);
            mView = itemvView;
            Namabarang = (TextView) itemvView.findViewById(R.id.nama_barang);
            imgBarang = (ImageView) itemvView.findViewById(R.id.barang_img);
            hargaBarang = (TextView) itemvView.findViewById(R.id.harga_barang);
            detBarang = (TextView) itemvView.findViewById(R.id.detail_barang);
            barang_card = (CardView) itemvView.findViewById(R.id.card_Barang);

            itemvView.setOnClickListener(this);
            itemvView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(mItems.get(position));
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem Edit = contextMenu.add(Menu.NONE, 1, 1, "Edit Item");
            MenuItem Delete = contextMenu.add(Menu.NONE, 2, 2, "Delete Item");
            Edit.setOnMenuItemClickListener(this);
            Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onEditClick(mItems.get(position));
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
        void onItemClick(Item item);

        void onDeleteClick(int position);

        void onEditClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }











}





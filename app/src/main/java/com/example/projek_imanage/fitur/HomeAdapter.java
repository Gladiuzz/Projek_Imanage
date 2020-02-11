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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.projek_imanage.R;
import com.example.projek_imanage.model.Item;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context mcontext;
    private List<Item> mItems;
    private HomeAdapter.OnItemClickListener mListener;

    public HomeAdapter (Context context, List<Item> items){
        mcontext = context;
        mItems = items;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent,false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatrupiah = NumberFormat.getCurrencyInstance(localeID);

        Item itemCurrent = mItems.get(position);
        holder.nama_home.setText(itemCurrent.getNama_Barang());
        holder.harga_home.setText(formatrupiah.format(itemCurrent.getHarga()));
        holder.qty_home.setText(Integer.toString(itemCurrent.getJumlah()));
        Glide.with(mcontext).load(itemCurrent.getGambar_Barang()).into(holder.img_home);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
    , MenuItem.OnMenuItemClickListener {
        ImageView img_home;
        TextView nama_home, harga_home, qty_home;
        CardView mCard_home;
        View mView;
        public HomeViewHolder(final View HomeView){
            super(HomeView);
            mView = HomeView;
            nama_home = (TextView) HomeView.findViewById(R.id.nama_barang_home);
            img_home = (ImageView) HomeView.findViewById(R.id.barang_img_home);
            harga_home = (TextView) HomeView.findViewById(R.id.harga_barang_home);
            qty_home = (TextView) HomeView.findViewById(R.id.jml_qty_home);
            mCard_home = (CardView) HomeView.findViewById(R.id.card_Barang_home);

            HomeView.setOnClickListener(this);
            HomeView.setOnCreateContextMenuListener(this);
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

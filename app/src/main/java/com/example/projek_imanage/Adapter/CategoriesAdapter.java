package com.example.projek_imanage.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_imanage.R;
import com.example.projek_imanage.model.Item;
import com.example.projek_imanage.model.Kategori;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.kategoriViewHolder> {
    private Context mcontext;
    private List<Kategori> mKategori;
    private CategoriesAdapter.OnItemClickListener mListener;

    public CategoriesAdapter(Context context, List<Kategori> categories) {
        mcontext = context;
        mKategori = categories;
    }

    @NonNull
    @Override
    public kategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row, parent,false);
        return new kategoriViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull kategoriViewHolder holder, int position) {
        Kategori kategori = mKategori.get(position);
        holder.nama_kategori.setText(kategori.getNama_kategori());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class kategoriViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
            , MenuItem.OnMenuItemClickListener {
        TextView nama_kategori;
        View mView;

        public kategoriViewHolder(final View KategoriView) {
            super(KategoriView);
            mView = KategoriView;
            nama_kategori = (TextView) KategoriView.findViewById(R.id.nama_barang_home);


            KategoriView.setOnClickListener(this);
            KategoriView.setOnCreateContextMenuListener(this);
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
//            return false;
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onEditClick(mKategori.get(position));
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
                    mListener.onItemClick(mKategori.get(position));
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
        void onItemClick(Kategori kategori);

        void onDeleteClick(int position);

        void onEditClick(Kategori kategori);
    }

    public void setOnItemClickListener(CategoriesAdapter.OnItemClickListener listener){
        mListener = listener;
    }
}

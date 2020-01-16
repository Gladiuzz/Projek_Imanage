package com.example.projek_imanage.fitur;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Listitem extends Fragment {


    private RecyclerView mDataList;
    private DatabaseReference dbr, mItemRef;

//    private FirebaseRecyclerAdapter<Item,ItemViewHolder> FRA;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_item, container, false);
        mDataList = (view.findViewById(R.id.data_barang));
        mDataList.setHasFixedSize(true);
        mDataList.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            String id = getArguments().getString(btm_navigation.USER_ID);
            dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("Item");
            mItemRef = dbr.child(id);
            dbr.keepSynced(true);
            if (id == null){
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        }

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(dbr, Item.class).build();
        FirebaseRecyclerAdapter<Item,ItemViewHolder>  firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Item,ItemViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {
                holder.setNama_Barang(model.getNama_Barang());
                holder.setDetail(model.getDeskripsi());
                Picasso.get().load(model.getGambar_Barang()).into(holder.imgBarang, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =  LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.barang_row,parent,false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return holder;
            }



        };
        mDataList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }

        return view;
    }





    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBarang;
        TextView Namabarang, detBarang;
        View mView;
        public ItemViewHolder(View itemvView){
            super(itemvView);
            mView = itemvView;
            imgBarang = (ImageView) itemvView.findViewById(R.id.barang_img);
        }
        public void setNama_Barang(String nama_Barang){
            Namabarang = (TextView) mView.findViewById(R.id.nama_barang);
            Namabarang.setText(nama_Barang);
        }

        public void setDetail(String Detail){
            detBarang = (TextView) mView.findViewById(R.id.detail_barang);
            detBarang.setText(Detail);
        }
    }
}

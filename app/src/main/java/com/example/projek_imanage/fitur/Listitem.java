package com.example.projek_imanage.fitur;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.projek_imanage.detail_list;
import com.example.projek_imanage.list_det;
import com.example.projek_imanage.loginactivity;
import com.example.projek_imanage.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Listitem extends Fragment implements ItemAdapter.OnItemClickListener {



    private ProgressBar mprogressCircle;
    private FirebaseStorage mStorage;
    private StorageReference SR;
    private DatabaseReference dbr;
    private ValueEventListener mDBListener;
    private RecyclerView mDataList;
    private ItemAdapter mAdapter;
    private List<Item> mItems;
    private Item items;
    private FirebaseAuth mAuth;

    private TextView ttl_item;
    private EditText search_bar;
    private int countItems;

    private String id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.list_item, container, false);
        mDataList = (view.findViewById(R.id.data_barang));
        mprogressCircle = (view.findViewById(R.id.progress_circle));
        ttl_item = (view.findViewById(R.id.no_item));
        search_bar = (view.findViewById(R.id.search));
        mDataList.setHasFixedSize(true);
        mDataList.setLayoutManager(new LinearLayoutManager(getContext()));

        items = new Item();

        mItems = new ArrayList<>();

        mAdapter = new ItemAdapter(getContext(), mItems);

        mDataList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(Listitem.this);

        mStorage = FirebaseStorage.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);

        ItemSearch();



        mDBListener = dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mItems.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    item.setKey(itemSnapshot.getKey());
                    mItems.add(item);
                }
                if (dataSnapshot.exists()){
                    countItems = (int) dataSnapshot.getChildrenCount();
                    ttl_item.setText(Integer.toString(countItems) + " Items");
                }
                else{
                    countItems = (int) dataSnapshot.getChildrenCount();
                    ttl_item.setText(Integer.toString(countItems) + " Items");
                    RelativeLayout mEmpty = view.findViewById(R.id.list_empty);
                    getLayoutInflater().inflate(R.layout.list_item, mEmpty);
                }


                mAdapter.notifyDataSetChanged();

                mprogressCircle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mprogressCircle.setVisibility(View.INVISIBLE);
            }
        });

        return view;

    }




    @Override
    public void onItemClick(Item item) {
//        Toast.makeText(getContext(), "Normal Click at position :" + position, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), list_det.class);
        i.putExtra("id_barang", item.getId_item());
        i.putExtra("nama_barang", item.getNama_Barang());
        i.putExtra("kategori_barang", item.getKategori());
        i.putExtra("jumlah_barang", item.getJumlah());
        i.putExtra("harga_barang", item.getHarga());
        i.putExtra("detail_barang", item.getDeskripsi());
        i.putExtra("tanggal_barang", item.getTanggal());
        i.putExtra("gambar_Barang", item.getGambar_Barang());

        startActivity(i);
    }

    @Override
    public void onEditClick(Item item) {

        Intent i = new Intent(getActivity(), detail_list.class);
        i.putExtra("id_barang", item.getId_item());
        i.putExtra("nama_barang", item.getNama_Barang());
        i.putExtra("kategori_barang", item.getKategori());
        i.putExtra("jumlah_barang", item.getJumlah());
        i.putExtra("harga_barang", item.getHarga());
        i.putExtra("detail_barang", item.getDeskripsi());
        i.putExtra("tanggal_barang", item.getTanggal());
        i.putExtra("gambar_Barang", item.getGambar_Barang());

        startActivity(i);
        //        startActivity(new Intent(getActivity(), detail_list.class)
//                .putExtra("id_barang", "")
//                .putExtra("nama_barang", "")
//                );


//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        String id = currentUser.getUid();
//        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);
//
//        dbr.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    @Override
    public void onDeleteClick(int position) {
        Item selectedItem = mItems.get(position);
        final String selectedKey = selectedItem.getKey();


        StorageReference dataRef = mStorage.getReferenceFromUrl(selectedItem.getGambar_Barang());
        dataRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dbr.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        dbr.removeEventListener(mDBListener);
    }

    private void ItemSearch() {
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Item> filterList = new ArrayList<>();

        for (Item item : mItems){
            if (item.getNama_Barang().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }

        mAdapter.fiterList(filterList);
    }
}


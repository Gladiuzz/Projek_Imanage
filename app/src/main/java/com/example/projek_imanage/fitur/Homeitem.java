package com.example.projek_imanage.fitur;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_imanage.HomeAdapter;
import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.detail_list;
import com.example.projek_imanage.list_det;
import com.example.projek_imanage.loginactivity;
import com.example.projek_imanage.model.Item;
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

import java.util.ArrayList;
import java.util.List;

public class Homeitem extends Fragment implements HomeAdapter.OnItemClickListener {
//    CardView CV_addItem,CV_list,CV_transaksi,CV_profile;
    private TextView username, email;
    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private FirebaseUser currentUser;
    private ValueEventListener mDBListener;
    private DatabaseReference dbr;
    private RecyclerView mDataList;
    private HomeAdapter mAdapter;
    private List<Item> mItems;
    private ProgressBar mprogressCircle;
    private Item items;
    private int countItems;
    private String id;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.home_item, container, false);
        username = (view.findViewById(R.id.nameUser));
        mprogressCircle = (view.findViewById(R.id.progress_circle1));
        mDataList = (view.findViewById(R.id.data_barang_horizontal));
        mDataList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        items = new Item();

        mItems = new ArrayList<>();

        mAdapter = new HomeAdapter(getContext(), mItems);

        mAdapter.setOnItemClickListener(Homeitem.this);

        mDataList.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);


//        String email = currentUser.getEmail();
//        getEmail.setText(email);

        mDBListener = dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mItems.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    item.setKey(itemSnapshot.getKey());
                    mItems.add(item);
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

        loadUserInformation();
        return view;


    }


    private void loadUserInformation(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String id = mAuth.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Users").child(id);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child("name").getValue(String.class);
                username.setText(user_name);

//                if (dataSnapshot.exists()){
//                    countItems = (int) dataSnapshot.getChildrenCount();
//                    username.setText(Integer.toString(countItems) + "Items");
//                }
//                else{
//                    username.setText("0 Items");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onItemClick(Item item) {
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

    }
}

package com.example.projek_imanage.fitur;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.projek_imanage.loginactivity;
import com.example.projek_imanage.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


    private RecyclerView mDataList;
    private ItemAdapter mAdapter;
    private ProgressBar mprogressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference dbr;
    private ValueEventListener mDBListener;
    private List<Item> mItems;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_item, container, false);
        mDataList = (view.findViewById(R.id.data_barang));
        mprogressCircle = (view.findViewById(R.id.progress_circle));
        mDataList.setHasFixedSize(true);
        mDataList.setLayoutManager(new LinearLayoutManager(getContext()));

        mItems = new ArrayList<>();

        mAdapter = new ItemAdapter(getContext(), mItems);

        mDataList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(Listitem.this);

        mStorage = FirebaseStorage.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);



        mDBListener = dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mItems.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);
                    item.setkey(itemSnapshot.getKey());
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

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Normal Click at position :" + position, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onDetailClick(int position) {
//        Toast.makeText(getContext(), "Detail Click at position : " + position, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onEditClick(int position) {
        Toast.makeText(getContext(), "Edit Click at position :" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Item selectedItem = mItems.get(position);
        final String selectedKey = selectedItem.getkey();

        StorageReference dataRef = mStorage.getReferenceFromUrl(selectedItem.getGambar_Barang());
        dataRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dbr.child(selectedKey).removeValue();
                Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbr.removeEventListener(mDBListener);
    }
}


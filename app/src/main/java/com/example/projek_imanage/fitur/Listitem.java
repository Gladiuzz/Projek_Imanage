package com.example.projek_imanage.fitur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Listitem extends Fragment {

    private RecyclerView mDataList;
    private DatabaseReference dbr;



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
            dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);
            dbr.keepSynced(true);
            if (id == null){
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        }




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

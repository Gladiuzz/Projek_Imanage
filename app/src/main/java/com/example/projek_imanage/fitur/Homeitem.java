package com.example.projek_imanage.fitur;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.loginactivity;
import com.google.firebase.auth.FirebaseAuth;

public class Homeitem extends Fragment {
//    CardView CV_addItem,CV_list,CV_transaksi,CV_profile;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.home_item, container, false);

    }


}

package com.example.projek_imanage.fitur;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.loginactivity;
import com.google.firebase.auth.FirebaseAuth;

public class profileitem extends Fragment {
    private TextView username, email;
    private ImageView logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.profile_item, container, false);
        username = (view.findViewById(R.id.label_name));
        email = (view.findViewById(R.id.label_email));
        logout = (view.findViewById(R.id.logout_ic));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogout();
            }
        });

        return view;
    }

    public void setLogout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), loginactivity.class));
    }
}

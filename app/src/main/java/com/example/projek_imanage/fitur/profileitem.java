package com.example.projek_imanage.fitur;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.edt_profile;
import com.example.projek_imanage.loginactivity;
import com.example.projek_imanage.model.Item;
import com.example.projek_imanage.model.User;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class profileitem extends Fragment {
    private TextView username, email;
    private ImageView logout, edit;
    private CircleImageView img_avatar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference dbr;
    private StorageReference SR;
    private FirebaseStorage FS;
    private User users;

    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.profile_item, container, false);
        username = (view.findViewById(R.id.user_name));
        email = (view.findViewById(R.id.label_email));
        logout = (view.findViewById(R.id.logout_ic));
        edit = (view.findViewById(R.id.ic_edt));
        img_avatar = (view.findViewById(R.id.img_profile));
//        avatar_bg = (view.findViewById(R.id.avatar_bg));





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogout();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtProfile();
            }
        });

        loadUserInformation();

        return view;
    }

    public void setLogout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), loginactivity.class));
    }

    private void edtProfile(){
        startActivity(new Intent(getContext(), edt_profile.class));
    }

    private void loadUserInformation(){

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        id = mAuth.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Users").child(id);
        SR = firebaseStorage.getReference();
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getActivity() == null){
                    return;
                }
//                String user_name = dataSnapshot.child("name").getValue(String.class);
                users = dataSnapshot.getValue(User.class);
                username.setText(users.getName());
//                if (users.getAvatar())
                Glide.with(getContext()).load(users.getAvatar()).into(img_avatar);


//                if (dataSnapshot.child(users.getAvatar()).equals("")){
//                    Glide.with(getContext()).load(R.drawable.am56pz2_460swp).into(img_avatar);
//
//                }
//                Glide.with(getContext()).load(dataSnapshot.child("avatar").getValue(String.class)).into(avatar_bg);

//                SR.child("img_Avatar").child(id).child("test").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String gambar = uri.toString();
//
//                        Glide.with(getActivity()).load(gambar).into(img_avatar);
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        if (currentUser != null){
            if (currentUser.getDisplayName() != null){
                username.setText(currentUser.getDisplayName());
            }
            if (currentUser.getEmail() != null){
                email.setText(currentUser.getEmail());
            }
        }
    }





}

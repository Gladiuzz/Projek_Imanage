package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.fitur.profileitem;
import com.example.projek_imanage.model.User;
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
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class edt_profile extends AppCompatActivity {
    private EditText edtNama, edtEmail, edtPassword;
    private ImageView mSubmit;
    private Button btn_avatar;
    private DatabaseReference dbr;
    private FirebaseStorage FS;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private FirebaseUser currentUser;
    private User users;

    private static int PICK_IMAGE = 123;
    private Uri mGambarUri;
    private CircleImageView mAvatar;

    private String id, avatar_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_profile);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        id = mAuth.getUid();
        dbr = FirebaseDatabase.getInstance().getReference("Users");


        component();
        loadUserInformation();

        btn_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileChooser();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtProfile();
                sendAvatar();
            }
        });
    }

    private void component(){
        edtNama = findViewById(R.id.edtnama);
        edtEmail = findViewById(R.id.edtemail);
        edtPassword = findViewById(R.id.edtpass);
        mSubmit = findViewById(R.id.submit_edt);
        btn_avatar = findViewById(R.id.btn_img);
        mAvatar = findViewById(R.id.img_avatar);




    }

    private void loadUserInformation(){
        dbr.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child("name").getValue(String.class);


                edtNama.setText(user_name);
//                Glide.with(getApplicationContext()).load(dataSnapshot.child("avatar").getValue(String.class)).into(mAvatar);

                if (currentUser != null){
                    if (currentUser.getEmail() != null){
                        edtEmail.setText(currentUser.getEmail());
//                        edtPassword.setText(users.getAvatar());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void edtProfile(){
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        final String name = edtNama.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Enter nama", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mGambarUri != null) {
            final StorageReference imgReference = storageReference.child("img_Avatar").child(id).child("test");
            UploadTask uploadTask = imgReference.putFile(mGambarUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    id = currentUser.getUid();


                    imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String avatar = uri.toString();

                            User users = new User(id, name, email, password, avatar);
                            dbr.child(id).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    currentUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });

                                    currentUser.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                }
                            });
                            startActivity(new Intent(edt_profile.this, btm_navigation.class));
                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(edt_profile.this, "asda", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            id = currentUser.getUid();


//            Toast.makeText(this, "fuck this", Toast.LENGTH_SHORT).show();

            dbr.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String avatar = dataSnapshot.child("avatar").getValue(String.class);
                    String name = edtNama.getText().toString().trim();
                    User users = new User(id, name, email, password, avatar);
                    dbr.child(id).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(edt_profile.this, "ganti user berhasil", Toast.LENGTH_SHORT).show();

                            currentUser.updateEmail(email);

                            currentUser.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(edt_profile.this, "wtf", Toast.LENGTH_LONG).show();
                                }
                            });
                            startActivity(new Intent(edt_profile.this, btm_navigation.class));
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            mGambarUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mGambarUri);
                mAvatar.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }


    }

    private void sendAvatar(){

    }

    private void OpenFileChooser(){
        Intent profileIntent = new Intent();
        profileIntent.setType("image/*");
        profileIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}

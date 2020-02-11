package com.example.projek_imanage.fitur;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.projek_imanage.btm_navigation;

import com.example.projek_imanage.R;
import com.example.projek_imanage.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class add_Fragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    public add_Fragment() {
        //Required empty public constructor
    }

    //merefers ke firebase Realtime db
    private static final int PICK_IMAGE_REQUEST = 1;

    private RelativeLayout mAddGambar;
    private ImageView gambar_Barang;
    private ProgressBar mProgressBar;

    private Uri mGambarUri;
    private Item items;

    private DatabaseReference dbr;
    private StorageReference SR;
    private FirebaseAuth mAuth;
    private StorageTask uploadtask;
    private EditText nama_item, kategori, jumlah, harga, tgl,desc;
//    private TextView tgl;
    private Button btnAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.add_item, container, false);
        nama_item = (view.findViewById(R.id.edtnama_barang));
        kategori = (view.findViewById(R.id.edtkategori_barang));
        jumlah = (view.findViewById(R.id.edtquantity));
        harga = (view.findViewById(R.id.edtharga));
        tgl = (view.findViewById(R.id.edttanggal));
        desc = (view.findViewById(R.id.edtdetail));
        btnAdd = (view.findViewById(R.id.btn_add));
        mAddGambar = (view.findViewById(R.id.layout_addGambar));
        gambar_Barang = (view.findViewById(R.id.img_barang));
        mProgressBar = (view.findViewById(R.id.progress_bar));
        items = new Item();



        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new add_Fragment();
                newFragment.show(getChildFragmentManager(), "DatePicker");
            }
        });

        // Buat memasukan gambar
        mAddGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        // Yang SR untuk membuat folder Img_Barang, sedangkan dbr memasukan data ke table Item yang disesuaikan dengan id usernya
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String id = currentUser.getUid();
            SR = FirebaseStorage.getInstance().getReference("Img_Barang");
            dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);

        //----------------------------------------------------------------------------------

        Add_Item();
        return view;
    }

    public void Add_Item() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer Qty;
                final Integer harga_barang;

                final String Nama_item = items.setNama_Barang(nama_item.getText().toString().trim());
                final String Kategori = items.setKategori(kategori.getText().toString().trim());
                final String Jumlah = jumlah.getText().toString();
                final String Harga = harga.getText().toString();
                final String Tanggal = items.setTanggal(tgl.getText().toString().trim());
                final String Deskripsi = items.setDeskripsi(desc.getText().toString().trim());

                if (TextUtils.isEmpty(Nama_item)){
                    Toast.makeText(getContext(), "Enter nama barang!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(Kategori)){
                    Toast.makeText(getContext(), "Enter kategori", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (harga.length() == 0){
                    Toast.makeText(getContext(), "Enter Harga", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(Deskripsi)){
                    Toast.makeText(getContext(), "Enter Deskripsi", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (mGambarUri == null){
                    Toast.makeText(getContext(), "Enter img", Toast.LENGTH_SHORT).show();
                }

                //----------------------------------------------------------------------------------

                if (TextUtils.isEmpty(Jumlah)){
                    Toast.makeText(getContext(), "Enter jumlah quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Qty = items.setHarga(Integer.parseInt(Jumlah));
                }

                if (TextUtils.isEmpty(Harga)){
                    Toast.makeText(getContext(), "Enter Harga", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    harga_barang = items.setHarga(Integer.parseInt(Harga));
                }


                if (mGambarUri != null){
                    // membuat extensinya di dalam Firebase Storage
                    final StorageReference fileReference = SR.child(System.currentTimeMillis() + "."+ getFileExtension(mGambarUri));
                    fileReference.putFile(mGambarUri)
                    //----------------------------------------------------------------------------------
                            // Input berhasil
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 4000);

                                    Toast.makeText(getActivity(), "Berhasil menambah Item", Toast.LENGTH_SHORT).show();



                                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
//                                            final Uri uridownload = uri;

                                            String gambar = uri.toString();

                                            String id = dbr.push().getKey();
                                            Item item = new Item(id, Nama_item, Kategori,Deskripsi, Qty, harga_barang, Tanggal, gambar);
                                            dbr.child(id).setValue(item);
                                        }
                                    });



                                }

                            })
                            //----------------------------------------------------------------------------------

                            // kalau input gagal
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            //----------------------------------------------------------------------------------

                            // Input sedang dalam tahap proses
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                }
                            });
                            //----------------------------------------------------------------------------------
                }

//                String id = dbr.push().getKey();
//                Item item = new Item(id, Nama_item, Kategori,Deskripsi, Jumlah, Harga, Tanggal, );


//                dbr.child(id).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getActivity(), "Berhasil menambah Item", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getActivity(), "Gagal menambah Item", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
    }





    // buat datepicker
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), add_Fragment.this, yy, mm, dd);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yy);
        c.set(Calendar.MONTH, mm);
        c.set(Calendar.DAY_OF_MONTH, dd);
        String date = mm+"/"+dd+"/"+yy;
        tgl.setText(date);
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    //----------------------------------------------------------------------------------

    // Buat ngambil gambar
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mGambarUri = data.getData();
            gambar_Barang.setImageURI(mGambarUri);
        }
    }
    //----------------------------------------------------------------------------------

    // extension buat gambar
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}

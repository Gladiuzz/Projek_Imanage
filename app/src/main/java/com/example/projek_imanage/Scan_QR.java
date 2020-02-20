package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projek_imanage.fitur.add_Fragment;
import com.example.projek_imanage.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.util.List;

public class Scan_QR extends AppCompatActivity {
    CameraView cameraView;
    boolean isDetected = false;
    Button btn_start_again;
    public String id_item ,nama_Barang, kategori ,deskripsi, tanggal, gambar_Barang, mkey,test;
    public Integer jumlah, harga;

    private DatabaseReference dbr, dbr_kategori;
    private StorageReference SR;
    private FirebaseAuth mAuth;

    FirebaseVisionBarcodeDetectorOptions options;
    FirebaseVisionBarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__qr);

        id_item = getIntent().getStringExtra("id_barang");
        nama_Barang = getIntent().getStringExtra("nama_barang");
        kategori = getIntent().getStringExtra("kategori_barang");
        deskripsi = getIntent().getStringExtra("detail_barang");
        jumlah = getIntent().getIntExtra("jumlah_barang", 0);
        harga = getIntent().getIntExtra("harga_barang", 0);
        tanggal = getIntent().getStringExtra("tanggal_barang");
        gambar_Barang = getIntent().getStringExtra("gambar_Barang");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        SR = FirebaseStorage.getInstance().getReference("Img_Barang");
        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);

        Dexter.withActivity(this)
                .withPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO})
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        setupCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void setupCamera() {
        btn_start_again = (Button) findViewById(R.id.button_again);
        btn_start_again.setEnabled(isDetected);
        btn_start_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDetected = !isDetected;
                btn_start_again.setEnabled(isDetected);

            }
        });
        cameraView = (CameraView) findViewById(R.id.cameraView);
        cameraView.setLifecycleOwner(this);
        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                processImage(getVisionImageFromFrame(frame));
            }

        });

        options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build();
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    }

    private void processImage(FirebaseVisionImage visionImageFromFrame) {
        if (!isDetected){
            detector.detectInImage(visionImageFromFrame).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                @Override
                public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                        processResult(firebaseVisionBarcodes);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Scan_QR.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
//        if (firebaseVisionBarcodes.size() == 1){
            isDetected = true;
            btn_start_again.setEnabled(isDetected);
            for (FirebaseVisionBarcode item: firebaseVisionBarcodes){
                Point[] corners = item.getCornerPoints();
                int value_type = item.getValueType();
                String rawValue = item.getRawValue();
                switch (value_type){
                    case FirebaseVisionBarcode.TYPE_TEXT:
//                         createDialog(rawValue);
                            String hah = item.getRawValue();
                            Intent i = new Intent(getApplicationContext(), btm_navigation.class);
                            Item item_db = new Item(id_item, nama_Barang, kategori,deskripsi, jumlah, harga, tanggal, gambar_Barang, hah);
                            dbr.child(id_item).setValue(item_db).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Scan_QR.this, "berhasil", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(i);
                        break;



                }
            }
//        }
    }

    private void createDialog(String rawValue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(rawValue)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private FirebaseVisionImage getVisionImageFromFrame(Frame frame) {
        byte[] data = frame.getData();
        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setHeight(frame.getSize().getHeight())
                .setWidth(frame.getSize().getWidth())
//                .setRotation(frame.getRotation()) land scape mode
                .build();
        return FirebaseVisionImage.fromByteArray(data,metadata);
    }

}

package com.putra.pengkurankonsentrasihidrokuinon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectImageActivity extends AppCompatActivity {

    ImageView ivGetImage , ivSetImage;
    Button btnScan;
    Dialog selectPickImageDialog;
    private static final int CAPTURE_IMAGE_CODE = 0;
    private static final int GALLERY_IMAGE_CODE = 1;
    private String cameraFilePath;
    Bitmap bitmap;
    ProgressBar progressBar;


    // fungsi yang dijalankan saar pertama kali membuka halaman
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ivGetImage = findViewById(R.id.ivScanImage);
        ivSetImage = findViewById(R.id.ivSetImage);
        btnScan = findViewById(R.id.btnScan);
        progressBar = findViewById(R.id.progressbarSelectImage);
        selectPickImageDialog = new Dialog(this);

        ivGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        ivSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    Intent intent = new Intent(SelectImageActivity.this, ResultActivity.class);
                    intent.putExtra("result", bitmap);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Tolong pilih gambar yang akan di scan", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // fungsi yang dijalankan saat menekan tombol back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // fungsi yang dijalankan saat menekan tombol kamera pada layar yang akan menampilkan
    // popup untuk memilih mengambil gambar dari kamera atau dari gallery
    public void showPopup(View v) {
        Button btnCamera, btnGallery;
        selectPickImageDialog.setContentView(R.layout.popup);
        btnCamera =selectPickImageDialog.findViewById(R.id.btnCamera);
        btnGallery = selectPickImageDialog.findViewById(R.id.btnGallery);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectPickImageDialog.dismiss();
                ivGetImage.setVisibility(View.GONE);
                ivSetImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                checkPermission();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPickImageDialog.dismiss();
                ivGetImage.setVisibility(View.GONE);
                ivSetImage.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                fromGallery();
            }
        });
        selectPickImageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        selectPickImageDialog.show();
    }


    // fungsi yang dijalankan saat sudah melakukan pengambilan gambar dari kamera
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    // fungsi yang dijalankan saat penggunana untuk memberikan permission untuk mengakses camera atau gallery
    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SelectImageActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAPTURE_IMAGE_CODE);
        }else{
            captureFromCamera();
        }
    }

    // fungsi yang dijalankan saat memeilih mengambil gambar dari kamera
    private void captureFromCamera(){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAPTURE_IMAGE_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // fungsi yang dijalankan saat sudah mendapatkan hak akses dari pengguna untuk menggunakan kamera atau gallery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAPTURE_IMAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                captureFromCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                checkPermission();
            }
        }
    }

    // fungsi yang dijalankan saat mengambil gambar dari gallery
    private void fromGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        pickPhoto.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(pickPhoto , GALLERY_IMAGE_CODE);
    }

    // fungsi yang dijalankan saat sudah mendapatkan hasil dari kamera atau gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_CODE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            if (selectedImage != null){
                CropImage.activity(selectedImage)
                        .start(this);
            }else{
                ivSetImage.setVisibility(View.GONE);
            }
        }else if (requestCode == CAPTURE_IMAGE_CODE && resultCode == RESULT_OK){
            if (cameraFilePath != null){
                CropImage.activity(Uri.parse(cameraFilePath))
                        .start(this);
            }else{
                ivSetImage.setVisibility(View.GONE);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Glide.with(this).load(resultUri).into(ivSetImage);
                progressBar.setVisibility(View.GONE);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                progressBar.setVisibility(View.GONE);
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cti.lifego.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.button.MaterialButton;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class UploadPrescriptionFragment extends BaseFragment {

    private int CAMERA_REQUEST_CODE = 552;
    private int GALLERY_REQUEST_CODE = 553;
    private ImageView prescription;
    private MaterialButton camera, gallery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.upload_prescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        camera = view.findViewById(R.id.upload_camera);
        gallery = view.findViewById(R.id.upload_gallery);
        prescription = view.findViewById(R.id.prescription_image);
        camera.setOnClickListener(v -> openCamera());

        gallery.setOnClickListener(v -> openGallery());
    }

    private void openCamera() {
        ImagePicker.cameraOnly().start(this);
    }

    private void openGallery() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL)
                .single()
                .toolbarFolderTitle("Select image(s)")
                .toolbarImageTitle("Tap to select")
                .showCamera(false)
                .includeVideo(false)
                .start(GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            if (resultCode == RESULT_OK){
                //try {
                if ((requestCode == GALLERY_REQUEST_CODE) || (requestCode == CAMERA_REQUEST_CODE)){
                    Image image = ImagePicker.getFirstImageOrNull(data);

                    String filePath = image.getPath();
                    Glide.with(this).load(filePath).into(prescription);
                    uploadImage(filePath);
                }
            }
            else {
                Log.i("Error", String.valueOf(resultCode));
            }
        }
    }

    private void uploadImage(String filePath) {
        // create RequestBody instance from file
        File file = new File(filePath);
        RequestBody fileBody =  RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileBody);
        RequestBody description = RequestBody.create("image-type", MediaType.parse("text/plain"));


    }

}

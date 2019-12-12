/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cti.lifego.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import static android.app.Activity.RESULT_OK;

public class UploadPrescriptionFragment extends Fragment {

    public int CAMERA_REQUEST_CODE = 552;
    public int GALLERY_REQUEST_CODE = 553;
    ImageView prescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.upload_prescription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            //try {
            if ((requestCode == GALLERY_REQUEST_CODE) || (requestCode == CAMERA_REQUEST_CODE)){
                if ((resultCode == RESULT_OK)){
                    Image image = ImagePicker.getFirstImageOrNull(data);
                    String filePath = image.getPath();
                    Glide.with(this).load(filePath).into(prescription);
                }
            }
        }
    }
}

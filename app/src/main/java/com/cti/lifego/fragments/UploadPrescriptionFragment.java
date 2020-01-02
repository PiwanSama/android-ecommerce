/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.button.MaterialButton;

import static android.app.Activity.RESULT_OK;

public class UploadPrescriptionFragment extends Fragment {

    private int CAMERA_REQUEST_CODE = 552;
    private int GALLERY_REQUEST_CODE = 553;
    private ImageView prescription;
    private MaterialButton camera, gallery;
    private Context mContext;

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
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
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
                }
            }
            else {
                Log.i("Error", String.valueOf(resultCode));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}

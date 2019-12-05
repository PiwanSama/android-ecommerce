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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cti.lifego.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class CartFragment extends Fragment {

    public int CAMERA_REQUEST_CODE = 552;
    public int GALLERY_REQUEST_CODE = 553;
    ImageView prescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton checkout = view.findViewById(R.id.checkout_button);
        prescription = view.findViewById(R.id.prescription_img);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkout_summary_fragment);
            }
        });

        MaterialButton camera = view.findViewById(R.id.camera_button);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        MaterialButton upload = view.findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
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

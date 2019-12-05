package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.cti.lifego.R;

import static com.cti.lifego.R.array.central_region_array;
import static com.cti.lifego.R.array.country_code_array;
import static com.cti.lifego.R.array.progress_statebar_array;
import static com.cti.lifego.R.array.region_array;

public class UserAddressFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner area_spinner;
    private AppCompatSpinner region_spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.user_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        region_spinner = view.findViewById(R.id.spinner_region);
        area_spinner = view.findViewById(R.id.spinner_area);
        area_spinner.setEnabled(false);
        setUpRegionSpinner();
    }

    private void setUpRegionSpinner() {
        region_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(region_array));
        regionAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        region_spinner.setAdapter(regionAdapter);
    }

    private void setUpAreaSpinner(int id) {
        area_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(id));
        areaAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        area_spinner.setAdapter(areaAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
        area_spinner.setEnabled(true);
        {
            int id = parent.getId();
            if (id == R.id.spinner_region){
                String region = parent.getItemAtPosition(position).toString();
                switch (region){
                    case "Central":
                        setUpAreaSpinner(central_region_array);
                        break;
                    case "West":
                        setUpAreaSpinner(country_code_array);
                        break;
                    case "East":
                        setUpAreaSpinner(progress_statebar_array);
                        break;
                    case "North":
                        setUpAreaSpinner(central_region_array);
                        break;
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

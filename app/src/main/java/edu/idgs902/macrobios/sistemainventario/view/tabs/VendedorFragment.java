package edu.idgs902.macrobios.sistemainventario.view.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;

public class VendedorFragment extends Fragment {

    public VendedorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_vendedor, container, false);

        view.findViewById(R.id.btnPrueba).setOnClickListener(v -> {
            Toast.makeText(getActivity().getApplicationContext(),"Hola!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
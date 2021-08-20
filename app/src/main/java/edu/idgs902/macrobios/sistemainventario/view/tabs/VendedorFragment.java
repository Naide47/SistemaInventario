package edu.idgs902.macrobios.sistemainventario.view.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.ReporteVendedorActivity;

public class VendedorFragment extends Fragment {

    public VendedorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_vendedor, container, false);

        view.findViewById(R.id.btnPrueba).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReporteVendedorActivity.class));
        });

        return view;
    }
}
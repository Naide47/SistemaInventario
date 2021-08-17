package edu.idgs902.macrobios.sistemainventario.view.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.view.vendedor.AgregarVendedor;

public class VendedorFragment extends Fragment {

    public VendedorFragment() {
        // Required empty public constructor
    }

    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_vendedor, container, false);

        vista.findViewById(R.id.btnAgregar).setOnClickListener(view -> {
            Intent intent = new Intent(vista.getContext(), AgregarVendedor.class);
            startActivity(intent);
        });

        return vista;
    }

}
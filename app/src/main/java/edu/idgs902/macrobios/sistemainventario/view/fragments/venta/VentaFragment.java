package edu.idgs902.macrobios.sistemainventario.view.fragments.venta;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVenta;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.VentaAdapter;
import edu.idgs902.macrobios.sistemainventario.model.Venta;

public class VentaFragment extends Fragment  implements VentaAdapter.OnItemListener{

    private VentaAdapter adapter;
    private ControllerVenta controller;
    private List<Venta> ventas;

    public VentaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venta, container, false);

        obtenerDatos();
        addListener(view);

        return view;
    }

    private void addListener(View view) {
        RecyclerView vent_lista = view.findViewById(R.id.vent_lista);
        vent_lista.setHasFixedSize(true);
        vent_lista.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VentaAdapter(ventas,VentaFragment.this);
        vent_lista.setAdapter(adapter);

        view.findViewById(R.id.vent_fab).setOnClickListener(v -> startActivity(new Intent(getActivity(), AgregarVentaActivity.class)));

        ((EditText) view.findViewById(R.id.vent_buscador)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buscador(s.toString());
            }
        });
    }

    private void buscador(String texto) {
        List<Venta> nuevaLista = new ArrayList<>();
        for (Venta venta: ventas) {
            if (String.valueOf(venta.getNoVenta()).toLowerCase().contains(texto.toLowerCase())
            || venta.getFecha().toLowerCase().contains(texto.toLowerCase())||
            String.valueOf(venta.getTotal_venta()).toLowerCase().contains(texto.toLowerCase())) {
                nuevaLista.add(venta);
            }
        }
        adapter.actualizarLista(nuevaLista);
    }

    private void obtenerDatos() {
        controller = new ControllerVenta(getContext());
        ventas = controller.getVentas();
    }

    @Override
    public void onItemClick(int position) {

    }
}
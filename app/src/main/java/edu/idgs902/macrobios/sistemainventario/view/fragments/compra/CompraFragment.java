package edu.idgs902.macrobios.sistemainventario.view.fragments.compra;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerCompra;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.CompraAdapter;
import edu.idgs902.macrobios.sistemainventario.model.Compra;


public class CompraFragment extends Fragment implements CompraAdapter.OnItemListener {

    private CompraAdapter adapter;
    private ControllerCompra controller;
    private List<Compra> compras;

    public CompraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compra, container, false);

        obtenerDatos();
        addListeners(view);

        return view;
    }

    private void addListeners(View view) {
        RecyclerView comp_lista = view.findViewById(R.id.comp_lista);
        comp_lista.setHasFixedSize(true);
        comp_lista.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CompraAdapter(compras, CompraFragment.this);
        comp_lista.setAdapter(adapter);

        view.findViewById(R.id.comp_fab).setOnClickListener(v -> startActivity(new Intent(getActivity(), AgregarCompraActivity.class)));

        ((EditText) view.findViewById(R.id.comp_buscador)).addTextChangedListener(new TextWatcher() {
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
        if (compras != null) {
            List<Compra> nuevaLista = new ArrayList<>();
            for (Compra compra : compras) {
                if (String.valueOf(compra.getNoCompra()).toLowerCase().contentEquals(texto.toLowerCase())
                        || compra.getFecha().toLowerCase().contains(texto.toLowerCase()) ||
                        String.valueOf(compra.getTotal_compra()).toLowerCase().contains(texto.toLowerCase())) {
                    nuevaLista.add(compra);
                }
            }
            adapter.actualizarLista(nuevaLista);
        }
    }

    private void obtenerDatos() {
        controller = new ControllerCompra(getContext());
        compras = controller.getCompras();

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AccionesCompraActivity.class);
        intent.putExtra("compra", compras.get(position));
        startActivity(intent);
    }
}
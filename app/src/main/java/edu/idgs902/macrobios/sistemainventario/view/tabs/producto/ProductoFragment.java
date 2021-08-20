package edu.idgs902.macrobios.sistemainventario.view.tabs.producto;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.ProductoAdapter;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ProductoFragment extends Fragment implements ProductoAdapter.OnItemListener {

    private ProductoAdapter adapter;
    private ControllerProducto controller;
    private List<Producto> productos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        obtenerDatos();
        addListeners(view);

        return view;
    }

    private void addListeners(View view) {
        RecyclerView prodLista = view.findViewById(R.id.prod_lista);
        prodLista.setHasFixedSize(true);
        prodLista.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductoAdapter(productos, ProductoFragment.this);
        prodLista.setAdapter(adapter);

        view.findViewById(R.id.prod_fab_ag).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AgregarProductoActivity.class));
        });

        view.findViewById(R.id.prod_fab_rep).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReporteProductosActivity.class));
        });

        ((EditText) view.findViewById(R.id.prod_buscador)).addTextChangedListener(new TextWatcher() {
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
        if (productos != null) {
            List<Producto> nuevaLista = new ArrayList<>();
            for (Producto producto : productos) {
                if (producto.getNuProducto().toLowerCase().contains(texto.toLowerCase()) ||
                        producto.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                        producto.getLinea().toLowerCase().contains(texto.toLowerCase())) {
                    nuevaLista.add(producto);
                }
            }
            adapter.actualizarList(nuevaLista);
        }
    }

    private void obtenerDatos() {
        controller = new ControllerProducto(getContext());
        productos = controller.getProductos();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AccionesProductoActivity.class);
        intent.putExtra("producto", productos.get(position));
        startActivity(intent);
    }


}
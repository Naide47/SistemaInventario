package edu.idgs902.macrobios.sistemainventario.view.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.LiatElement;
import edu.idgs902.macrobios.sistemainventario.R;

import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVendedor;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.AgregarVendedor;
import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.ListAdapterVendedor;
import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.ListElementVendedor;
import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.ModificarVendedor;

//import edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor.ReporteVendedorActivity;


public class VendedorFragment extends Fragment implements ListAdapterVendedor.OnVendedorListener {

    public VendedorFragment() {
        // Required empty public constructor
    }

    private List<ListElementVendedor> elementsV;

    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_vendedor, container, false);

        init();


        vista.findViewById(R.id.btnAgregar).setOnClickListener(view -> {
            Intent intent = new Intent(vista.getContext(), AgregarVendedor.class);
            startActivity(intent);

        //view.findViewById(R.id.btnPrueba).setOnClickListener(v -> {
            //startActivity(new Intent(getActivity(), ReporteVendedorActivity.class));

        });

        return vista;
    }

    private void init(){
        ControllerVendedor controllerVendedor = new ControllerVendedor(vista.getContext());
        List<Vendedor> vendedor = controllerVendedor.getVendedores();

        ControllerPersona controllerPersona = new ControllerPersona(vista.getContext(), vista.getContext());
        List<Persona> personas = controllerPersona.getPersonas();

        elementsV = new ArrayList<>();

        for (int i = 0; i < vendedor.size(); i++){
            if (vendedor.get(i).getNoPersona() == personas.get(i).getNoPersona()){
                elementsV.add(new ListElementVendedor(String.valueOf(vendedor.get(i).getComisiones()), vendedor.get(i).getNoVendedor(), personas.get(i).getNoPersona(), personas.get(i).getNombre()));
            }
        }

        ListAdapterVendedor listAdapterVendedor = new ListAdapterVendedor(elementsV, vista.getContext(), this);
        RecyclerView recyclerView = vista.findViewById(R.id.listRecycleViewV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        recyclerView.setAdapter(listAdapterVendedor);
    }

    @Override
    public void onVendedorClick(int position) {
        int nopersona = elementsV.get(position).getNoPersona();
        int novendedor = elementsV.get(position).getNoVendedor();

        Intent intent = new Intent(vista.getContext(), ModificarVendedor.class);
        intent.putExtra("noPersona", nopersona);
        intent.putExtra("noVendedor", novendedor);
        startActivity(intent);
    }
}








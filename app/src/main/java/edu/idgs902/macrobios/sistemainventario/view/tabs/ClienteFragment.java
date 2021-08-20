package edu.idgs902.macrobios.sistemainventario.view.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.LiatElement;
import edu.idgs902.macrobios.sistemainventario.ListAdapter;
import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.view.AgregarCliente;
import edu.idgs902.macrobios.sistemainventario.view.ModificarCliente;
import edu.idgs902.macrobios.sistemainventario.view.tabs.externos.ReporteExternosActivity;


public class ClienteFragment extends Fragment implements ListAdapter.OnClienteListener {

    private List<LiatElement> elements;

    View vista;

    public ClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_cliente, container, false);

        init();

        vista.findViewById(R.id.btnAgregar).setOnClickListener(view -> {
            Intent intent = new Intent(vista.getContext(), AgregarCliente.class);
            startActivity(intent);
        });

        vista.findViewById(R.id.btnReporte).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReporteExternosActivity.class);
            intent.putExtra("tipo", 1);
            startActivity(intent);
        });

        return vista;
    }

    private void init() {

        ControllerExternos controllerExternos = new ControllerExternos(vista.getContext(), vista.getContext());
        List<Externo> externo = controllerExternos.getExternos();

        ControllerPersona controllerPersona = new ControllerPersona(vista.getContext(), vista.getContext());
        List<Persona> personas = controllerPersona.getPersonas();

        Log.e("externos", externo.toString());
        Log.e("personas", personas.toString());

        elements = new ArrayList<>();

        for (Persona persona : personas) {
            for (Externo externo1 : externo) {
                if (persona.getNoPersona() == externo1.getNoPersona()) {
                    if (externo1.getTipo() == 1) {
                        elements.add(new LiatElement(externo1.getNoExterno(), persona.getNoPersona(), "#775447", persona.getNombre(), externo1.getCiudad(), String.valueOf(externo1.getSaldo())));
                    }
                }
            }
        }


//        // Recorremos la lista de externos
//        for (int i = 0; i < externo.size(); i++) {
//
//            // Comprobamos que el noPersona de externos sea igual al noPersona de personas
//            if (externo.get(i).getNoPersona() == personas.get(i).getNoPersona()) {
//                // Comprobamos que el tipo sea 1 osea que es un cliente
//                if (externo.get(i).getTipo() == 1) {
//                    elements.add(new LiatElement(externo.get(i).getNoExterno(), personas.get(i).getNoPersona(), "#775447", personas.get(i).getNombre(), externo.get(i).getCiudad(), String.valueOf(externo.get(i).getSaldo())));
//                }
//            }
//
//        }
        Log.e("Clientes", elements.toString());

        ListAdapter listAdapter = new ListAdapter(elements, vista.getContext(), this);
        RecyclerView recyclerView = vista.findViewById(R.id.listRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onClienteClick(int position) {
        //elements.get(position);

        int nopersona = elements.get(position).getNoPersona();
        int noexterno = elements.get(position).getNoExterno();

        Intent intent = new Intent(vista.getContext(), ModificarCliente.class);
        intent.putExtra("noPersona", nopersona);
        intent.putExtra("noExterno", noexterno);
        startActivity(intent);

        //Log.d("nopersona", "onClienteClick: " + nopersona);
        //Log.d("noexterno", "onClienteClick: " + noexterno);

    }
}
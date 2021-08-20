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

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.LiatElement;
import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.view.proveedor.AgregarProveedor;
import edu.idgs902.macrobios.sistemainventario.view.proveedor.ListAdapterProveedor;
import edu.idgs902.macrobios.sistemainventario.view.proveedor.ListElemetProveedor;
import edu.idgs902.macrobios.sistemainventario.view.proveedor.ModificarProveedor;
import edu.idgs902.macrobios.sistemainventario.view.tabs.externos.ReporteExternosActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProveedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProveedorFragment extends Fragment implements ListAdapterProveedor.OnProveedorListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProveedorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProveedorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProveedorFragment newInstance(String param1, String param2) {
        ProveedorFragment fragment = new ProveedorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<ListElemetProveedor> elementsP;

    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_proveedor, container, false);

        init();

        vista.findViewById(R.id.btnAgregar).setOnClickListener(view -> {
            Intent intent = new Intent(vista.getContext(), AgregarProveedor.class);
            startActivity(intent);
        });

        vista.findViewById(R.id.btnReporte).setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), ReporteExternosActivity.class);
            intent.putExtra("tipo", 2);
            startActivity(intent);
        });

        return vista;
    }

    private void init() {

        ControllerExternos controllerExternos = new ControllerExternos(vista.getContext(), vista.getContext());
        List<Externo> externo = controllerExternos.getExternos();

        ControllerPersona controllerPersona = new ControllerPersona(vista.getContext(), vista.getContext());
        List<Persona> personas = controllerPersona.getPersonas();


        elementsP = new ArrayList<>();

        // Recorremos la lista de externos
        for(int i=0; i < externo.size(); i++) {

            // Comprobamos que el noPersona de externos sea igual al noPersona de personas
            if (externo.get(i).getNoPersona() == personas.get(i).getNoPersona()) {
                // Comprobamos que el tipo sea 2 osea que es un proveedor
                if (externo.get(i).getTipo() == 2) {

                    elementsP.add(new ListElemetProveedor(externo.get(i).getNoExterno(), personas.get(i).getNoPersona(),"#775447", personas.get(i).getNombre(), externo.get(i).getCiudad(), String.valueOf(externo.get(i).getSaldo())));
                }
            }

        }

        ListAdapterProveedor listAdapterProveedor = new ListAdapterProveedor(elementsP, vista.getContext(), this);
        RecyclerView recyclerView = vista.findViewById(R.id.list_productos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(vista.getContext()));
        recyclerView.setAdapter(listAdapterProveedor);
    }

    @Override
    public void onProveedorClick(int position) {
        //Log.d("noExterno", "onProveedorClick: "+elementsP.get(position).getNoExterno());

        int nopersona = elementsP.get(position).getNoPersona();
        int noexterno = elementsP.get(position).getNoExterno();

        Intent intent = new Intent(vista.getContext(), ModificarProveedor.class);
        intent.putExtra("noPersona", nopersona);
        intent.putExtra("noExterno", noexterno);
        startActivity(intent);

        //Log.d("nopersona", "onProveedorClick: " + nopersona);
        //Log.d("noexterno", "onProveedorClick: " + noexterno);
    }
}
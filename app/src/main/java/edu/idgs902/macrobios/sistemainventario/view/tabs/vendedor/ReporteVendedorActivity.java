package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;

public class ReporteVendedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_vendedor);

        List<Vendedor> vendedorList = new ArrayList<>();
//        ControllerVendedor cv = new ControllerVendedor();
//        List<Vendedor> vendedorList = cv.getVendedoresCompleto();
        vendedorList.add(new Vendedor(1, "Victor", "Calle 1", "Colonia 2", "477", "email", 1, 200));

        RecyclerView list_vendedores = findViewById(R.id.list_vendedores);
        list_vendedores.setHasFixedSize(true);
        list_vendedores.setLayoutManager(new LinearLayoutManager(ReporteVendedorActivity.this));
        ReporteVendedoresAdapter adapter = new ReporteVendedoresAdapter(vendedorList);

        String titulo = String.format(getString(R.string.txt_rep), "vendedores");
        ((TextView) findViewById(R.id.repVend_txtTitulo)).setText(titulo);

        findViewById(R.id.repVend_btnVolver).setOnClickListener(v -> onBackPressed());

    }
}
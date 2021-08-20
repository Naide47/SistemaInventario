package edu.idgs902.macrobios.sistemainventario.view.tabs.externos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.model.Externo;

public class ReporteExternosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_externos);

        int tipo = getIntent().getIntExtra("tipo", 0);
        String titulo = "";
        ControllerExternos ce = new ControllerExternos(ReporteExternosActivity.this, ReporteExternosActivity.this);
        List<Externo> externos = ce.getExternosCompletos(tipo);
        switch (tipo) {
            case 1:
                titulo = String.format(getString(R.string.txt_rep), "clientes");
                break;
            case 2:
                titulo = String.format(getString(R.string.txt_rep), "proveedores");
                break;
            default:
                onBackPressed();
        }
        if (externos.size() == 0) {
            Toast.makeText(ReporteExternosActivity.this,"ESTA VACIO",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        RecyclerView list_externos = findViewById(R.id.list_externos);
        list_externos.setHasFixedSize(true);
        list_externos.setLayoutManager(new LinearLayoutManager(ReporteExternosActivity.this));
        ReporteExternosAdapter adapter = new ReporteExternosAdapter(externos);
        list_externos.setAdapter(adapter);

        ((TextView) findViewById(R.id.repExt_txtTitulo)).setText(titulo);
        findViewById(R.id.repExt_btnVolver).setOnClickListener(v -> onBackPressed());

    }
}
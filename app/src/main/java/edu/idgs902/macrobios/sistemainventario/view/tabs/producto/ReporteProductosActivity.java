package edu.idgs902.macrobios.sistemainventario.view.tabs.producto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ReporteProductosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_productos);

        ControllerProducto cp = new ControllerProducto(ReporteProductosActivity.this);
        List<Producto> productoList = cp.getProductos();

        RecyclerView list_productos = findViewById(R.id.list_productos);
        list_productos.setHasFixedSize(true);
        if (productoList.size() == 0) {
            productoList = new ArrayList<>();
        }
        list_productos.setLayoutManager(new LinearLayoutManager(ReporteProductosActivity.this));
        ReporteProductosAdapter adapter = new ReporteProductosAdapter(productoList);
        list_productos.setAdapter(adapter);

        String titulo = String.format(getString(R.string.txt_rep), "productos");
        ((TextView) findViewById(R.id.repProd_txtTitulo)).setText(titulo);

        findViewById(R.id.repProd_btnVolver).setOnClickListener(v -> onBackPressed());
    }
}
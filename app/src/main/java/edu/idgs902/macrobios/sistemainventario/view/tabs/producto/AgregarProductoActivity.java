package edu.idgs902.macrobios.sistemainventario.view.tabs.producto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class AgregarProductoActivity extends AppCompatActivity {

    private ControllerProducto cp;

    private EditText agProd_edtNu, agProd_edtNombre;
    private Spinner agProd_spnLinea;

    private String nu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        initData();
        addListeners();
    }

    private void agregarProducto() {
        if (checkIsNotEmpty()) {
            Producto producto = new Producto(agProd_edtNu.getText().toString(),
                    agProd_edtNombre.getText().toString(),
                    agProd_spnLinea.getSelectedItem().toString());
            if (cp.addProducto(producto)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AgregarProductoActivity.this);
                dialog.setTitle(R.string.txt_agregado_titulo);
                String mensaje = String.format(getResources().getString(R.string.txt_agredado_cuerpo), "El", "producto");
                dialog.setMessage(mensaje);
                dialog.setPositiveButton(R.string.txt_ok, (d, w) -> {
                    d.dismiss();
                    onBackPressed();
                });
                dialog.create().show();
            } else {
                Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkIsNotEmpty() {
        if (TextUtils.isEmpty(agProd_edtNu.getText()))
            return false;
        else if (TextUtils.isEmpty(agProd_edtNombre.getText()))
            return false;
        else if (agProd_spnLinea.getSelectedItem().toString().equals(getString(R.string.seleccionar)))
            return false;
        else
            return true;
    }

    private void addListeners() {
        agProd_edtNu = findViewById(R.id.agProd_edtNu);
        agProd_edtNombre = findViewById(R.id.agProd_edtNombre);
        agProd_spnLinea = findViewById(R.id.agProd_spnLinea);

        ArrayAdapter<CharSequence> lineas = ArrayAdapter.createFromResource(AgregarProductoActivity.this, R.array.prod_categorias, R.layout.support_simple_spinner_dropdown_item);
        agProd_spnLinea.setAdapter(lineas);
        agProd_spnLinea.setSelection(0, false);
        agProd_spnLinea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String[] letras = {"A", "B", "C", "D", "E", "R", "S", "T", "U", "X"};
                    int pos = position -1;
                    int result = cp.getCuenta(letras[pos]);

                    if (result != -1) {
                        String text = letras[pos] + result;
                        agProd_edtNu.setText(text);
                    } else {
                        agProd_edtNu.setText("ERROR");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.agProd_btnCancelar).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.agProd_btnAceptar).setOnClickListener(v -> agregarProducto());
    }

    private void initData() {
        cp = new ControllerProducto(AgregarProductoActivity.this);
    }
}
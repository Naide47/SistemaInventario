package edu.idgs902.macrobios.sistemainventario.view.tabs.producto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class AccionesProductoActivity extends AppCompatActivity {

    private final String[] letras = {"A", "B", "C", "D", "E", "R", "S", "T", "U", "X"};
    private final String[] lineas = {
            "Hombre 25 – 30",
            "Joven 22 – 25",
            "Niño 18 – 21",
            "Niño 15 – 17",
            "Niño 12 – 14",
            "Dama 22 -26",
            "Niña 18 – 21",
            "Niña 15 – 17",
            "Niña 12 – 14",
            "BEBE 10 – 12"
    };

    private Producto producto;

    private EditText edtProd_edtNu, edtProd_edtNombre, edtProd_edtExistencia,
            edtProd_edtPCosto, edtProd_edtPPromedio, edtProd_edtPMayor, edtProd_edtPMenor;
    private Spinner edtProd_spnLinea;

    private Button edtProd_btnModificar, edtProd_btnAceptar, edtProd_btnEliminar,
            edtProd_btnCancelar, edtProd_btnVolver;

    private String nuProducto;
    private ControllerProducto cp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones_producto);

        obtenerDatos();
        addListeners();
        colocarDatos();
        activarCampos(false);
        activarBotones(View.VISIBLE, View.GONE);

    }

    private void activarCampos(boolean activo) {
//        edtProd_edtNu.setEnabled(activo);
        edtProd_edtNombre.setEnabled(activo);
        edtProd_spnLinea.setEnabled(activo);
        edtProd_edtExistencia.setEnabled(activo);
        edtProd_edtPCosto.setEnabled(activo);
        edtProd_edtPPromedio.setEnabled(activo);
        edtProd_edtPMayor.setEnabled(activo);
        edtProd_edtPMenor.setEnabled(activo);
    }

    private void activarBotones(int principales, int secundarios) {
        edtProd_btnModificar.setVisibility(principales);
        edtProd_btnAceptar.setVisibility(secundarios);
        edtProd_btnEliminar.setVisibility(principales);
        edtProd_btnCancelar.setVisibility(secundarios);
        edtProd_btnVolver.setVisibility(principales);
    }

    private void colocarDatos() {
        edtProd_edtNu.setText(producto.getNuProducto());
        edtProd_edtNombre.setText(producto.getNombre());
        edtProd_edtExistencia.setText(String.valueOf(producto.getExistencia()));
        edtProd_edtPCosto.setText(String.valueOf(String.valueOf(producto.getP_costo())));
        edtProd_edtPPromedio.setText(String.valueOf(producto.getP_promedio()));
        edtProd_edtPMayor.setText(String.valueOf(producto.getP_venta_mayor()));
        edtProd_edtPMenor.setText(String.valueOf(producto.getP_venta_menor()));

        for (int i = 0; i < lineas.length; i++) {
            if (producto.getLinea().equals(lineas[i])) {
                edtProd_spnLinea.setSelection(i);
                break;
            }
        }
    }

    private void addListeners() {
//        EditText
        edtProd_edtNu = findViewById(R.id.edtProd_edtNu);
        edtProd_edtNombre = findViewById(R.id.edtProd_edtNombre);
        edtProd_edtExistencia = findViewById(R.id.edtProd_edtExistencia);
        edtProd_edtPCosto = findViewById(R.id.edtProd_edtPCosto);
        edtProd_edtPPromedio = findViewById(R.id.edtProd_edtPPromedio);
        edtProd_edtPMayor = findViewById(R.id.edtProd_edtPMayor);
        edtProd_edtPMenor = findViewById(R.id.edtProd_edtPMenor);
//        Spinner
        edtProd_spnLinea = findViewById(R.id.edtProd_spnLinea);
        edtProd_spnLinea.setAdapter(ArrayAdapter.createFromResource(AccionesProductoActivity.this,
                R.array.prod_categorias2, R.layout.support_simple_spinner_dropdown_item));
        edtProd_spnLinea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals(producto.getLinea())) {
                    int result = cp.getCuenta(letras[position]);
                    if (result != -1) {
                        String text = letras[position] + result;
                        edtProd_edtNu.setText(text);
                    } else {
                        edtProd_edtNu.setText("ERROR");
                    }
                } else {
                    if (!edtProd_edtNu.getText().toString().equals(nuProducto))
                        edtProd_edtNu.setText(nuProducto);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        Botones
        edtProd_btnModificar = findViewById(R.id.edtProd_btnModificar);
        edtProd_btnModificar.setOnClickListener(v -> activarEdicion(View.GONE, View.VISIBLE, true, false));
        edtProd_btnAceptar = findViewById(R.id.edtProd_btnAceptar);
        edtProd_btnAceptar.setOnClickListener(v -> realizarEdicion());
        edtProd_btnCancelar = findViewById(R.id.edtProd_btnCancelar);
        edtProd_btnCancelar.setOnClickListener(v -> activarEdicion(View.VISIBLE, View.GONE, false, true));
        edtProd_btnEliminar = findViewById(R.id.edtProd_btnEliminar);
        edtProd_btnEliminar.setOnClickListener(v -> realizarEliminacion());
        edtProd_btnVolver = findViewById(R.id.edtProd_btnVolver);
        findViewById(R.id.edtProd_btnVolver).setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void activarEdicion(int principales, int secundarios, boolean campos, boolean restaurar) {
        activarCampos(campos);
        activarBotones(principales, secundarios);
        if (restaurar) {
            colocarDatos();
        }
    }

    private void realizarEdicion() {
//        Log.i("realizarEdicion", "Entro");
        if (checkIsNotEmpty()) {
//            Log.i("realizarEdicion", "No esta vacio");
            Producto producto = new Producto(this.producto.getNoProducto(),
                    edtProd_edtNu.getText().toString(),
                    edtProd_edtNombre.getText().toString(), edtProd_spnLinea.getSelectedItem().toString(),
                    Integer.parseInt(edtProd_edtExistencia.getText().toString()),
                    Double.parseDouble(edtProd_edtPCosto.getText().toString()),
                    Double.parseDouble(edtProd_edtPPromedio.getText().toString()),
                    Double.parseDouble(edtProd_edtPMayor.getText().toString()),
                    Double.parseDouble(edtProd_edtPMenor.getText().toString()));
//            Log.i("realizarEdicion", "Producto armado");
            if (cp.updateProducto(producto)) {
//                Log.i("realizarEdicion", "Actualizo");
                AlertDialog.Builder dialog = new AlertDialog.Builder(AccionesProductoActivity.this);
                dialog.setTitle(R.string.txt_actualizado_titulo);
                String mensaje = String.format(getResources().getString(R.string.txt_actualizado_cuerpo), "El", "producto", "o");
                dialog.setMessage(mensaje);
                dialog.setPositiveButton(R.string.txt_ok, (d, w) -> {
                    d.dismiss();
                    onBackPressed();
                });
                dialog.create().show();
            } //else {
//                Log.i("realizarEdicion", "No actualizo");
//                Toast.makeText(AccionesProductoActivity.this, "cp.updateProducto", Toast.LENGTH_LONG);
            }
//        } else
//            Log.i("realizarEdicion", "Esta vacio");
//            Toast.makeText(AccionesProductoActivity.this, "checkIsNotEmpty", Toast.LENGTH_LONG);
    }

    private void realizarEliminacion() {
        AlertDialog.Builder alertaEliminar = new AlertDialog.Builder(AccionesProductoActivity.this);
        String mensaje = String.format(getResources().getString(R.string.txt_eliminar_cuerpo), "e", "producto");
        String exito = String.format(getResources().getString(R.string.txt_eliminar_exito), "El", "producto", "o");

        alertaEliminar.setTitle(R.string.txt_eliminar_titulo);
        alertaEliminar.setMessage(mensaje);
        alertaEliminar.setPositiveButton(getString(R.string.btn_aceptar), (d, w) -> {
            if (cp.deleteProducto(producto.getNoProducto())) {
                Toast.makeText(AccionesProductoActivity.this, exito, Toast.LENGTH_LONG).show();
            d.dismiss();
            onBackPressed();
            } //else {
//                Log.e("realizarEliminacion", "NO SE ELIMINO");
//            }
        });
        alertaEliminar.setNegativeButton(getString(R.string.btn_cancelar), (d, w) -> {
            d.dismiss();
        });

        alertaEliminar.create().show();
    }

    private boolean checkIsNotEmpty() {
        if (TextUtils.isEmpty(edtProd_edtNu.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtNombre.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtExistencia.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtPCosto.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtPPromedio.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtPMayor.getText()))
            return false;
        else if (TextUtils.isEmpty(edtProd_edtPMenor.getText()))
            return false;
        else
            return true;
    }

    private void obtenerDatos() {
        producto = getIntent().getParcelableExtra("producto");
        cp = new ControllerProducto(AccionesProductoActivity.this);
        nuProducto = producto.getNuProducto();
    }
}
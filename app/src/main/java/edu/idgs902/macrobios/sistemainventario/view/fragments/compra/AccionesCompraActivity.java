package edu.idgs902.macrobios.sistemainventario.view.fragments.compra;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerCompra;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.DetalleCompraAdapter;
import edu.idgs902.macrobios.sistemainventario.model.Compra;
import edu.idgs902.macrobios.sistemainventario.model.DetalleCompra;
import edu.idgs902.macrobios.sistemainventario.model.Externo;

public class AccionesCompraActivity extends AppCompatActivity implements DetalleCompraAdapter.AccionesLista, DatePickerDialog.OnDateSetListener {

    private Compra compra;

    private ControllerCompra controllerCompra;

    private EditText actComp_edtNo, actComp_edtFecha, actComp_edtProveedor,
            actComp_edtNombre, actComp_edtCalle, actComp_edtFR;
    private TextView actComp_txtTPares, actComp_txtSuma, actComp_txtIva, actComp_txtTotal;
    private Button actComp_btnProv, actComp_btnModificar,
            actComp_btnEliminar, actComp_btnAceptar, actComp_btnCancelar;
    private Spinner actComp_spnFR;

    private List<DetalleCompra> detallesCompra;
    private DetalleCompraAdapter adapter;
    private Externo proveedor;

    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones_compra);

        obtenerDatos();
        addListeners();
        colocarInfo();
        activarCampos(false);
        mostrarBotones(View.VISIBLE, View.GONE);
    }

    private void mostrarBotones(int acciones, int edicion) {
        actComp_btnModificar.setVisibility(acciones);
        actComp_btnEliminar.setVisibility(acciones);
        actComp_btnAceptar.setVisibility(edicion);
        actComp_btnCancelar.setVisibility(edicion);
    }

    private void activarCampos(boolean estado) {
        actComp_edtProveedor.setEnabled(estado);
        actComp_btnProv.setEnabled(estado);
        actComp_edtFecha.setEnabled(estado);
        actComp_spnFR.setEnabled(estado);
        for (int i = 0; i < detallesCompra.size(); i++) {
            detallesCompra.get(i).setEstado(estado);
        }
        adapter.actualizarLista(detallesCompra);
    }

    private void colocarInfo() {
        actComp_edtNo.setText(String.valueOf(compra.getNoCompra()));
        actComp_edtProveedor.setText(String.valueOf(compra.getExterno_proveedor().getNoExterno()));
        actComp_edtNombre.setText(compra.getExterno_proveedor().getNombre());
        actComp_edtCalle.setText(compra.getExterno_proveedor().getCalle());
        actComp_edtFecha.setText(compra.getFecha());
        actComp_spnFR.setSelection((compra.getF_r().equals(getString(R.string.cv_factura)) ? 0 : 1));
        actComp_edtFR.setText(String.valueOf(compra.getF_r_no()));
        actComp_txtTPares.setText(String.valueOf(compra.getTotal_pares()));
        actComp_txtSuma.setText(String.valueOf(compra.getSuma()));
        actComp_txtIva.setText(String.valueOf(compra.getIva()));
        actComp_txtTotal.setText(String.valueOf(compra.getTotal_compra()));
        detallesCompra = compra.getDetallesCompra();
        adapter.actualizarLista(detallesCompra);
    }

    private void addListeners() {
        // RecyclerView
        RecyclerView compra_detalle = findViewById(R.id.compra_detalle);
        compra_detalle.setHasFixedSize(true);
        compra_detalle.setLayoutManager(new LinearLayoutManager(AccionesCompraActivity.this));
        adapter = new DetalleCompraAdapter(AccionesCompraActivity.this, detallesCompra, AccionesCompraActivity.this);
        compra_detalle.setAdapter(adapter);
        // EditText
        actComp_edtNo = findViewById(R.id.actComp_edtNo);
        actComp_edtFecha = findViewById(R.id.actComp_edtFecha);
        actComp_edtProveedor = findViewById(R.id.actComp_edtProveedor);
        actComp_edtNombre = findViewById(R.id.actComp_edtNombre);
        actComp_edtCalle = findViewById(R.id.actComp_edtCalle);
        actComp_edtFR = findViewById(R.id.actComp_edtFR);
        // TextView
        actComp_txtTPares = findViewById(R.id.actComp_txtTPares);
        actComp_txtSuma = findViewById(R.id.actComp_txtSuma);
        actComp_txtIva = findViewById(R.id.actComp_txtIva);
        actComp_txtTotal = findViewById(R.id.actComp_txtTotal);
//        Button
        actComp_btnProv = findViewById(R.id.actComp_btnProv);
        actComp_btnModificar = findViewById(R.id.actComp_btnModificar);
        actComp_btnEliminar = findViewById(R.id.actComp_btnEliminar);
        actComp_btnAceptar = findViewById(R.id.actComp_btnAceptar);
        actComp_btnCancelar = findViewById(R.id.actComp_btnCancelar);
//        Spinner
        actComp_spnFR = findViewById(R.id.actComp_spnFR);
        accionesButton();
        accionesEditText();
        accionesSpn();

    }

    private void accionesButton() {
        actComp_btnProv.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(actComp_edtProveedor.getText())) {
                int noExterno_proveedor = Integer.parseInt(actComp_edtProveedor.getText().toString());
                if (noExterno_proveedor == compra.getExterno_proveedor().getNoExterno()) {
                    mostrarToast("Mismo proveedor");
                    return;
                }
                ControllerExternos controllerExterno = new ControllerExternos(AccionesCompraActivity.this, AccionesCompraActivity.this);
                Externo aux = controllerExterno.getExternoCompleto(noExterno_proveedor);
                if (aux != null && aux.getTipo() == 2) {
                    proveedor = aux;
                    actComp_edtNombre.setText(proveedor.getNombre());
                    actComp_edtCalle.setText(proveedor.getCalle());
                } else {
                    mostrarToast("Proveedor no encontrado");
                }
            } else {
                mostrarToast("Por favor ingrese el número del proveedor");
            }
        });
        actComp_btnModificar.setOnClickListener(v -> activarEdicion(false, true, false));
        actComp_btnCancelar.setOnClickListener(v -> activarEdicion(true, false, true));
        actComp_btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder alertaEliminar = new AlertDialog.Builder(AccionesCompraActivity.this);
            String mensaje = String.format(getResources().getString(R.string.txt_eliminar_cuerpo), "a", "compra");
            String exito = String.format(getResources().getString(R.string.txt_eliminar_exito), "La", "compra", "a");

            alertaEliminar.setTitle(R.string.txt_eliminar_titulo);
            alertaEliminar.setMessage(mensaje);
            alertaEliminar.setPositiveButton(getString(R.string.btn_aceptar), (d, w) -> {
                if (controllerCompra.deleteCompra(compra.getNoCompra())) {
                    Toast.makeText(AccionesCompraActivity.this, exito, Toast.LENGTH_LONG).show();
                    d.dismiss();
                    onBackPressed();
                } else {
                    mostrarToast("NO SE ELIMINO");
                }
            });
            alertaEliminar.setNegativeButton(getString(R.string.btn_cancelar), (d, w) -> {
                d.dismiss();
            });
            alertaEliminar.create().show();
        });
        actComp_btnAceptar.setOnClickListener(v -> {
            if (checarEstaCompleto()) {

                if (compra.getExterno_proveedor() == proveedor &&
                        compra.getFecha().equals(actComp_edtFecha.getText().toString()) &&
                        compra.getTotal_pares() == Integer.parseInt(actComp_txtTPares.getText().toString()) &&
                        compra.getSuma() == Double.parseDouble(actComp_txtSuma.getText().toString()) &&
                        compra.getTotal_compra() == Double.parseDouble(actComp_txtTotal.getText().toString())) {
                    mostrarToast("No se han registrado cambios");
                    return;
                }
                compra.setExterno_proveedor(proveedor);
                compra.setFecha(actComp_edtFecha.getText().toString());
                compra.setTotal_pares(Integer.parseInt(actComp_txtTPares.getText().toString()));
                compra.setSuma(Double.parseDouble(actComp_txtSuma.getText().toString()));
                compra.setIva(Double.parseDouble(actComp_txtIva.getText().toString()));
                compra.setTotal_compra(Double.parseDouble(actComp_txtTotal.getText().toString()));
                compra.setDetallesCompra(adapter.getLista());
                if (controllerCompra.updateCompra(compra)) {
                    AlertDialog.Builder alertaModificacion = new AlertDialog.Builder(AccionesCompraActivity.this);
                    alertaModificacion.setTitle(R.string.txt_actualizado_titulo);
                    String mensaje = String.format(getString(R.string.txt_actualizado_cuerpo), "La", "compra", "a");
                    alertaModificacion.setMessage(mensaje);
                    alertaModificacion.setPositiveButton(R.string.txt_ok, (d, w) -> {
                        d.dismiss();
                        onBackPressed();
                    });
                    alertaModificacion.create().show();
                } else {
                    mostrarToast("Fallo al actualizar");
                }
            } else {
                mostrarToast("Por favor, completa los campos.");
            }
        });
        findViewById(R.id.actComp_btnVolver).setOnClickListener(v -> onBackPressed());

    }

    private void activarEdicion(boolean mostrarBotones, boolean activarCampos, boolean restaurar) {
        mostrarBotones(
                (mostrarBotones) ? View.VISIBLE : View.GONE,
                (mostrarBotones) ? View.GONE : View.VISIBLE
        );
        activarCampos(activarCampos);
        if (restaurar) {
            colocarInfo();
        }
    }

    private void accionesEditText() {
        actComp_edtFecha.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(
                    AccionesCompraActivity.this,
                    AccionesCompraActivity.this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });
    }

    private void accionesSpn() {
        actComp_spnFR.setAdapter(ArrayAdapter.createFromResource(AccionesCompraActivity.this,
                R.array.cv_f_r_opciones, R.layout.support_simple_spinner_dropdown_item));
        actComp_spnFR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.cv_factura))) {
                    double suma = Double.parseDouble(actComp_txtSuma.getText().toString());

                    double iva = suma * 0.16;

                    double total = suma + iva;

                    actComp_txtIva.setText(df.format(iva));
                    actComp_txtTotal.setText(df.format(total));
                } else {
                    actComp_txtIva.setText(df.format(0.00));
                    actComp_txtTotal.setText(actComp_txtSuma.getText());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean checarEstaCompleto() {
        boolean estaCompleto = true;
        if (TextUtils.isEmpty(actComp_edtProveedor.getText())) {
            estaCompleto = false;
        }
        if (TextUtils.isEmpty(actComp_edtFecha.getText())) {
            estaCompleto = false;
        }
        if (actComp_txtTPares.getText().toString().equals(String.valueOf(0))) {
            estaCompleto = false;
        }
        if (actComp_txtSuma.getText().toString().equals(String.valueOf(0))) {
            estaCompleto = false;
        }
        if (actComp_txtTotal.getText().toString().equals(String.valueOf(0))) {
            estaCompleto = false;
        }
        return estaCompleto;
    }

    private void obtenerDatos() {
        compra = getIntent().getParcelableExtra("compra");
        proveedor = compra.getExterno_proveedor();
        controllerCompra = new ControllerCompra(AccionesCompraActivity.this);
        detallesCompra = compra.getDetallesCompra();
        df = new DecimalFormat("#0.00");

    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(AccionesCompraActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Métodos sobreescritos

    @Override
    public void calcularTotal(int position, String charSeq, int tipo) {
        if (tipo == 1) {
            detallesCompra.get(position).setCantidad_producto(Integer.parseInt(charSeq));
        } else {
            detallesCompra.get(position).setPrecio_compra(Double.parseDouble(charSeq));
        }

        int totalPares = 0;
        double suma = 0;
        for (DetalleCompra detalleCompra : detallesCompra) {
            totalPares += detalleCompra.getCantidad_producto();
            suma += (detalleCompra.getCantidad_producto() * detalleCompra.getPrecio_compra());
        }
        double iva = 0.0;
        if (actComp_spnFR.getSelectedItem().toString().equals(getString(R.string.cv_factura))) {
            iva = suma * 0.16;
        }
        double total = suma + iva;

        actComp_txtTPares.setText(String.valueOf(totalPares));
        actComp_txtSuma.setText(df.format(suma));
        actComp_txtIva.setText(df.format(iva));
        actComp_txtTotal.setText(df.format(total));
    }

    @Override
    public void eliminarElemento(int position) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha = dayOfMonth + "/" + month + "/" + year;
        actComp_edtFecha.setText(fecha);
    }
}
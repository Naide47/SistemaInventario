package edu.idgs902.macrobios.sistemainventario.view.fragments.compra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerCompra;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.DetalleCompraAdapter;
import edu.idgs902.macrobios.sistemainventario.model.Compra;
import edu.idgs902.macrobios.sistemainventario.model.DetalleCompra;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class AgregarCompraActivity extends AppCompatActivity implements DetalleCompraAdapter.AccionesLista, DatePickerDialog.OnDateSetListener {

    private ControllerProducto controllerProducto;
    private ControllerExternos controllerExterno;
    private ControllerCompra controllerCompra;

    private EditText agComp_edtNo, agComp_edtFecha, agComp_edtProveedor,
            agComp_edtNombre, agComp_edtCalle, agComp_edtBProd, agComp_edtFR;
    private TextView agComp_txtTPares, agComp_txtSuma, agComp_txtIva, agComp_txtTotal;
    private Button agComp_btnProv, agComp_btnBProd, agComp_btnAgregar;
    private Spinner agComp_spnFR;

    private List<DetalleCompra> detallesCompra;
    private DetalleCompraAdapter adapter;
    private Externo proveedor;

    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_compra);

        iniciarVariables();
        addListeners();
    }

    private void addListeners() {
//        RecyclerView
        RecyclerView compra_detalle = findViewById(R.id.compra_detalle);
        compra_detalle.setHasFixedSize(true);
        compra_detalle.setLayoutManager(new LinearLayoutManager(AgregarCompraActivity.this));
        adapter = new DetalleCompraAdapter(AgregarCompraActivity.this, detallesCompra, AgregarCompraActivity.this);
        compra_detalle.setAdapter(adapter);
//        EditText
        agComp_edtNo = findViewById(R.id.agComp_edtNo);
        agComp_edtNo.setText(String.valueOf(controllerCompra.getNoCompra()));
        agComp_edtFecha = findViewById(R.id.agComp_edtFecha);
        agComp_edtFecha.setOnClickListener(v -> agregarFecha());
        agComp_edtProveedor = findViewById(R.id.agComp_edtProveedor);
        agComp_edtNombre = findViewById(R.id.agComp_edtNombre);
        agComp_edtCalle = findViewById(R.id.agComp_edtCalle);
        agComp_edtBProd = findViewById(R.id.agComp_edtBProd);
        agComp_edtFR = findViewById(R.id.agComp_edtFR);
//        TextView
        agComp_txtTPares = findViewById(R.id.agComp_txtTPares);
        agComp_txtSuma = findViewById(R.id.agComp_txtSuma);
        agComp_txtIva = findViewById(R.id.agComp_txtIva);
        agComp_txtTotal = findViewById(R.id.agComp_txtTotal);
//        Bototes
        agComp_btnProv = findViewById(R.id.agComp_btnProv);
        agComp_btnProv.setOnClickListener(v -> agregarProveedor());
        agComp_btnBProd = findViewById(R.id.agComp_btnBProd);
        agComp_btnBProd.setOnClickListener(v -> agregarProducto());
        agComp_btnAgregar = findViewById(R.id.agComp_btnAgregar);
        agComp_btnAgregar.setOnClickListener(v -> agregarCompra());
        findViewById(R.id.agComp_btnVolver).setOnClickListener(v -> onBackPressed());
//        Spinner
        agComp_spnFR = findViewById(R.id.agComp_spnFR);
        agComp_spnFR.setAdapter(ArrayAdapter.createFromResource(AgregarCompraActivity.this, R.array.cv_f_r_opciones, R.layout.support_simple_spinner_dropdown_item));
        agComp_spnFR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.cv_factura))) {
                    int noFactura = controllerCompra.getNoFactura();
                    agComp_edtFR.setText(String.valueOf(noFactura));
                    double suma = Double.parseDouble(agComp_txtSuma.getText().toString());
                    double iva = suma * 0.16;
                    double total = suma + iva;
                    agComp_txtIva.setText(df.format(iva));
                    agComp_txtTotal.setText(df.format(total));
                } else {
                    agComp_txtIva.setText(df.format(0.00));
                    agComp_txtTotal.setText(agComp_txtSuma.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void agregarFecha() {
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void agregarCompra() {
        if (checkIsNotEmpty()) {
            detallesCompra = adapter.getLista();
            String f_r = agComp_spnFR.getSelectedItem().toString();
            String f_r_no = agComp_edtFR.getText().toString();

            Compra compra = new Compra(proveedor,
                    agComp_edtFecha.getText().toString(),
                    f_r, Integer.parseInt(f_r_no),
                    Integer.parseInt(agComp_txtTPares.getText().toString()),
                    Double.parseDouble(agComp_txtSuma.getText().toString()),
                    Double.parseDouble(agComp_txtIva.getText().toString()),
                    Double.parseDouble(agComp_txtTotal.getText().toString()),
                    detallesCompra);

            if (controllerCompra.addCompra(compra)) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(AgregarCompraActivity.this);
                dialog.setTitle(R.string.txt_agregado_titulo);
                String mensaje = String.format(getResources().getString(R.string.txt_agredado_cuerpo), "La", "compra");
                dialog.setMessage(mensaje);
                dialog.setPositiveButton(R.string.txt_ok, (d, w) -> {
                    d.dismiss();
                    onBackPressed();
                });
                dialog.create().show();
            } else {
                Toast.makeText(this, "Error al agregar la compra", Toast.LENGTH_LONG).show();
            }

        } else {
            mostarToast("Completa los campos y/o declara cantidades y costos");
        }
    }


    private void agregarProducto() {
        if (!TextUtils.isEmpty(agComp_edtBProd.getText())) {
            String nuProducto = agComp_edtBProd.getText().toString().trim().toUpperCase();
            for (DetalleCompra detalleCompra : detallesCompra) {
                if (nuProducto.equals(detalleCompra.getProducto().getNuProducto())) {
                    mostarToast("Ese producto ya esta en la lista");
                    return;
                }
            }

            Producto productoAux = controllerProducto.getProducto(nuProducto);
            if (productoAux != null) {
                DetalleCompra detalleCompra = new DetalleCompra(productoAux, 0, 0);
                detallesCompra = adapter.getLista();
                detallesCompra.add(detalleCompra);
                adapter.actualizarLista(detallesCompra);
            } else {
                mostarToast("Por favor ingrese el número del producto");
            }

        }
    }

    private void agregarProveedor() {
        if (!TextUtils.isEmpty(agComp_edtProveedor.getText())) {
            Externo aux = controllerExterno.getExternoCompleto(Integer.parseInt(agComp_edtProveedor.getText().toString()));
            if (aux != null && aux.getTipo() == 2) {
                proveedor = aux;
                agComp_edtNombre.setText(proveedor.getNombre());
                agComp_edtCalle.setText(proveedor.getCalle());

            } else {
                mostarToast("Proveedor no encontrado");
            }
        } else {
            mostarToast("Por favor ingrese el número del proveedor");
        }
    }

    private void iniciarVariables() {
        controllerProducto = new ControllerProducto(AgregarCompraActivity.this);
        controllerExterno = new ControllerExternos(AgregarCompraActivity.this, AgregarCompraActivity.this);
        controllerCompra = new ControllerCompra(AgregarCompraActivity.this);
        detallesCompra = new ArrayList<>();
        df = new DecimalFormat("#0.00");
    }

    private boolean checkIsNotEmpty() {
        if (TextUtils.isEmpty(agComp_edtProveedor.getText()))
            return false;
        else if (TextUtils.isEmpty(agComp_edtNombre.getText()))
            return false;
        else if (TextUtils.isEmpty(agComp_edtCalle.getText()))
            return false;
        else if (TextUtils.isEmpty(agComp_edtFecha.getText()))
            return false;
        else if (detallesCompra.size() == 0)
            return false;
        else if (agComp_txtTPares.getText().toString().equals(String.valueOf(0)))
            return false;
        else if (agComp_txtSuma.getText().toString().equals(String.valueOf(0)))
            return false;
        else if (agComp_txtIva.getText().toString().equals(String.valueOf(0)))
            return false;
        else if (agComp_txtTotal.getText().toString().equals(String.valueOf(0)))
            return false;
        else
            return true;
    }

    private void mostarToast(String mensaje) {
        Toast.makeText(AgregarCompraActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }


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
        if (agComp_spnFR.getSelectedItem().toString().equals(getString(R.string.cv_factura))) {
            iva = suma * 0.16;
        }
        double total = suma + iva;

        agComp_txtTPares.setText(String.valueOf(totalPares));
        agComp_txtSuma.setText(df.format(suma));
        agComp_txtIva.setText(df.format(iva));
        agComp_txtTotal.setText(df.format(total));
    }

    @Override
    public void eliminarElemento(int position) {
        detallesCompra = adapter.getLista();
        detallesCompra.remove(position);
        adapter.actualizarLista(detallesCompra);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha = dayOfMonth + "/" + month + "/" + year;
        this.agComp_edtFecha.setText(fecha);
    }
}
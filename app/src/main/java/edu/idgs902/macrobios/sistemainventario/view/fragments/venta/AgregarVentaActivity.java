package edu.idgs902.macrobios.sistemainventario.view.fragments.venta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerProducto;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVenta;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.DetalleVentaAdapter;
import edu.idgs902.macrobios.sistemainventario.model.DetalleVenta;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Producto;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.model.Venta;

public class AgregarVentaActivity extends AppCompatActivity implements DetalleVentaAdapter.AccionesLista, DatePickerDialog.OnDateSetListener {

    private ControllerProducto controllerProducto;
    private ControllerExternos controllerExternos;
    private ControllerVenta controllerVenta;

    private EditText agVenta_edtNumero, agVenta_edtClsClave, agVenta_edtClsNombre,
            agVenta_edtClsCalle, agVent_edtVendClave, agVenta_edtVendNombre, agVenta_edtFecha,
            agVenta_edtFactura, agVenta_edtComision, agVent_edtProdClave;
    private Spinner agVenta_spnFR;
    private Button agVenta_btnAgregar, agVenta_btnClsBuscar, agVenta_btnVendBuscar, agVent_btnProdBuscar;
    private TextView agVenta_txtPares, agVenta_txtSuma, agVenta_txtIva, agVenta_txtTotal;

    private List<DetalleVenta> detallesVenta;
    private DetalleVentaAdapter adapter;
    private Externo cliente;
    private Vendedor vendedor;

    DecimalFormat df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_venta);

        iniciarVariables();
        addListeners();
    }

    private void addListeners() {
//        RecyclerView
        RecyclerView venta_detalle = findViewById(R.id.venta_detalle);
        venta_detalle.setHasFixedSize(true);
        venta_detalle.setLayoutManager(new LinearLayoutManager(AgregarVentaActivity.this));
        adapter = new DetalleVentaAdapter(AgregarVentaActivity.this, detallesVenta, AgregarVentaActivity.this);
        venta_detalle.setAdapter(adapter);
//        EditText
        agVenta_edtNumero = findViewById(R.id.agVenta_edtNumero);
        agVenta_edtClsClave = findViewById(R.id.agVenta_edtClsClave);
        agVenta_edtClsNombre = findViewById(R.id.agVenta_edtClsNombre);
        agVenta_edtClsCalle = findViewById(R.id.agVenta_edtClsCalle);
        agVent_edtVendClave = findViewById(R.id.agVent_edtVendClave);
        agVenta_edtVendNombre = findViewById(R.id.agVenta_edtVendNombre);
        agVenta_edtFecha = findViewById(R.id.agVenta_edtFecha);
        agVenta_edtFactura = findViewById(R.id.agVenta_edtFactura);
        agVenta_edtComision = findViewById(R.id.agVenta_edtComision);
        agVent_edtProdClave = findViewById(R.id.agVent_edtProdClave);
//        Spinner
        agVenta_spnFR = findViewById(R.id.agVenta_spnFR);
//        Button
        findViewById(R.id.agVent_btnVolver).setOnClickListener(v -> onBackPressed());
        agVenta_btnAgregar = findViewById(R.id.agVenta_btnAgregar);
        agVenta_btnClsBuscar = findViewById(R.id.agVenta_btnClsBuscar);
        agVenta_btnVendBuscar = findViewById(R.id.agVenta_btnVendBuscar);
        agVent_btnProdBuscar = findViewById(R.id.agVent_btnProdBuscar);
//        TextView
        agVenta_txtPares = findViewById(R.id.agVenta_txtPares);
        agVenta_txtSuma = findViewById(R.id.agVenta_txtSuma);
        agVenta_txtIva = findViewById(R.id.agVenta_txtIva);
        agVenta_txtTotal = findViewById(R.id.agVenta_txtTotal);
        addEditTextActions();
        addSpinnerActions();
        addButtonsActions();
    }

    private void addButtonsActions() {
        agVenta_btnClsBuscar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(agVenta_edtClsClave.getText())) {
                Externo aux = controllerExternos.getExternoCompleto(Integer.parseInt(agVenta_edtClsClave.getText().toString()));
                if (aux != null && aux.getTipo() == 1) {
                    cliente = aux;
                    agVenta_edtClsNombre.setText(cliente.getNombre());
                    agVenta_edtClsCalle.setText(cliente.getCalle());
                } else {
                    mostrarToast("Cliente no encontrado");
                }
            } else {
                mostrarToast("Por favor ingrese el número del cliente");
            }
        });
        agVenta_btnVendBuscar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(agVent_edtVendClave.getText())) {
//                    Vendedor aux = controllerVendedor.getVendedor(Integer.parseInt(agVent_edtVendClave.getText().toString()));
                Vendedor aux = new Vendedor(1, "Antonio", "Calle Vendedor",
                        "Colonia Vendedor", "4779512423", "antonio@email.com", 1, 0);
                if (aux != null) {
                    vendedor = aux;
                    agVenta_edtVendNombre.setText(vendedor.getNombre());
                } else {
                    mostrarToast("Vendedor no encontrado");
                }
            } else {
                mostrarToast("Por favor ingrese el número del vendedor");
            }
        });
        agVent_btnProdBuscar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(agVent_edtProdClave.getText())) {
                String nuProducto = agVent_edtProdClave.getText().toString().trim().toUpperCase();
                for (DetalleVenta detalleVenta : detallesVenta) {
                    if (nuProducto.equals(detalleVenta.getProducto().getNuProducto())) {
                        mostrarToast("Este producto ya ha sido agregado");
                        return;
                    }
                }

                Producto productoAux = controllerProducto.getProducto(nuProducto);
                if (productoAux != null) {
                    DetalleVenta detalleVenta = new DetalleVenta(productoAux, 0, productoAux.getP_venta_menor());
                    detallesVenta = adapter.getLista();
                    detallesVenta.add(detalleVenta);
                    adapter.actualizarList(detallesVenta);
                    agVent_edtProdClave.getText().clear();
                } else {
                    mostrarToast("Producto no encontrado");
                }
            } else {
                mostrarToast("Por favor ingresa la clave del producto");
            }
        });
        agVenta_btnAgregar.setOnClickListener(v -> {
            if (checarEstaCompleto()) {
//                    mostrarToast("Agregar venta");
                detallesVenta = adapter.getLista();
                String f_r = agVenta_spnFR.getSelectedItem().toString();
                String f_r_no = agVenta_edtFactura.getText().toString();
                Venta venta = new Venta(cliente,
                        vendedor,
                        agVenta_edtFecha.getText().toString(),
                        Integer.parseInt(agVenta_edtComision.getText().toString()),
                        f_r,
                        Integer.parseInt(f_r_no),
                        Integer.parseInt(agVenta_txtPares.getText().toString()),
                        Double.parseDouble(agVenta_txtSuma.getText().toString()),
                        Double.parseDouble(agVenta_txtIva.getText().toString()),
                        Double.parseDouble(agVenta_txtTotal.getText().toString()),
                        detallesVenta);

                if (controllerVenta.addVenta(venta)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AgregarVentaActivity.this);
                    dialog.setTitle(R.string.txt_agregado_titulo);
                    String mensaje = String.format(getResources().getString(R.string.txt_agredado_cuerpo), "La", "venta");
                    dialog.setMessage(mensaje);
                    dialog.setPositiveButton(R.string.txt_ok, (d, w) -> {
                        d.dismiss();
                        onBackPressed();
                    });
                    dialog.create().show();
                } else {
                    mostrarToast("Error al agregar la venta");
                }
            } else {
                mostrarToast("Por favor completa todos los campos.");
            }
        });
    }

    private void addEditTextActions() {
        agVenta_edtNumero.setText(String.valueOf(controllerVenta.getNoVenta()));
        agVenta_edtFecha.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(
                    AgregarVentaActivity.this,
                    AgregarVentaActivity.this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });
    }

    private void addSpinnerActions() {
        agVenta_spnFR.setAdapter(ArrayAdapter.createFromResource(AgregarVentaActivity.this, R.array.cv_f_r_opciones,
                R.layout.support_simple_spinner_dropdown_item));
        agVenta_spnFR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.cv_factura))) {
                    int noFactura = controllerVenta.getNoFactura();
                    agVenta_edtFactura.setText(String.valueOf(noFactura));
                    double suma = Double.parseDouble(agVenta_txtSuma.getText().toString());
                    double iva = suma * 0.16;
                    double total = suma + iva;
                    agVenta_txtIva.setText(df.format(iva));
                    agVenta_txtTotal.setText(df.format(total));
                } else {
                    agVenta_edtFactura.getText().clear();
                    agVenta_txtIva.setText(df.format(0.00));
                    agVenta_txtTotal.setText(agVenta_txtSuma.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void iniciarVariables() {
        controllerProducto = new ControllerProducto(AgregarVentaActivity.this);
        controllerExternos = new ControllerExternos(AgregarVentaActivity.this, AgregarVentaActivity.this);
        controllerVenta = new ControllerVenta(AgregarVentaActivity.this);
        detallesVenta = new ArrayList<>();
        df = new DecimalFormat("#0.00");
    }

    private boolean checarEstaCompleto() {
        boolean estaCompleto = true;
        if (TextUtils.isEmpty(agVenta_edtClsClave.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVenta_edtClsNombre.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVenta_edtClsCalle.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVent_edtVendClave.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVenta_edtVendNombre.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVenta_edtFecha.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(agVenta_edtComision.getText()))
            estaCompleto = false;
        if (detallesVenta.size() == 0)
            estaCompleto = false;
        if (agVenta_txtPares.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;
        if (agVenta_txtSuma.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;
        if (agVenta_txtTotal.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;

        return estaCompleto;
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(AgregarVentaActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }

    // Metodos implementados

    @Override
    public void calcularTotal(int position, String numero, int tipo) {
        if (tipo == 1) {
            detallesVenta.get(position).setCantidad_producto(Integer.parseInt(numero));
        } else {
            detallesVenta.get(position).setPrecio_venta(Double.parseDouble(numero));
        }

        int totalPares = 0;
        double suma = 0;
        for (DetalleVenta detalleVenta : detallesVenta) {
            totalPares += detalleVenta.getCantidad_producto();
            suma += detalleVenta.getCantidad_producto() * detalleVenta.getPrecio_venta();
        }
        double iva = 0.0;
        if (agVenta_spnFR.getSelectedItem().toString().equals(getString(R.string.cv_factura))) {
            iva = suma * 0.16;
        }
        double total = suma + iva;
        agVenta_txtPares.setText(String.valueOf(totalPares));
        agVenta_txtSuma.setText(df.format(suma));
        agVenta_txtIva.setText(df.format(iva));
        agVenta_txtTotal.setText(df.format(total));
    }

    @Override
    public void eliminarElemento(int position) {
        detallesVenta = adapter.getLista();
        detallesVenta.remove(position);
        adapter.actualizarList(detallesVenta);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha = dayOfMonth + "/" + month + "/" + year;
        agVenta_edtFecha.setText(fecha);
    }
}
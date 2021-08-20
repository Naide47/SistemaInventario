package edu.idgs902.macrobios.sistemainventario.view.fragments.venta;

import android.app.AlertDialog;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVenta;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.DetalleVentaAdapter;
import edu.idgs902.macrobios.sistemainventario.model.DetalleVenta;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.model.Venta;

public class AccionesVentaActivity extends AppCompatActivity implements DetalleVentaAdapter.AccionesLista, DatePickerDialog.OnDateSetListener {

    private EditText actVent_edtNumero, actVent_edtClsClave, actVent_edtClsNombre,
            actVent_edtClsCalle, actVent_edtVendClave, actVent_edtVendNombre, actVent_edtFecha,
            actVent_edtFactura, actVent_edtComision;
    private TextView actVent_txtPares, actVent_txtSuma, actVent_txtIva, actVent_txtTotal;
    private Button actVent_btnClsBuscar, actVent_btnVendBuscar, actVent_btnModificar, actVent_btnCancelar,
            actVent_btnEliminar, actVent_btnAceptar;
    private Spinner actVent_spnFR;

    private Venta venta;
    private List<DetalleVenta> detallesVenta;
    private ControllerVenta controllerVenta;
    private DetalleVentaAdapter adapter;
    private Externo cliente;
    private Vendedor vendedor;

    DecimalFormat df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones_venta);

        obtenerDatos();
        addListeners();
        colocarInfo();
        activarCampos(false);
        mostrarBotones(View.VISIBLE, View.GONE);
    }

    private void mostrarBotones(int acciones, int edicion) {
        actVent_btnModificar.setVisibility(acciones);
        actVent_btnEliminar.setVisibility(acciones);
        actVent_btnAceptar.setVisibility(edicion);
        actVent_btnCancelar.setVisibility(edicion);
    }

    private void activarCampos(boolean estado) {
        actVent_edtClsClave.setEnabled(estado);
        actVent_btnClsBuscar.setEnabled(estado);
        actVent_edtVendClave.setEnabled(estado);
        actVent_btnVendBuscar.setEnabled(estado);
        actVent_edtFecha.setEnabled(estado);
        actVent_spnFR.setEnabled(estado);
        actVent_edtComision.setEnabled(estado);
        for (int i = 0; i < detallesVenta.size(); i++) {
            detallesVenta.get(i).setEstado(estado);
        }
        adapter.actualizarList(detallesVenta);
    }

    private void colocarInfo() {
        actVent_edtNumero.setText(String.valueOf(venta.getNoVenta()));
        actVent_edtClsClave.setText(String.valueOf(venta.getExterno_cliente().getNoExterno()));
        Log.e("cliente", venta.getExterno_cliente().toString());
        actVent_edtClsNombre.setText(venta.getExterno_cliente().getNombre());
        actVent_edtClsCalle.setText(venta.getExterno_cliente().getCalle());
        actVent_edtVendClave.setText(String.valueOf(venta.getVendedor().getNoVendedor()));
        actVent_edtVendNombre.setText(venta.getVendedor().getNombre());
        actVent_edtFecha.setText(venta.getFecha());
        actVent_spnFR.setSelection((venta.getF_r().equals(getString(R.string.cv_factura)) ? 0 : 1));
        actVent_edtFactura.setText(String.valueOf(venta.getF_r_no()));
        actVent_edtComision.setText(String.valueOf(venta.getComision()));
        actVent_txtPares.setText(String.valueOf(venta.getTotal_pares()));
        actVent_txtSuma.setText(String.valueOf(venta.getSuma()));
        actVent_txtIva.setText(String.valueOf(venta.getIva()));
        actVent_txtTotal.setText(String.valueOf(venta.getTotal_venta()));
        detallesVenta = venta.getDetallesVenta();
        adapter.actualizarList(detallesVenta);
    }

    private void addListeners() {
        //RecyclerView
        RecyclerView venta_detalle = findViewById(R.id.venta_detalle);
        venta_detalle.setHasFixedSize(true);
        venta_detalle.setLayoutManager(new LinearLayoutManager(AccionesVentaActivity.this));
        adapter = new DetalleVentaAdapter(AccionesVentaActivity.this, detallesVenta, AccionesVentaActivity.this);
        venta_detalle.setAdapter(adapter);
//        EditText
        actVent_edtNumero = findViewById(R.id.actVent_edtNumero);
        actVent_edtClsClave = findViewById(R.id.actVent_edtClsClave);
        actVent_edtClsNombre = findViewById(R.id.actVent_edtClsNombre);
        actVent_edtClsCalle = findViewById(R.id.actVent_edtClsCalle);
        actVent_edtVendClave = findViewById(R.id.actVent_edtVendClave);
        actVent_edtVendNombre = findViewById(R.id.actVent_edtVendNombre);
        actVent_edtFecha = findViewById(R.id.actVent_edtFecha);
        actVent_edtFactura = findViewById(R.id.actVent_edtFactura);
        actVent_edtComision = findViewById(R.id.actVent_edtComision);
//        TextView
        actVent_txtPares = findViewById(R.id.actVent_txtPares);
        actVent_txtSuma = findViewById(R.id.actVent_txtSuma);
        actVent_txtIva = findViewById(R.id.actVent_txtIva);
        actVent_txtTotal = findViewById(R.id.actVent_txtTotal);
//        Button
        actVent_btnClsBuscar = findViewById(R.id.actVent_btnClsBuscar);
        actVent_btnVendBuscar = findViewById(R.id.actVent_btnVendBuscar);
        actVent_btnModificar = findViewById(R.id.actVent_btnModificar);
        actVent_btnEliminar = findViewById(R.id.actVent_btnEliminar);
        actVent_btnAceptar = findViewById(R.id.actVent_btnAceptar);
        actVent_btnCancelar = findViewById(R.id.actVent_btnCancelar);
//        Spinner
        actVent_spnFR = findViewById(R.id.actVent_spnFR);
        accionesSpinner();
        accionesEditText();
        accionesButton();
    }

    private void accionesButton() {
        actVent_btnClsBuscar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(actVent_edtClsClave.getText())) {
                int noCliente = Integer.parseInt(actVent_edtClsClave.getText().toString());
                if (noCliente == venta.getExterno_cliente().getNoExterno()) {
                    mostrarToast("Mismo cliente");
                    return;
                }
                ControllerExternos controllerExternos = new ControllerExternos(AccionesVentaActivity.this, AccionesVentaActivity.this);
                Externo aux = controllerExternos.getExternoCompleto(noCliente);
                if (aux != null && aux.getTipo() == 1) {
                    cliente = aux;
                    actVent_edtClsNombre.setText(cliente.getNombre());
                    actVent_edtClsCalle.setText(cliente.getCalle());
                } else {
                    mostrarToast("Cliente no encontrado");
                }
            } else {
                mostrarToast("Por favor ingrese el número del cliente");
            }
        });
        actVent_btnVendBuscar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(actVent_edtVendClave.getText())) {
//                    Vendedor aux = controllerVendedor.getVendedor(Integer.parseInt(agVent_edtVendClave.getText().toString()));
//                int noVendedor = Integer.parseInt(actVent_edtVendClave.getText().toString());
//                if (noVendedor == vendedor.getNoVendedor()) {
//                    mostrarToast("Mismo vendedor");
//                    return;
//                }
//                    Vendedor aux = controllerVendedor.getVendedor(noVendedor);
//                if (aux != null) {
//                    vendedor = aux;
//                    actVent_edtVendNombre.setText(vendedor.getNombre());
//                } else {
//                    mostrarToast("Vendedor no encontrado");
//                }
            } else {
                mostrarToast("Por favor ingrese el número del vendedor");
            }
        });
        actVent_btnModificar.setOnClickListener(v -> activarEdicion(false, true, false));
        actVent_btnCancelar.setOnClickListener(v -> activarEdicion(true, false, true));
        actVent_btnAceptar.setOnClickListener(v -> {
            if (checarEstaCompleto()) {

                int pares = Integer.parseInt(actVent_txtPares.getText().toString());
                String fecha = actVent_edtFecha.getText().toString();
                double suma = Double.parseDouble(actVent_txtSuma.getText().toString()),
                        iva = Double.parseDouble(actVent_txtIva.getText().toString()),
                        total = Double.parseDouble(actVent_txtTotal.getText().toString());

                if (venta.getExterno_cliente().getNoExterno() == cliente.getNoExterno() &&
                        venta.getVendedor().getNoVendedor() == vendedor.getNoVendedor() &&
                        venta.getFecha().equals(fecha) &&
                        venta.getTotal_pares() == pares &&
                        venta.getSuma() == suma &&
                        venta.getIva() == iva &&
                        venta.getTotal_venta() == total) {
                    mostrarToast("No se han registrado cambios");
                    return;
                }

                detallesVenta = adapter.getLista();
                venta.setExterno_cliente(cliente);
                venta.setVendedor(vendedor);
                venta.setFecha(fecha);
                venta.setTotal_pares(pares);
                venta.setSuma(suma);
                venta.setIva(iva);
                venta.setTotal_venta(total);
                if (controllerVenta.updateVenta(venta)) {
                    AlertDialog.Builder alertaModificacion = new AlertDialog.Builder(AccionesVentaActivity.this);
                    alertaModificacion.setTitle(R.string.txt_actualizado_titulo);
                    String mensaje = String.format(getString(R.string.txt_actualizado_cuerpo), "La", "venta", "a");
                    alertaModificacion.setMessage(mensaje);
                    alertaModificacion.setPositiveButton(R.string.txt_ok, (d, w) -> {
                        d.dismiss();
                        onBackPressed();
                    });
                    alertaModificacion.create().show();
                } else {
                    mostrarToast("FALLO AL ACTUALIZAR");
                }
            } else {
                mostrarToast("Por favor, completa los campos");
            }
        });
        actVent_btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder alertaEliminar = new AlertDialog.Builder(AccionesVentaActivity.this);
            String mensaje = String.format(getResources().getString(R.string.txt_eliminar_cuerpo), "a", "venta");
            String exito = String.format(getResources().getString(R.string.txt_eliminar_exito), "La", "venta", "a");

            alertaEliminar.setTitle(R.string.txt_eliminar_titulo);
            alertaEliminar.setMessage(mensaje);
            alertaEliminar.setPositiveButton(getString(R.string.btn_aceptar), (d, w) -> {
                if (controllerVenta.deleteVenta(venta.getNoVenta())) {
                    Toast.makeText(AccionesVentaActivity.this, exito, Toast.LENGTH_LONG).show();
                    d.dismiss();
                    onBackPressed();
                } else {
                    mostrarToast("NO SE ELIMINO");
                }
            });
            alertaEliminar.setNegativeButton(getString(R.string.btn_cancelar), (d, w) -> d.dismiss());
            alertaEliminar.create().show();
        });
        findViewById(R.id.actVent_btnVolver).setOnClickListener(v -> onBackPressed());

    }

    private void accionesEditText() {
        actVent_edtFecha.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(
                    AccionesVentaActivity.this,
                    AccionesVentaActivity.this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });
    }

    private void accionesSpinner() {
        actVent_spnFR.setAdapter(ArrayAdapter.createFromResource(AccionesVentaActivity.this,
                R.array.cv_f_r_opciones, R.layout.support_simple_spinner_dropdown_item));
        actVent_spnFR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.cv_factura))) {
                    double suma = Double.parseDouble(actVent_txtSuma.getText().toString()),
                            iva = suma * 0.16, total = suma + iva;
                    actVent_txtIva.setText(df.format(iva));
                    actVent_txtTotal.setText(df.format(total));
                } else {
                    actVent_txtIva.setText(df.format(0.0));
                    actVent_txtTotal.setText(actVent_txtSuma.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void obtenerDatos() {
        venta = getIntent().getParcelableExtra("venta");
        cliente = venta.getExterno_cliente();
//        venta.setVendedor(new Vendedor(1, "Antonio", "Calle Vendedor",
//                "Colonia Vendedor", "4779512423", "antonio@email.com", 1, 0));
        vendedor = venta.getVendedor();
        controllerVenta = new ControllerVenta(AccionesVentaActivity.this);
        detallesVenta = venta.getDetallesVenta();
        df = new DecimalFormat("#0.00");
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

    private boolean checarEstaCompleto() {
        boolean estaCompleto = true;
        if (TextUtils.isEmpty(actVent_edtClsClave.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtClsNombre.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtClsCalle.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtVendClave.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtVendNombre.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtFecha.getText()))
            estaCompleto = false;
        if (TextUtils.isEmpty(actVent_edtComision.getText()))
            estaCompleto = false;
        if (detallesVenta.size() == 0)
            estaCompleto = false;
        if (actVent_txtPares.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;
        if (actVent_txtSuma.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;
        if (actVent_txtTotal.getText().toString().equals(String.valueOf(0)))
            estaCompleto = false;
        return estaCompleto;
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(AccionesVentaActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    //Metodos sobreescritos

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha = dayOfMonth + "/" + month + "/" + year;
        actVent_edtFecha.setText(fecha);
    }

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
            suma += (detalleVenta.getCantidad_producto() * detalleVenta.getPrecio_venta());
        }
        double iva = 0.0;
        if (actVent_spnFR.getSelectedItem().toString().equals(getString(R.string.cv_factura))) {
            iva = suma * 0.16;
        }
        double total = suma + iva;

        actVent_txtPares.setText(String.valueOf(totalPares));
        actVent_txtSuma.setText(df.format(suma));
        actVent_txtIva.setText(df.format(iva));
        actVent_txtTotal.setText(df.format(total));
    }

    @Override
    public void eliminarElemento(int position) {

    }
}
package edu.idgs902.macrobios.sistemainventario.view.proveedor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.view.HomeActivity;

public class AgregarProveedor extends AppCompatActivity {

    EditText NoProveedor,NombreProveedor, CalleProveedor, ColoniaProveedor, CiudadProveedor, RfcProveedor, TelefonoProveedor, EmailProveedor, SaldoProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_proveedor);

        NoProveedor = findViewById(R.id.NoProveedor);
        NombreProveedor = findViewById(R.id.NombreProveedor);
        CalleProveedor = findViewById(R.id.CalleProveedor);
        ColoniaProveedor = findViewById(R.id.ColoniaProveedor);
        CiudadProveedor = findViewById(R.id.CiudadProveedor);
        RfcProveedor = findViewById(R.id.RfcProveedor);
        TelefonoProveedor = findViewById(R.id.TelefonoProveedor);
        EmailProveedor = findViewById(R.id.EmailProveedor);
        SaldoProveedor = findViewById(R.id.SaldoProveedor);

        findViewById(R.id.btnAtras).setOnClickListener(view -> {
            onBackPressed();
        });

        findViewById(R.id.btnAltaProveedor).setOnClickListener(view -> {
            agregar();
        });

    }

    public void agregar(){
        try {
            if(!NombreProveedor.getText().toString().isEmpty() &&
                    !CalleProveedor.getText().toString().isEmpty() &&
                    !ColoniaProveedor.getText().toString().isEmpty() &&
                    !CiudadProveedor.getText().toString().isEmpty() &&
                    !RfcProveedor.getText().toString().isEmpty() &&
                    !TelefonoProveedor.getText().toString().isEmpty() &&
                    !EmailProveedor.getText().toString().isEmpty() &&
                    !SaldoProveedor.getText().toString().isEmpty()){

                Persona persona = new Persona(NombreProveedor.getText().toString(),
                        CalleProveedor.getText().toString(),
                        ColoniaProveedor.getText().toString(),
                        TelefonoProveedor.getText().toString(),
                        EmailProveedor.getText().toString());

                ControllerPersona controllerPersona = new ControllerPersona(this, this);

                long result  = controllerPersona.addPersona(persona);

                Externo externo = new Externo(2,
                        RfcProveedor.getText().toString(),
                        CiudadProveedor.getText().toString(),
                        Double.parseDouble(SaldoProveedor.getText().toString()),
                        (int) result);

                ControllerExternos controllerExternos = new ControllerExternos(this, this);

                controllerExternos.addExterno(externo);

            }
            alerta("Confirmacion", "El proveedor fue agregado con exito");
            limpiar();
        }catch (Error e){
            alerta("Precaucion", "Debe llenar todo los campos");
            Log.e("ProveedorFragment", e.toString());
        }

    }

    private void alerta(String titulo, String cuerpo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(cuerpo);
        builder.setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void limpiar(){
        NombreProveedor.setText("");
        CalleProveedor.setText("");
        ColoniaProveedor.setText("");
        CiudadProveedor.setText("");
        RfcProveedor.setText("");
        TelefonoProveedor.setText("");
        EmailProveedor.setText("");
        SaldoProveedor.setText("");
    }

}
package edu.idgs902.macrobios.sistemainventario.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.view.tabs.ClienteFragment;

public class AgregarCliente extends AppCompatActivity {

    EditText NoCliente,NombreCliente, CalleCliente, ColoniaCliente, CiudadCliente, RfcCliente, TelefonoCliente, EmailCliente, SaldoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        NoCliente = findViewById(R.id.NoCliente);
        NombreCliente = findViewById(R.id.NombreCliente);
        CalleCliente = findViewById(R.id.CalleCliente);
        ColoniaCliente = findViewById(R.id.ColoniaCliente);
        CiudadCliente = findViewById(R.id.CiudadCliente);
        RfcCliente = findViewById(R.id.RfcCliente);
        TelefonoCliente = findViewById(R.id.TelefonoCliente);
        EmailCliente = findViewById(R.id.EmailCliente);
        SaldoCliente = findViewById(R.id.SaldoCliente);

        findViewById(R.id.btnAtras).setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnAltaCliente).setOnClickListener(view -> {
            agregar();
        });

    }

    public void agregar(){
        try {
            if(!NombreCliente.getText().toString().isEmpty() &&
                    !CalleCliente.getText().toString().isEmpty() &&
                    !ColoniaCliente.getText().toString().isEmpty() &&
                    !CiudadCliente.getText().toString().isEmpty() &&
                    !RfcCliente.getText().toString().isEmpty() &&
                    !TelefonoCliente.getText().toString().isEmpty() &&
                    !EmailCliente.getText().toString().isEmpty() &&
                    !SaldoCliente.getText().toString().isEmpty()){

                Persona persona = new Persona(NombreCliente.getText().toString(),
                        CalleCliente.getText().toString(),
                        ColoniaCliente.getText().toString(),
                        TelefonoCliente.getText().toString(),
                        EmailCliente.getText().toString());

                ControllerPersona controllerPersona = new ControllerPersona(this, this);

                long result  = controllerPersona.addPersona(persona);

                Externo externo = new Externo(1,
                        RfcCliente.getText().toString(),
                        CiudadCliente.getText().toString(),
                        Double.parseDouble(SaldoCliente.getText().toString()),
                        (int) result);

                ControllerExternos controllerExternos = new ControllerExternos(this, this);

                controllerExternos.addExterno(externo);

            }
            alerta("Confirmacion", "El cliente fue agregado con exito");
            limpiar();
        }catch (Error e){
            alerta("Precaucion", "Debe llenar todo los campos");
            Log.e("ClienteFragment", e.toString());
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
        NombreCliente.setText("");
        CalleCliente.setText("");
        ColoniaCliente.setText("");
        CiudadCliente.setText("");
        RfcCliente.setText("");
        TelefonoCliente.setText("");
        EmailCliente.setText("");
        SaldoCliente.setText("");
    }

}
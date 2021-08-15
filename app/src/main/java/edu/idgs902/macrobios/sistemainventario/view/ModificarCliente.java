package edu.idgs902.macrobios.sistemainventario.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.PeriodicSync;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerExternos;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.model.Externo;
import edu.idgs902.macrobios.sistemainventario.model.Persona;

public class ModificarCliente extends AppCompatActivity {

    EditText NoCliente,NombreCliente, CalleCliente, ColoniaCliente, CiudadCliente, RfcCliente, TelefonoCliente, EmailCliente, SaldoCliente;

    int nopersona = 0;
    int noexterno = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);

        NombreCliente = findViewById(R.id.NombreClienteM);
        CalleCliente = findViewById(R.id.CalleClienteM);
        ColoniaCliente = findViewById(R.id.ColoniaClienteM);
        CiudadCliente = findViewById(R.id.CiudadClienteM);
        RfcCliente = findViewById(R.id.RfcClienteM);
        TelefonoCliente = findViewById(R.id.TelefonoClienteM);
        EmailCliente = findViewById(R.id.EmailClienteM);
        SaldoCliente = findViewById(R.id.SaldoClienteM);

        Intent intent = getIntent();
        nopersona = intent.getIntExtra("noPersona", 0);
        noexterno = intent.getIntExtra("noExterno", 0);

        //Log.d("noPersona", "onCreate: " + nopersona);
        //Log.d("noExterno", "onCreate: " + noexterno);

        init(nopersona, noexterno);

        findViewById(R.id.btnAtras).setOnClickListener(view -> {
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
        });

        findViewById(R.id.btnActivar).setOnClickListener(view ->{
            activar();
        });

        findViewById(R.id.btnModificacionClienteM).setOnClickListener(view -> {
            modificar(nopersona, noexterno);
        });

        findViewById(R.id.btnBajaClienteM).setOnClickListener(view -> {
            ControllerPersona controllerPersona = new ControllerPersona(this, this);
            Persona persona = controllerPersona.getPersona(nopersona);
            alerta("Eliminacion", "¿Esta seguro que desae eliminar a "+persona.getNombre()+"?" );
        });

    }

    public void init(int nopersona, int noexterno) {
        ControllerExternos controllerExternos = new ControllerExternos(this, this);
        Externo externo = controllerExternos.getExterno(noexterno);

        ControllerPersona controllerPersona = new ControllerPersona(this, this);
        Persona persona = controllerPersona.getPersona(nopersona);

        NombreCliente.setText(persona.getNombre());
        CalleCliente.setText(persona.getCalle());
        ColoniaCliente.setText(persona.getColonia());
        CiudadCliente.setText(externo.getCiudad());
        RfcCliente.setText(externo.getRfc());
        TelefonoCliente.setText(persona.getTelefono());
        EmailCliente.setText(persona.getEmail());
        SaldoCliente.setText(String.valueOf(externo.getSaldo()));

        //Log.d("externo", "init: "+externo.getSaldo());

    }

    public void modificar(int nopersona, int noexterno){
        try {
            if(!NombreCliente.getText().toString().isEmpty() &&
                    !CalleCliente.getText().toString().isEmpty() &&
                    !ColoniaCliente.getText().toString().isEmpty() &&
                    !CiudadCliente.getText().toString().isEmpty() &&
                    !RfcCliente.getText().toString().isEmpty() &&
                    !TelefonoCliente.getText().toString().isEmpty() &&
                    !EmailCliente.getText().toString().isEmpty() &&
                    !SaldoCliente.getText().toString().isEmpty()){

                Persona persona = new Persona(nopersona,
                        NombreCliente.getText().toString(),
                        CalleCliente.getText().toString(),
                        ColoniaCliente.getText().toString(),
                        TelefonoCliente.getText().toString(),
                        EmailCliente.getText().toString());

                ControllerPersona controllerPersona = new ControllerPersona(this, this);

                controllerPersona.updatePersona(persona);

                Externo externo = new Externo(noexterno,
                        RfcCliente.getText().toString(),
                        CiudadCliente.getText().toString(),
                        Double.parseDouble(SaldoCliente.getText().toString()));

                ControllerExternos controllerExternos = new ControllerExternos(this, this);

                controllerExternos.updateExterno(externo);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmacion");
                builder.setMessage("Cliente modificado");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        desactivar();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }catch (Error e){
            Log.e("ClienteFragment", e.toString());
        }

    }


    public void eliminar(int noexterno, int nopersona){
        try {
            ControllerPersona controllerPersona = new ControllerPersona(this, this);
            controllerPersona.deletePersona(nopersona);

            ControllerExternos controllerExternos = new ControllerExternos(this, this);
            controllerExternos.deleteExterno(noexterno);

        }catch (Error e){
            Log.e("Eliminacion", "eliminar: " + e.toString());
        }
    }

    private void activar(){
        NombreCliente.setEnabled(true);
        CalleCliente.setEnabled(true);
        ColoniaCliente.setEnabled(true);
        CiudadCliente.setEnabled(true);
        RfcCliente.setEnabled(true);
        TelefonoCliente.setEnabled(true);
        EmailCliente.setEnabled(true);
        SaldoCliente.setEnabled(true);
    }

    private void desactivar(){
        NombreCliente.setEnabled(false);
        CalleCliente.setEnabled(false);
        ColoniaCliente.setEnabled(false);
        CiudadCliente.setEnabled(false);
        RfcCliente.setEnabled(false);
        TelefonoCliente.setEnabled(false);
        EmailCliente.setEnabled(false);
        SaldoCliente.setEnabled(false);
    }

    private void alerta(String titulo, String cuerpo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(cuerpo);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                volver();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void volver(){
        eliminar(noexterno, nopersona);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
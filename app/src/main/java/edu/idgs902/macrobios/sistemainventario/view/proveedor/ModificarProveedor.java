package edu.idgs902.macrobios.sistemainventario.view.proveedor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class ModificarProveedor extends AppCompatActivity {

    EditText NoProveedor,NombreProveedor, CalleProveedor, ColoniaProveedor, CiudadProveedor, RfcProveedor, TelefonoProveedor, EmailProveedor, SaldoProveedor;

    int nopersona = 0;
    int noexterno = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_proveedor);

        NoProveedor = findViewById(R.id.NoProveedor);
        NombreProveedor = findViewById(R.id.NombreProveedor);
        CalleProveedor = findViewById(R.id.CalleProveedor);
        ColoniaProveedor = findViewById(R.id.ColoniaProveedor);
        CiudadProveedor = findViewById(R.id.CiudadProveedor);
        RfcProveedor = findViewById(R.id.RfcProveedor);
        TelefonoProveedor = findViewById(R.id.TelefonoProveedor);
        EmailProveedor = findViewById(R.id.EmailProveedor);
        SaldoProveedor = findViewById(R.id.SaldoProveedor);

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

        findViewById(R.id.btnModificacionProveedor).setOnClickListener(view -> {
            modificar(nopersona, noexterno);
        });

        findViewById(R.id.btnBajaProveedor).setOnClickListener(view -> {
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

        NombreProveedor.setText(persona.getNombre());
        CalleProveedor.setText(persona.getCalle());
        ColoniaProveedor.setText(persona.getColonia());
        CiudadProveedor.setText(externo.getCiudad());
        RfcProveedor.setText(externo.getRfc());
        TelefonoProveedor.setText(persona.getTelefono());
        EmailProveedor.setText(persona.getEmail());
        SaldoProveedor.setText(String.valueOf(externo.getSaldo()));

        //Log.d("externo", "init: "+externo.getSaldo());

    }

    public void modificar(int nopersona, int noexterno){
        try {
            if(!NombreProveedor.getText().toString().isEmpty() &&
                    !CalleProveedor.getText().toString().isEmpty() &&
                    !ColoniaProveedor.getText().toString().isEmpty() &&
                    !CiudadProveedor.getText().toString().isEmpty() &&
                    !RfcProveedor.getText().toString().isEmpty() &&
                    !TelefonoProveedor.getText().toString().isEmpty() &&
                    !EmailProveedor.getText().toString().isEmpty() &&
                    !SaldoProveedor.getText().toString().isEmpty()){

                Persona persona = new Persona(nopersona,
                        NombreProveedor.getText().toString(),
                        CalleProveedor.getText().toString(),
                        ColoniaProveedor.getText().toString(),
                        TelefonoProveedor.getText().toString(),
                        EmailProveedor.getText().toString());

                ControllerPersona controllerPersona = new ControllerPersona(this, this);

                controllerPersona.updatePersona(persona);

                Externo externo = new Externo(noexterno,
                        RfcProveedor.getText().toString(),
                        CiudadProveedor.getText().toString(),
                        Double.parseDouble(SaldoProveedor.getText().toString()));

                ControllerExternos controllerExternos = new ControllerExternos(this, this);

                controllerExternos.updateExterno(externo);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmacion");
                builder.setMessage("Proveedor modificado");

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
            Log.e("ProveedorFragment", e.toString());
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
        NombreProveedor.setEnabled(true);
        CalleProveedor.setEnabled(true);
        ColoniaProveedor.setEnabled(true);
        CiudadProveedor.setEnabled(true);
        RfcProveedor.setEnabled(true);
        TelefonoProveedor.setEnabled(true);
        EmailProveedor.setEnabled(true);
        SaldoProveedor.setEnabled(true);
    }

    private void desactivar(){
        NombreProveedor.setEnabled(false);
        CalleProveedor.setEnabled(false);
        ColoniaProveedor.setEnabled(false);
        CiudadProveedor.setEnabled(false);
        RfcProveedor.setEnabled(false);
        TelefonoProveedor.setEnabled(false);
        EmailProveedor.setEnabled(false);
        SaldoProveedor.setEnabled(false);
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
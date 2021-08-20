package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerPersona;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVendedor;
import edu.idgs902.macrobios.sistemainventario.model.Persona;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.view.HomeActivity;

public class ModificarVendedor extends AppCompatActivity {

    EditText NombreVendedor, CalleVendedor, ColoniaVendedor, TelefonoVendedor, EmailVendedor, ComisionVendedor;

    int nopersona = 0;
    int novendedor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_vendedor);

        NombreVendedor = findViewById(R.id.NombreVendedor);
        CalleVendedor = findViewById(R.id.CalleVendedor);
        ColoniaVendedor = findViewById(R.id.ColoniaVendedor);
        TelefonoVendedor = findViewById(R.id.TelefonoVendedor);
        EmailVendedor = findViewById(R.id.EmailVendedor);
        ComisionVendedor = findViewById(R.id.ComisionVendedor);

        Intent intent = getIntent();
        nopersona = intent.getIntExtra("noPersona", 0);
        novendedor = intent.getIntExtra("noVendedor", 0);

        init(novendedor);

        findViewById(R.id.btnAtras).setOnClickListener(view -> {
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
        });

        findViewById(R.id.btnActivar).setOnClickListener(view ->{
            activar();
        });

        findViewById(R.id.btnModificacionVendedor).setOnClickListener(view -> {
            modificar(novendedor, nopersona);
        });

        findViewById(R.id.btnBajaVendedor).setOnClickListener(view -> {
            ControllerPersona controllerPersona = new ControllerPersona(this, this);
            Persona persona = controllerPersona.getPersona(nopersona);
            alerta("Eliminacion", "¿Esta seguro que desae eliminar a "+persona.getNombre()+"?" );
        });

    }

    public void init (int novendedor) {
        ControllerVendedor controllerVendedor = new ControllerVendedor(this);
        Vendedor vendedor = controllerVendedor.getVendedor(novendedor);

        ControllerPersona controllerPersona = new ControllerPersona(this, this);
        Persona persona = controllerPersona.getPersona(nopersona);

        NombreVendedor.setText(persona.getNombre());
        CalleVendedor.setText(persona.getCalle());
        ColoniaVendedor.setText(persona.getColonia());
        TelefonoVendedor.setText(persona.getTelefono());
        EmailVendedor.setText(persona.getEmail());
        ComisionVendedor.setText(String.valueOf(vendedor.getComisiones()));
    }

    public void modificar (int novendedor, int nopersona){
        try {

            if(!NombreVendedor.getText().toString().isEmpty() &&
                    !CalleVendedor.getText().toString().isEmpty() &&
                    !ColoniaVendedor.getText().toString().isEmpty() &&
                    !TelefonoVendedor.getText().toString().isEmpty() &&
                    !EmailVendedor.getText().toString().isEmpty() &&
                    !ComisionVendedor.getText().toString().isEmpty()) {

                Vendedor vendedor = new Vendedor(nopersona, NombreVendedor.getText().toString(),
                        CalleVendedor.getText().toString(),
                        ColoniaVendedor.getText().toString(),
                        TelefonoVendedor.getText().toString(),
                        EmailVendedor.getText().toString(),
                        novendedor,
                        Double.parseDouble(ComisionVendedor.getText().toString()));

                ControllerVendedor controllerVendedor = new ControllerVendedor( this);

                controllerVendedor.updateVendedor(vendedor);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmacion");
                builder.setMessage("Vendedor modificado");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        desactivar();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }catch (Error e) {
            Log.e("VendedorFragment", e.toString());
        }
    }

    public void eliminar(int novendedor, int nopersona){
        try {
            ControllerPersona controllerPersona = new ControllerPersona(this, this);
            controllerPersona.deletePersona(nopersona);

            ControllerVendedor controllerVendedor = new ControllerVendedor(this);
            controllerVendedor.deleteVendedor(novendedor);

        }catch (Error e){
            Log.e("Eliminacion", "eliminar: " + e.toString());
        }
    }

    private void activar(){
        NombreVendedor.setEnabled(true);
        CalleVendedor.setEnabled(true);
        ColoniaVendedor.setEnabled(true);
        TelefonoVendedor.setEnabled(true);
        EmailVendedor.setEnabled(true);
        ComisionVendedor.setEnabled(true);
    }

    private void desactivar(){
        NombreVendedor.setEnabled(false);
        CalleVendedor.setEnabled(false);
        ColoniaVendedor.setEnabled(false);
        TelefonoVendedor.setEnabled(false);
        EmailVendedor.setEnabled(false);
        ComisionVendedor.setEnabled(false);
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
        eliminar(novendedor, nopersona);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
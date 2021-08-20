package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.ControllerVendedor;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;
import edu.idgs902.macrobios.sistemainventario.view.HomeActivity;

public class AgregarVendedor extends AppCompatActivity {

    EditText NombreVendedor, CalleVendedor, ColoniaVendedor, TelefonoVendedor, EmailVendedor, ComisionVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vendedor);

        NombreVendedor = findViewById(R.id.NombreVendedor);
        CalleVendedor = findViewById(R.id.CalleVendedor);
        ColoniaVendedor = findViewById(R.id.ColoniaVendedor);
        TelefonoVendedor = findViewById(R.id.TelefonoVendedor);
        EmailVendedor = findViewById(R.id.EmailVendedor);
        ComisionVendedor = findViewById(R.id.ComisionVendedor);

        findViewById(R.id.btnAtras).setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            limpiar();
        });

        findViewById(R.id.btnAltaVendedor).setOnClickListener(view -> {
            agregar();
            limpiar();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

    }

    public void agregar(){
        try {
            if(!NombreVendedor.getText().toString().isEmpty() &&
                    !CalleVendedor.getText().toString().isEmpty() &&
                    !ColoniaVendedor.getText().toString().isEmpty() &&
                    !TelefonoVendedor.getText().toString().isEmpty() &&
                    !EmailVendedor.getText().toString().isEmpty() &&
                    !ComisionVendedor.getText().toString().isEmpty()) {

                Vendedor vendedor = new Vendedor(NombreVendedor.getText().toString(),
                        CalleVendedor.getText().toString(),
                        ColoniaVendedor.getText().toString(),
                        TelefonoVendedor.getText().toString(),
                        EmailVendedor.getText().toString(),
                        Double.parseDouble(ComisionVendedor.getText().toString()));

                ControllerVendedor controllerVendedor = new ControllerVendedor( this);
                controllerVendedor.addVendedor(vendedor);

                //alerta("Confirmacion", "El vendedor fue agregado con exito " + controllerVendedor.addVendedor(vendedor));

            }
            alerta("Confirmacion", "El vendedor fue agregado con exito");
            limpiar();
        }catch (Error e){
            alerta("Precaucion", "Debe llenar todo los campos");
            Log.e("VendedorFragment", e.toString());
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
        NombreVendedor.setText("");
        CalleVendedor.setText("");
        ColoniaVendedor.setText("");
        TelefonoVendedor.setText("");
        EmailVendedor.setText("");
        ComisionVendedor.setText("");
    }

}
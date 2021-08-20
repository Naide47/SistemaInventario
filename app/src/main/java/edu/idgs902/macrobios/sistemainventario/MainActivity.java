package edu.idgs902.macrobios.sistemainventario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import edu.idgs902.macrobios.sistemainventario.view.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtUsuario = findViewById(R.id.edtUsuario),
                edtContra = findViewById(R.id.edtContra);

        findViewById(R.id.button).setOnClickListener(view -> {

            if (edtUsuario.getText().toString().equals("admin@email.com") &&
                    edtContra.getText().toString().equals("admin123")) {

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
package com.example.veterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class mascotasRegistrar extends AppCompatActivity {

    EditText etTipo, etRaza, etNombre, etPeso, etColor;

    Button btnRegistrarMascota, btnBuscarMascota, btnRegresar, btnListarMacotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas_registrar);

        loadUI();

        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarCampos();
            }
        });

        btnBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscarMascota.class));
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnListarMacotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), listarMascota.class));
            }
        });
    }

    private void comprobarCampos(){
        String tipo, nombre, raza, peso, color;

        tipo = etTipo.getText().toString();
        raza = etRaza.getText().toString();
        nombre = etNombre.getText().toString();
        peso = etPeso.getText().toString();
        color = etColor.getText().toString();

        if (tipo.isEmpty() || raza.isEmpty() || nombre.isEmpty() || peso.isEmpty() || color.isEmpty()){
            notificar("Complete el formulario");
            etTipo.requestFocus();
        }else{
            // 2. Preguntamos si está seguro
            preguntar();
        }
    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("AlonsoPet");
        dialogo.setMessage("¿Está seguro de registrar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();
            }
        });
        dialogo.show();
    }

    private void registrarMascota(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bdveterinaria", null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();
        parametros.put("tipo", etTipo.getText().toString());
        parametros.put("raza", etRaza.getText().toString());
        parametros.put("nombre", etNombre.getText().toString());
        parametros.put("peso", etPeso.getText().toString());
        parametros.put("color", etColor.getText().toString());

        long idobtenido = db.insert("mascotas","idmascota", parametros);

        notificar("Datos guardados correctamente - " + String.valueOf(idobtenido));
        reiniciarUI();
        etTipo.requestFocus();
    }

    private void reiniciarUI(){
        etTipo.setText(null);
        etRaza.setText(null);
        etNombre.setText(null);
        etPeso.setText(null);
        etColor.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void loadUI(){
        etTipo                  = findViewById(R.id.etTipo);
        etRaza                  = findViewById(R.id.etRaza);
        etNombre                = findViewById(R.id.etNombre);
        etPeso                  = findViewById(R.id.etPeso);
        etColor                 = findViewById(R.id.etColor);

        btnRegistrarMascota     = findViewById(R.id.btnRegistrarMascota);
        btnBuscarMascota        = findViewById(R.id.btnBuscarMascota);
        btnRegresar             = findViewById(R.id.btnRegresar);
        btnListarMacotas        = findViewById(R.id.btnListarMascota);
    }

}
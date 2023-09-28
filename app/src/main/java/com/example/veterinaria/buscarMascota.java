package com.example.veterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscarMascota extends AppCompatActivity {

    ConexionSQLiteHelper conexion;

    EditText etIdBuscador1, etTipo, etRaza, etNombre, etPeso, etColor;

    Button btnBuscar, btnActualizar, btnEliminar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota);

        loadUI();

        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdveterinaria",null,1);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarMascota();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarActualizar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarEliminar();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void preguntarEliminar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("PrestaPe");
        dialogo.setMessage("¿Está seguro de eliminar?");
        dialogo.setCancelable(false);

        // SET: Asignar = Entregar
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarMascota();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // POR AHORA VACIO
            }
        });
        dialogo.show();
    }

    private void eliminarMascota(){
        // PASO 1: Permisos
        SQLiteDatabase db = conexion.getWritableDatabase();
        // PASO 2: Campos de criterio (WHERE campo = 1)
        String [] campoCriterio = {etIdBuscador1.getText().toString()};
        // PASO 3: Eliminación
        db.delete("mascotas","idmascota=?", campoCriterio);
        db.close();

        // ACTUALIZA INTERFAZ
        limpiarValores();
        notificar("Eliminado correctamente");
        etIdBuscador1.requestFocus();
    }
    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void preguntarActualizar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("AlonsoPet");
        dialogo.setMessage("¿Esta seguro de actualizar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editar();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();
    }

    private void editar(){
        SQLiteDatabase db = conexion.getWritableDatabase();

        // Paso 2 : Campo de criterio
        String[] campoCriterio = {etIdBuscador1.getText().toString() };

        // PASO 3 : Contenedor de datos a enviar
        ContentValues parametros = new ContentValues();

        // PASO 4 : Asignar al contenido los valores EditText
        parametros.put("tipo", etTipo.getText().toString());
        parametros.put("raza", etRaza.getText().toString());
        parametros.put("nombre", etNombre.getText().toString());
        parametros.put("peso", etPeso.getText().toString());
        parametros.put("color", etColor.getText().toString());

        // PASO 5: Enviamos los datos
        db.update("mascotas",parametros,"idmascota=?",campoCriterio);
        db.close();
        // Regresar todo a un estado inicial (INTERFAZ)
        notificar("Actualizado correctamente");
        etIdBuscador1.requestFocus();
        limpiarValores();
    }


    private void limpiarValores(){
        etIdBuscador1.setText(null);
        etTipo.setText(null);
        etRaza.setText(null);
        etNombre.setText(null);
        etPeso.setText(null);
        etColor.setText(null);
    }

    private void buscarMascota(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] campoCriterio = { etIdBuscador1.getText().toString() };
        String[] campos = {"tipo","raza","nombre","peso","color"};
        try {
            // PASO 5: Ejecutar la consulta -> Cursores
            Cursor cursor = db.query("mascotas",campos,"idmascota=?",campoCriterio, null, null, null);
            cursor.moveToFirst();

            // PASO 6: El cursor envía la información a las cajas
            etTipo.setText(cursor.getString(0));
            etRaza.setText(cursor.getString(1));
            etNombre.setText(cursor.getString(2));
            etPeso.setText(cursor.getString(3));
            etColor.setText(cursor.getString(4));
            // PASO 7: Cerrar el cursor
            cursor.close();
        }
        catch(Exception e){
            Toast.makeText(this,"No encontrado",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUI(){
        etIdBuscador1 = findViewById(R.id.etIdBuscador1);
        etTipo = findViewById(R.id.etBuscarTipo);
        etRaza = findViewById(R.id.etBuscarRaza);
        etNombre = findViewById(R.id.etBuscarNombre);
        etPeso = findViewById(R.id.etBuscarPeso);
        etColor = findViewById(R.id.etBuscarColor);


        btnBuscar = findViewById(R.id.btnbuscarMascota);
        btnActualizar = findViewById(R.id.btnActualizarMascota);
        btnEliminar = findViewById(R.id.btnEliminarMascota);
        btnRegresar = findViewById(R.id.btnRegresar);


    }
}
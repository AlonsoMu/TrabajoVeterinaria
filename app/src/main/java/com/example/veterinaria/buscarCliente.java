package com.example.veterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscarCliente extends AppCompatActivity {

    ConexionSQLiteHelper conexion;

    EditText etIdBuscador, etApellidos, etNombres, etTelefono, etEmail, etDireccion, etFechaNacimiento;

    Button btnBuscar,btnActualizar, btnEliminar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);

        loadUI();

        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdveterinaria",null,1);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCliente();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarActualizar();
            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                eliminarCliente();
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

    private void eliminarCliente(){
        // PASO 1: Permisos
        SQLiteDatabase db = conexion.getWritableDatabase();
        // PASO 2: Campos de criterio (WHERE campo = 1)
        String [] campoCriterio = {etIdBuscador.getText().toString()};
        // PASO 3: Eliminación
        db.delete("clientes","idcliente=?", campoCriterio);
        db.close();

        // ACTUALIZA INTERFAZ
        limpiarCampos();
        notificar("Eliminado correctamente");
        etIdBuscador.requestFocus();
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
        String[] campoCriterio = {etIdBuscador.getText().toString() };

        // PASO 3 : Contenedor de datos a enviar
        ContentValues parametros = new ContentValues();

        // PASO 4 : Asignar al contenido los valores EditText
        parametros.put("apellidos", etApellidos.getText().toString());
        parametros.put("nombres", etNombres.getText().toString());
        parametros.put("telefono", etTelefono.getText().toString());
        parametros.put("email", etEmail.getText().toString());
        parametros.put("direccion", etDireccion.getText().toString());
        parametros.put("fechanacimiento", etFechaNacimiento.getText().toString());

        // PASO 5: Enviamos los datos
        db.update("clientes",parametros,"idcliente=?",campoCriterio);
        db.close();
        // Regresar todo a un estado inicial (INTERFAZ)
        notificar("Actualizado correctamente");
        etIdBuscador.requestFocus();
        limpiarCampos();
    }

    private void limpiarCampos(){
        etIdBuscador.setText(null);
        etApellidos.setText(null);
        etNombres.setText(null);
        etTelefono.setText(null);
        etEmail.setText(null);
        etDireccion.setText(null);
        etFechaNacimiento.setText(null);
    }




    private void buscarCliente(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] campoCriterio = { etIdBuscador.getText().toString() };
        String[] campos = {"apellidos","nombres","telefono","email","direccion","fechanacimiento"};
        try {
            // PASO 5: Ejecutar la consulta -> Cursores
            Cursor cursor2 = db.query("clientes",campos,"idcliente=?",campoCriterio, null, null, null);
            cursor2.moveToFirst();

            // PASO 6: El cursor envía la información a las cajas
            etApellidos.setText(cursor2.getString(0));
            etNombres.setText(cursor2.getString(1));
            etTelefono.setText(cursor2.getString(2));
            etEmail.setText(cursor2.getString(3));
            etDireccion.setText(cursor2.getString(4));
            etFechaNacimiento.setText(cursor2.getString(5));
            // PASO 7: Cerrar el cursor
            cursor2.close();
        }
        catch(Exception e){
            Toast.makeText(this,"No encontrado",Toast.LENGTH_SHORT).show();
        }
    }



    private void loadUI(){
        etIdBuscador = findViewById(R.id.etIdBuscador);
        etApellidos = findViewById(R.id.etBuscarApellidos);
        etNombres = findViewById(R.id.etBuscarNombres);
        etTelefono = findViewById(R.id.etBuscarTelefono);
        etEmail = findViewById(R.id.etBuscarEmail);
        etDireccion = findViewById(R.id.etBuscarDireccion);
        etFechaNacimiento = findViewById(R.id.etBuscarFechaNacimiento);


        btnBuscar = findViewById(R.id.btnbuscarCliente);
        btnActualizar = findViewById(R.id.btnActualizarCliente);
        btnEliminar = findViewById(R.id.btnEliminarCliente);
        btnRegresar = findViewById(R.id.btnRegresar);


    }
}
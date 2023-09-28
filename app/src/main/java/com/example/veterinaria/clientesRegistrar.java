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

public class clientesRegistrar extends AppCompatActivity {

    EditText etApellidos, etNombres, etTelefono, etEmail, etDireccion, etFechanacimiento;

    Button btnRegistrarCliente, btnBuscarCliente, btnRegresar, btnListarClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_registrar);

        loadUI();
        btnRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscarCliente.class));
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnListarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), listarCliente.class));
            }
        });
    }

    private void validarCampos(){
        String apellidos, nombres, telefono, email, direccion, fechanacimiento;

        apellidos = etApellidos.getText().toString();
        nombres = etNombres.getText().toString();
        telefono = etTelefono.getText().toString();
        email = etEmail.getText().toString();
        direccion = etDireccion.getText().toString();
        fechanacimiento = etFechanacimiento.getText().toString();

        if (apellidos.isEmpty() || nombres.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechanacimiento.isEmpty()){
            notificar("Complete el formulario");
            etApellidos.requestFocus();
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
                registrarCliente();
            }
        });
        dialogo.show();
    }
    private void registrarCliente(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"bdveterinaria", null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();
        parametros.put("apellidos", etApellidos.getText().toString());
        parametros.put("nombres", etNombres.getText().toString());
        parametros.put("telefono", etTelefono.getText().toString());
        parametros.put("email", etEmail.getText().toString());
        parametros.put("direccion", etDireccion.getText().toString());
        parametros.put("fechanacimiento", etFechanacimiento.getText().toString());

        long idobtenido = db.insert("clientes","idcliente", parametros);

        notificar("Datos guardados correctamente - " + String.valueOf(idobtenido));
        reiniciarUI();
        etApellidos.requestFocus();
    }

    private void reiniciarUI(){
        etApellidos.setText(null);
        etNombres.setText(null);
        etTelefono.setText(null);
        etEmail.setText(null);
        etDireccion.setText(null);
        etFechanacimiento.setText(null);
    }
    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void loadUI(){
        etApellidos             = findViewById(R.id.etApellidos);
        etNombres               = findViewById(R.id.etNombres);
        etTelefono              = findViewById(R.id.etTelefono);
        etEmail                 = findViewById(R.id.etEmail);
        etDireccion             = findViewById(R.id.etDireccion);
        etFechanacimiento       = findViewById(R.id.etFechaNacimiento);

        btnRegistrarCliente    = findViewById(R.id.btnRegistrarCliente);
        btnBuscarCliente        = findViewById(R.id.btnBuscarCliente);
        btnRegresar             = findViewById(R.id.btnRegresar);
        btnListarClientes       = findViewById(R.id.btnListarCliente);
    }
}
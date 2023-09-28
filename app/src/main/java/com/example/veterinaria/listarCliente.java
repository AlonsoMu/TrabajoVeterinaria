package com.example.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listarCliente extends AppCompatActivity {

    ListView lvRegistrosClientes;

    ArrayList<String> listaInformacion;
    ArrayList<Persona> listaPersonas;
    ConexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        loadUI();

        //Aperturar la conexion
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdveterinaria",null,1);

        //Obtener los datos
        consultarListaPersonas();

        //Enviamos los datos al LISTVIEW
        //Paso!: Crear un adaptador (puente)
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);

        //Paso2: Asignar el adaptador al LV
        lvRegistrosClientes.setAdapter(adaptador);

        lvRegistrosClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Direccion: " + listaPersonas.get(position).getDireccion() + "\n";
                mensaje += "Fecha Nacimiento: " + listaPersonas.get(position).getFechanacimiento();
                Toast.makeText(listarCliente.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarListaPersonas(){
        //Paso1: Permisos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Paso2: Inicializar un objeto de tipo persona
        Persona persona = null;

        //Paso3: Construir nuestra coleccion de personas
        listaPersonas = new ArrayList<Persona>();

        //Paso4: Consulta - SQL
        //db.rawQuery es para hacer consultas
        Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);

        //Paso 5: Recorrer el cursor (resultados/registros)
        while(cursor.moveToNext()){
            //Paso6: Guardar el valor obtenido de la consulta dentro de un objeto Persona
            persona = new Persona();
            persona.setIdcliente(cursor.getInt(0));
            persona.setApellidos(cursor.getString(1));
            persona.setNombre(cursor.getString(2));
            persona.setTelefono(cursor.getString(3));
            persona.setEmail(cursor.getString(4));
            persona.setDireccion(cursor.getString(5));
            persona.setFechanacimiento(cursor.getString(6));

            //Paso7: Agregar el objeto Persona a la coleccion ArrayList<Persona>
            listaPersonas.add(persona);
        }

        //Ahora invocamos al método....
        obtenerLista();

    }
    private void obtenerLista(){
        //Paso!: Construimos nuestra lista con los datos a mostrar
        listaInformacion = new ArrayList<String>();

        //Paso2: Recorremos la coleccion de personas
        for(int i = 0; i <listaPersonas.size(); i++){
            //Paso3: Enviamos la informacion de la primera lista a la segunda
            listaInformacion.add(listaPersonas.get(i).getApellidos() + " " + listaPersonas.get(i).getNombre());


        }
    }

    private void loadUI(){
        lvRegistrosClientes = findViewById(R.id.lvRegistroClientes);
    }
}
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

public class listarMascota extends AppCompatActivity {

    ListView lvRegistrosMascotas;

    ArrayList<String> listaInformacion;
    ArrayList<Animal> listaAnimales;
    ConexionSQLiteHelper conexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mascota);

        loadUI();
        //Aperturar la conexion
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdveterinaria",null,1);
        consultarListaMascotas();

        //Enviamos los datos al LISTVIEW
        //Paso!: Crear un adaptador (puente)
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        lvRegistrosMascotas.setAdapter(adaptador);

        lvRegistrosMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Tipo: " + listaAnimales.get(position).getTipo() + "\n";
                mensaje += "Raza: " + listaAnimales.get(position).getRaza();
                mensaje += "Nombre: " + listaAnimales.get(position).getNombre();
                Toast.makeText(listarMascota.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarListaMascotas(){
        //Paso1: Permisos
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Paso2: Inicializar un objeto de tipo persona
        Animal animal = null;

        //Paso3: Construir nuestra coleccion de personas
        listaAnimales = new ArrayList<Animal>();

        //Paso4: Consulta - SQL
        //db.rawQuery es para hacer consultas
        Cursor cursor = db.rawQuery("SELECT * FROM mascotas", null);

        //Paso 5: Recorrer el cursor (resultados/registros)
        while(cursor.moveToNext()){
            //Paso6: Guardar el valor obtenido de la consulta dentro de un objeto Persona
            animal = new Animal();
            animal.setIdmascota(cursor.getInt(0));
            animal.setTipo(cursor.getString(1));
            animal.setRaza(cursor.getString(2));
            animal.setNombre(cursor.getString(3));
            animal.setPeso(cursor.getString(4));
            animal.setColor(cursor.getString(5));

            //Paso7: Agregar el objeto Persona a la coleccion ArrayList<Persona>
            listaAnimales.add(animal);
        }

        //Ahora invocamos al método....
        obtenerLista();

    }

    private void obtenerLista(){
        //Paso!: Construimos nuestra lista con los datos a mostrar
        listaInformacion = new ArrayList<String>();

        //Paso2: Recorremos la coleccion de personas
        for(int i = 0; i <listaAnimales.size(); i++){
            //Paso3: Enviamos la informacion de la primera lista a la segunda
            listaInformacion.add(listaAnimales.get(i).getTipo() + " " + listaAnimales.get(i).getNombre()+ " " + listaAnimales.get(i).getPeso());


        }
    }

    private void loadUI(){
        lvRegistrosMascotas = findViewById(R.id.lvRegistroMascotas);
    }
}
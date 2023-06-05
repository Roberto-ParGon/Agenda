package com.example.activityhistorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listaSubjects;
    ArrayAdapter<String> adaptador;
    ArrayList<String> elementos;

    SharedPreferences sharedPreferences;
    String itemsString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSubjects = findViewById(R.id.vistaList);


        elementos = new ArrayList<>();
        // Agregar elementos a la lista
        elementos.add("Subject 1");
        elementos.add("Subject 2");
        elementos.add("Subject 3");
        elementos.add("Subject 4");
        elementos.add("Subject 5");


        // Guardar los elementos en SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        itemsString = TextUtils.join(",", elementos);
        editor.putString("items", itemsString);
        editor.apply();

        // Cargar los elementos desde SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        itemsString = sharedPreferences.getString("items", "");
        String[] itemsArray = itemsString.split(",");
        elementos = new ArrayList<>(Arrays.asList(itemsArray));
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        listaSubjects.setAdapter(adaptador);

        /* WORKING
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        listaSubjects.setAdapter(adaptador);
         */

        listaSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* WORKING
                String selectedItem = elementos.get(position); // Obtener el elemento seleccionado
                Intent intent = new Intent(MainActivity.this, DescActivityHistorial.class);
                intent.putExtra("selectedItem", selectedItem); // Pasar el valor seleccionado al segundo Activity
                startActivity(intent); // Abrir el segundo Activity
                 */

                String selectedItem = elementos.get(position); // Obtener el elemento seleccionado
                Intent intent = new Intent(MainActivity.this, DescActivityHistorial.class);
                intent.putExtra("selectedItem", selectedItem); // Pasar el valor seleccionado a la nueva Activity
                startActivityForResult(intent, 1); // Iniciar la nueva Activity y esperar resultado
            }
        });


        FloatingActionButton fabDialog = findViewById(R.id.fab);
        fabDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDel = new AlertDialog.Builder(MainActivity.this);
                alertDel.setMessage("¿Seguro que desea borrar todo el historial?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Implementación de borrar el registro
                                if (elementos.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No hay elementos para borrar", Toast.LENGTH_SHORT).show();
                                } else {
                                    elementos.clear();
                                    adaptador.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, "Se borraron todos los elementos satisfactoriamente", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alertDel.create();
                titulo.setTitle("Eliminación de todos los registros");
                titulo.show();

                Window window = titulo.getWindow();
                if (window != null) {
                    // Cambiar el color de fondo
                    window.setBackgroundDrawableResource(R.color.red);
                }

            }
        });
    }

    //Método que va a esperar el resultado del nuevo activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String selectedItem = data.getStringExtra("selectedItem"); // Obtener el elemento eliminado
                elementos.remove(selectedItem); // Eliminar el elemento del ArrayList
                adaptador.notifyDataSetChanged(); // Actualizar el ListView
            }
        }
    }

}
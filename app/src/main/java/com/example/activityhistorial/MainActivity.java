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

    //Definición de los elementos a usar de manera global.
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
        //Creación y adición de items a la lista 'elementos'.
        elementos = new ArrayList<>();
        elementos.add("Subject 1");
        elementos.add("Subject 2");
        elementos.add("Subject 3");
        elementos.add("Subject 4");
        elementos.add("Subject 5");


        //Se guardan los elementos en un archivo llamado "myPrefs" utilizando SharedPreferences.
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Se almacena la lista de elementos en una sola cadena separados por una coma ",".
        itemsString = TextUtils.join(",", elementos);
        //Esta cadena se almacena en 'items' y se guarda en sharedPreferences utilizando apply().
        editor.putString("items", itemsString);
        editor.apply();

        //Se recupera la ínformación almacenada en "MyPrefs", y se almacena en 'itemsString'.
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        itemsString = sharedPreferences.getString("items", "");
        //Se divide mediante comas "," la información recuperada y se almacena en un arreglo.
        String[] itemsArray = itemsString.split(",");
        //El arreglo se presenta mediante lista utilizando el método asList para crear la lista mediante el arreglo.
        elementos = new ArrayList<>(Arrays.asList(itemsArray));
        //Se crea un adaptador qué va a presentar la lista utilizando el arreglo creado mediante lista.
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        //Se le establece el adaptador al listView para mostrar la información.
        listaSubjects.setAdapter(adaptador);

        /* WORKING
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        listaSubjects.setAdapter(adaptador);
         */

        listaSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* FUNCIONANDO CON VALORES ESTÁTICOS.
                String selectedItem = elementos.get(position);
                Intent intent = new Intent(MainActivity.this, DescActivityHistorial.class);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
                 */
                //Se obtiene el item de la lista de elementos en base a su posición.
                String selectedItem = elementos.get(position);
                Intent intent = new Intent(MainActivity.this, DescActivityHistorial.class);
                //Se le añade el valor a transportar utilizando putExtra, definiéndolo como "SelectedItem",
                //Y almacenando la información obetenida de la lista con 'selectedItem'.
                intent.putExtra("selectedItem", selectedItem);
                //Este método permite iniciar una actividad y esperar un resultado.
                startActivityForResult(intent, 1);
            }
        });

    //Se recupera el id del fab y se le asigna un onClickListener.
        FloatingActionButton fabDialog = findViewById(R.id.fab);
        fabDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se utiliza un alerDialog para confirmar la eliminación.
                AlertDialog.Builder alertDel = new AlertDialog.Builder(MainActivity.this);
                alertDel.setMessage("¿Seguro que desea borrar todo el historial?")
                        .setCancelable(false)//Permite evitar que el alerDialog se cierra al pulsar fuera de el.
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            //En caso de que se seleccione la opción "SI":
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Se verifica si hay elementos de la lista disponibles para borrar.
                                if (elementos.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No hay elementos para borrar", Toast.LENGTH_SHORT).show();
                                } else {
                                    //En caso de que existe al menos uno, borra toda la lista. Y muestra un Toast.
                                    elementos.clear();
                                    adaptador.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, "Se borraron todos los elementos satisfactoriamente", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        //En caso de que se seleccione "NO" del AlerDialog, se cierra este.
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                //Se personaliza el mensaje a mostar en el AlerDialog.
                AlertDialog titulo = alertDel.create();
                titulo.setTitle("Eliminación de todos los registros");
                titulo.show();
                //Se verifica que existe la ventana mostrada en el AlertDialog.
                Window window = titulo.getWindow();
                if (window != null) {
                    //Se cambia el color de la ventana utilizando un color definido en colors.xml.
                    window.setBackgroundDrawableResource(R.color.red);
                }

            }
        });
    }

    //Método que va a esperar el resultado del nuevo activity llamado en startActivityForResult().
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Se verifica que los datos mandados en el método sean los mismos, indicando que el envío
        //y el recibimiento de datos fue correcto.
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //Se obtiene el elemento que se desea eliminar.
                String selectedItem = data.getStringExtra("selectedItem");
                //Se elimina el elemento recibido en la lista de 'elementos' y se actualiza el listView.
                elementos.remove(selectedItem);
                adaptador.notifyDataSetChanged();
            }
        }
    }

}
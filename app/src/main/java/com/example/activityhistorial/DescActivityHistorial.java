package com.example.activityhistorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DescActivityHistorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_historial);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.celest)));
        }

        //Método que muestra el topBotton de la actionBar para regresar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtener el valor pasado desde el MainActivity.
        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");

        // Utilizar el valor seleccionado del SegundoActivity en el nombre.
        TextView nameView = findViewById(R.id.nameInput);
        nameView.setText(selectedItem);
        //Fecha.
        TextView DateView = findViewById(R.id.dateInput);
        DateView.setText(selectedItem);
        //Materia.
        TextView subjectView = findViewById(R.id.subjectInput);
        subjectView.setText(selectedItem);
        //Descripción
        TextView descView = findViewById(R.id.descInput);
        descView.setText(selectedItem);

        //Se recupera el fab y se realiza la eliminación similar en el 'MainActivity'.
        FloatingActionButton fabDialog = findViewById(R.id.fab);
        fabDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDel = new AlertDialog.Builder(DescActivityHistorial.this);
                alertDel.setMessage("¿Seguro que desea borrar el registro "+selectedItem+"?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Obtener el elemento seleccionado.
                                String selectedItem = getIntent().getStringExtra("selectedItem");
                                Intent intent = new Intent();
                                // Pasar el elemento eliminado al MainActivity
                                intent.putExtra("selectedItem", selectedItem);
                                setResult(RESULT_OK, intent); // Establecer el resultado del mensaje esperado como OK.
                                finish(); // cerrar la Activity 'DescActivityHistorial' y mostrar un Toast con el mensaje de borrado.
                                Toast.makeText(DescActivityHistorial.this, "Se borró "+selectedItem+" satisfactoriamente.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //setResult(RESULT_CANCELED); // Establecer el resultado como CANCELED
                                //finish(); // Cerrar la activity.
                                dialogInterface.cancel();
                            }
                        });

                // Obtener los botones del diálogo para personalizar su apariencia
                final AlertDialog dialog = alertDel.create();
                dialog.setTitle("Eliminación de todos los registros");

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                        //Color de fondo de los botones.
                        positiveButton.setBackgroundColor(getResources().getColor(R.color.green));
                        negativeButton.setBackgroundColor(getResources().getColor(R.color.red));

                        //Color del texto en los botones.
                        positiveButton.setTextColor(getResources().getColor(android.R.color.white));
                        negativeButton.setTextColor(getResources().getColor(android.R.color.white));
                    }
                });

                dialog.show();
                // Cambiar el color de fondo de la ventana del AlertDialog
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawableResource(R.color.celest);
                }
            }
        });
    }

    //Método que permite qué, al seleccionar regresar al menu nos regrese al home.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
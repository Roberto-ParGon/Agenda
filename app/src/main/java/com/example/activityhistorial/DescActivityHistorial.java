package com.example.activityhistorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DescActivityHistorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_historial);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtener el valor pasado desde el MainActivity
        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");

        // Utilizar el valor seleccionado en el SegundoActivity
        TextView nameView = findViewById(R.id.nameInput);
        nameView.setText(selectedItem);

        TextView DateView = findViewById(R.id.dateInput);
        DateView.setText(selectedItem);

        TextView subjectView = findViewById(R.id.subjectInput);
        subjectView.setText(selectedItem);

        TextView descView = findViewById(R.id.descInput);
        descView.setText(selectedItem);


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
                                //Implementación de borrar el registro

                                String selectedItem = getIntent().getStringExtra("selectedItem"); // Obtener el elemento seleccionado
                                Intent intent = new Intent();
                                intent.putExtra("selectedItem", selectedItem); // Pasar el elemento eliminado al MainActivity
                                setResult(RESULT_OK, intent); // Establecer el resultado como OK
                                finish(); // Cerrar la ConfirmarEliminacionActivity
                                Toast.makeText(DescActivityHistorial.this, "Se borró "+selectedItem+" satisfactoriamente.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //setResult(RESULT_CANCELED); // Establecer el resultado como CANCELED
                                //finish(); // Cerrar la ConfirmarEliminacionActivity sin eliminar nada
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo = alertDel.create();
                titulo.setTitle("Eliminación del registro");
                titulo.show();

                Window window = titulo.getWindow();
                if (window != null) {
                    // Cambiar el color de fondo
                    window.setBackgroundDrawableResource(R.color.red);
                }

            }
        });


    }

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
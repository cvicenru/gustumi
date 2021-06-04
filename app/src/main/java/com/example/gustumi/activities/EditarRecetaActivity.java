package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gustumi.R;
import com.example.gustumi.adapters.MostrarRecetasAdapter;
import com.example.gustumi.model.Receta;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarRecetaActivity extends AppCompatActivity {


    private String recetaId;

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;


    EditText editTextNombreReceta,
            editTextTiempoElaboracion,
            editTextIngredientes,
            editTextElaboracion;

    Button btnActualizarReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_receta_activity_layout);

        editTextNombreReceta = findViewById(R.id.editTextNombreReceta);
        editTextTiempoElaboracion = findViewById(R.id.editTextTiempoElaboracion);
        editTextIngredientes = findViewById(R.id.editTextIngredientes);
        editTextElaboracion = findViewById(R.id.editTextElaboracion);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Recetas");

        recetaId = getIntent().getStringExtra("recetaId");//guarda el dato del Extra (id de la receta que el usuario desea editar) que se envía desde el MostrarRecetasAdapter


        obtenerDatosReceta();

        /*BOTÓN ACTUALIZAR RECETA*/
        btnActualizarReceta = findViewById(R.id.btnActualizarReceta);
        btnActualizarReceta.setOnClickListener(new View.OnClickListener() {//cuando el usuario pulse se actualizará la receta en la BD datos
            @Override
            public void onClick(View v) {
                actualizarReceta();
            }
        });
    }//fin onCreate()

    /*-----------MÉTODOS---------*/

    /*Método que trae de la BD los datos de la receta para poder editarlos*/
    private void obtenerDatosReceta() {
        firebaseFirestore.collection("Recetas").document(recetaId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {//si el documento al que estoy apuntando existe
                    String data = "";

                    Receta receta = documentSnapshot.toObject(Receta.class);

                    //Trae los datos de la receta
                    String nombreReceta = documentSnapshot.getString("nombreReceta");

                    String tiempoElaboracion = documentSnapshot.getString("tiempoElaboracion");


                    //ingredientes
                    for (String ingredientes : receta.getIngredientes()) {
                        data += "\n-" + ingredientes;
                    }
                    data += "\n\n";
                    editTextIngredientes.setText(data);

                    //elaboración
                    for (String elaboracion : receta.getElaboracion()) {
                        data += "\n-" + elaboracion;
                    }

                    data += "\n\n";
                    editTextElaboracion.setText(data);


                    //muestro el nombre y el tiempo de elaboración de la BD en los EditText
                    editTextNombreReceta.setText(nombreReceta);
                    editTextTiempoElaboracion.setText(tiempoElaboracion);


                }//fin if
            }
        });

    }//fin obtenerDatosReceta()


    /*ACTUALIZAR RECETA*/
    private void actualizarReceta() {
        //Obtengo los datos de la receta
        String nombreReceta = editTextNombreReceta.getText().toString();
        String tiempoElaboracion = editTextTiempoElaboracion.getText().toString();
        //String ingredientes = editTextNombreReceta.getText().toString();

        Map<String, Object> map = new HashMap<>();//Creo un mapa de valores

        //establezco los atributos con sus valores
        map.put("nombreReceta", nombreReceta);
        map.put("tiempoElaboracion", tiempoElaboracion);

        // map.put("ingredientes", FieldValue.arrayUnion(ingredientes));


        //El método update recibe un mapa de valores
        firebaseFirestore.collection("Recetas").document(recetaId).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {//si la actualización de la receta se ha realizado correctamente
                Toast.makeText(EditarRecetaActivity.this, "La receta se ha actualizado correctamente", Toast.LENGTH_SHORT).show();

                //redirecciona al usuario a MostrarRecetasAdapter
                Intent intent = new Intent(EditarRecetaActivity.this, MostrarRecetasActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {//si la actualización de la receta no se ha podido realizar
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarRecetaActivity.this, "La receta no se ha podido actualizar", Toast.LENGTH_SHORT).show();
            }
        });

    }//fin actualizarReceta()

}//fin clase
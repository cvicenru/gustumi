package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.gustumi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleRecetaActivity extends AppCompatActivity {

    private static final String TAG = "DetalleRecetaActivity";

    private SharedPreferences sharedPreferences;
    private ToggleButton toggleButtonFav;

    FirebaseFirestore db;
    FirebaseAuth mAuth;//Autenticación


    // Referencia a un archivo de imagen en Cloud Storage
    StorageReference storageReference;//


    TextView tViewNombreRecetaDetalle,
            tViewTiempoElaboracionDetalle,
            tViewIngredientesDetalle,
            tViewElaboracionDetalle;

    ImageView imgRecetaDetalle, btnAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_receta_activity_layout);

        sharedPreferences = getSharedPreferences("archivoPreferencias", Context.MODE_PRIVATE);

        btnAtras = (ImageView) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        toggleButtonFav = (ToggleButton) findViewById(R.id.toggleButtonFav);
        toggleButtonFav.setChecked(sharedPreferences.getBoolean("FavoritosEstado", false));


        imgRecetaDetalle = (ImageView) findViewById(R.id.imgRecetaDetalle);
        tViewNombreRecetaDetalle = findViewById(R.id.tViewNombreRecetaDetalle);
        tViewTiempoElaboracionDetalle = findViewById(R.id.tViewTiempoElaboracionDetalle);
        tViewIngredientesDetalle = findViewById(R.id.tViewIngredientesDetalle);
        tViewElaboracionDetalle = findViewById(R.id.tViewElaboracionDetalle);

        //textViewPrueba = (TextView) findViewById(R.id.textViewPrueba);

        storageReference = FirebaseStorage.getInstance().getReference();

        /**cargarPreferencias(); //Leer el archivo "preferencias" cada vez que se carga la aplicación para verificar que hay información almacenada**/


        //MOSTRAR IMAGEN DE LA RECETA
        String imagen = "";

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                imagen = null;
            } else {
                imagen = bundle.getString("imagen");
            }
        } else {
            imagen = (String) savedInstanceState.getSerializable("imagen");
        }

        Glide.with(this).load(imagen).apply(RequestOptions.circleCropTransform()).into(imgRecetaDetalle);



        /*MOSTRAR NOMBRE DE LA RECETA*/
        final String nombreReceta;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                nombreReceta = null;
            } else {
                nombreReceta = bundle.getString("nombreReceta");
            }
        } else {
            nombreReceta = (String) savedInstanceState.getSerializable("nombreReceta");
        }

        tViewNombreRecetaDetalle.setText(nombreReceta);

        /*MOSTRAR TIEMPO DE ELABORACIÓN*/
        String tiempoElaboracion;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                tiempoElaboracion = null;
            } else {
                tiempoElaboracion = bundle.getString("tiempoElaboracion");
            }
        } else {
            tiempoElaboracion = (String) savedInstanceState.getSerializable("tiempoElaboracion");
        }

        tViewTiempoElaboracionDetalle.setText(tiempoElaboracion);


        //------------------MOSTRAR LISTA DE INGREDIENTES------------------
        String ingredientesString = "";
        String listIngredientesDetalle = null;


        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                ingredientesString = null;
            } else {
                ingredientesString = bundle.getString("ingredientes");

                listIngredientesDetalle = ingredientesString.replace("[", "· ").replace("]", "").replace(",", "\n\n·");

            }
        } else {
            ingredientesString = (String) savedInstanceState.getSerializable("ingredientes");
        }


        for (int i = 0; i < listIngredientesDetalle.length(); i++) {
            tViewIngredientesDetalle.setText(listIngredientesDetalle);

        }

        //MOSTRAR PROCESO DE ELABORACIÓN
        String elaboracionString = "";

        String listElaboracionDetalle = null;

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                elaboracionString = null;
            } else {
                elaboracionString = bundle.getString("elaboracion");

                listElaboracionDetalle = elaboracionString.replace("[", "").replace("]", "").replace("., ", ".\n\n");
            }
        } else {
            elaboracionString = (String) savedInstanceState.getSerializable("elaboracion");
        }

        for (int i = 0; i < listElaboracionDetalle.length(); i++) {
            tViewElaboracionDetalle.setText(listElaboracionDetalle);
        }


       /*RESGUARDO
        //Botón Favoritos
        toggleButtonFav.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(DetalleRecetaActivity.this, "Se ha añadido a favoritos", Toast.LENGTH_LONG).show();

                    FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
                    String userId = usuarioFirebase.getUid();

                    Map<String, Object> favorito = new HashMap<>();
                    favorito.put("nombreReceta", nombreReceta);

                    db.collection("Usuarios").document(userId).collection("Favoritos")
                            .add(favorito)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("favorito", "\n" + "Error al añadir el documento", e);
                                }
                            });


                    Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");
                } else {
                    Toast.makeText(DetalleRecetaActivity.this, "Se ha eliminado de favoritos", Toast.LENGTH_LONG).show();

                    Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");


                }
                guardarDatosFavoritos();
            }
        }));
*/

       /*BOTÓN FAVORITOS*/
        toggleButtonFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("FavoritosEstado", isChecked).commit();

                if (isChecked) {

                    Toast.makeText(DetalleRecetaActivity.this, "Se ha añadido a favoritos", Toast.LENGTH_LONG).show();

                    FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
                    String userId = usuarioFirebase.getUid();

                    Map<String, Object> favorito = new HashMap<>();
                    favorito.put("nombreReceta", nombreReceta);

                    db.collection("Usuarios").document(userId).collection("Favoritos")
                            .add(favorito)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("favorito", "\n" + "Error al añadir el documento", e);
                                }
                            });


                    Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");
                } else {

                    Toast.makeText(DetalleRecetaActivity.this, "Se ha eliminado de favoritos", Toast.LENGTH_LONG).show();


                    SharedPreferences.Editor editorEliminar = DetalleRecetaActivity.this.getSharedPreferences("preferencias", MODE_PRIVATE).edit();

                    editorEliminar.remove("nombreReceta");

                    editorEliminar.apply();


                    Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");
                }
            }
        });

/*
        //Botón Favoritos
        toggleButtonFav.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(DetalleRecetaActivity.this, "Se ha añadido a favoritos", Toast.LENGTH_LONG).show();

                    FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
                    String userId = usuarioFirebase.getUid();

                    Map<String, Object> favorito = new HashMap<>();
                    favorito.put("nombreReceta", nombreReceta);

                    db.collection("Usuarios").document(userId).collection("Favoritos")
                            .add(favorito)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("favorito", "\n" + "Error al añadir el documento", e);
                                }
                            });
                    guardarDatosFavoritos();

                    Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");
                } else {
                    Toast.makeText(DetalleRecetaActivity.this, "Se ha eliminado de favoritos", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editorEliminar = DetalleRecetaActivity.this.getSharedPreferences("preferencias", MODE_PRIVATE).edit();

                    editorEliminar.remove("nombreReceta");

                    editorEliminar.apply();

                    Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");


                }
                guardarDatosFavoritos();
            }
        }));
*/

/** PROBANDO A ELIMINAR : NO VA
 //Botón Favoritos
 toggleButtonFav.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

SharedPreferences sharedPreferences = getSharedPreferences("preferencias", MODE_PRIVATE);

//Leer la información
String stringNombreReceta = tViewNombreRecetaDetalle.getText().toString();//Obtengo lo que tengo en el campo que se muestra el nombre de la receta y l oalmaceno en la variable stringNombreReceta

SharedPreferences.Editor editor = sharedPreferences.edit();


if (isChecked) {

Toast.makeText(DetalleRecetaActivity.this, "Se ha añadido a favoritos", Toast.LENGTH_LONG).show();

editor.putString("nombreReceta", stringNombreReceta); //agrego los datos que quiero guardar | clave = "nombreReceta" , valor (dato a guardar) = stringNombreReceta

FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
String userId = usuarioFirebase.getUid();

Map<String, Object> favorito = new HashMap<>();
favorito.put("nombreReceta", nombreReceta);

db.collection("Usuarios").document(userId).collection("Favoritos")
.add(favorito)
.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
@Override public void onSuccess(DocumentReference documentReference) {
Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
}
})
.addOnFailureListener(new OnFailureListener() {
@Override public void onFailure(@NonNull Exception e) {
Log.w("favorito", "\n" + "Error al añadir el documento", e);
}
});


Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");
} else {

Toast.makeText(DetalleRecetaActivity.this, "Se ha eliminado de favoritos", Toast.LENGTH_LONG).show();

SharedPreferences.Editor editorEliminar = DetalleRecetaActivity.this.getSharedPreferences("preferencias", MODE_PRIVATE).edit();

editorEliminar.remove("nombreReceta");

editorEliminar.apply();


Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");


}
//guardarDatosFavoritos();
}
}));**/

/**
 //Botón Favoritos
 toggleButtonFav.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
if (isChecked) {
Toast.makeText(DetalleRecetaActivity.this, "Se ha añadido a favoritos", Toast.LENGTH_LONG).show();

SharedPreferences.Editor editor = getSharedPreferences("preferencias", MODE_PRIVATE).edit();
editor.putBoolean("nombreReceta", true);
editor.commit();



FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
String userId = usuarioFirebase.getUid();

Map<String, Object> favorito = new HashMap<>();
favorito.put("nombreReceta", nombreReceta);

db.collection("Usuarios").document(userId).collection("Favoritos")
.add(favorito)
.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
@Override public void onSuccess(DocumentReference documentReference) {
Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
}
})
.addOnFailureListener(new OnFailureListener() {
@Override public void onFailure(@NonNull Exception e) {
Log.w("favorito", "\n" + "Error al añadir el documento", e);
}
});


Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");

} else {
Toast.makeText(DetalleRecetaActivity.this, "Se ha eliminado de favoritos", Toast.LENGTH_LONG).show();

SharedPreferences.Editor editor = getSharedPreferences("preferencias", MODE_PRIVATE).edit();
editor.putBoolean("nombreReceta", false);
editor.commit();



Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");


}
guardarDatosFavoritos();
}
}));
 **/

    /*    //Botón Favoritos
        toggleButtonFav.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                   // guardarDatosFavoritos();

                    FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
                    String userId = usuarioFirebase.getUid();

                    Map<String, Object> favorito = new HashMap<>();
                    favorito.put("nombreReceta", nombreReceta);

                    db.collection("Usuarios").document(userId).collection("Favoritos")
                            .add(favorito)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("favorito", "DocumentSnapshot añadido con ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("favorito", "\n" + "Error al añadir el documento", e);
                                }
                            });


                    Log.d(TAG, "-------------AÑADIR A FAVORITOS----------------------");
                } else {

                    Log.d(TAG, "------------ELIMINAR FAVORITOS----------------------------");


                }
            }
        }));
*/

    }//fin onCreate


    /*----------MÉTODOS----------*/

    /**
     * //Método para leer el archivo "preferencias" cada vez que se carga la aplicación para verificar que hay información almacenada
     * private void cargarPreferencias() {
     * SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);//"preferencias": nombre del archivo || Nivel de visualización privado para que solo se pueda acceder a él desde la app
     * <p>
     * String stringNameReceta = sharedPreferences.getString("nombreReceta", "No existe la información"); //Le pongo el identificador/ dato clave de la información (en este caso, "nombreReceta") | El 2º parámetro muestra la info por defecto en caso de que no tuviese nombre o el archivo de preferencias no existiera
     * <p>
     * textViewPrueba.setText(stringNameReceta);
     * <p>
     * Log.d(TAG, "-----------------cargarPreferencias() -----------------------");
     * <p>
     * }//fin cargarPreferencias()
     * <p>
     * <p>
     * /*Método para crear el archivo "preferencias" que guarde la información de la receta Favorita
     */
    /*private void guardarDatosFavoritos() {

        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);//"preferencias": nombre del archivo || Nivel de visualización privado para que solo se pueda acceder a él desde la app

        //Leer la información
        String stringNombreReceta = tViewNombreRecetaDetalle.getText().toString();//Obtengo lo que tengo en el campo que se muestra el nombre de la receta y l oalmaceno en la variable stringNombreReceta


        SharedPreferences.Editor editor = sharedPreferences.edit();//Asigno al archivo "preferencias" los datos || Editor permite editar (ir almacenando los datos) que el archivo sharedPreferences
        editor.putString("nombreReceta", stringNombreReceta); //agrego los datos que quiero guardar | clave = "nombreReceta" , valor (dato a guardar) = stringNombreReceta

        //textViewPrueba.setText(stringNombreReceta);


        editor.commit(); // Para completar el proceso de crear el archivo preferencias y almacenar la información

        Log.d(TAG, "...........................guardarDatosFavoritos() ...........................");
    }//Fin guardarDatosFavoritos()
*/

}//fin clase DetalleRecetaActivity





















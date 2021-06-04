package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.gustumi.R;
import com.example.gustumi.model.Receta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FavoritosActivity extends AppCompatActivity {


    private static final String TAG = "FavoritosActivity";
    /**FirebaseFirestore db; //Cloud Firestore
    CollectionReference collectionReference;

    FirebaseAuth mAuth;//Autenticación**/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos_activity_layout);

/**
        //Con esto, guardo en el String el nombre elegida por el usuario al hacer clic en el onclickCategoría de ExplorarCategoriasActivity
        Bundle bundle = this.getIntent().getExtras();
        String stringNombreReceta = bundle.getString("nombreReceta");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtengo los datos del usuario conectado
        String userId = usuarioFirebase.getUid();

        /*db.collection("Usuarios").document(userId).collection("Favoritos").whereEqualTo("nombreReceta", stringNombreReceta)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Receta receta = document.toObject(Receta.class);//llamo a Receta, tomo el documento que corresponde a Receta y obtengo el valor
                            //array recetas favoritas.add(receta)
                        } else {
                            Log.d(TAG, "No existe el documento.");
                        }
                    }
                    //dapter.notifyDataSetChanged(); //se muestran los cambios en tiempo real
                }
            }
        });

        //Inicializo Firestore
        /**db = FirebaseFirestore.getInstance();
         collectionReference = db
         .collection("Usuarios").document(userID)
         .collection("Favoritos").document(favortoID);
         collectionReference.add(usuarios);**/


        //DocumentReference documentReference = db.collection("Usuarios").document(userId);


    }
}
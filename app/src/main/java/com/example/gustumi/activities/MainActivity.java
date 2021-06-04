package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.gustumi.R;
import com.example.gustumi.adapters.AprendeAdapter;
import com.example.gustumi.adapters.RecetaAdapter;
import com.example.gustumi.model.Aprende;
import com.example.gustumi.model.Receta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


    /*DECLARACIÓN DE VARIABLES*/

    TextView iconoPerfil, iconoAprende,iconoExplorarCategorias;

    RecyclerView recyclerRecetasMain;
    ArrayList<Receta> arrayListRecetas;

    RecyclerView recyclerAprendeMain;
    ArrayList<Aprende> arrayListAprende;


    FirebaseFirestore db;

    RecetaAdapter adapter;
    AprendeAdapter adapterAprende;

    LinearLayoutManager linearLayoutManager;

    LinearLayoutManager linearLayManagAprende;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerRecetasMain = findViewById(R.id.recyclerRecetasMain);
        recyclerRecetasMain.setLayoutManager(linearLayoutManager);
        recyclerRecetasMain.setHasFixedSize(true);
        arrayListRecetas = new ArrayList<>();


        linearLayManagAprende = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerAprendeMain = findViewById(R.id.recyclerAprendeMain);
        recyclerAprendeMain.setLayoutManager(linearLayManagAprende);
        recyclerAprendeMain.setHasFixedSize(true);
        arrayListAprende = new ArrayList<>();



        iconoPerfil = findViewById(R.id.iconoPerfil);
        iconoExplorarCategorias = findViewById(R.id.iconoExplorarCategorias);
        iconoAprende = findViewById(R.id.iconoAprende);

        adapter = new RecetaAdapter(this, arrayListRecetas);
        recyclerRecetasMain.setAdapter(adapter);
        recyclerAprendeMain.setAdapter(adapter);


        adapterAprende = new AprendeAdapter(this, arrayListAprende);
        recyclerAprendeMain.setAdapter(adapterAprende);
        recyclerAprendeMain.setAdapter(adapterAprende);



        //Menú
                //Icono aprende
        iconoAprende.setOnClickListener(new View.OnClickListener() {//onClick btn aprende
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AprendeActivity.class));//parámetros: clase en la que estoy -> clase a la que voy
            }
        });



        //Icono Explorar categorías
        iconoExplorarCategorias.setOnClickListener(new View.OnClickListener() {//onClick btn Explorar categorías
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExplorarCategoriasActivity.class));
            }
        });



        db.collection("Recetas").orderBy("nombreReceta")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Receta receta = document.toObject(Receta.class);
                            arrayListRecetas.add(receta);
                        } else {
                            Log.d(TAG, "No existe el documento.");
                        }
                    }
                    adapter.notifyDataSetChanged();//se muestran los cambios en tiempo real
                }//fin if
            }
        });



        db.collection("Aprende")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Aprende aprende = document.toObject(Aprende.class);
                            arrayListAprende.add(aprende);
                        } else {
                            Log.d(TAG, "No existe el documento.");
                        }
                    }
                    adapterAprende.notifyDataSetChanged();//se muestran los cambios en tiempo real
                }//fin if
            }
        });


    }//Fin onCreate()





    /*------MÉTODOS------*/



    //onClickGestionarReceta -> nos dirige al activity donde poder crear una nueva receta
    public void onClickGestionarReceta(View view) {
        //Creo objeto de tipo Intent
        Intent intentGestionarReceta = new Intent(this, GestionarRecetaActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        //nos ayuda a lanzar un segundo activity
        startActivity(intentGestionarReceta);
    }//Fin onClickGestionarReceta

    //Método onClick TextView perfil usuario: al hacer clic en el icono del perfil nos llevará al perfil del usuario
    public void clickIconoPerfil(View view) {
        //Creo objeto de tipo Intent
        Intent intentIngredientes = new Intent(this, PerfilUsuarioActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        //nos ayuda a lanzar un segundo activity
        startActivity(intentIngredientes);
    }//Fin clickIconoPerfil()

    //onClickVerMasAprende -> nos dirige al activity donde poder ver Aprende
    public void onClickVerMasAprende(View view) {
        //Creo objeto de tipo Intent
        Intent intentVerMasAprende = new Intent(this, AprendeActivity.class);//parámetros: clase en la que estoy -> clase a la que voy
        //nos ayuda a lanzar un segundo activity
        startActivity(intentVerMasAprende);
    }//Fin onClickVerMasAprende

}//fin MainActivity
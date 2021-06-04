package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gustumi.R;
import com.example.gustumi.model.Receta;
import com.example.gustumi.adapters.RecetaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ResultadoBusquedaActivity extends AppCompatActivity {

    private static final String TAG = "ResultadoBusquedaActivity";

    FirebaseFirestore db;

    ArrayList<Receta> arraylistReceta;
    RecyclerView recyclerView;
    RecetaAdapter adapter;

    LinearLayoutManager linearLayoutManager;

    ImageView btnAtras;

    private TextView textViewResultadoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_busqueda_activity_layout);

        btnAtras = (ImageView) findViewById(R.id.btnAtras);

        //Establezco el nombre de la categoría seleccionada por el usuario
        textViewResultadoBusqueda = findViewById(R.id.textViewResultadoBusqueda);

        //Con esto, guardo en el String la categoría elegida por el usuario al hacer clic en el oncluckCategoría de ExplorarCategoriasActivity
        Bundle bundle = this.getIntent().getExtras();
        String categoria = bundle.getString("categoria");


        textViewResultadoBusqueda.setText(categoria);//muestro en el textview el nombre de la categoría correspondiente

        db = FirebaseFirestore.getInstance();

        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        db.collection("Recetas").whereEqualTo("categoria", categoria)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Receta receta = document.toObject(Receta.class);//llamo a Receta, tomo el documento que corresponde a Receta y obtengo el valor

                            arraylistReceta.add(receta);
                        } else {
                            Log.d(TAG, "No existe el documento.");
                        }
                    }
                    adapter.notifyDataSetChanged(); //se muestran los cambios en tiempo real
                }//fin if
            }
        });

        recyclerView = findViewById(R.id.recyclerRecetas);
        linearLayoutManager = new LinearLayoutManager(this);

        //conectar recyclerView con el LinearLayoutManager para la orientación
        recyclerView.setLayoutManager(linearLayoutManager);
        arraylistReceta = new ArrayList<>();
        adapter = new RecetaAdapter(this, arraylistReceta);
        recyclerView.setAdapter(adapter);

    }//fin onCreate



}//fin clase
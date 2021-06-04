package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gustumi.R;
import com.example.gustumi.adapters.AprendeAdapter;
import com.example.gustumi.model.Aprende;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AprendeActivity extends AppCompatActivity {

    private static final String TAG = "AprendeActivity";

    RecyclerView recyclerViewAprende;
    ArrayList<Aprende> arrayListAprende;


    FirebaseFirestore db;

    AprendeAdapter aprendeAdapter;

    LinearLayoutManager linearLayoutManager;

    ImageView btnAtras;

    SearchView searchAprende;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aprende_activity_layout);

        db = FirebaseFirestore.getInstance();

        btnAtras = (ImageView) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchAprende = findViewById(R.id.searchAprende);

        recyclerViewAprende = findViewById(R.id.recyclerAprende);
        recyclerViewAprende.setHasFixedSize(true);
        recyclerViewAprende.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAprende.addItemDecoration(decoration);
        arrayListAprende = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);

        //conectar recyclerView con el LinearLayoutManager para la orientación
        recyclerViewAprende.setLayoutManager(linearLayoutManager);

        aprendeAdapter = new AprendeAdapter(this, arrayListAprende);
        recyclerViewAprende.setAdapter(aprendeAdapter);


        db.collection("Aprende").orderBy("titulo")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Aprende aprende = document.toObject(Aprende.class);//llamo a Aprende, tomo el documento que corresponde a Aprende y obtengo el valor
                            arrayListAprende.add(aprende);
                        } else {
                            Log.d(TAG, "No existe el documento.");
                        }
                    }
                    aprendeAdapter.notifyDataSetChanged(); //se muestran los cambios en tiempo real
                }//fin if
            }
        });


        //searchview: permite al usuario introducir en el buscador la palabra para que sea más cómodo encontrar el ítem que busca
        searchAprende.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String nuevoTexto) {//si es texto del buscador cambia
                buscar(nuevoTexto);//llamo al método buscar y le paso el texto introducido en el searchview
                return true;
            }
        });


    }//fin onCreate


    /*Método buscar*/
    private void buscar(String nuevoTexto) {
        ArrayList<Aprende> miLista = new ArrayList<>(); //array de Aprende

        for (Aprende objAprende : arrayListAprende) {//bucle for de Aprende, en el que guardo la información en objAprende de la lista creada (arrayListAprende)
            if (objAprende.getTitulo().toLowerCase().contains(nuevoTexto.toLowerCase())) { //si objAprende, cojo el título y lo paso a minúsculas igual que el valor que haya introducido el usuario
                miLista.add(objAprende);//añado a miLista el objAprende
            }

        }//fin for

        AprendeAdapter adapter = new AprendeAdapter(this, miLista);//le paso al adapter miLista de aprende
        recyclerViewAprende.setAdapter(adapter);//le paso el adaptador al recyclerView

    }//fin método buscar

}//fin clase AprendeActivity
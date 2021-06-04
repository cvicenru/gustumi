package com.example.gustumi.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustumi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private static final String TAG = "PerfilUsuarioActivity";


    private TextView textViewNombreUsuario, textViewcorreoUsuario;

    private Button btnFavoritos, btnCerrarSesion;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    ImageView btnAtras;

    private String idUsuario;//almacenaré el id del usuario

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario_activity);

        btnAtras = (ImageView) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {//onClick btn atrás
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(PerfilUsuarioActivity.this);

        //Establezco el nombre de usuario y el correo
        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario);
        textViewcorreoUsuario = findViewById(R.id.textViewcorreoUsuario);

        btnFavoritos = findViewById(R.id.btnFavoritos);

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        //Instancia de Firebase
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//getCurrentUser obtiene el usuario con sesión activa. Si no hay un usuario que haya accedido, getCurrentUser muestra un valor nulo.

        db = FirebaseFirestore.getInstance();
        idUsuario = mAuth.getCurrentUser().getUid();//Obtengo el id del usuario


        //Para recuperar los datos de la colección Usuarios:
        final DocumentReference documentReference = db.collection("Usuarios").document(idUsuario);//Accedo a la colección Usuarios y le paso el id del usuario
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {//addSnapshotListener es en tiempo real. Si cambiamos el nombre del usuario se modificará a tiempo real en la app.
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {

                            //Accedo a los campos de la colección
                            textViewNombreUsuario.setText(documentSnapshot.getString("nombreUsuario"));

                        }//fin onEvent()
                    }
                }
        );

        textViewcorreoUsuario.setText(usuarioFirebase.getEmail());//Obtener el correo del usuario para mostrarlo en el Perfil de usuario


        //Botón Favoritos
        btnFavoritos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Envio al usuario al activity Favoritos
                Intent intent = new Intent(PerfilUsuarioActivity.this, FavoritosActivity.class);
                startActivity(intent);
            }
        });




        //Botón Cerrar Sesión
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();//Cierra sesión

                //Envio al usuario a Iniciar Sesión
                Intent intent = new Intent(PerfilUsuarioActivity.this, IniciarSesionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Se ha cerrado sesión correctamente.", Toast.LENGTH_LONG).show();//toast con mensaje de registro correcto
                progressDialog.dismiss();//oculto el progresDialog
            }
        });
    }//Fin onCreate()
}//Fin clase PerfilUsuarioActivity
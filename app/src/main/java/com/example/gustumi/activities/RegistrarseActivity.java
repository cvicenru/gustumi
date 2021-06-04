package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustumi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    //Instancias variables
    TextView textViewTienesCuenta;
    Button btnRegistrarse;
    EditText inputNombreUsuario, inputCorreo, inputContrasena, inputConfirmarContrasena;
    private ProgressDialog progressDialog;

    //Firebase
    FirebaseAuth mAuth;//Autenticación
    FirebaseFirestore db; //Cloud Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse_activity_layout);

        inputNombreUsuario = findViewById(R.id.inputNombreUsuario);
        inputCorreo = findViewById(R.id.inputCorreo);
        inputContrasena = findViewById(R.id.inputContrasena);
        inputConfirmarContrasena = findViewById(R.id.inputConfirmarContrasena);

        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        textViewTienesCuenta = findViewById(R.id.textViewTienesCuenta);

        /*Cuando haga clic en el botón REGISTRAR nos llevará al método verificarDatos()*/
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDatos();
            }
        });

        //Clic en ¿Tienes cuenta?
        textViewTienesCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrarseActivity.this, IniciarSesionActivity.class));
            }
        });

        //Inicializo el progresDialog
        progressDialog = new ProgressDialog(RegistrarseActivity.this);

        //Inicializo Firebase
        mAuth = FirebaseAuth.getInstance();

        //Inicializo Firestore
        db = FirebaseFirestore.getInstance();

    }//fin onCreate()

    /*VERIFICAR DATOS*/
    public void verificarDatos() {
        final String userName = inputNombreUsuario.getText().toString();
        final String email = inputCorreo.getText().toString();
        String password = inputContrasena.getText().toString();
        String confirmPass = inputConfirmarContrasena.getText().toString();

        if (userName.isEmpty() || userName.length() < 5) {//si el nombre de usuario está vacío o es menor de 5 caracteres no es válido
            mostrarErrores(inputNombreUsuario, "El nombre de usuario debe de contener, al menos, 5 caracteres.");
        } else if (email.isEmpty() || !email.contains("@")) {//si el correo está vacío o no contiene @ es email no válido
            mostrarErrores(inputCorreo, "El correo introducido no es válido.");
        } else if (password.isEmpty() || password.length() < 7) {//si la contraseña está vacía y es menor a 7 caracteres no es válida
            mostrarErrores(inputContrasena, "Contraseña no válida, mínimo 7 caracteres.");
        } else if (confirmPass.isEmpty() || !confirmPass.equals(password)) {//si la confirmación de contraseña está vacía y no es igual que la contraseña no es válida
            mostrarErrores(inputConfirmarContrasena, "Revise las contraseñas. Deben de coincidir.");

        } else {//Cuando pasamos al else es porque la validación de los datos es correcta

            //Mostrar el progresDialog
            progressDialog.setTitle("Crear Cuenta");//Título del progresDialog
            progressDialog.setMessage("Registrando usuario...");//mensaje
            progressDialog.setCanceledOnTouchOutside(false);//por si se pulsa que no se cancele y se siga mostrando
            progressDialog.show();//muestro el progresDialog

            //Registrar el usuario
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//Le paso el email y la contraseña. Para recibir la respuesta del servidor lo hago con addOnCompleteListener()
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //validación
                    if (task.isSuccessful()) {//si es correcto
                        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
                        String userId = usuarioFirebase.getUid();
                        DocumentReference documentReference = db.collection("Usuarios").document(userId);//creo la colección Usuarios (en caso de que no exista). si existe, solamente agregará los datos. Al documento que se crea dentro de la colección Usuarios le paso el ID del usuario

                        //Necesito mandarlo en un objeto de datos al documentReference con el método set. Para ello, creo un HashMap con clave String y valor Object
                        Map<String, Object> datosUsuario = new HashMap<>();

                        datosUsuario.put("nombreUsuario", userName);//regreso al objeto datosUsuario y le pongo los datos
                        datosUsuario.put("correo", email);//regreso al objeto datosUsuario y le pongo los datos
                        documentReference.set(datosUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {//al documentReference le paso el objeto datosUsuario y le añado un evento
                            @Override
                            public void onSuccess(Void aVoid) {//si es correcto
                                Log.d("Registro", "Usuario creado.");//Muestro un Log

                                progressDialog.dismiss();//oculto el progresDialog

                                //Redireccionar al usuario al IniciarSesionActivity para que inicie sesión
                                Intent intent = new Intent(RegistrarseActivity.this, IniciarSesionActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Te has registrado correctamente", Toast.LENGTH_LONG).show();//toast con mensaje de registro correcto
                            }
                        }).addOnFailureListener(new OnFailureListener() {//evento en caso de que falle
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.d("Registro", "Error al crear.");//Muestro un Log
                            }
                        });


                    } else {//si no es correcto muestro un Toast
                        Toast.makeText(getApplicationContext(), "Error al registrarse. El nombre de usuario o el email ya existen.", Toast.LENGTH_LONG).show();//le paso el contexto, el mensaje, duración y muestro el toast con show()
                        progressDialog.dismiss();//oculto el progresDialog
                    }
                }
            });//fin addOnCompleteListener


        }

    }

    /*Este método recibe el input donde queremos establecer el error y el mensaje (String)*/
    private void mostrarErrores(EditText input, String s) {
        input.setError(s);//le pasamos el String (el mensaje)
        input.requestFocus();//para que el mensaje se ponga al lado
    }

}
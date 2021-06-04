package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustumi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {


    TextView textViewRegistrarse, olvidasteContrasena;
    EditText inputCorreo, inputContrasena;
    Button btnIniciarSesion;

    //Firebase Autenticación
    FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.iniciar_sesion_activity_layout);

        inputCorreo = findViewById(R.id.inputCorreo);
        inputContrasena = findViewById(R.id.inputContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        textViewRegistrarse = findViewById(R.id.textViewRegistrarse);
        olvidasteContrasena = findViewById(R.id.textViewOlvidarContrasena);

        /*Cuando haga clic en Registrarse nos llevará a RegistrarseActivity*/
        textViewRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IniciarSesionActivity.this, RegistrarseActivity.class));
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDatos();
            }
        });

        progressDialog = new ProgressDialog(IniciarSesionActivity.this);

        //Inicializo Firebase
        mAuth = FirebaseAuth.getInstance();


        //Olvidar contraseña
        olvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, OlvidarContrasenaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//FIN onCreate

    /*-------------------MÉTODOS-------------------*/

    /*VERIFICAR DATOS*/
    public void verificarDatos() {
        String email = inputCorreo.getText().toString();
        String password = inputContrasena.getText().toString();
        if (email.isEmpty() || !email.contains("@")) {//si el correo está vacío o no contiene @ es email no válido
            mostrarErrores(inputCorreo, "El correo introducido no es válido.");
        } else if (password.isEmpty() || password.length() < 7) {//si la contraseña está vacía y es menor a 7 caracteres no es válida
            mostrarErrores(inputContrasena, "Contraseña no válida, mínimo 7 caracteres.");

        } else {//Cuando pasamos al else es porque la validación de los datos es correcta

            //Mostrar el progresDialog
            progressDialog.setTitle("Iniciar Sesión");//Título del progresDialog
            progressDialog.setMessage("Iniciando sesión...");//mensaje
            progressDialog.setCanceledOnTouchOutside(false);//por si se pulsa que no se cancele y se siga mostrando
            progressDialog.show();//muestro el progresDialog

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {//Le paso el email y la contraseña. Para recibir la respuesta del servidor lo hago con addOnCompleteListener()
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Validación
                    if (task.isSuccessful()) {//si es correcto
                        progressDialog.dismiss();//oculto el progresDialog

                        //redireccionar al usuario. Le paso el contexto: estoy en IniciarSesionActivity y lo envío a MainActivity
                        Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {//si no es correcto
                        Toast.makeText(getApplicationContext(), "No has podido iniciar sesión. Comprueba correo/contraseña.", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();//oculto el progresDialog
                    }
                }
            });
        }
    }//Fin verificarDatos()


    /*MOSTRAR ERRORES*/
    private void mostrarErrores(EditText editText, String string) {
        editText.setError(string);//le pasamos el String (el mensaje)
        editText.requestFocus();//para que el mensaje se ponga al lado
    }//fin mostrarErrores()


    //Método para verificar que el usuario existe
    @Override
    protected void onStart() {

        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();//Obtener una instancia del usuario de Firebase

        if (usuarioFirebase != null) {//Si es distinto de null quiere decir que el usuario está logueado

            //redireccionar al usuario. Le paso el contexto: estoy en IniciarSesionActivity y lo envío a MainActivity
            Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        super.onStart();
    }//fin onStart

}//fin clase
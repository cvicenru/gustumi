package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gustumi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarContrasenaActivity extends AppCompatActivity {

    Button btnRestablecerContrasena;
    EditText inputCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.olvidar_contrasena_activity_layout);

        btnRestablecerContrasena = findViewById(R.id.btnRestablecerContrasena);
        inputCorreo = findViewById(R.id.inputCorreo);

        /*Al hacer clic en el botón Restablecer contraseña*/
        btnRestablecerContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }// fin onCreate()


    /*----------MÉTODOS----------*/

    /*Valida que se haya introducido un email*/
    public void validar() {
        String email = inputCorreo.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {//si el correo está vacío o no sigue los patrones de email de android
            inputCorreo.setError("El correo introducido no es válido.");//muestra el error
            return; //para que no realice ninguna función
        }

        //Si es un correo válido
        enviarEmail(email);

    }//fin validar


    /*Método para volver a la Actividad anterior en la que te encuentras en el momento*/
    /*Cuando el usuario hace clic en el botón predeterminado de su móvil para volver a la pantalla anterior la app le llevará al IniciarSesionActivity.*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(OlvidarContrasenaActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
        finish();
    }//fin onBackPressed

/*Enviar email al usuario para restablecer contraseña*/
    public void enviarEmail(String email) { //recibe el email como parámetro
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String direccionEmail = email;
        mAuth.sendPasswordResetEmail(direccionEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {//Si el correo es correcto
                            Toast.makeText(OlvidarContrasenaActivity.this, "Correo enviado. Revise su bandeja de entrada.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OlvidarContrasenaActivity.this, IniciarSesionActivity.class);//Cuando se ha enviado el correo para poder restablecer la contraseña nos envía a IniciarSesionActivity para poder poner el correo y la nueva contraseña
                            startActivity(intent);
                            finish();
                        }else{//Cuando no se ha enviado correctamente el correo. Solamente entrará en el else cuando el correo no sea válido o no exista
                            Toast.makeText(OlvidarContrasenaActivity.this, "El correo introducido no es válido.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }//fin sendEmail()
}
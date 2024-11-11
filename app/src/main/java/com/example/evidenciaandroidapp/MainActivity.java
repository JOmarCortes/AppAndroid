package com.example.evidenciaandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button singIn;
    TextView singUp;
    Spinner roleSpinner;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        singIn = findViewById(R.id.sing_in);
        singUp = findViewById(R.id.sing_up);
        roleSpinner = findViewById(R.id.role_spinner);

        // Configurar el Spinner con el array de roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());
                String role = roleSpinner.getSelectedItem().toString(); // Obtener el rol seleccionado

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Ingrese email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Ingreso correcto como " + role, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Ingreso fallido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

package com.example.dicerollapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dicerollapp.UI_elements.Activities.SettingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
public class PasswordResetActivity extends AppCompatActivity{
    private EditText email;
    private Button reset;
    private ImageButton buttonPrevious;
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_password_reset);

        email = findViewById(R.id.editEmail);
        reset = findViewById(R.id.buttonReset);
        buttonPrevious = findViewById(R.id.button_previous);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPasswordResetEmail(email);
                Toast.makeText(PasswordResetActivity.this, "Email Send", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PasswordResetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the main page (MainActivity)
                Intent intent = new Intent(PasswordResetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });

    }

    private void sendPasswordResetEmail(EditText editEmail){
        String email = editEmail.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Password reset email sent successfully
                            Toast.makeText(getApplicationContext(), "Password reset email sent. Check your inbox.", Toast.LENGTH_SHORT).show();
                        } else {
                            // An error occurred
                            Exception e = task.getException();
                            Log.e("TAG", "Error sending password reset email", e);
                            Toast.makeText(getApplicationContext(), "Error sending password reset email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

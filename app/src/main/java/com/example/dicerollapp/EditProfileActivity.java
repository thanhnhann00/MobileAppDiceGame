package com.example.dicerollapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dicerollapp.UI_elements.Activities.SettingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private String name,email,phone;
    private EditText editTextName,editTextEmail,editTextPhone;
    private Button saveBtn;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        saveBtn = findViewById(R.id.buttonSave);
        editTextName = findViewById(R.id.editTName);
        editTextEmail = findViewById(R.id.editTEmail);
        editTextPhone = findViewById(R.id.editTPhone);

        Intent data = getIntent();
        name = data.getStringExtra("Name");
        email = data.getStringExtra("Email");
        phone = data.getStringExtra("Phone Number");

        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty() || editTextPhone.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProfileActivity.this, "Fields are empty.",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String sEmail = editTextEmail.getText().toString();

                user.verifyBeforeUpdateEmail(sEmail,null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Email",sEmail);
                        edited.put("Name",editTextName.getText().toString());
                        edited.put("Phone Number",editTextPhone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditProfileActivity.this, "Email changed!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditProfileActivity.this, SettingsActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfileActivity.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this,"Update Fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
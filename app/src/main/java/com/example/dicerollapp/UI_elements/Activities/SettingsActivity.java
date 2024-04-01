package com.example.dicerollapp.UI_elements.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dicerollapp.CreateAccountActivity;
import com.example.dicerollapp.DiceActivity;
import com.example.dicerollapp.EditProfileActivity;
import com.example.dicerollapp.LoginActivity;
import com.example.dicerollapp.MainActivity;
import com.example.dicerollapp.MainMenuActivity;
import com.example.dicerollapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Set;


public class SettingsActivity extends AppCompatActivity {
    private Button deleteAccount,changeProfile,resetPassword;
    private ImageButton previous;

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    final private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    final private FirebaseUser currentUser = fAuth.getCurrentUser();
    final private String userID = currentUser.getUid();
    final DocumentReference DR = fStore.collection("users").document(userID);
    private TextView name, email, phoneNum;


    //private String currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);
        phoneNum = findViewById(R.id.tvPhoneNum);

        deleteAccount = findViewById(R.id.buttonDeleteAccount);
        changeProfile = findViewById(R.id.buttonChangeProfile);
        resetPassword = findViewById(R.id.buttonResetPassword);
        previous = findViewById(R.id.buttonPrevious);



        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    name.setText(documentSnapshot.getString("Name"));
                    email.setText(documentSnapshot.getString("Email"));
                    phoneNum.setText(documentSnapshot.getString("Phone Number"));
            }
        });



        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditProfileActivity.class);
                i.putExtra("Name",name.getText().toString());
                i.putExtra("Email",email.getText().toString());
                i.putExtra("Phone Number",phoneNum.getText().toString());

                startActivity(i);
                finish();
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(userID);
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the main page (MainActivity)
                Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }


    public void deleteAccount(String id) {
        String nt = id;
        FirebaseFirestore fsStore = FirebaseFirestore.getInstance();
        currentUser.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // User deleted successfully from Firebase Authentication
                        // Now, delete associated data in Firebase Firestore
                        DocumentReference DR = fsStore.collection("users").document(nt);
                        DR.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document deleted successfully from Firebase Firestore
                                        Toast.makeText(SettingsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SettingsActivity.this, "Delete Fail", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SettingsActivity.this,"Delete Fail",Toast.LENGTH_SHORT).show();
                    }
                });
    }




}


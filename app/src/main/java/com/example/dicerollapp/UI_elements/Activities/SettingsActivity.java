package com.example.dicerollapp.UI_elements.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dicerollapp.DiceActivity;
import com.example.dicerollapp.LoginActivity;
import com.example.dicerollapp.MainActivity;
import com.example.dicerollapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class SettingsActivity extends AppCompatActivity {
    private Button deleteAccount;

    //private String currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        deleteAccount = findViewById(R.id.buttonDeleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
                Toast.makeText(SettingsActivity.this, "Account Deleted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));

            }
        });
    }

    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("User Data");
    private void deleteAccount() {
        usersRef.orderByChild("email").equalTo(LoginActivity.currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String userKey = childSnapshot.getKey();
                    // Delete user node
                    usersRef.child(userKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // User deleted successfully
                            Log.d("MainActivity", "User deleted successfully");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to delete user
                            Log.e("MainActivity", "Error deleting user", e);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling
                Log.e("MainActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }
}


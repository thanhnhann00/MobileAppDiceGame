package com.example.dicerollapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText email;
    private EditText password,name,phone;
    private Button register;
    private FirebaseAuth auth;
    private ImageButton buttonPrevious;
    private DatabaseReference mDatabase;
    private FirebaseFirestore fstore;
     String sName,sEmail,sPass,sPhone,userID;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_create_account);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editPhone);
        password = findViewById(R.id.editTextPassword);
        register = findViewById(R.id.buttonRegister);
        buttonPrevious = findViewById(R.id.button_previous);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("User Data");
        fstore = FirebaseFirestore.getInstance();

        register.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sEmail = email.getText().toString().trim();
                sPass = password.getText().toString().trim();
                sName = name.getText().toString().trim();
                sPhone =phone.getText().toString().trim();

                if (TextUtils.isEmpty(sEmail) || TextUtils.isEmpty(sPass)) {
                    Toast.makeText(CreateAccountActivity.this, "Empty credentails!", Toast.LENGTH_SHORT).show();
                } else if (sPass.length() < 6) {
                    Toast.makeText(CreateAccountActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(sEmail, sPass);
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the main page (MainActivity)
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //--------------
                    //add to database
                    //UserData u = new UserData(email,password,"email");
                    //mDatabase.push().setValue(u);
                    //---------------
                    userID = auth.getCurrentUser().getUid();
                    DocumentReference DR = fstore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Name",sName);
                    user.put("Phone Number",sPhone);
                    user.put("Email",sEmail);
                    user.put("Password",sPass);
                    DR.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                        }
                    });
                    //---------------
                    Toast.makeText(CreateAccountActivity.this, "Creating account successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(CreateAccountActivity.this,"Unsuccessful creating account! ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

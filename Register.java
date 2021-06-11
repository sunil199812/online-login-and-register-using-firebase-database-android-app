package com.hillking.laundry;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText fname,memail,pass,mphone;
    Button reg;
    TextView ctext;
    FirebaseAuth fAuth;
    ProgressBar pbar;
    //FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname   = findViewById(R.id.fname);
        memail      = findViewById(R.id.memail);
        pass   = findViewById(R.id.pass);
        mphone  = findViewById(R.id.mphone);
        reg= findViewById(R.id.mlog);
        ctext  = findViewById(R.id.ctext);

        fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();
        pbar = findViewById(R.id.pbar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                final String fullName = fname.getText().toString();
                final String phone    = mphone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pass.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    pass.setError("Password Must be >= 6 Characters");
                    return;
                }

                pbar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        ctext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}

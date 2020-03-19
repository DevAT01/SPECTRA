package com.example.spectra;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SignUpActivity extends AppCompatActivity {
    EditText sEmail, sPass, sConfirmPass, Name,SID;
    Button SignUp;
    Spinner spinner;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private void addUserInformation(){
        String name = Name.getText().toString().trim();
        String SIDm = SID.toString().trim();
        String Branch = spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            UserInformation userInformation = new UserInformation(name, SIDm, Branch);
            FirebaseUser user = mAuth.getCurrentUser();
            databaseReference.child(user.getUid()).setValue(userInformation);
            Toast.makeText(this, "Information saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "You should enter the details", Toast.LENGTH_SHORT).show();
        }
    };


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Name = findViewById(R.id.name);
        sEmail = findViewById(R.id.sgemail);
        sPass = findViewById(R.id.sgpass);
        sConfirmPass = findViewById(R.id.sgconfirmpass);
        SignUp  = findViewById(R.id.SignUp);
        spinner = findViewById(R.id.BranchSpinner);
        mAuth = FirebaseAuth.getInstance();
        SID = findViewById(R.id.sid);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(SignUpActivity.this, HomePage.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };



        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = sEmail.getText().toString();
                final String password = sPass.getText().toString();
                final String cpassword = sConfirmPass.getText().toString();

                if(email.isEmpty()){
                    sEmail.setError("Please enter the email ID");
                    sEmail.requestFocus();
                }
                else if(password.isEmpty()){
                    sPass.setError("Please enter the Password");
                    sPass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else if(password.equals(cpassword)&&(!email.isEmpty())) {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp Error", Toast.LENGTH_SHORT).show();
                            } else {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                current_user_db.setValue(true);
                                addUserInformation();
                                startActivity(new Intent(SignUpActivity.this, HomePage.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}



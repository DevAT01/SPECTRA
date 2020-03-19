package com.example.spectra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SLHOME extends AppCompatActivity {
    public Button SignUp;
    public Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_l_h_o_m_e);
        SignUp = findViewById(R.id.btnSignUp);
        Login = findViewById(R.id.btnLogin);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SLHOME.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SLHOME.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

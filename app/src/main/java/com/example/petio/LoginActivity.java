package com.example.petio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText emailText,passText;
    private String  email,pass;
    private FirebaseAuth mAuth; // firebase üzerinden  kullanıcı girişi sağlayıcısı
    private CardView loginBtn;
    private TextView signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText=findViewById(R.id.email);
        passText= findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        signUpButton = findViewById(R.id.loginSignup);

        mAuth=FirebaseAuth.getInstance();

        openHomePage();

        loginBtn.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    public void openHomePage() {// kullanıcı oturumu kapatmadıysa uygulama home ekranından başlar.
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:
                email=emailText.getText().toString();
                pass=passText.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
                    mAuth.signInWithEmailAndPassword(email,pass) // mauth üzerinden
                            // email ve şifre kontrollü kullanici girişi
                            .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{

                    Toast.makeText(LoginActivity.this,"Boş alan bırakmayınız.",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.loginSignup:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }
}
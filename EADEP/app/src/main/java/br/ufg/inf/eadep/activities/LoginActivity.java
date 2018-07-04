package br.ufg.inf.eadep.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.model.Aluno;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private FirebaseAuth auth;
    private Aluno aluno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.email_edit);
        etPassword = (EditText) findViewById(R.id.pswd_edit);
        btnLogin = (Button) findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")){

                    aluno = new Aluno();
                    aluno.setEmail(etEmail.getText().toString());
                    aluno.setSenha(etPassword.getText().toString());

                    validarLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validarLogin(){
        auth = FirebaseConfig.getFirebaseAuth();
        auth.signInWithEmailAndPassword(aluno.getEmail(),
                aluno.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent it = new Intent(LoginActivity.this, DisciplinasActivity.class);
                    startActivity(it);

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("email", etEmail.getText().toString());
                    editor.commit();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro no login", Toast.LENGTH_SHORT);
                }
            }
        });

    }
}

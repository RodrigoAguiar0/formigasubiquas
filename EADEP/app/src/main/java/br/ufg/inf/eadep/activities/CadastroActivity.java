package br.ufg.inf.eadep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.ufg.inf.eadep.DAO.AlunoDAO;
import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.helper.Base64Custom;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Aluno;

public class CadastroActivity extends AppCompatActivity{

    private EditText etNome;
    private EditText etSenha;
    private EditText etSobrenome;
    private EditText etEmail;
    private EditText etTelefone;
    private EditText etAno;
    private EditText etMatricula;
    private Button insertBtn;
    private FirebaseAuth auth;

    private Aluno aluno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        etNome = (EditText)findViewById(R.id.name_edit_student);
        etSobrenome = (EditText)findViewById(R.id.lastname_edit_student);
        etSenha = (EditText)findViewById(R.id.password_edit_student);
        etEmail = (EditText)findViewById(R.id.email_edit_student);
        etTelefone = (EditText)findViewById(R.id.phone_edit_student);
        etAno = (EditText)findViewById(R.id.year_edit_student);
        etMatricula = (EditText)findViewById(R.id.registration_number_edit_student);
        insertBtn = (Button)findViewById(R.id.insert_btn);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aluno = new Aluno();

                aluno.setNomeCompleto(etNome.getText().toString() + etSobrenome.getText().toString());
                aluno.setEmail(etEmail.getText().toString());
                aluno.setSenha(etSenha.getText().toString());
                aluno.setAno(Integer.parseInt(etAno.getText().toString()));
                aluno.setMatricula(Integer.parseInt(etMatricula.getText().toString()));
                aluno.setTelefone(etTelefone.getText().toString());

                addAluno();
            }
        });
    }

    public void addAluno(){
        auth = FirebaseConfig.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(aluno.getEmail(),
                aluno.getSenha()).addOnCompleteListener(CadastroActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            "Aluno cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String emailAluno = Base64Custom.codeBase64(aluno.getEmail());
                    FirebaseUser firebaseUser = task.getResult().getUser();

                    aluno.addAluno();

                    Preferences preferences = new Preferences(CadastroActivity.this);
                    preferences.saveUserEmail(aluno.getEmail());
                    goToLoginAluno();
                } else {
                    String exception;

                    try{
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e1){
                        exception = "Insira uma senha mais segura (mínimo 8 caracteres, letras e/ou números)";
                    } catch(FirebaseAuthInvalidCredentialsException e2){
                        exception = "O e-mail informado é inválido, digite um e-mail válido (email@example.com)";
                    } catch(FirebaseAuthUserCollisionException e3) {
                        exception = "Este e-mail já está cadastrado no sistema";
                    } catch (Exception e4) {
                        exception = "Erro ao efetuar o cadastro";
                        e4.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, "Erro: " + exception, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToLoginAluno(){
        Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}

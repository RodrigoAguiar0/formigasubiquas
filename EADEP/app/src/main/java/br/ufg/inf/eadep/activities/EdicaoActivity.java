package br.ufg.inf.eadep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.helper.Base64Custom;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Aluno;

/**
 * Created by rodri on 7/6/2018.
 */

public class EdicaoActivity extends AppCompatActivity {
    private EditText reissueNome;
    private EditText reissueSenha;
    private EditText reissueSobrenome;
    private EditText reissueEmail;
    private EditText reissueTelefone;
    private EditText reissueAno;
    private EditText reissueMatricula;
    private Button logoutBtn;
    private Button reissueBtn;
    private FirebaseAuth auth;
    private Preferences preferences;
    Base64Custom base64Custom;
    DatabaseReference firebase;
    private ValueEventListener valueEventListenerAlunos;

    private Aluno aluno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_cadastro);

        aluno = new Aluno();

        preferences = new Preferences(this);
        auth = FirebaseConfig.getFirebaseAuth();
        reissueNome = findViewById(R.id.name_reissue_student);
        reissueSenha = findViewById(R.id.password_reissue_student);
        reissueSobrenome = findViewById(R.id.lastname_reissue_student);
        reissueTelefone = findViewById(R.id.phone_reissue_student);
        reissueAno = findViewById(R.id.year_reissue_student);
        reissueMatricula = findViewById(R.id.registration_number_reissue_student);
        reissueBtn = findViewById(R.id.reissue_btn);
        logoutBtn = findViewById(R.id.logout_btn);

        String id = base64Custom.codeBase64(preferences.getEmail());

        firebase = FirebaseConfig.getFirebase().child("aluno").child(id);

        valueEventListenerAlunos = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                aluno = dataSnapshot.getValue(Aluno.class);
                reissueNome.setText(aluno.getNomeCompleto().split(" ")[0]);
                reissueSobrenome.setText(aluno.getNomeCompleto().split(" ")[1]);
                reissueSenha.setText(aluno.getSenha());
                reissueTelefone.setText(aluno.getTelefone());
                reissueAno.setText(aluno.getAno());
                reissueMatricula.setText(aluno.getMatricula());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        reissueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reissueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aluno.setNomeCompleto(reissueNome.getText().toString() + reissueSobrenome.getText().toString());
                aluno.setSenha(reissueSenha.getText().toString());
                aluno.setAno(Integer.parseInt(reissueAno.getText().toString()));
                aluno.setMatricula(Integer.parseInt(reissueMatricula.getText().toString()));
                aluno.setTelefone(reissueTelefone.getText().toString());
                aluno.setEmail(preferences.getEmail());

                Map<String, Object> alunoMap = aluno.toMap();

                firebase.updateChildren(alunoMap).addOnCompleteListener(EdicaoActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EdicaoActivity.this,
                                    "Mudanças salvas com sucesso!", Toast.LENGTH_LONG).show();

                            Intent it = new Intent(EdicaoActivity.this, DisciplinasActivity.class);
                            startActivity(it);
                        } else {
                            String exception;

                            try{
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e1){
                                exception = "Insira uma senha mais segura (mínimo 8 caracteres, letras e/ou números)";
                            } catch (Exception e4) {
                                exception = e4.getMessage();
                            }

                            Toast.makeText(EdicaoActivity.this, "Erro: " + exception, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EdicaoActivity.this, LoginActivity.class);
                Preferences sharedPref = new Preferences(EdicaoActivity.this);
                sharedPref.saveUserEmail("");
                startActivity(it);
            }
        });
    }
}

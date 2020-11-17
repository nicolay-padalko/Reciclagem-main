package com.example.reciclagem;

//Importações
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


//Classe Login
/*Esta classe realiza a autenticação do usuário no aplicativo*/
public class Login extends AppCompatActivity {

    //Atributos
    private Button Entrar;
    private TextView mBtnSignIn;
    private EditText mEtEmail, mEtPassword;

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Instância de atributos para manipulação dos campos da classe
        mEtEmail = (EditText) findViewById(R.id.loginText);
        mEtPassword = (EditText) findViewById(R.id.senhaText);
        mBtnSignIn = findViewById(R.id.btEntrar);

        //Função para o botão Entrar
        /*Esta função verifica se o email e senha digitados conferem com o banco e loga o usuário no app*/
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    startActivity(new Intent(Login.this, Menu.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Usuario ou senha não confere, tente novamente",Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });
    }



    public void startCadastroActivity(View view) {

        Intent cadActivity = new Intent(this, Cadastro.class);
        startActivity(cadActivity);
    }
}//Fim da classe
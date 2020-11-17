package com.example.reciclagem;

//Importações
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Classe MainActivity
/*Esta classe auxilia a parte de login*/
public class MainActivity extends AppCompatActivity {

    //Atributos
    TextView textViewEmail, Logout;
    private static final String TAG = "MyActivity";

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instância de atributos
        textViewEmail = findViewById(R.id.mEmailText);
        Logout = findViewById(R.id.btLogout);

        //Instância do firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);

        //Função para o botão "logout"
        //Chamada a classe Login
        Logout.setOnClickListener(new View.OnClickListener() {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                MainActivity.this.finish();
            }

        });
    }

    //Chamada ao firebase
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            //Retorno do usuário
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            //Caso o usuário retornado seja null, inicia-se a classe Login
            if (firebaseUser == null) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }


            //Caso haja um usuário, coloca-se o email no campo
            if (firebaseUser != null) {
                textViewEmail.setText(firebaseUser.getEmail());
            }
        }
    };

    //Função para chamada da classe Menu pelo botão "entrar"
    public void startMenuActivity(View view) {

        Intent menuActivity = new Intent(this, Menu.class);
        startActivity(menuActivity);
    }



}//Fim da classe

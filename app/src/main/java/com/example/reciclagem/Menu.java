package com.example.reciclagem;

//Importações
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

//Classe Menu
//Esta classe apresenta as opções do aplicativo, através dos botões
public class Menu extends AppCompatActivity {


    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    //Função para o botão "ajuda"
    //Esta função inicia a classe Ajuda
    public void startAjudaActivity(View view) {

        Intent ajudaActivity = new Intent(this, Ajuda.class);
        startActivity(ajudaActivity);
    }

    //Função para o botão "Verificar codigo"
    //Esta função inicia a classe VerifCode
    public void startVerCodActivity(View view) {

        Intent verCodActivity = new Intent(this, VerifCode.class);
        startActivity(verCodActivity);
    }

    //Função para o botão "perfil"
    //Esta função inicia a classe Perfil
    public void startPerfilActivity(View view) {

        Intent perfilActivity = new Intent(this, Perfil.class);
        startActivity(perfilActivity);
    }


    public void startLoginlActivity(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent loginActivity = new Intent(this, Login.class);
        startActivity(loginActivity);

    }

}//Fim da classe

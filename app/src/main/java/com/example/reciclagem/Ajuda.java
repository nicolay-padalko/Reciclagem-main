package com.example.reciclagem;

//Importações
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//Classe Ajuda
//Esta classe é uma tela e contém os métodos para dar funcionalidade aos botões nela presentes
public class Ajuda extends AppCompatActivity {

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
    }

    //Função  para o botão "verificar lixo"
    //Este método chama a classe VerifLixo
    public void startVerifLixoActivity(View view) {

        Intent verLixoActivity = new Intent(this, VerifLixo.class);
        startActivity(verLixoActivity);
    }

}//Fim a classe
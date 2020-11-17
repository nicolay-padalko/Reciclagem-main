package com.example.reciclagem;

//Importações
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//Classe Perfil
/*Esta classe é a responsável por recuperar os dados cadastrais do usuário, mostrá-los e, apresentar
* ainda a opção de alteração dos mesmos*/
public class Perfil extends AppCompatActivity {

    //Atributos
    private String user;
    public static String uid;

    private static final String TAG = "MyActivity";

    private FirebaseAuth mAuth;

    TextView textViewNome;
    TextView textViewCpf;
    TextView textViewTelefone;
    TextView textViewEndereco;
    TextView textViewData;
    TextView textViewEmail;
    TextView textViewCredito;

    FirebaseFirestore db;

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Instância do firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Instância de atributos
        textViewNome = findViewById(R.id.pNome);
        textViewCpf = findViewById(R.id.pCpf);
        textViewTelefone = findViewById(R.id.pTelefone);
        textViewEndereco = findViewById(R.id.pEndereco);
        textViewData = findViewById(R.id.pData);
        textViewEmail = findViewById(R.id.pEmail);
        textViewCredito = findViewById(R.id.pCredito);

        //Retorno de usuário
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Captação do id do usuário logado
        String userId = mAuth.getCurrentUser().getUid();



        /*Caso o usuário não seja nulo, este bloco recupera os dados do usuário logado, com base
        no seu id do banco e passa cada informação para os seus respectivos campos nesta classe. Caso
        haja algum problema, uma mensagem é mostrada para o usuário*/
        if(currentUser != null) {

            //Referência ao usuário, com base no id, criada
            DocumentReference docRef = db.collection("usuarios").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                            //Dados são passados para os devidos campos
                            textViewNome.setText(document.getString("nome"));
                            textViewCpf.setText(document.getString("cpf"));
                            textViewTelefone.setText(document.getString("tel"));
                            textViewEndereco.setText(document.getString("CEP"));
                            textViewData.setText(document.getString("dia"));
                            textViewEmail.setText(document.getString("email"));
                            textViewCredito.setText(document.getString("cred"));
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });



        }


    }

    //Função para o botão "alterar cadastro"
    //Inicia a classe PerfilEdit
    public void startPerfilAlteraActivity(View view) {

        Intent perfilAlteraActivity = new Intent(this, PerfilEdit.class);
        startActivity(perfilAlteraActivity);
    }
}
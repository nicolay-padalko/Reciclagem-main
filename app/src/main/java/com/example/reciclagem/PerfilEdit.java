package com.example.reciclagem;

//Importações
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reciclagem.utils.MaskEditUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//Classe PefilEdit
public class PerfilEdit extends AppCompatActivity {

    //Atributos
    private String user;
    private String uid;
    private static final String TAG = "MyActivity";
    //TextView textViewCredito;
    private Button Alterar;

    FirebaseFirestore db;
    EditText tvNome;
    EditText tvCpf;
    EditText tvData;
    EditText tvTel;
    EditText tvEnd;

    String nNome;
    String nCpf;
    String nData;
    String nTel;
    String nEnd;

    private FirebaseAuth mAuth;





    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);

        //Instância do firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String userId = mAuth.getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();

        //Instância dos atributos
        tvNome = findViewById(R.id.eNome);
        tvCpf = findViewById(R.id.edCpf);
        tvData = findViewById(R.id.eData);
        tvTel = findViewById(R.id.eTelefone);
        tvEnd = findViewById(R.id.eEndereco);

        tvCpf.addTextChangedListener(MaskEditUtil.mask(tvCpf, MaskEditUtil.FORMAT_CPF));
        tvData.addTextChangedListener(MaskEditUtil.mask(tvData, MaskEditUtil.FORMAT_DATE));
        tvTel.addTextChangedListener(MaskEditUtil.mask(tvTel, MaskEditUtil.FORMAT_FONE));
        tvEnd.addTextChangedListener(MaskEditUtil.mask(tvEnd, MaskEditUtil.FORMAT_CEP));

        Alterar = findViewById(R.id.btAtualizar);

        //Com base no id do usuário logado, os campos do formulário da classe são preenchidos com os dados
        //"antigos" do usuário, retornados do firebase. Em caso de erro, uma mensagem é exibida
        DocumentReference docRef = db.collection("usuarios").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        //Os campos são preenchidos com os dados
                        tvNome.setText(document.getString("nome"));
                        tvCpf.setText(document.getString("cpf"));
                        tvTel.setText(document.getString("tel"));
                        tvEnd.setText(document.getString("CEP"));
                        tvData.setText(document.getString("dia"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });


        //Função para o botão "atualizar"
        /*Esta função pega os dados escritos nos campos para atualização e os passa para o firebase.
        * Em caso de erro, uma mensagem é exibida*/
        Alterar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Retorno dos dados escritos nos campos do formulário
                nNome = tvNome.getText().toString();
                nCpf = tvCpf.getText().toString();
                nData = tvData.getText().toString();
                nTel = tvTel.getText().toString();
                nEnd = tvEnd.getText().toString();

                //Referência ao usuário no firebase
                DocumentReference usuarioRef = db.collection("usuarios").document(userId);

                //Este bloco atualiza os dados em seus respectivos campos no banco
                usuarioRef.update(

                        "nome", nNome,
                        "CEP", nEnd,
                        "dia", nData,
                        "cpf", nCpf,
                        "tel", nTel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });

                //Chamada a classe Perfil
                startActivity(new Intent(PerfilEdit.this, Perfil.class));

            }
        });
    }

}//Fim da classe
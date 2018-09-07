package br.edu.ifsp.scl.sdm.layoutssdm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;

    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO = "ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO";
    private final String ESTADO_NOME_TAG = "ESTADO_NOME_TAG";
    private final String ESTADO_EMAILS_TAG = "ESTADO_EMAILS_TAG";
    private final String ESTADO_TELEFONES_TAG = "ESTADO_TELEFONES_TAG";

    private EditText txtNome, txtEmail, txtTelefone;
    private Button btnLimpar, btnSalvar;

    private LinearLayout telefoneLinearLayout, emailLinearLayout;
    private ArrayList<View> telefoneArrayList, emailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        //Referencias das Views
        notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);

        txtNome = findViewById(R.id.nomeEditText);
        //txtEmail = findViewById(R.id.emailEditText);
        //txtTelefone = findViewById(R.id.telefoneEditText);

        //Mapeando componentes da interface que armazenarão views dinamicas
        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);

        //Instanciando os ArraysLists para armazenar as Views
        telefoneArrayList = new ArrayList<>();
        emailArrayList = new ArrayList<>();

        btnLimpar = findViewById(R.id.limparButton);

        //Tratando evento de check no checkbox
        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }

            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
            }
        });

    }

    public void adicionarTelefone(View view){

        if (view.getId() ==  R.id.adicionarTelefoneButton){

            LayoutInflater layoutInflater = getLayoutInflater();

            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);

        }
    }

    public void adicionarEmail(View view){

        if (view.getId() ==  R.id.adicionarEmailButton){

            LayoutInflater layoutInflater = getLayoutInflater();

            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout, null);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ESTADO_NOME_TAG, txtNome.getText().toString());
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificacoesCheckBox.isChecked());
        outState.putInt(ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        //salvar os Arrays com as Views

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            txtNome.setText(savedInstanceState.getString(ESTADO_NOME_TAG));

            notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));

            if (notificacoesCheckBox.isChecked()) {
                notificacoesRadioGroup.setVisibility(View.VISIBLE);
            } else {
                notificacoesRadioGroup.setVisibility(View.GONE);
            }

            int idRadioButtonSelecionado = savedInstanceState.getInt(ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO);
            if (idRadioButtonSelecionado != -1) {
                notificacoesRadioGroup.check(savedInstanceState.getInt(ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO));
            }

            //Recuperar os Arrays com as Views
            emailArrayList = (ArrayList<View>) savedInstanceState.get(ESTADO_EMAILS_TAG);

        }

    }

    public void limparFormulario(){

        try {
            txtNome.setText("");
            notificacoesRadioGroup.clearCheck();
            notificacoesCheckBox.setChecked(false);
            txtNome.requestFocus();
            txtEmail.setText("");
            txtTelefone.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

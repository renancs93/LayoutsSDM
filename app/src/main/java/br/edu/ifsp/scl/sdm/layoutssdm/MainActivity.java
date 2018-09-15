package br.edu.ifsp.scl.sdm.layoutssdm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements Serializable{

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;

    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO = "ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO";
    private final String ESTADO_NOME_TAG = "ESTADO_NOME_TAG";
    private final String ESTADO_EMAILS_TAG = "ESTADO_EMAILS_TAG";
    private final String ESTADO_TELEFONES_TAG = "ESTADO_TELEFONES_TAG";
    private final String ESTADO_TELEFONES_TIPO_TAG = "ESTADO_TELEFONES_TIPO_TAG";

    private EditText txtNome, txtEmail, txtTelefone;
    private Button btnLimpar, btnSalvar;

    private LinearLayout telefoneLinearLayout, emailLinearLayout;
    //private ArrayList<View> telefoneArrayList, emailArrayList;

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
        //telefoneArrayList = new ArrayList<>();
        //emailArrayList = new ArrayList<>();

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
            //telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }

    }

    public void adicionarEmail(View view){

        if (view.getId() ==  R.id.adicionarEmailButton){

            LayoutInflater layoutInflater = getLayoutInflater();

            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout, null);
            //emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }

    public void restaurarViews(int resourceView, ArrayList<String> arrayListString, ArrayList<Integer> arrayListInteger){

        if (resourceView == R.layout.novo_email_layout){

            for (String email : arrayListString){

                LayoutInflater layoutInflater = getLayoutInflater();
                //adiciona o Layout de Email
                View viewEmail = layoutInflater.inflate(resourceView, null);

                EditText editTextEmail = viewEmail.findViewById(R.id.emailEditText);
                editTextEmail.setText(email);

                emailLinearLayout.addView(viewEmail);
            }
        }
        if(resourceView == R.layout.novo_telefone_layout){

            for(int i=0 ; i<arrayListString.size() ; i++){

                LayoutInflater layoutInflater = getLayoutInflater();

                //adiciona o Layout de Email
                View viewTelefone = layoutInflater.inflate(resourceView, null);

                EditText editTextTelefone =  viewTelefone.findViewById(R.id.telefoneEditText);
                Spinner spinnerTelefone = viewTelefone.findViewById(R.id.tipoTelefoneSpinner);

                editTextTelefone.setText(arrayListString.get(i).toString());
                spinnerTelefone.setSelection(arrayListInteger.get(i));

                telefoneLinearLayout.addView(viewTelefone);
            }
        }

    }

//    public void restaurarViews(LinearLayout layout, ArrayList<View> arrayList){
//
//        try {
//
//            for (int i=0; i<arrayList.size() ; i++){
//
//                View holderLayout = arrayList.get(i);
//                if(holderLayout.getParent()!=null)
//                    ((LinearLayout)holderLayout.getParent()).removeView(holderLayout); // <- fix
//
//                layout.addView(arrayList.get(i));
//
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ESTADO_NOME_TAG, txtNome.getText().toString());
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificacoesCheckBox.isChecked());
        outState.putInt(ESTADO_NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        ArrayList<String> emailsSalvos = new ArrayList<>();
        ArrayList<String> telefonesSalvos = new ArrayList<>();
        ArrayList<Integer> tiposTelefonesSalvos = new ArrayList<>();

        //salvar os Arrays com as Views
        if(emailLinearLayout.getChildCount() > 0){

            for(int i=0 ; i<emailLinearLayout.getChildCount() ; i++){

                View view = emailLinearLayout.getChildAt(i);
                EditText editTextEmail = view.findViewById(R.id.emailEditText);
                emailsSalvos.add(editTextEmail.getText().toString());
            }
        }
        if(telefoneLinearLayout.getChildCount() > 0){

            for(int i=0 ; i<telefoneLinearLayout.getChildCount() ; i++){

                View view = telefoneLinearLayout.getChildAt(i);

                EditText editTextTelefone = view.findViewById(R.id.telefoneEditText);
                Spinner spinnerTelefoneTipo = view.findViewById(R.id.tipoTelefoneSpinner);

                telefonesSalvos.add(editTextTelefone.getText().toString());

                if (spinnerTelefoneTipo.getSelectedItem().equals("Fixo")){
                    tiposTelefonesSalvos.add(0);
                }
                else {
                    tiposTelefonesSalvos.add(1);
                }
            }

        }

        outState.putStringArrayList(ESTADO_EMAILS_TAG, emailsSalvos);
        outState.putStringArrayList(ESTADO_TELEFONES_TAG, telefonesSalvos);
        outState.putIntegerArrayList(ESTADO_TELEFONES_TIPO_TAG, tiposTelefonesSalvos);

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

            //recupera os arrays dos dados dinâmicos
            ArrayList<String> emailsSalvos = savedInstanceState.getStringArrayList(ESTADO_EMAILS_TAG);
            ArrayList<String> telefonesSalvos = savedInstanceState.getStringArrayList(ESTADO_TELEFONES_TAG);
            ArrayList<Integer> tiposTelefonesSalvos = savedInstanceState.getIntegerArrayList(ESTADO_TELEFONES_TIPO_TAG);

            //chama devidos métodos para o preenchimento das views dinâmicas
            if (emailsSalvos != null && emailsSalvos.size() > 0){
                restaurarViews(R.layout.novo_email_layout, emailsSalvos, null);
            }
            if(telefonesSalvos != null && telefonesSalvos.size() > 0){
                restaurarViews(R.layout.novo_telefone_layout, telefonesSalvos, tiposTelefonesSalvos);
            }
        }

    }

    public void limparFormulario(){

        try {
            txtNome.setText("");
            notificacoesRadioGroup.clearCheck();
            notificacoesCheckBox.setChecked(false);
            emailLinearLayout.removeAllViews();
            telefoneLinearLayout.removeAllViews();

            txtNome.requestFocus(); //coloca o cursor no campo de nome
            //txtEmail.setText("");
            //txtTelefone.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.CadastrarClientes;
import br.com.sulpasso.sulpassomobile.modelo.Atividade;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.views.CadastroClientesFragmentalized;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertDataPedidos;

/**
 * Created by Lucas on 30/11/2018 - 16:49 as part of the project SulpassoMobile.
 */
public class FormularioClientesFragment extends Fragment implements AlertDataPedidos.AlterarData
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    Button btn_gravar = null;

    EditText edt_razao = null;
    EditText edt_fantasia = null;
    EditText edt_cnpj = null;
    EditText edt_ie = null;
    EditText edt_bairro = null;
    EditText edt_endereco = null;
    EditText edt_numero = null;
    EditText edt_cep = null;
    EditText edt_telefone = null;
    EditText edt_celular = null;
    EditText edt_contato = null;
    EditText edt_email = null;
    EditText edt_representante = null;
    EditText edt_cpf = null;
    EditText edt_rg = null;
    EditText edt_potencial = null;
    EditText edt_area = null;
    EditText edt_roteiro = null;
    EditText edt_com1 = null;
    EditText edt_com1_fone = null;
    EditText edt_com2 = null;
    EditText edt_com2_fone = null;
    EditText edt_com3 = null;
    EditText edt_com3_fone = null;
    EditText edt_com_data = null;
    EditText edt_obs = null;

    RadioGroup tipo_pessoa = null;
    RadioButton t_fisica = null;
    RadioButton t_juridica = null;

    Spinner spnr_uf = null;
    Spinner spnr_atividade = null;
    Spinner spnr_tipologia = null;
    Spinner spnr_cidade = null;
    Spinner spnr_natureza = null;
    Spinner spnr_banco = null;
    Spinner spnr_visita = null;
    Spinner spnr_clientes = null;

    TextView txt_aniversario = null;

    TableLayout tbl_layout = null;

    /*NOVOS CAMPOS AAROTTA*/
    RadioGroup rgNc1 = null;
    RadioButton rbNc11 = null;
    RadioButton rbNc12 = null;
    RadioButton rbNc13 = null;

    RadioGroup rgNc2 = null;
    RadioButton rbNc21 = null;
    RadioButton rbNc22 = null;
    RadioButton rbNc23 = null;

    RadioGroup rgNc3 = null;
    RadioButton rbNc31 = null;
    RadioButton rbNc32 = null;

    RadioGroup rgNc4 = null;
    RadioButton rbNc41 = null;
    RadioButton rbNc42 = null;
    RadioButton rbNc43 = null;

    RadioGroup rgNc5 = null;
    RadioButton rbNc51 = null;
    RadioButton rbNc52 = null;
    RadioButton rbNc53 = null;

    RadioGroup rgNc6 = null;
    RadioButton rbNc61 = null;
    RadioButton rbNc62 = null;
    RadioButton rbNc63 = null;

    EditText  edtNc7;

    ArrayList<String> str_uf = new ArrayList<String>();
    ArrayList<String> str_atividades = new ArrayList<String>();
    ArrayList<String> str_bancos = new ArrayList<String>();
    ArrayList<String> str_cidade = new ArrayList<String>();
    ArrayList<String> str_natureza = new ArrayList<String>();
    ArrayList<String> str_tipologias = new ArrayList<String>();
    ArrayList<Atividade> array_atividades = new ArrayList<Atividade>();
    ArrayList<Banco> array_bancos = new ArrayList<Banco>();
    ArrayList<Cidade> array_cidade = new ArrayList<Cidade>();
    ArrayList<Natureza> array_natureza  = new ArrayList<Natureza>();
    ArrayList<Tipologia> array_tipologias = new ArrayList<Tipologia>();
    ArrayList<Cliente> array_clientes = new ArrayList<Cliente>();
    ArrayList<String> str_clientes = new ArrayList<String>();

    ArrayAdapter<String> adapter_atividades = null;
    ArrayAdapter<String> adapter_banco = null;
    ArrayAdapter<String> adapter_cidades = null;
    ArrayAdapter<String> adapter_naturezas = null;
    ArrayAdapter<String> adapter_tipos = null;
    ArrayAdapter<String> adapter_estados = null;
    ArrayAdapter<String> adapter_clientes = null;

    Boolean obrigatorio;
    Boolean tela_inicial;
    String data;
    int empresa = -1;

    private CadastrarClientes controle;

    public FormularioClientesFragment(){}
    /**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_formulario_cadastro_clientes, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Create an object of the Android_Gesture_Detector  Class
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(getActivity().getApplicationContext(), android_gesture_detector);

        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };

        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.controle = new CadastrarClientes(getActivity().getApplicationContext());

        str_atividades.clear();
        str_bancos.clear();
        str_cidade.clear();
        str_natureza.clear();
        str_tipologias.clear();
        str_uf.clear();

        obrigatorio = this.controle.cadastro_obrigatorio();
        str_uf = this.controle.listar_estados();
        array_atividades = this.controle.listar_atividades();
        array_bancos = this.controle.listar_bancos();
        array_cidade = this.controle.listar_cidades(str_uf.get(0));
        array_natureza = this.controle.listar_naturezas();
        array_tipologias = this.controle.listar_tipologias();
        array_clientes = this.controle.listar_clientes();
        empresa = this.controle.getEmpreza();

        str_atividades.add("SELECIONE");
        str_bancos.add("SELECIONE");
        str_cidade.add("SELECIONE");
        str_natureza.add("SELECIONE");
        str_tipologias.add("SELECIONE");
        str_uf.add(0, "SELECIONE");
        str_clientes.add(0, "SELECIONE O ANTERIOR");

        for (int i = 0; i < array_atividades.size(); i++){ str_atividades.add(array_atividades.get(i).toDisplay()); }

        for (int i = 0; i < array_bancos.size(); i++){ str_bancos.add(array_bancos.get(i).toDisplay()); }

        for (int i = 0; i < array_cidade.size(); i++){ str_cidade.add(array_cidade.get(i).toDisplay()); }

        for (int i = 0; i < array_natureza.size(); i++){ str_natureza.add(array_natureza.get(i).toDisplay()); }

        for (int i = 0; i < array_tipologias.size(); i++){ str_tipologias.add(array_tipologias.get(i).toDisplay()); }

        for (Cliente cli : array_clientes){ str_clientes.add(cli.toDisplay()); }


        this.setUpLayout();
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/


/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        tela_inicial = true;

        btn_gravar = (Button) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_btn_salvar);

        edt_razao = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_razao);
        edt_fantasia = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_fantasia);
        edt_cnpj = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_cnpj);
        edt_ie = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_ie);
        edt_bairro = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_bairro);
        edt_endereco = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_endereco);
        edt_numero = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_numero);
        edt_cep = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_cep);
        edt_telefone = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_telefone);
        edt_celular = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_celular);
        edt_contato = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_contato);
        edt_email = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_mail);
        edt_representante = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_representante);
        edt_cpf = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_cpf);
        edt_rg = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_rg);
        edt_potencial = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_potencial);
        edt_area = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_area);
        edt_roteiro = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_rota);
        edt_com1 = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com1);
        edt_com1_fone = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com1_fone);
        edt_com2 = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com2);
        edt_com2_fone = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com2_fone);
        edt_com3 = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com3);
        edt_com3_fone = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com3_fone);
        edt_com_data = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_com3_data);
        edt_obs = (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_edt_obs);

        spnr_uf = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_uf);
        spnr_atividade = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_atividade);
        spnr_tipologia = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_tipologia);
        spnr_cidade = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_cidade);
        spnr_natureza = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_natureza);
        spnr_banco = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_banco);
        spnr_visita = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_visita);
        spnr_clientes = (Spinner) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_spnr_rota);

        txt_aniversario = (TextView) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_txt_aniversario);

        tbl_layout = (TableLayout) getActivity().findViewById(R.id.ffcc_cadastro_cliente_new_tb_layout);

        if(empresa != 4)
        {
            tbl_layout.setVisibility(View.GONE);
        }
        else
        {
            rgNc1 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc1);
            rbNc11 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc11);
            rbNc12 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc12);
            rbNc13 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc13);

            rgNc2 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc2);
            rbNc21 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc21);
            rbNc22 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc22);
            rbNc23 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc23);

            rgNc3 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc3);
            rbNc31 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc31);
            rbNc32 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc32);

            rgNc4 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc4);
            rbNc41 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc41);
            rbNc42 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc42);
            rbNc43 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc43);

            rgNc5 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc5);
            rbNc51 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc51);
            rbNc52 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc52);
            rbNc53 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc53);

            rgNc6 	=  (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_nc6);
            rbNc61 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc61);
            rbNc62 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc62);
            rbNc63 	=  (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_nc63);

            edtNc7 	=  (EditText) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_newc7);

            tbl_layout.setVisibility(View.VISIBLE);
        }

        /*
        adapter_atividades = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_atividades);
        adapter_banco = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_bancos);
        adapter_cidades = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_cidade);
        adapter_naturezas = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_natureza);
        adapter_tipos = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_tipologias);
        adapter_estados = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_uf);
        adapter_clientes = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, str_clientes);
        */

        adapter_atividades = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_atividades);
        adapter_banco = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_bancos);
        adapter_cidades = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_cidade);
        adapter_naturezas = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_natureza);
        adapter_tipos = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_tipologias);
        adapter_estados = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_uf);
        adapter_clientes = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_clientes);

        spnr_atividade.setAdapter(adapter_atividades);
        spnr_banco.setAdapter(adapter_banco);
        spnr_cidade.setAdapter(adapter_cidades);
        spnr_natureza.setAdapter(adapter_naturezas);
        spnr_tipologia.setAdapter(adapter_tipos);
        spnr_uf.setAdapter(adapter_estados);
        spnr_clientes.setAdapter(adapter_clientes);
        ArrayAdapter<CharSequence> adapter_visita = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.array_dias, R.layout.spinner_dropdown_item);
        spnr_visita.setAdapter(adapter_visita);

        spnr_uf.setOnItemSelectedListener(escolher_estado);
        spnr_cidade.setOnItemSelectedListener(escolher_cidade);

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        txt_aniversario.setText(sf.format(today));
        data = txt_aniversario.getText().toString();
        txt_aniversario.setOnClickListener(alterar_data);

        tipo_pessoa = (RadioGroup) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rg_tpessoa);

        tipo_pessoa.setOnCheckedChangeListener(altera_tipo);

        t_fisica = (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_fisica);
        t_juridica = (RadioButton) getActivity().findViewById(R.id.ffcc_cadastro_cliente_formulario_rb_juridica);

        if (empresa == 4)
        {
            t_fisica.setClickable(false);
            t_juridica.setClickable(false);
            t_fisica.setEnabled(false);
            t_juridica.setEnabled(false);
        }
        else
        {
            t_fisica.setClickable(true);
            t_juridica.setClickable(true);
            t_fisica.setEnabled(true);
            t_juridica.setEnabled(true);
        }

        edt_roteiro.setClickable(false);
        edt_roteiro.setEnabled(false);

        spnr_clientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if (arg2 != 0){ edt_roteiro.setText("" +  ( array_clientes.get(arg2 - 1).getRoteiro() + 1)); }
                else { /*****/ }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
        });

        btn_gravar.setOnClickListener(salvar_cadastro);

		/*
		 Jansen Felipe

		 a biblioteca referenciada abaixo MaskEditTextChangedListener foi desenvolvida por
		 Jansen Felipe em 23/03/2015.
		 A biblioteca possui código aberto mas estamos utilizando apenas o .jar sem alterações no
		 projeto original do desenvolvedor.
		 */

        MaskEditTextChangedListener cpf_masck = new MaskEditTextChangedListener("###.###.###-##", edt_cpf);
        MaskEditTextChangedListener cnpj_masck = new MaskEditTextChangedListener("##.###.###.####-##", edt_cnpj);

//        edt_cpf.addTextChangedListener(cpf_masck);
//        edt_cnpj.addTextChangedListener(cnpj_masck);
    }

    private boolean validarDadosNovos()
    {
        Toast t;

        if(empresa == 4)
        {
            if(rgNc1.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc1.requestFocus();
                return false;
            }
            if(rgNc2.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc2.requestFocus();
                return false;
            }
            if(rgNc3.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc3.requestFocus();
                return false;
            }
            if(rgNc4.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc4.requestFocus();
                return false;
            }
            if(rgNc5.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc5.requestFocus();
                return false;
            }
            if(rgNc6.getCheckedRadioButtonId() == -1)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                rgNc6.requestFocus();
                return false;
            }
            if(edtNc7.getText().toString().trim().length() <= 0)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo dados adicionais deve ser preenchido", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                edtNc7.requestFocus();
                return false;
            }

            return true;
        }

        return true;
    }

    private void salvar_cadastro()
    {
        if(!validarDadosNovos())
        {
            return;
        }
        //criar rotina salvar
        String t_p = "";

        switch (tipo_pessoa.getCheckedRadioButtonId())
        {
            case R.id.cadastro_cliente_formulario_rb_fisica:
                t_p = "F";
                break;
            case R.id.cadastro_cliente_formulario_rb_juridica:
                t_p = "J";
                break;
            default:
                break;
        }

        int rnc1 = 0;
        int rnc2 = 0;
        int rnc3 = 0;
        int rnc4 = 0;
        int rnc5 = 0;
        int rnc6 = 0;

        if(empresa == 4)
        {
            switch (rgNc1.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc11:
                    rnc1 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc12:
                    rnc1 = 2;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc13:
                    rnc1 = 3;
                    break;
                default:
                    rnc1 = 0;
                    break;
            }

            switch (rgNc2.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc21:
                    rnc2 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc22:
                    rnc2 = 2;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc23:
                    rnc2 = 3;
                    break;
                default:
                    rnc2 = 0;
                    break;
            }

            switch (rgNc3.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc31:
                    rnc3 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc32:
                    rnc3 = 2;
                    break;
                default:
                    rnc3 = 0;
                    break;
            }

            switch (rgNc4.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc41:
                    rnc4 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc42:
                    rnc4 = 2;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc43:
                    rnc4 = 3;
                    break;
                default:
                    rnc4 = 0;
                    break;
            }

            switch (rgNc5.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc51:
                    rnc5 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc52:
                    rnc5 = 2;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc53:
                    rnc5 = 3;
                    break;
                default:
                    rnc5 = 0;
                    break;
            }

            switch (rgNc6.getCheckedRadioButtonId())
            {
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc61:
                    rnc6 = 1;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc62:
                    rnc6 = 2;
                    break;
                case R.id.ffcc_cadastro_cliente_formulario_rb_nc63:
                    rnc6 = 3;
                    break;
                default:
                    rnc6 = 0;
                    break;
            }
        }


        ClienteNovo cliente = new ClienteNovo();
        ManipulacaoStrings ms = new ManipulacaoStrings();

        cliente.setNome(ms.trata(edt_razao.getText().toString()));
        cliente.setFantazia(ms.trata(edt_fantasia.getText().toString()));
        cliente.setEndereco(ms.trata(edt_endereco.getText().toString()));
        cliente.setUf(str_uf.get(spnr_uf.getSelectedItemPosition()));
        cliente.setBairro(ms.trata(edt_bairro.getText().toString()));
        cliente.setCidade(array_cidade.get(spnr_cidade.getSelectedItemPosition() - 1).getCodigo());
        cliente.setFone(edt_telefone.getText().toString());
        cliente.setCell(edt_celular.getText().toString());
        cliente.setCgc(edt_cnpj.getText().toString());
        cliente.setIe(edt_ie.getText().toString());
        cliente.setContato(ms.trata(edt_contato.getText().toString()));
        cliente.setEmail(ms.trata(edt_email.getText().toString()));
        cliente.setAniversario(txt_aniversario.getText().toString());
        cliente.setCep(edt_cep.getText().toString());
        cliente.setT_pessoa(t_p);
        cliente.setRep(ms.trata(edt_representante.getText().toString()));
        cliente.setIdent(edt_rg.getText().toString());

        int roteiro = 0;
        try { roteiro = Integer.parseInt(edt_roteiro.getText().toString()); }
        catch (Exception e) { roteiro = 0; }

        cliente.setZona(roteiro);
        cliente.setVisita("" + (spnr_visita.getSelectedItemPosition() + 2));
        cliente.setCpf(edt_cpf.getText().toString());
        cliente.setTipologia(array_tipologias.get(spnr_tipologia.getSelectedItemPosition() - 1).getTipologia());

        cliente.setPagto(array_natureza.get(spnr_natureza.getSelectedItemPosition() - 1).getCodigo());
        cliente.setBanco(array_bancos.get(spnr_banco.getSelectedItemPosition() - 1).getCodigo());

        float potencial = 0;
        try { potencial = Float.parseFloat(edt_potencial.getText().toString()); }
        catch (Exception e) { potencial = 0; }

        cliente.setPotencial(potencial);

        cliente.setObs(ms.trata(edt_obs.getText().toString()));
        cliente.setNr_res(ms.trata(edt_numero.getText().toString()));
        cliente.setAtividade(array_atividades.get(spnr_atividade.getSelectedItemPosition() - 1).getCodigo());

        int area = 0;
        try { area = Integer.parseInt(edt_area.getText().toString()); }
        catch (Exception e) { area =  0; }

        cliente.setArea(area);

        cliente.setComercial1(ms.trata(edt_com1.getText().toString()));
        cliente.setComercial1_fone(ms.trata(edt_com1_fone.getText().toString()));
        cliente.setComercial2(ms.trata(edt_com2.getText().toString()));
        cliente.setComercial2_fone(ms.trata(edt_com2_fone.getText().toString()));
        cliente.setComercial3(ms.trata(edt_com3.getText().toString()));
        cliente.setComercial3_fone(ms.trata(edt_com3_fone.getText().toString()));
        cliente.setComercial_abertura(ms.trata(edt_com_data.getText().toString()));
        cliente.setData(data);

        cliente.setNc11(rnc1);
        cliente.setNc21(rnc2);
        cliente.setNc31(rnc3);
        cliente.setNc41(rnc4);
        cliente.setNc51(rnc5);
        cliente.setNc61(rnc6);

        cliente.setNc7(empresa == 4 ? edtNc7.getText().toString() : "");


        this.controle.setCliente(cliente);
        try
        {
            this.controle.salvarCadastro();

            Toast t = Toast.makeText(getActivity().getApplicationContext(), "Cadastro de cliente salvo com sucesso." +
                    "\nAcesse a tela de atualização para enviar as informações a emrpesa.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();

            ((CadastroClientesFragmentalized)getActivity()).alterarFragmento(0);
        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(getActivity().getApplicationContext(), "O cadastro não pode ser salvo." +
                    "\nVerifique as informações e tente novamente.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();
        }
    }

/**************************************************************************************************/
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
/**************************************************************************************************/
    private AdapterView.OnItemSelectedListener escolher_estado = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            if (arg2 != 0)
            {
                str_cidade.clear();

                System.out.println(str_uf.get(arg2).toString());
                array_cidade = controle.listar_cidades(str_uf.get(arg2));

                str_cidade.add("SELECIONE");

                for (int i = 0; i < array_cidade.size(); i++) { str_cidade.add(array_cidade.get(i).toDisplay()); }

                adapter_cidades = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_dropdown_item, str_cidade);
                spnr_cidade.setAdapter(adapter_cidades);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
    };

    private AdapterView.OnItemSelectedListener escolher_cidade = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            if (arg2 != 0)
            {
                edt_cep.setText(array_cidade.get(arg2 - 1).getCep());
            }
            else { edt_cep.setText(""); }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
    };

    private android.widget.RadioGroup.OnCheckedChangeListener altera_tipo = new android.widget.RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            if (checkedId == R.id.ffcc_cadastro_cliente_formulario_rb_fisica)
            {
                edt_ie.setText("ISENTO");
                edt_cnpj.setText("11111111111111");
                edt_ie.setEnabled(false);
                edt_cnpj.setEnabled(false);
                controle.setTipo(0);
            }
            else
            {
                edt_ie.setText("");
                edt_cnpj.setText("");
                edt_ie.setEnabled(true);
                edt_cnpj.setEnabled(true);
                controle.setTipo(1);
            }
        }
    };

    private View.OnClickListener alterar_data = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) { selecionarData(); }
    };

    private void selecionarData()
    {
        AlertDataPedidos dialog = new AlertDataPedidos();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }

    private View.OnClickListener salvar_cadastro = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Toast t;

            if(edt_razao.getText().length() < 10)
            {
                t = Toast.makeText(getActivity().getApplicationContext(), "Campo Razao Social e de preenchimento obrigatorio e deve conter no minimo 10 caracteres", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                edt_razao.requestFocus();
            } else
            {
                if(edt_fantasia.getText().length() < 10)
                {
                    t = Toast.makeText(getActivity().getApplicationContext(), "Campo Nome Fantazia e de preenchimento obrigatorio e deve conter no minimo 10 caracteres", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();

                    edt_fantasia.requestFocus();
                } else
                {
                    if((tipo_pessoa.getCheckedRadioButtonId() == R.id.cadastro_cliente_formulario_rb_juridica &&
                            !controle.validarDocumento(edt_cnpj.getText().toString(), 1)))
                    {
                        t = Toast.makeText(getActivity().getApplicationContext(), "Cnpj invalido", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                        t.show();

                        edt_cnpj.requestFocus();
                    } else
                    {
                        if((edt_ie.getText().length() < 6 || edt_ie.getText().length() > 20) &&
                                tipo_pessoa.getCheckedRadioButtonId() == R.id.cadastro_cliente_formulario_rb_juridica)
                        {
                            t = Toast.makeText(getActivity().getApplicationContext(), "IE invalido", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                            t.show();

                            edt_ie.requestFocus();
                        } else
                        {
                            if (spnr_uf.getSelectedItemPosition() < 1)
                            {
                                t = Toast.makeText(getActivity().getApplicationContext(), "Escolha um estado", Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                t.show();

                                edt_ie.requestFocus();
                            } else
                            {
                                if (spnr_atividade.getSelectedItemPosition() < 1)
                                {
                                    t = Toast.makeText(getActivity().getApplicationContext(), "Escolha uma atividade", Toast.LENGTH_LONG);
                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                    t.show();

                                    edt_ie.requestFocus();
                                } else
                                {
                                    if (spnr_tipologia.getSelectedItemPosition() < 1)
                                    {
                                        t = Toast.makeText(getActivity().getApplicationContext(), "Escolha uma tipologia", Toast.LENGTH_LONG);
                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                        t.show();

                                        edt_ie.requestFocus();
                                    } else
                                    {
                                        if (spnr_cidade.getSelectedItemPosition() < 1)
                                        {
                                            t = Toast.makeText(getActivity().getApplicationContext(), "Escolha uma cidade", Toast.LENGTH_LONG);
                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                            t.show();

                                            edt_ie.requestFocus();
                                        } else
                                        {
                                            if(edt_bairro.getText().length() < 3)
                                            {
                                                t = Toast.makeText(getActivity().getApplicationContext(), "Campo bairro deve ser preenchido", Toast.LENGTH_LONG);
                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                t.show();

                                                edt_bairro.requestFocus();
                                            } else
                                            {
                                                if(edt_endereco.getText().length() < 3)
                                                {
                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Digite um endereco", Toast.LENGTH_LONG);
                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                    t.show();

                                                    edt_endereco.requestFocus();
                                                } else
                                                {
                                                    if(edt_numero.getText().length() < 1)
                                                    {
                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Numero de residencia incompleto", Toast.LENGTH_LONG);
                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                        t.show();

                                                        edt_numero.requestFocus();
                                                    } else
                                                    {
                                                        if(edt_cep.getText().length() < 4)
                                                        {
                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Cep invalido", Toast.LENGTH_LONG);
                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                            t.show();

                                                            edt_cep.requestFocus();
                                                        } else
                                                        {
                                                            if(edt_telefone.getText().length() < 7)
                                                            {
                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Digite um telefone para contato", Toast.LENGTH_LONG);
                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                t.show();

                                                                edt_telefone.requestFocus();
                                                            } else
                                                            {
                                                                if(edt_celular.getText().length() < 8 && obrigatorio)
                                                                {
                                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Digite um celular para contato", Toast.LENGTH_LONG);
                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                    t.show();

                                                                    edt_celular.requestFocus();
                                                                } else
                                                                {
                                                                    if(edt_contato.getText().length() < 3)
                                                                    {
                                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Digite o nome do comprador", Toast.LENGTH_LONG);
                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                        t.show();

                                                                        edt_contato.requestFocus();
                                                                    } else
                                                                    {
                                                                        if(edt_email.getText().length() < 4)
                                                                        {
                                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Digite o email da empreza", Toast.LENGTH_LONG);
                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                            t.show();

                                                                            edt_email.requestFocus();
                                                                        } else
                                                                        {
                                                                            if(edt_representante.getText().length() < 5)
                                                                            {
                                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Representante legal deve ser preenchido", Toast.LENGTH_LONG);
                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                t.show();

                                                                                edt_representante.requestFocus();
                                                                            } else
                                                                            {
                                                                                if(edt_cpf.getText().length() < 11 || !controle.validarDocumento(edt_cpf.getText().toString(), 0))
                                                                                {
                                                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Cpf do representante invalido", Toast.LENGTH_LONG);
                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                    t.show();

                                                                                    edt_cpf.requestFocus();
                                                                                } else
                                                                                {
                                                                                    if(edt_rg.getText().length() < 4)
                                                                                    {
                                                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Rg do representante invalido", Toast.LENGTH_LONG);
                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                        t.show();

                                                                                        edt_rg.requestFocus();
                                                                                    } else
                                                                                    {
                                                                                        if (spnr_natureza.getSelectedItemPosition() < 1)
                                                                                        {
                                                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Escolha uma natureza", Toast.LENGTH_LONG);
                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                            t.show();

                                                                                            edt_rg.requestFocus();
                                                                                        } else
                                                                                        {
                                                                                            if (spnr_banco.getSelectedItemPosition() < 1)
                                                                                            {
                                                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Escolha um banco", Toast.LENGTH_LONG);
                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                t.show();

                                                                                                edt_rg.requestFocus();
                                                                                            } else
                                                                                            {
                                                                                                if(edt_potencial.getText().length() < 3 && obrigatorio)
                                                                                                {
                                                                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Qual o potencial de compra?", Toast.LENGTH_LONG);
                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                    t.show();

                                                                                                    edt_potencial.requestFocus();
                                                                                                } else
                                                                                                {
                                                                                                    if(spnr_visita.getSelectedItemPosition() < 0)
                                                                                                    {
                                                                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Escolha um dia de visitas", Toast.LENGTH_LONG);
                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                        t.show();

                                                                                                        edt_area.requestFocus();
                                                                                                    } else
                                                                                                    {
                                                                                                        if(edt_area.getText().length() < 1 && obrigatorio)
                                                                                                        {
                                                                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Escolha uma natureza", Toast.LENGTH_LONG);
                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                            t.show();

                                                                                                            edt_area.requestFocus();
                                                                                                        } else
                                                                                                        {
                                                                                                            if(edt_roteiro.getText().length() < 1 && obrigatorio)
                                                                                                            {
                                                                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Escolha o roteiro", Toast.LENGTH_LONG);
                                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                t.show();

                                                                                                                edt_roteiro.requestFocus();
                                                                                                            } else
                                                                                                            {
                                                                                                                if(edt_com1.getText().length() < 5 && obrigatorio)
                                                                                                                {
                                                                                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                    t.show();

                                                                                                                    edt_com1.requestFocus();
                                                                                                                } else
                                                                                                                {
                                                                                                                    if(edt_com1_fone.getText().length() < 7 && obrigatorio)
                                                                                                                    {
                                                                                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                        t.show();

                                                                                                                        edt_com1_fone.requestFocus();
                                                                                                                    } else
                                                                                                                    {
                                                                                                                        if(edt_com2.getText().length() < 5 && obrigatorio)
                                                                                                                        {
                                                                                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                            t.show();

                                                                                                                            edt_com2.requestFocus();
                                                                                                                        } else
                                                                                                                        {
                                                                                                                            if(edt_com2_fone.getText().length() < 7 && obrigatorio)
                                                                                                                            {
                                                                                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                t.show();

                                                                                                                                edt_com2_fone.requestFocus();
                                                                                                                            } else
                                                                                                                            {
                                                                                                                                if(edt_com3.getText().length() < 5 && obrigatorio)
                                                                                                                                {
                                                                                                                                    t = Toast.makeText(getActivity().getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                    t.show();

                                                                                                                                    edt_com3.requestFocus();
                                                                                                                                } else
                                                                                                                                {
                                                                                                                                    if(edt_com3_fone.getText().length() < 7 && obrigatorio)
                                                                                                                                    {
                                                                                                                                        t = Toast.makeText(getActivity().getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                        t.show();

                                                                                                                                        edt_com3_fone.requestFocus();
                                                                                                                                    } else
                                                                                                                                    {
                                                                                                                                        if(edt_com_data.getText().length() < 10 && obrigatorio)
                                                                                                                                        {
                                                                                                                                            t = Toast.makeText(getActivity().getApplicationContext(), "Data abertura da empresa", Toast.LENGTH_LONG);
                                                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                            t.show();

                                                                                                                                            edt_com_data.requestFocus();
                                                                                                                                        }else
                                                                                                                                        {
                                                                                                                                            if(edt_obs.getText().length() < 5 && obrigatorio)
                                                                                                                                            {
                                                                                                                                                t = Toast.makeText(getActivity().getApplicationContext(), "Campo observacao deve ser preenchido", Toast.LENGTH_LONG);
                                                                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                                t.show();

                                                                                                                                                edt_obs.requestFocus();
                                                                                                                                            }
                                                                                                                                            else { salvar_cadastro(); }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    };

/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
/**************************************************************************************************/
    @Override
    public void indicarNovaData(String data)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        data = ms.dataVisual(data);

        txt_aniversario.setText(data);
        Toast.makeText(getActivity().getApplicationContext(), "Aniversário do cliente indicado = " + data, Toast.LENGTH_LONG).show();
    }
/**************************************************************************************************/
/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/
    private class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener
    {
        @Override
        public boolean onDown(MotionEvent e) { return false; }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) { return false; }

        @Override
        public boolean onSingleTapUp(MotionEvent e) { return false; }

        @Override
        public void onShowPress(MotionEvent e) { Log.d("Gesture ", " onShowPress"); }

        @Override
        public boolean onDoubleTap(MotionEvent e) { return false; }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) { return false; }

        @Override
        public void onLongPress(MotionEvent e) { Log.d("Gesture ", " onLongPress"); }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            /*
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int scrollUpBegin = (int) height - ((height * 20) / 100);
            int scrollDownBegin = (int) height - (height - ((height * 20) / 100));
            int scrollEnd = (int) height - ((height * 50) / 100);
            */
            Log.d("Gesture ", " onScroll");

            if (e1.getY() < e2.getY())
            {
                Log.d("Gesture ", " Scroll Down");
                        /*
                        if (e1.getY() < scrollDownBegin && e2.getY() <= scrollEnd)
                        {
                            Log.d("Gesture ", " Scroll Down");
                        }
                        else { Log.d("Gesture ", " Scroll Down -- To Lower"); }
                        */
            }
            if (e1.getY() > e2.getY())
            {
                Log.d("Gesture ", " Scroll Up");
                        /*
                        if (e1.getY() > scrollUpBegin) { Log.d("Gesture ", " Scroll Up -- To high"); }
                        else { Log.d("Gesture ", " Scroll Up"); }
                        */
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if(Math.abs(velocityX) > Math.abs(velocityY))
            {
                if (e1.getX() < e2.getX()) //Left to Right swipe
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                    //((CadastroClientesFragmentalized) getActivity()).alterarFragmento(1);
                }
                if (e1.getX() > e2.getX()) { ((CadastroClientesFragmentalized) getActivity()).alterarFragmento(0); } //Right to Left swipe

                return true;
            }
            else
            {
                    /*
                    if (e1.getY() < e2.getY()) //Up to Down swipe
                    {
                        //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                            ((Pedido) getActivity()).alterarFragmento(0);
                    }
                    if (e1.getY() > e2.getY()) { ((Pedido) getActivity()).alterarFragmento(4); } //Down to Up swipe
                    */

                return false;
            }
        }
    }
}
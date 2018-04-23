package br.com.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.CadastrarClientes;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Atividade;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensGravosos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensPromocoes;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosLista;

/**
 * Created by Lucas on 20/11/2017 - 17:14 as part of the project SulpassoMobile.
 */
public class CadastroClientes extends AppCompatActivity
{
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

    Context this__ = this;

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
    String nome_arq;
    int nr_seq;
    int cod_vendedor;
    int parte_executada;
    int tipo_busca;
    int empresa = -1;

    private final int TODOS = 1;
    private final int ENVIADOS = 2;
    private final int N_ENVIADOS = 3;


    private CadastrarClientes controle;

/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_consulta);

        this.controle = new CadastrarClientes(getApplicationContext());

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
        
        this.setupLayout();
        //displayView(R.layout.fragment_consulta_pedidos_lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.menu_consulta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentManager fragmentManager;
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.consulta_pedidos_todos :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    /*
                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(0, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                    */
                }
                break;
            case R.id.consulta_pedidos_resumo :

                break;
            case R.id.consulta_pedidos_nao :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    /*
                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(2, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                    */
                }
                break;
            case R.id.consulta_pedidos_direta :

                break;
            case R.id.consulta_pedidos_enviados :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    /*
                    try
                    {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidos(1, ""));
                    }
                    catch (GenercicException e) { e.printStackTrace(); }
                    */
                }
                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = getString(R.string.telasConsulta);
        Fragment fragment = null;

        getFragmentManager().popBackStackImmediate();
        switch (position)
        {
            case R.layout.fragment_consulta_pedidos_lista :
                fragment = new ConsultaPedidosLista();
                //title += fragTitles[0];
                break;
            case R.layout.fragment_consulta_pedidos_diretas :
                fragment = new ConsultaItensPromocoes();
                //title += fragTitles[1];
                break;
            case R.layout.fragment_consulta_pedidos_resumo :
                fragment = new ConsultaItensGravosos();
                //title += fragTitles[2];
                break;

            default:
                Toast.makeText(this, "A opção selecionada não e reconhecida pelo sistema", Toast.LENGTH_LONG).show();
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            } else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }
        setTitle(title);
    }

    public void solicitacao_sair()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sair Cadastro");
        alert.setMessage("Deseja sair da cadastro de clientes?");

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
//                moveTaskToBack(true);
//                onDestroy();
            }
        });

        alert.setNegativeButton("NAO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) { /*****/ }
        });

        alert.show();
    }

    private void salvar_cadastro()
    {

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

        this.controle.setCliente(cliente);
        try
        {
            this.controle.salvarCadastro();

            Toast t = Toast.makeText(getApplicationContext(), "Cadastro de cliente salvo com sucesso." +
                    "\nAcesse a tela de atualização para enviar as informações a emrpesa.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();

            finish();
        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(getApplicationContext(), "O cadastro não pode ser salvo." +
                    "\nVerifique as informações e tente novamente.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();
        }
    }

    public boolean validarCadastro()
    {
        /*

			if(edt_razao.getText().length() < 5){
				mensagem.tipo_mensagem(2, "", "Campo Razao Social e de preenchimento obrigatorio " +
						"e deve conter no minimo 10 caracteres");
				edt_razao.requestFocus();
			} else {
				if(edt_fantasia.getText().length() < 5){
					mensagem.tipo_mensagem(2, "", "Campo Nome Fantazia e de preenchimento obrigatorio" +
							" e deve conter no minimo 10 caracteres");
					edt_fantasia.requestFocus();
				} else {
					if((tipo_pessoa.getCheckedRadioButtonId() == R.id.this.controle_formulario_rb_juridica &&
							!validar_cgc(edt_cnpj.getText().toString()))){
						mensagem.tipo_mensagem(2, "", "Cnpj invalido");
						edt_cnpj.requestFocus();
					} else {
						if((edt_ie.getText().length() < 6 || edt_ie.getText().length() > 20) && tipo_pessoa.getCheckedRadioButtonId() == R.id.this.controle_formulario_rb_juridica){
							mensagem.tipo_mensagem(2, "", "IE invalido");
							edt_ie.requestFocus();
						} else {
							if (spnr_uf.getSelectedItemPosition() < 1){
								mensagem.tipo_mensagem(2, "", "Escolha um estado");
								edt_ie.requestFocus();
							} else {
								if (spnr_atividade.getSelectedItemPosition() < 1){
									mensagem.tipo_mensagem(2, "", "Escolha uma atividade");
									edt_ie.requestFocus();
								} else {
									if (spnr_tipologia.getSelectedItemPosition() < 1){
										mensagem.tipo_mensagem(2, "", "Escolha uma tipologia");
										edt_ie.requestFocus();
									} else {
										if (spnr_cidade.getSelectedItemPosition() < 1){
											mensagem.tipo_mensagem(2, "", "Escolha uma cidade");
											edt_ie.requestFocus();
										} else {
											if(edt_bairro.getText().length() < 3){
												mensagem.tipo_mensagem(2, "", "Campo bairro deve ser preenchido");
												edt_bairro.requestFocus();
											} else {
												if(edt_endereco.getText().length() < 3){
													mensagem.tipo_mensagem(2, "", "Digite um endereco");
													edt_endereco.requestFocus();
												} else {
													if(edt_numero.getText().length() < 1){
														mensagem.tipo_mensagem(2, "", "Numero de residencia incompleto");
														edt_numero.requestFocus();
													} else {
														if(edt_cep.getText().length() < 4){
															mensagem.tipo_mensagem(2, "", "Cep invalido");
															edt_cep.requestFocus();
														} else {
															if(edt_telefone.getText().length() < 7){
																mensagem.tipo_mensagem(2, "", "Digite um telefone para contato");
																edt_telefone.requestFocus();
															} else {
																if(edt_celular.getText().length() < 8 && obrigatorio){
																	mensagem.tipo_mensagem(2, "", "Digite um celular para contato");
																	edt_celular.requestFocus();
																} else {
																	if(edt_contato.getText().length() < 3){
																		mensagem.tipo_mensagem(2, "", "Digite o nome do comprador");
																		edt_contato.requestFocus();
																	} else {
																		if(edt_email.getText().length() < 4){
																			mensagem.tipo_mensagem(2, "", "Digite o email da empreza");
																			edt_email.requestFocus();
																		} else {
																			if(edt_representante.getText().length() < 5){
																				mensagem.tipo_mensagem(2, "", "Representante legal deve ser preenchido");
																				edt_representante.requestFocus();
																			} else {
																				if(edt_cpf.getText().length() < 11 || !validar_cpf(edt_cpf.getText().toString())){
																					mensagem.tipo_mensagem(2, "", "Cpf do representante invalido");
																					edt_cpf.requestFocus();
																				} else {
																					if(edt_rg.getText().length() < 4){
																						mensagem.tipo_mensagem(2, "", "Rg do representante invalido");
																						edt_rg.requestFocus();
																					} else {
																						if (spnr_natureza.getSelectedItemPosition() < 1){
																							mensagem.tipo_mensagem(2, "", "Escolha uma natureza");
																							edt_rg.requestFocus();
																						} else {
																							if (spnr_banco.getSelectedItemPosition() < 1){
																								mensagem.tipo_mensagem(2, "", "Escolha um banco");
																								edt_rg.requestFocus();
																							} else {
																								if(edt_potencial.getText().length() < 3 && obrigatorio){
																									mensagem.tipo_mensagem(2, "", "Qual o potencial de compra?");
																									edt_potencial.requestFocus();
																								} else {
																									if(spnr_visita.getSelectedItemPosition() < 0){
																										mensagem.tipo_mensagem(2, "", "Escolha um dia de visitas");
																										edt_area.requestFocus();
																									} else {
																										if(edt_area.getText().length() < 1 && obrigatorio){
																											mensagem.tipo_mensagem(2, "", "Escolha a area");
																											edt_area.requestFocus();
																										} else {
																											if(edt_roteiro.getText().length() < 1 && obrigatorio){
																												mensagem.tipo_mensagem(2, "", "Escolha o roteiro");
																												edt_roteiro.requestFocus();
																											} else {
																												if(edt_com1.getText().length() < 5 && obrigatorio){
																													mensagem.tipo_mensagem(2, "", "Informe informacao comercial");
																													edt_com1.requestFocus();
																												} else {
																													if(edt_com1_fone.getText().length() < 7 && obrigatorio){
																														mensagem.tipo_mensagem(2, "", "Telefone do estabelecimento de referencia");
																														edt_com1_fone.requestFocus();
																													} else {
																														if(edt_com2.getText().length() < 5 && obrigatorio){
																															mensagem.tipo_mensagem(2, "", "Informe informacao comercial");
																															edt_com2.requestFocus();
																														} else {
																															if(edt_com2_fone.getText().length() < 7 && obrigatorio){
																																mensagem.tipo_mensagem(2, "", "Telefone do estabelecimento de referencia");
																																edt_com2_fone.requestFocus();
																															} else {
																																if(edt_com3.getText().length() < 5 && obrigatorio){
																																	mensagem.tipo_mensagem(2, "", "Informe informacao comercial");
																																	edt_com3.requestFocus();
																																} else {
																																	if(edt_com3_fone.getText().length() < 7 && obrigatorio){
																																		mensagem.tipo_mensagem(2, "", "Telefone do estabelecimento de referencia");
																																		edt_com3_fone.requestFocus();
																																	} else {
																																		if(edt_com_data.getText().length() < 10 && obrigatorio){
																																			mensagem.tipo_mensagem(2, "", "Data abertura da referencia");
																																			edt_com_data.requestFocus();
																																		}else {
																																			if(edt_obs.getText().length() < 5 && obrigatorio){
																																				mensagem.tipo_mensagem(2, "", "Campo observacao deve ser preenchido");
																																				edt_obs.requestFocus();
																																			} else {
																																				salvar_cadastro();
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

         */
        return false;
    }

    private void setupLayout()
    {
        setContentView(R.layout.activity_cadastro_cliente);
        tela_inicial = true;

        btn_gravar = (Button) findViewById(R.id.cadastro_cliente_formulario_btn_salvar);

        edt_razao = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_razao);
        edt_fantasia = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_fantasia);
        edt_cnpj = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_cnpj);
        edt_ie = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_ie);
        edt_bairro = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_bairro);
        edt_endereco = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_endereco);
        edt_numero = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_numero);
        edt_cep = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_cep);
        edt_telefone = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_telefone);
        edt_celular = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_celular);
        edt_contato = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_contato);
        edt_email = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_mail);
        edt_representante = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_representante);
        edt_cpf = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_cpf);
        edt_rg = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_rg);
        edt_potencial = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_potencial);
        edt_area = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_area);
        edt_roteiro = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_rota);
        edt_com1 = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com1);
        edt_com1_fone = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com1_fone);
        edt_com2 = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com2);
        edt_com2_fone = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com2_fone);
        edt_com3 = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com3);
        edt_com3_fone = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com3_fone);
        edt_com_data = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_com3_data);
        edt_obs = (EditText) findViewById(R.id.cadastro_cliente_formulario_edt_obs);

        spnr_uf = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_uf);
        spnr_atividade = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_atividade);
        spnr_tipologia = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_tipologia);
        spnr_cidade = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_cidade);
        spnr_natureza = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_natureza);
        spnr_banco = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_banco);
        spnr_visita = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_visita);
        spnr_clientes = (Spinner) findViewById(R.id.cadastro_cliente_formulario_spnr_rota);

        txt_aniversario = (TextView) findViewById(R.id.cadastro_cliente_formulario_txt_aniversario);

        adapter_atividades = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_atividades);
        adapter_banco = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_bancos);
        adapter_cidades = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_cidade);
        adapter_naturezas = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_natureza);
        adapter_tipos = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_tipologias);
        adapter_estados = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_uf);
        adapter_clientes = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_clientes);

        spnr_atividade.setAdapter(adapter_atividades);
        spnr_banco.setAdapter(adapter_banco);
        spnr_cidade.setAdapter(adapter_cidades);
        spnr_natureza.setAdapter(adapter_naturezas);
        spnr_tipologia.setAdapter(adapter_tipos);
        spnr_uf.setAdapter(adapter_estados);
        spnr_clientes.setAdapter(adapter_clientes);
        ArrayAdapter<CharSequence> adapter_visita = ArrayAdapter.createFromResource(this__, R.array.array_dias, android.R.layout.simple_spinner_dropdown_item);
        spnr_visita.setAdapter(adapter_visita);

        spnr_uf.setOnItemSelectedListener(escolher_estado);
        spnr_cidade.setOnItemSelectedListener(escolher_cidade);

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        txt_aniversario.setText(sf.format(today));
        data = txt_aniversario.getText().toString();
        txt_aniversario.setOnClickListener(alterar_data);

        tipo_pessoa = (RadioGroup) findViewById(R.id.cadastro_cliente_formulario_rg_tpessoa);

        tipo_pessoa.setOnCheckedChangeListener(altera_tipo);

        t_fisica = (RadioButton) findViewById(R.id.cadastro_cliente_formulario_rb_fisica);
        t_juridica = (RadioButton) findViewById(R.id.cadastro_cliente_formulario_rb_juridica);

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

        edt_cpf.addTextChangedListener(cpf_masck);
        edt_cnpj.addTextChangedListener(cnpj_masck);
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private android.widget.RadioGroup.OnCheckedChangeListener altera_tipo = new android.widget.RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            if (checkedId == R.id.cadastro_cliente_formulario_rb_fisica)
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
        public void onClick(View v) { showDialog(0); }
    };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id)
        {
            case 0:
                return new DatePickerDialog(this__, select_date, ano, mes, dia);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener select_date = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            ManipulacaoStrings ms = new ManipulacaoStrings();

            String month = ms.comEsquerda(String.valueOf(monthOfYear+1), "0", 2);
            String day = ms.comEsquerda(String.valueOf(dayOfMonth), "0", 2);

            String data = day + "/" + month + "/" + ms.comEsquerda(String.valueOf(year), "0", 4);

            txt_aniversario.setText(data);
        }
    };

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

                adapter_cidades = new ArrayAdapter<String>(this__, android.R.layout.simple_spinner_dropdown_item, str_cidade);
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
            if (arg2 != 0){ edt_cep.setText(array_cidade.get(arg2 - 1).getCep()); }
            else { edt_cep.setText(""); }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
    };

    private View.OnClickListener salvar_cadastro = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Toast t;

            if(edt_razao.getText().length() < 10)
            {
                t = Toast.makeText(getApplicationContext(), "Campo Razao Social e de preenchimento obrigatorio e deve conter no minimo 10 caracteres", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                edt_razao.requestFocus();
            } else
            {
                if(edt_fantasia.getText().length() < 10)
                {
                    t = Toast.makeText(getApplicationContext(), "Campo Nome Fantazia e de preenchimento obrigatorio e deve conter no minimo 10 caracteres", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                    t.show();

                    edt_fantasia.requestFocus();
                } else
                {
                    if((tipo_pessoa.getCheckedRadioButtonId() == R.id.cadastro_cliente_formulario_rb_juridica &&
                            !controle.validarDocumento(edt_cnpj.getText().toString(), 1)))
                    {
                        t = Toast.makeText(getApplicationContext(), "Cnpj invalido", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                        t.show();

                        edt_cnpj.requestFocus();
                    } else
                    {
                        if((edt_ie.getText().length() < 6 || edt_ie.getText().length() > 20) &&
                                tipo_pessoa.getCheckedRadioButtonId() == R.id.cadastro_cliente_formulario_rb_juridica)
                        {
                            t = Toast.makeText(getApplicationContext(), "IE invalido", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                            t.show();

                            edt_ie.requestFocus();
                        } else
                        {
                            if (spnr_uf.getSelectedItemPosition() < 1)
                            {
                                t = Toast.makeText(getApplicationContext(), "Escolha um estado", Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                t.show();

                                edt_ie.requestFocus();
                            } else
                            {
                                if (spnr_atividade.getSelectedItemPosition() < 1)
                                {
                                    t = Toast.makeText(getApplicationContext(), "Escolha uma atividade", Toast.LENGTH_LONG);
                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                    t.show();

                                    edt_ie.requestFocus();
                                } else
                                {
                                    if (spnr_tipologia.getSelectedItemPosition() < 1)
                                    {
                                        t = Toast.makeText(getApplicationContext(), "Escolha uma tipologia", Toast.LENGTH_LONG);
                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                        t.show();

                                        edt_ie.requestFocus();
                                    } else
                                    {
                                        if (spnr_cidade.getSelectedItemPosition() < 1)
                                        {
                                            t = Toast.makeText(getApplicationContext(), "Escolha uma cidade", Toast.LENGTH_LONG);
                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                            t.show();

                                            edt_ie.requestFocus();
                                        } else
                                        {
                                            if(edt_bairro.getText().length() < 3)
                                            {
                                                t = Toast.makeText(getApplicationContext(), "Campo bairro deve ser preenchido", Toast.LENGTH_LONG);
                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                t.show();

                                                edt_bairro.requestFocus();
                                            } else
                                            {
                                                if(edt_endereco.getText().length() < 3)
                                                {
                                                    t = Toast.makeText(getApplicationContext(), "Digite um endereco", Toast.LENGTH_LONG);
                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                    t.show();

                                                    edt_endereco.requestFocus();
                                                } else
                                                {
                                                    if(edt_numero.getText().length() < 1)
                                                    {
                                                        t = Toast.makeText(getApplicationContext(), "Numero de residencia incompleto", Toast.LENGTH_LONG);
                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                        t.show();

                                                        edt_numero.requestFocus();
                                                    } else
                                                    {
                                                        if(edt_cep.getText().length() < 4)
                                                        {
                                                            t = Toast.makeText(getApplicationContext(), "Cep invalido", Toast.LENGTH_LONG);
                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                            t.show();

                                                            edt_cep.requestFocus();
                                                        } else
                                                        {
                                                            if(edt_telefone.getText().length() < 7)
                                                            {
                                                                t = Toast.makeText(getApplicationContext(), "Digite um telefone para contato", Toast.LENGTH_LONG);
                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                t.show();

                                                                edt_telefone.requestFocus();
                                                            } else
                                                            {
                                                                if(edt_celular.getText().length() < 8 && obrigatorio)
                                                                {
                                                                    t = Toast.makeText(getApplicationContext(), "Digite um celular para contato", Toast.LENGTH_LONG);
                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                    t.show();

                                                                    edt_celular.requestFocus();
                                                                } else
                                                                {
                                                                    if(edt_contato.getText().length() < 3)
                                                                    {
                                                                        t = Toast.makeText(getApplicationContext(), "Digite o nome do comprador", Toast.LENGTH_LONG);
                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                        t.show();

                                                                        edt_contato.requestFocus();
                                                                    } else
                                                                    {
                                                                        if(edt_email.getText().length() < 4)
                                                                        {
                                                                            t = Toast.makeText(getApplicationContext(), "Digite o email da empreza", Toast.LENGTH_LONG);
                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                            t.show();

                                                                            edt_email.requestFocus();
                                                                        } else
                                                                        {
                                                                            if(edt_representante.getText().length() < 5)
                                                                            {
                                                                                t = Toast.makeText(getApplicationContext(), "Representante legal deve ser preenchido", Toast.LENGTH_LONG);
                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                t.show();

                                                                                edt_representante.requestFocus();
                                                                            } else
                                                                            {
                                                                                if(edt_cpf.getText().length() < 11 || !controle.validarDocumento(edt_cpf.getText().toString(), 0))
                                                                                {
                                                                                    t = Toast.makeText(getApplicationContext(), "Cpf do representante invalido", Toast.LENGTH_LONG);
                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                    t.show();

                                                                                    edt_cpf.requestFocus();
                                                                                } else
                                                                                {
                                                                                    if(edt_rg.getText().length() < 4)
                                                                                    {
                                                                                        t = Toast.makeText(getApplicationContext(), "Rg do representante invalido", Toast.LENGTH_LONG);
                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                        t.show();

                                                                                        edt_rg.requestFocus();
                                                                                    } else
                                                                                    {
                                                                                        if (spnr_natureza.getSelectedItemPosition() < 1)
                                                                                        {
                                                                                            t = Toast.makeText(getApplicationContext(), "Escolha uma natureza", Toast.LENGTH_LONG);
                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                            t.show();

                                                                                            edt_rg.requestFocus();
                                                                                        } else
                                                                                        {
                                                                                            if (spnr_banco.getSelectedItemPosition() < 1)
                                                                                            {
                                                                                                t = Toast.makeText(getApplicationContext(), "Escolha um banco", Toast.LENGTH_LONG);
                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                t.show();

                                                                                                edt_rg.requestFocus();
                                                                                            } else
                                                                                            {
                                                                                                if(edt_potencial.getText().length() < 3 && obrigatorio)
                                                                                                {
                                                                                                    t = Toast.makeText(getApplicationContext(), "Qual o potencial de compra?", Toast.LENGTH_LONG);
                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                    t.show();

                                                                                                    edt_potencial.requestFocus();
                                                                                                } else
                                                                                                {
                                                                                                    if(spnr_visita.getSelectedItemPosition() < 0)
                                                                                                    {
                                                                                                        t = Toast.makeText(getApplicationContext(), "Escolha um dia de visitas", Toast.LENGTH_LONG);
                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                        t.show();

                                                                                                        edt_area.requestFocus();
                                                                                                    } else
                                                                                                    {
                                                                                                        if(edt_area.getText().length() < 1 && obrigatorio)
                                                                                                        {
                                                                                                            t = Toast.makeText(getApplicationContext(), "Escolha uma natureza", Toast.LENGTH_LONG);
                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                            t.show();

                                                                                                            edt_area.requestFocus();
                                                                                                        } else
                                                                                                        {
                                                                                                            if(edt_roteiro.getText().length() < 1 && obrigatorio)
                                                                                                            {
                                                                                                                t = Toast.makeText(getApplicationContext(), "Escolha o roteiro", Toast.LENGTH_LONG);
                                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                t.show();

                                                                                                                edt_roteiro.requestFocus();
                                                                                                            } else
                                                                                                            {
                                                                                                                if(edt_com1.getText().length() < 5 && obrigatorio)
                                                                                                                {
                                                                                                                    t = Toast.makeText(getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                    t.show();

                                                                                                                    edt_com1.requestFocus();
                                                                                                                } else
                                                                                                                {
                                                                                                                    if(edt_com1_fone.getText().length() < 7 && obrigatorio)
                                                                                                                    {
                                                                                                                        t = Toast.makeText(getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                        t.show();

                                                                                                                        edt_com1_fone.requestFocus();
                                                                                                                    } else
                                                                                                                    {
                                                                                                                        if(edt_com2.getText().length() < 5 && obrigatorio)
                                                                                                                        {
                                                                                                                            t = Toast.makeText(getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                            t.show();

                                                                                                                            edt_com2.requestFocus();
                                                                                                                        } else
                                                                                                                        {
                                                                                                                            if(edt_com2_fone.getText().length() < 7 && obrigatorio)
                                                                                                                            {
                                                                                                                                t = Toast.makeText(getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                t.show();

                                                                                                                                edt_com2_fone.requestFocus();
                                                                                                                            } else
                                                                                                                            {
                                                                                                                                if(edt_com3.getText().length() < 5 && obrigatorio)
                                                                                                                                {
                                                                                                                                    t = Toast.makeText(getApplicationContext(), "Informe informacao comercial", Toast.LENGTH_LONG);
                                                                                                                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                    t.show();

                                                                                                                                    edt_com3.requestFocus();
                                                                                                                                } else
                                                                                                                                {
                                                                                                                                    if(edt_com3_fone.getText().length() < 7 && obrigatorio)
                                                                                                                                    {
                                                                                                                                        t = Toast.makeText(getApplicationContext(), "Telefone do estabelecimento de referencia", Toast.LENGTH_LONG);
                                                                                                                                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                        t.show();

                                                                                                                                        edt_com3_fone.requestFocus();
                                                                                                                                    } else
                                                                                                                                    {
                                                                                                                                        if(edt_com_data.getText().length() < 10 && obrigatorio)
                                                                                                                                        {
                                                                                                                                            t = Toast.makeText(getApplicationContext(), "Data abertura da empresa", Toast.LENGTH_LONG);
                                                                                                                                            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                                                                                                                            t.show();

                                                                                                                                            edt_com_data.requestFocus();
                                                                                                                                        }else
                                                                                                                                        {
                                                                                                                                            if(edt_obs.getText().length() < 5 && obrigatorio)
                                                                                                                                            {
                                                                                                                                                t = Toast.makeText(getApplicationContext(), "Campo observacao deve ser preenchido", Toast.LENGTH_LONG);
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

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){ solicitacao_sair(); }

        return super.onKeyDown(keyCode, event);
    }
/**************************************************************************************************/
}
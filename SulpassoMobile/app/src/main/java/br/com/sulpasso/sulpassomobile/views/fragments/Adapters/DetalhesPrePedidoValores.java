package br.com.sulpasso.sulpassomobile.views.fragments.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoItem;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;

/**
 * Created by Lucas on 08/01/2018 - 14:35 as part of the project SulpassoMobile.
 */
public class DetalhesPrePedidoValores extends BaseAdapter
{
    ArrayList<PrePedidoItem> array_produtos = new ArrayList<PrePedidoItem>();
    Context contexto;

    /**
     * @param array_produtos
     * @param contexto
     */
    public DetalhesPrePedidoValores(ArrayList<PrePedidoItem> array_produtos, Context contexto) 
    {
        super();
        this.array_produtos = array_produtos;
        this.contexto = contexto;

        /*
        if( StaticFields.valores_levantamento_pre_pedido == null)
        {
            StaticFields.valores_levantamento_pre_pedido = new ArrayList<Integer>();
            StaticFields.valores_levantamento_pre_pedido_sugestao = new ArrayList<Integer>();
        }


        for(int i = 0; i < array_produtos.size(); i++)
        {
            StaticFields.valores_levantamento_pre_pedido.add(0);
            StaticFields.valores_levantamento_pre_pedido_sugestao.add(0);
        }
        */
    }

    @Override
    public int getCount() { return array_produtos.size(); }

    @Override
    public Object getItem(int position) { return array_produtos.get(position); }

    @Override
    public long getItemId(int item_id) { return item_id; }

    @Override
    public View getView(final int position, View view, ViewGroup view_group)
    {
        final PrePedidoItem prod = array_produtos.get(position);
        LayoutInflater inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.linha_detalhes_pre_pedido_valores, null);

        TextView descricao = (TextView) v.findViewById(R.id.tv_lppv_descricao);
        TextView complemento = (TextView) v.findViewById(R.id.tv_lppv_complemento);
        TextView referencia = (TextView) v.findViewById(R.id.tv_lppv_referencia);

        TextView valor_tabela = (TextView) v.findViewById(R.id.tv_lppv_valor);
        TextView estoque = (TextView) v.findViewById(R.id.tv_lppv_estoque);

        TextView quantidade = (TextView) v.findViewById(R.id.tv_lppv_quantidade);
        TextView data =  (TextView) v.findViewById(R.id.tv_lppv_data);
        TextView nf =  (TextView) v.findViewById(R.id.tv_lppv_nf);
        EditText calcular = (EditText) v.findViewById(R.id.edt_lppv_calcular);
        final TextView sugestao =  (TextView) v.findViewById(R.id.tv_lppv_sugestao);
        Button btn = (Button) v.findViewById(R.id.btn_lppv_inserir);

        //     codigo.setText(prod.getCodigo());
        //     referencia.setText(prod.getReferencia());
        descricao.setText(prod.getDescricao());

        //complemento.setText("" + prod.getComplemento() );
        //referencia.setText(prod.getReferencia());
        //estoque.setText("E.  " + prod.getEstoque());

        valor_tabela.setText("V.  " + Formatacao.format2d(prod.getValorDigitado()));
        quantidade.setText("Q.  " + prod.getQuantidade());
        data.setText("" + prod.getData());

        //nf.setText("" + prod.getNf());
        sugestao.setText("" + prod.getQuantidade());

        //calcular.setText(StaticFields.valores_levantamento_pre_pedido == null ? "0" : String.valueOf(StaticFields.valores_levantamento_pre_pedido.get(position)));
        //sugestao.setText(StaticFields.valores_levantamento_pre_pedido_sugestao == null ? "0" : String.valueOf(StaticFields.valores_levantamento_pre_pedido_sugestao.get(position)));

        calcular.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0)
            {
                if(arg0.length() > 0)
                {
                    try
                    {
                        int calc = Integer.parseInt(arg0.toString());
                        sugestao.setText("" + (prod.getQuantidade() - calc));

                        //StaticFields.valores_levantamento_pre_pedido.set(position, calc);
                        //StaticFields.valores_levantamento_pre_pedido_sugestao.set(position, (int)(prod.getQuantidade() - calc));
                    }
                    catch (Exception e) { sugestao.setText("0"); }
                }
                else
                { sugestao.setText("" + prod.getQuantidade()); }
            }
        });

        /*
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                Mensagens msg = new Mensagens(contexto);

                try
                {
                    if (StaticFields.getAbrir_pedido())
                    {
                        if (StaticFields.getTipo_venda() == 2)
                        {
                            if (StaticFields.valida_venda.getDevedor() == 0)
                            {
                                if ((StaticFields.cliente_pedido.getSituacao().toUpperCase().trim().equals("D") ||
                                        StaticFields.cliente_pedido.getSituacao().toUpperCase().trim().equals("B")))
                                {
                                    msg.tipo_mensagem(2, "", "Nao e permitido efetuar vendas para clientes devedores ou bloqueados.");
                                } else
                                {
                                    StaticFields.setCOD_PRODUTO(prod.getCodigo());
                                    StaticFields.posicao_levantamento = position;
                                    Intent i = new Intent(contexto, Pedidos.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    StaticFields.setPosicao_ultimo_item_selecionado(position);
                                    contexto.getApplicationContext().startActivity(i);
                                }
                            } else
                            {
                                StaticFields.setCOD_PRODUTO(prod.getCodigo());
                                StaticFields.posicao_levantamento = position;
                                Intent i = new Intent(contexto, Pedidos.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                StaticFields.setPosicao_ultimo_item_selecionado(position);
                                contexto.getApplicationContext().startActivity(i);
                            }
                        } else
                        {
                            StaticFields.setCOD_PRODUTO(prod.getCodigo());
                            StaticFields.posicao_levantamento = position;
                            Intent i = new Intent(contexto, Pedidos.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            StaticFields.setPosicao_ultimo_item_selecionado(position);
                            contexto.getApplicationContext().startActivity(i);
                        }
                    }
                }
                catch (Exception e) { msg.tipo_mensagem(1, "ERRO", "" + e.getMessage()); }
            }
        });

        for (int i = 0; i < StaticFields.lista_produtos.size(); i++)
        {
            if (prod.getCodigo() == StaticFields.lista_produtos.get(i).getCodigo())
            {
				/*descricao.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));
				complemento.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));
				codigo.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));
				estoque.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));
				valor.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));*
                v.setBackgroundColor(contexto.getResources().getColor(R.color.LawnGreen));
                break;
            }
            else { v.setBackgroundColor(Color.TRANSPARENT); }
        }
        */


        return v;
    }

}

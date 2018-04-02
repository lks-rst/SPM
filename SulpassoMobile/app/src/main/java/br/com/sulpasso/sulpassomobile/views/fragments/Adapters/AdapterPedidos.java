package br.com.sulpasso.sulpassomobile.views.fragments.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 27/03/2018 - 08:34 as part of the project SulpassoMobile.
 */
public class AdapterPedidos extends BaseAdapter
{
    private ArrayList<Venda> lista;
    private LayoutInflater mInflater;

    public AdapterPedidos(Context ctx, ArrayList<Venda> pedios)
    {
        this.lista = pedios;
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return this.lista.size(); }

    @Override
    public Object getItem(int position) { return this.lista.get(position); }

    @Override
    public long getItemId(int position) { return this.lista.get(position).getCodigo(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        final Venda mensagem  = (Venda) getItem(position);

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_consulta_pedidos, null);
            holder.codPedido = (TextView) convertView.findViewById(R.id.lblVdaCod);
            holder.cliente = (TextView) convertView.findViewById(R.id.lblVdaCli);
            holder.data = (TextView) convertView.findViewById(R.id.lblVdaData);
            holder.valor = (TextView) convertView.findViewById(R.id.lblVdaValor);
            holder.desconto = (TextView) convertView.findViewById(R.id.lblVdaDesc);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        ManipulacaoStrings ms = new ManipulacaoStrings();

        holder.codPedido.setText(String.valueOf(mensagem.getCodigo()));
        holder.cliente.setText(mensagem.getCliente().getRazao());
        holder.data.setText(ms.dataVisual(mensagem.getData()));
        holder.valor.setText(Formatacao.format2d(mensagem.getValor()));
        holder.desconto.setText(Formatacao.format2d(mensagem.getDesconto()));

        if(mensagem.getEnviado() == 0)
        {
            holder.codPedido.setBackgroundColor(Color.RED);
            holder.cliente.setBackgroundColor(Color.RED);
            holder.data.setBackgroundColor(Color.RED);
            holder.valor.setBackgroundColor(Color.RED);
            holder.desconto.setBackgroundColor(Color.RED);
        }
        else
        {
            holder.codPedido.setBackgroundColor(Color.BLUE);
            holder.cliente.setBackgroundColor(Color.BLUE);
            holder.data.setBackgroundColor(Color.BLUE);
            holder.valor.setBackgroundColor(Color.BLUE);
            holder.desconto.setBackgroundColor(Color.BLUE);
        }

        return convertView;
    }

    /***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView codPedido;
        private TextView cliente;
        private TextView data;
        private TextView valor;
        private TextView desconto;
    }
}
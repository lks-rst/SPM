package br.com.sulpasso.sulpassomobile.controle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;

/**
 * Created by Lucas on 08/01/2019 - 10:05 as part of the project SulpassoMobile.
 */
public class AdapterItensPedido extends BaseAdapter
{
    private ArrayList<Gravosos> lista;
    private LayoutInflater mInflater;

    public AdapterItensPedido(Context ctx, ArrayList<Gravosos> mensagens)
    {
        this.lista = mensagens;
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return this.lista.size(); }

    @Override
    public Object getItem(int position) { return this.lista.get(position); }

    @Override
    public long getItemId(int position) { return this.lista.get(position).getItem().getCodigo(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        final Gravosos mensagem  = (Gravosos) getItem(position);

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.altered_list_item, null);
            holder.codigo = (TextView) convertView.findViewById(R.id.lblAliCod);
            holder.referencia = (TextView) convertView.findViewById(R.id.lblAliRef);
            holder.coplemento = (TextView) convertView.findViewById(R.id.lblAliComp);
            holder.descricao = (TextView) convertView.findViewById(R.id.lblAliDesc);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        if(mensagem.getSold())
            convertView.setBackgroundResource(R.color.LightGreen);
        else
            convertView.setBackgroundResource(R.color.bgColor);

        holder.codigo.setText(String.valueOf(mensagem.getItem().getCodigo()));
        holder.referencia.setText(mensagem.getItem().getReferencia());
        holder.descricao.setText(mensagem.getItem().getDescricao().toUpperCase());
        holder.coplemento.setText(mensagem.getItem().getComplemento());

        return convertView;
    }

/***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView codigo;
        private TextView referencia;
        private TextView descricao;
        private TextView coplemento;
    }
}

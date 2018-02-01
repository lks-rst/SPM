package br.com.sulpassomobile.sulpasso.sulpassomobile.controle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Mensagem;

/**
 * Created by Lucas on 20/03/2017 - 14:54 as part of the project SulpassoMobile.
 */
public class AdapterMensagens extends BaseAdapter
{
    private ArrayList<Mensagem> lista;
    private LayoutInflater mInflater;

    public AdapterMensagens(Context ctx, ArrayList<Mensagem> mensagens)
    {
        this.lista = mensagens;
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
        final Mensagem mensagem  = (Mensagem) getItem(position);

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.mensagens, null);
            holder.usuario = (TextView) convertView.findViewById(R.id.lblUsrMessage);
            holder.envio = (TextView) convertView.findViewById(R.id.lblEnvio);
            holder.validade = (TextView) convertView.findViewById(R.id.lblValidade);
            holder.assunto = (TextView) convertView.findViewById(R.id.lblAssunto);
            holder.mensagem = (EditText) convertView.findViewById(R.id.lblMensagem);
            holder.imagem = (ImageView) convertView.findViewById(R.id.imgPlus);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        holder.usuario.setText(mensagem.getUsuario());
        holder.envio.setText(mensagem.getEnvio());
        holder.validade.setText(mensagem.getValidade());
        holder.assunto.setText(mensagem.getAssunto().toUpperCase());
        holder.mensagem.setText(mensagem.getMensagem());

        holder.mensagem.setVisibility(holder.mensagem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        holder.imagem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.mensagem.setVisibility(holder.mensagem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        return convertView;
    }

/***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView usuario;
        private TextView envio;
        private TextView validade;
        private TextView assunto;
        private EditText mensagem;
        private ImageView imagem;
    }
}

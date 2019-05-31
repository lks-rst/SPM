package br.com.sulpasso.sulpassomobile.views.fragments.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 29/05/2019 - 17:21 as part of the project SulpassoMobile.
 */
public class AdapterTitulosAbertos extends BaseAdapter
{
    private ArrayList<String> lista;
    private LayoutInflater mInflater;

    public AdapterTitulosAbertos(Context ctx, ArrayList<String> mensagens)
    {
        this.lista = mensagens;
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return this.lista.size(); }

    @Override
    public Object getItem(int position) { return this.lista.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        String[] dados = ((String) getItem(position)).split(" - ");
        String data = dados[2];
        Boolean limpo;

        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date aday = null;

        try
        {
            aday = sf.parse(data);

            if (today.compareTo(aday) < 0)
                limpo = false;
            else
                limpo = true;
        }
        catch (Exception e)
        {
            aday = today;
            limpo = true;
            e.printStackTrace();
        }

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.linha_titulos_abertos, null);

            holder.campo = (TextView) convertView.findViewById(R.id.lblTitulo);
            holder.linha = (TextView) convertView.findViewById(R.id.underLine);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        holder.campo.setText((String) getItem(position));

//        holder.campo.setTextColor(Color.BLACK);
        if(limpo)
        {
            holder.linha.setBackgroundColor(Color.RED);
        }
        else
        {
            holder.linha.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    /***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView campo;
        private TextView linha;
    }
}
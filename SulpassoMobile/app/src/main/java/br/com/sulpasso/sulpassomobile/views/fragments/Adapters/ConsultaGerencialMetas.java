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
import br.com.sulpasso.sulpassomobile.modelo.Meta;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;

/**
 * Created by Lucas on 01/03/2018 - 10:07 as part of the project SulpassoMobile.
 */
public class ConsultaGerencialMetas extends BaseAdapter
{
    private ArrayList<Meta> lista;
    private float ideal;

    private LayoutInflater mInflater;

    public ConsultaGerencialMetas(Context ctx, ArrayList<Meta> metas, float ideal)
    {
        this.lista = metas;
        this.mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ideal = ideal;
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
        final Meta meta  = (Meta) getItem(position);

        float pcli = 0;
        float ppeso = 0;
        float pcont = 0;

        int pColor = 0;
        int[] colors = new int[] {Color.rgb(0,139,139), Color.rgb(238, 0, 0) };

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.linha_metas, null);
            holder.familia = (TextView) convertView.findViewById(R.id.lblFamMeta);
            holder.peso = (TextView) convertView.findViewById(R.id.lblPesMeta);
            holder.cliente = (TextView) convertView.findViewById(R.id.lblCliMeta);
            holder.contribuicao = (TextView) convertView.findViewById(R.id.lblContMeta);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        try { pcli = ((meta.getRealizado_c() * 100) / meta.getMeta_c()); }
        catch (Exception e) { pcli = -1; }

        try { ppeso = ((meta.getRealizado_v() * 100) / meta.getMeta_v()); }
        catch (Exception e) { ppeso = -1; }

        try { pcont = ((meta.getRealizado_co() * 100) / meta.getMeta_co()); }
        catch (Exception e) { pcont = -1; }

        holder.familia.setText(meta.getFamilia().toUpperCase());
        holder.peso.setText(Formatacao.format2d(ppeso));
        holder.cliente.setText(Formatacao.format2d(pcli));
        holder.contribuicao.setText(Formatacao.format2d(pcont));

        if (ppeso > this.ideal){ pColor = 0; } else{ pColor = 1; }

        holder.familia.setTextColor(colors[pColor]);
        holder.peso.setTextColor(colors[pColor]);
        holder.cliente.setTextColor(colors[pColor]);
        holder.contribuicao.setTextColor(colors[pColor]);

        return convertView;
    }

    /***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView familia;
        private TextView peso;
        private TextView cliente;
        private TextView contribuicao;
    }
}
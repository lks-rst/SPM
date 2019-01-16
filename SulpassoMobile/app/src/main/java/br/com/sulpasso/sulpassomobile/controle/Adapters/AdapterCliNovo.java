package br.com.sulpasso.sulpassomobile.controle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 08/01/2019 - 17:44 as part of the project SulpassoMobile.
 */
public class AdapterCliNovo extends BaseAdapter
{
    private ArrayList<String> lista;
    private LayoutInflater mInflater;

    public AdapterCliNovo(Context ctx, ArrayList<String> mensagens)
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
        final String clienteNovo = getItem(position).toString();

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.altered_list_item, null);
            holder.codigo = (TextView) convertView.findViewById(R.id.lblAliCod);

            convertView.findViewById(R.id.lblAliRef).setVisibility(View.GONE);
            convertView.findViewById(R.id.lblAliDesc).setVisibility(View.GONE);
            convertView.findViewById(R.id.lblAliComp).setVisibility(View.GONE);

            convertView.setTag(holder);
        }
        else { holder = (ViewHolder) convertView.getTag(); }

        if(Integer.parseInt(clienteNovo.substring(0, 1)) == 1)
            convertView.setBackgroundResource(R.color.LightGreen);
        else
            convertView.setBackgroundResource(R.color.bgColor);

        holder.codigo.setText(clienteNovo.substring(1));


        return convertView;
    }

    /***********************************************************************************************************************************/
    private static class ViewHolder
    {
        private TextView codigo;
    }
}

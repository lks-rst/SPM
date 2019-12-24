package br.com.sulpasso.sulpassomobile.util.customViews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 11/09/2019 - 15:43 as part of the project SulpassoMobile.
 */
public class SpinnerFilter extends LinearLayout
{
    EditText edtFilter;
    Spinner spnrFilter;

    ArrayList<String> full;
    ArrayList<String> source;

    ArrayAdapter adapter;

    public SpinnerFilter(Context context)
    {
        super(context);
        this.init(context);
    }

    public SpinnerFilter(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.init(context);
    }

    private void init(Context context)
    {
        inflate(context, R.layout.spiner_filter, this);

        edtFilter = (EditText) findViewById(R.id.filterBusca);
        spnrFilter = (Spinner) findViewById(R.id.filterLista);

        edtFilter.addTextChangedListener(toFilter);
    }

    public void AddList(Context ctx, ArrayList<String> list)
    {
        this.full = list;
        toFull();

        /*
        ArrayList<String> itens = new ArrayList<>();

        for(int i = 0; i < source.size(); i++)
        {
            itens.add(source.get(i).toString());
        }
        */
    }

    private TextWatcher toFilter = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().length() >= 1)
            {
                filter(s.toString());
            }
            else
            {
                Toast.makeText(getContext(), "A consulta não pode ser realizada apenas com espaço", Toast.LENGTH_LONG).show();
                toFull();
            }
        }
    };

    protected void filter(String pattern)
    {
        this.source.clear();

        for(int i = 0; i < this.full.size(); i++)
        {
            if(this.full.get(i).indexOf(pattern) != -1)
                this.source.add(this.full.get(i));
        }

        this.adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, source);
        this.adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.spnrFilter.setAdapter(this.adapter);
    }

    private void toFull()
    {
        this.source = full;
        this.adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, source);
        this.adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.spnrFilter.setAdapter(this.adapter);
    }
}
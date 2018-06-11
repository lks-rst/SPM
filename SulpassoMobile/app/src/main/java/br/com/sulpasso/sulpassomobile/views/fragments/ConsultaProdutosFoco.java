package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultaFoco;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Foco;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertItensFoco;

/**
 * Created by Lucas on 05/06/2018 - 11:10 as part of the project SulpassoMobile.
 */
public class ConsultaProdutosFoco extends Fragment implements AlertItensFoco.CallbackFoco
{
    private ConsultaFoco consulta;

    public ConsultaProdutosFoco(){}
    /**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        consulta = new ConsultaFoco(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_itens_foco, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        ((getActivity().findViewById(R.id.fciBtnData))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItensFoco();
            }
        });

        ((ListView) (getActivity().findViewById(R.id.fciLvFoco))).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                consulta.removerItem(position);

                try { listarItens(); }
                catch (GenercicException e) { e.printStackTrace(); }

                return true;
            }
        });

        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }


    public void listarItens() throws GenercicException
    {
        ArrayList<String> dados = new ArrayList<>();
        ArrayList<Foco> itens = new ArrayList<>();

        itens = this.consulta.buscarFoco();

        for(Foco f : itens) { dados.add(f.toDisplay()); }

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.default_list_item,
                dados);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) (getActivity().findViewById(R.id.fciLvFoco))).setAdapter(adapter);
    }

    private void adicionarItensFoco()
    {
        AlertItensFoco dialog = new AlertItensFoco();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public ArrayList<Item> buscarItens(String descricao)
    {
        return this.consulta.listarItens(descricao);
    }

    @Override
    public void isnerirItens(ArrayList<Item> foco)
    {
        Toast t = Toast.makeText(getActivity().getApplicationContext(), null, Toast.LENGTH_LONG);
        t.setText("A lista de itens a ser inseridos como foco possui " + foco.size() + " elementos.");
        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
        t.show();

        this.consulta.inserirItensFoco(foco);

        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }


/*********************************END OF ITERFACES METHODS*****************************************/
}
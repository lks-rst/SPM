package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;

public class ConsultasPedidos extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.inicial_agenda :

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
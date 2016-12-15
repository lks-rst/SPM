package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.AtualizarSistema;

public class Atualizacao extends AppCompatActivity
{
    private AtualizarSistema controleAtualizacao;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private ProgressBar mProgressTwo;
    private int mProgressStatusTwo = 0;

    protected boolean ignore;
    protected String displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizacao);

        mProgress = (ProgressBar) findViewById(R.id.progress_bar_full);
        mProgressTwo = (ProgressBar) findViewById(R.id.progress_bar_pw);
    }
/*

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        boolean result = true;

        switch( event.getKeyCode() )
        {
            case KeyEvent.KEYCODE_MENU:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (ignore) result = false;
                break;
            case KeyEvent.KEYCODE_BACK:
                if (ignore) result = false;
                break;
            default:
                result= super.dispatchKeyEvent(event);
                break;
        }

        return result;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_atualiza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.atualizar_vendas :
                break;
            case R.id.atualizar_sistema :
                this.controleAtualizacao = new AtualizarSistema(getApplicationContext());
                new Atualizar().execute();
                break;
            case R.id.atualizar_clientes :
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Atualizar extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            ignore = true;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            displayMessage = controleAtualizacao.atualizar(3);
            publishProgress();
            displayMessage = controleAtualizacao.atualizar(4);
            publishProgress();
            displayMessage = controleAtualizacao.atualizar(5);
            publishProgress();

            for(int i = 0; i < 100; i++)
            {
                displayMessage = controleAtualizacao.atualizar(6);
                publishProgress();
            }
            controleAtualizacao.finalizarTabelas();
            publishProgress();

            controleAtualizacao.criarArquivoErros();
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            atualizarLoadBar();
        }

        @Override
        protected void onPostExecute(Void result)
        {
            controleAtualizacao.verificarErros();

            ignore = false;
        }
    }

    protected void atualizarLoadBar()
    {
        if(this.controleAtualizacao.isTabelas())
        {
            mProgressStatusTwo += 1;
            mProgressTwo.setProgress(mProgressStatusTwo);
            ((TextView) findViewById(R.id.textProgressTwo)).setText(this.displayMessage);
        }
        else
        {
            mProgressStatus += 25;
            mProgress.setProgress(mProgressStatus);
            ((TextView) findViewById(R.id.textProgressOne)).setText(this.displayMessage);
        }
    }
}

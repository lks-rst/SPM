package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Inicial;

/**
 * Created by Lucas on 12/12/2016 - 10:40 as part of the project SulpassoMobile.
 */
public class InicialCliente extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inicial_cliente, null);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        try{ this.setUpLayout(); }
        catch (Exception e) { }
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        int version;

        try { version = Integer.valueOf(Build.VERSION.SDK); }
        catch (Exception ev){ version = 3; }

        File imgFile;

        if(version == 19) { imgFile = new File("/storage/emulated/0/MobileVenda/imagem/logo.jpg"); }
        else { imgFile = new File("sdcard/MobileVenda/imagem/logo.jpg"); }

        if(imgFile.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ((ImageView) (getActivity().findViewById(R.id.imgFicLogo)))
                    .setImageBitmap(myBitmap);
        } else
        {
            imgFile = new  File("sdcard/MobileVenda/imagem/logo.jpeg");
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ((ImageView) (getActivity().findViewById(R.id.imgFicLogo)))
                        .setImageBitmap(myBitmap);
            }
            else
            {
                imgFile = new  File("sdcard/MobileVenda/imagem/logo.png");
                if(imgFile.exists())
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ((ImageView) (getActivity().findViewById(R.id.imgFicLogo)))
                            .setImageBitmap(myBitmap);
                }
                else
                {
                    imgFile = new  File("sdcard/MobileVenda/imagem/logo.bmp");
                    if(imgFile.exists())
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ((ImageView) (getActivity().findViewById(R.id.imgFicLogo)))
                                .setImageBitmap(myBitmap);
                    }
                    else { /*****/ }
                }
            }
        }

        ((TextView) (getActivity().findViewById(R.id.txtFicNome))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicNome)));
        ((TextView) (getActivity().findViewById(R.id.txtFicSecond))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicSecond)));
        ((TextView) (getActivity().findViewById(R.id.txtFicFone))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicFone)));
        ((TextView) (getActivity().findViewById(R.id.txtFicMail))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicMail)));
        ((TextView) (getActivity().findViewById(R.id.txtFicWeb))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicWeb)));
        ((TextView) (getActivity().findViewById(R.id.txtFicVendedor))).setText(String
                .valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicVendedor)));
    }
}
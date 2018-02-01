package br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations;

/**
 * Created by Lucas on 21/03/2017 - 11:11 as part of the project SulpassoMobile.
 */
public enum TipoVenda
{
    NORMAL(0), DIRETA(1), TROCA(2);

    private int value;

    private TipoVenda(int value) { this.value = value; }

    public int getValue() { return this.value; }

    public static TipoVenda getTipoFromInt(int valor)
    {
        switch (valor)
        {
            case 0 :
                return NORMAL;
            case 1 :
                return DIRETA;
            case 2 :
                return TROCA;
            default:
                return NORMAL;
        }
    }
}
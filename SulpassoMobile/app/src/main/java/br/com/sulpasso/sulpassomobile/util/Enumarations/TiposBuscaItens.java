package br.com.sulpasso.sulpassomobile.util.Enumarations;

/**
 * Created by Lucas on 05/01/2018 - 09:12 as part of the project SulpassoMobile.
 */
public enum TiposBuscaItens
{
    DESC_ANY(2), DESC_INI(1), REF(3), GRUPO(4), GRAVOSOS(5), TODOS(0), MIX(6), PRE(7), FAIL(-1);

    private int value;

    private TiposBuscaItens(int value) { this.value = value; }

    public int getValue() { return this.value; }

    public static TiposBuscaItens getTipoFromInt(int valor)
    {
        switch (valor)
        {
            case -1 :
                return FAIL;
            case 0 :
                return TODOS;
            case 1 :
                return DESC_INI;
            case 2 :
                return DESC_ANY;
            case 3 :
                return REF;
            case 4 :
                return GRUPO;
            case 5 :
                return GRAVOSOS;
            case 6 :
                return MIX;
            case 7 :
                return PRE;
            default:
                return TODOS;
        }
    }
}
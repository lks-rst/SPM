package br.com.sulpasso.sulpassomobile.util.Enumarations;

/**
 * Created by Lucas on 03/08/2016.
 */
public enum TiposBuscaCliente
{
    TODOS(0), FANTASIA(1), RAZAO(2), CIDADE(3), AGENDA(4), VISITA(5), CODIGO(6);

    private int value;

    private TiposBuscaCliente(int value) { this.value = value; }

    public int getValue() { return this.value; }

    public static TiposBuscaCliente getTipoFromInt(int valor)
    {
        switch (valor)
        {
            case 0 :
                return TODOS;
            case 1 :
                return FANTASIA;
            case 2 :
                return RAZAO;
            case 3 :
                return CIDADE;
            case 4 :
                return AGENDA;
            case 5 :
                return VISITA;
            case 6 :
                return CODIGO;
            default:
                return TODOS;
        }
    }
}
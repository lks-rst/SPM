package br.com.sulpasso.sulpassomobile.util.modelos;

/**
 * Created by Lucas on 22/11/2016 - 11:19 as part of the project SulpassoMobile.
 */
public class GenericItemFour
{
    private String dataOne;
    private String dataTwo;
    private String dataThre;
    private String dataFour;

    public String getDataOne() { return dataOne; }

    public void setDataOne(String dataOne) { this.dataOne = dataOne; }

    public String getDataTwo() { return dataTwo; }

    public void setDataTwo(String dataTwo) { this.dataTwo = dataTwo; }

    public String getDataThre() { return dataThre; }

    public void setDataThre(String dataThre) { this.dataThre = dataThre; }

    public String getDataFour() { return dataFour; }

    public void setDataFour(String dataFour) { this.dataFour = dataFour; }

    @Override
    public String toString()
    {
        return "GenericItemFour{" +
                "dataOne='" + dataOne + '\'' +
                ", dataTwo='" + dataTwo + '\'' +
                ", dataThre='" + dataThre + '\'' +
                ", dataFour='" + dataFour + '\'' +
                '}';
    }

    public String toDisplay()
    {
        return dataOne + " - " + dataFour + " \nQtd.: " +  dataTwo + " Val.:" +  dataThre;
    }
}
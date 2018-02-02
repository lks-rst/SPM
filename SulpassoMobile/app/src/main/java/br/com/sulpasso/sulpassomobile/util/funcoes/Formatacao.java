package br.com.sulpasso.sulpassomobile.util.funcoes;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Formatacao
{
	private static NumberFormat nf = NumberFormat.getCurrencyInstance();

	static public String retira_mascara(String str)
	{
		boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
				(str.indexOf(".") > -1 || str.indexOf(",") > -1));
		// Verificamos se existe m�scara
		if (hasMask)
		{
			// Retiramos a m�scara.
			str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
					.replaceAll("[.]", "").replaceAll("[$]", "");
		}

		try
		{
			// Transformamos o n�mero que est� escrito no EditText em
			// monet�rio.
			str = nf.format(Double.parseDouble(str) / 100);
		}
		catch (NumberFormatException e) { str = ""; }

		return str;
	}

//	static DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
	static DecimalFormat df3 = new DecimalFormat("###,###,##0.000");
	static DecimalFormat df2d = new DecimalFormat("########0.00");
	static DecimalFormat df2 = new DecimalFormat("########0.00"/*, dfs*/);

	static public String format3(float numero, int tipo)
	{
		String val = "";
		if (tipo == 2)
		{
			try { val = df2.format(numero); }
			catch (Exception e) { val = "0.00"; }
		}
		else
		{
			try { val = df3.format(numero); }
			catch (Exception e) { val = "0,000"; }
		}
		return val;
	}
	
	static public String format2d(float numero)
	{
		String val = "";

		try { val = df2d.format(numero).replace(",", "."); }
		catch (Exception e) { val = "0.000"; }

		return val;
	}

	static public String format2d(double numero)
	{
		String val = "";

		try { val = df2d.format(numero).replace(",", "."); }
		catch (Exception e) { val = "0.000"; }

		return val;
	}
}
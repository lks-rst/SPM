package br.com.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public class InsertionExeption extends br.com.sulpasso.sulpassomobile.exeption.GenercicException {

	public InsertionExeption(String msg){
		super.message = msg;
	}
	
}

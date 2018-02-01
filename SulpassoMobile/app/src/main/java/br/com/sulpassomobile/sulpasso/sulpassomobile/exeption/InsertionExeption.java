package br.com.sulpassomobile.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public class InsertionExeption extends GenercicException {

	public InsertionExeption(String msg){
		super.message = msg;
	}
	
}

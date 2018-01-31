package br.com.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public class UpdateExeption extends br.com.sulpasso.sulpassomobile.exeption.GenercicException {

	public UpdateExeption(String msg){
		super.message = msg;
	}
	
}

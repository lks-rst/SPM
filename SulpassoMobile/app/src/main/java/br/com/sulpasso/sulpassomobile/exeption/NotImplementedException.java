package br.com.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public class NotImplementedException extends br.com.sulpasso.sulpassomobile.exeption.GenercicException {


	public NotImplementedException(){
		super.message = "NOT IMPLEMENTED EXCEPTION";
	}

	public NotImplementedException(String msg){
		super.message = msg;
	}
	
}

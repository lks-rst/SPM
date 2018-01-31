package br.com.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public abstract class GenercicException extends Exception{
	protected String message;
	
	public final String getMessage(){
		return this.message;
	}
	
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.exeption;

/**
 * Created by lucas on 04/02/16.
 */
@SuppressWarnings("serial")
public class UnsuportedOperationExeption extends GenercicException {

	public UnsuportedOperationExeption(String msg){
		super.message = msg;
	}
	
}

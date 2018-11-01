package br.com.sulpasso.sulpassomobile.util.visuals;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lucas on 30/10/2018 - 15:39 as part of the project SulpassoMobile.
 */
public abstract class CustomTextWatcher implements TextWatcher { //Notice abstract class so we leave abstract method textWasChanged() for implementing class to define it

    public final TextView myTextView; //Remember EditText is a TextView so this works for EditText also


    public CustomTextWatcher(TextView tView) { //Notice I'm passing a view at the constructor, but you can pass other variables or whatever you need
        myTextView= tView;

    }

    private Timer timer = new Timer();
    private final int DELAY = 500; //milliseconds of delay for timer

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        timer.cancel();
        timer = new Timer();

        timer.schedule(

                new TimerTask() {
                    @Override
                    public void run() {
                        textWasChanged(s.toString());
                    }
                },
                DELAY

        );
    }

    public abstract void textWasChanged(String text); //Notice abstract method to leave implementation to implementing class
}

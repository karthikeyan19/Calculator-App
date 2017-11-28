package com.karthik.hello;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;

public class CalculatorActivity extends ActionBarActivity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    private EditText inputEditText;
    private Button[] numButtons=new Button[10];
    private Button delButton;
    private Button dotButton;
    private Queue<String> operatorQueue=new LinkedList<>();
    private Queue<Double> operandQueue=new LinkedList<>();
    int[] numberId={R.id.button_zero,R.id.button_one,R.id.button_two,R.id.button_three,R.id.button_four,R.id.button_four,
              R.id.button_five,R.id.button_six,R.id.button_seven,R.id.button_eight,R.id.button_nine};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        inputEditText= (EditText) findViewById(R.id.edit_text_input);
        for(int num_i=0;num_i<10;num_i++){
            numButtons[num_i]= (Button) findViewById(numberId[num_i]);
            numButtons[num_i].setOnClickListener(this);
        }

//        inputEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//               inputEditText.setSelection(inputEditText.getText().length());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        dotButton= (Button) findViewById(R.id.button_dot);
        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputEditText.getText().toString();
                String[] operands = input.split("[-+×÷]");
                if(!operands[operands.length-1].contains(".")){
                    inputEditText.setText(inputEditText.getText().toString()+".");
                }
            }
        });
        delButton= (Button) findViewById(R.id.button_del);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Button)view).getText().toString().equals("DEL"))
                del();
                else{
                    inputEditText.setText(getResources().getString(R.string.zeo));
                    delButton.setText("DEL");
                }
            }
        });

        Button equalToButton= (Button) findViewById(R.id.button_equal_to);
        equalToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = inputEditText.getText().toString();
                if (operatorQueue.size() != 0) {

                    String[] operands = input.split("[-+×÷]");

                    for (String s : operands) {
                        Log.d("values", s);
                        if(!s.equals("."))
                        operandQueue.add(Double.parseDouble(s));
                    }
                    double result = 0;
                    int no_i;
                    int i;
                    double temp;
                    while (operandQueue.size() != 0) {
                        String opertor = operatorQueue.remove();
                        switch (opertor) {

                            case "+":
                                 no_i=(result==0)?2:1;
                                 i=0;
                                while (operandQueue.size()!=0&&i<no_i){
                                   double a = operandQueue.remove();
                                    result = result + a;
                                    i++;
                                }
                                break;
                            case "-":
                                no_i=(result==0)?2:1;
                               temp=0;
                                if(result==0){
                                    temp=operandQueue.remove();

                                }if(no_i==2) {
                                   result=temp-operandQueue.remove();
                                }else
                                {
                                    result-=operandQueue.remove();
                                }

                                break;
                            case "×":
                                no_i=(result==0)?2:1;
                               temp=0;
                                if(result==0){
                                    temp=operandQueue.remove();

                                }if(no_i==2) {
                                   result=temp*operandQueue.remove();
                                 }else
                                 {
                                   result*=operandQueue.remove();
                                 }
                            break;
                            case "÷":
                                no_i=(result==0)?2:1;
                                 temp=0;
                                if(result==0){
                                    temp=operandQueue.remove();

                                }if(no_i==2) {
                                result=temp/operandQueue.remove();
                            }else
                            {
                                result/=operandQueue.remove();
                            }
                                break;
                        }
                    }
                    operandQueue.clear();
                    operatorQueue.clear();
                    inputEditText.setText(printResult(result+""));
                    delButton.setText(getString(R.string.clear_lbl));
                }
            }
        });


    }

    private String printResult(String result) {
        Log.d("result",result);
        if(result.contains(".")){
        String[] decimal=result.split("[.]");
          if(Double.parseDouble(decimal[1])>0)
             return result;
          else
             return decimal[0];
        }
        else{
            return result;
        }
    }

    @Override
    public android.support.v4.app.FragmentManager getSupportFragmentManager() {
        return null;
    }

   public void del(){
       if(!checkForZero()){
           String input=inputEditText.getText().toString();
           if(input.length()!=1){
               inputEditText.setText(input.substring(0,input.length()-1));
           }
           else {
               inputEditText.setText(R.string.zeo);
           }
       }
   }
    public void onOperatorClick(View view){
              delButton.setText(getString(R.string.delete_lbl));
              String s1=inputEditText.getText().toString();
              s1=s1.charAt(s1.length()-1)+"";
              String s2=((Button)view).getText()+"";
              if(s1.matches("[-+*/]")){
                  operatorQueue.remove();
                  operatorQueue.add(s2);
                  del();
              }else {
                  operatorQueue.add(s2);
              }
        inputEditText.setText(inputEditText.getText()+s2);
    }

    @Override
    public void onClick(View view)  {
        delButton.setText(getString(R.string.delete_lbl));
          if(!checkForZero()){
                   inputEditText.setText(inputEditText.getText().toString()
                           +((Button)view).getText());
                }else {
                     String input=((Button)view).getText()+"";
                     if (!input.equals(getResources().getString(R.string.zeo)))
                      inputEditText.setText(input+"");
                }
    }

    private boolean checkForZero() {
        if (inputEditText.getText().length()==1)
        try {
            double no=Double.parseDouble(inputEditText.getText().toString());
            return no == 0;

        }catch (NumberFormatException exception){
              return false;
        }
        else{
            return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}

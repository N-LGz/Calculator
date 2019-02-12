package calculator.android.fr.pogcalculator;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Calculator extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul;

    EditText displayOp, displayRes;

    float valueOne=0, valueTwo=0;
    char operator = 'h';

    boolean ifAddition, ifSubtract, ifMultiplication, ifDivision;

    Handler handler_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.zero);
        button1 = findViewById(R.id.un);
        button2 = findViewById(R.id.deux);
        button3 = findViewById(R.id.trois);
        button4 = findViewById(R.id.quatre);
        button5 = findViewById(R.id.cinq);
        button6 = findViewById(R.id.six);
        button7 = findViewById(R.id.sept);
        button8 = findViewById(R.id.huit);
        button9 = findViewById(R.id.neuf);
        buttonAdd = findViewById(R.id.additionne);
        buttonSub = findViewById(R.id.soustrait);
        buttonMul = findViewById(R.id.multiplie);
        buttonDivision = findViewById(R.id.divise);

        displayOp = findViewById(R.id.operand);
        displayRes = findViewById(R.id.result);

        handler_res = new Handler();
    }

    public void Operand(View view){
        switch(view.getId()) {
            case R.id.zero:
                displayOp.setText(displayOp.getText() + "0");
                break;

            case R.id.un:
                displayOp.setText(displayOp.getText() + "1");
                break;

            case R.id.deux:
                displayOp.setText(displayOp.getText() + "2");
                break;

            case R.id.trois:
                displayOp.setText(displayOp.getText() + "3");
                break;

            case R.id.quatre:
                displayOp.setText(displayOp.getText() + "4");
                break;

            case R.id.cinq:
                displayOp.setText(displayOp.getText() + "5");
                break;

            case R.id.six:
                displayOp.setText(displayOp.getText() + "6");
                break;

            case R.id.sept:
                displayOp.setText(displayOp.getText() + "7");
                break;

            case R.id.huit:
                displayOp.setText(displayOp.getText() + "8");
                break;

            case R.id.neuf:
                displayOp.setText(displayOp.getText() + "9");
                break;

            case R.id.additionne:
                try {
                    valueOne = Float.parseFloat(displayOp.getText() + "");
                    ifAddition = true;
                    displayOp.setText(null);
                    operator='+';
                } catch (NumberFormatException e) { displayOp.setText(""); }
                break;

            case R.id.soustrait:
                try {
                    valueOne = Float.parseFloat(displayOp.getText() + "");
                    ifSubtract = true;
                    displayOp.setText(null);
                    operator='-';
                } catch (NumberFormatException e) { displayOp.setText(""); }
                break;

            case R.id.multiplie:
                try {
                    valueOne = Float.parseFloat(displayOp.getText() + "");
                    ifMultiplication = true;
                    displayOp.setText(null);
                    operator='-';
                } catch (NumberFormatException e) { displayOp.setText(""); }
                break;

            case R.id.divise:
                try {
                    valueOne = Float.parseFloat(displayOp.getText() + "");
                    ifDivision = true;
                    displayOp.setText(null);
                    operator='/';
                } catch (NumberFormatException e) { displayOp.setText(""); }
                break;
        }

        LinearLayout right_row = findViewById(R.id.rightrow);
        if(ifAddition || ifSubtract || ifMultiplication || ifDivision){
            Button buttonEqual = new Button(this);
            buttonEqual.setHeight(right_row.getHeight());
            buttonEqual.setWidth(right_row.getWidth());
            buttonEqual.setText("=");
            buttonEqual.setOnClickListener(equal);
            right_row.addView(buttonEqual);
        }
    }

    View.OnClickListener equal = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            valueTwo = Float.parseFloat(displayOp.getText() + "");
            Runnable runnable = () -> {
                try {
                    Socket s = new Socket("192.168.43.188", 9876);
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    dos.writeDouble(valueOne);
                    dos.writeChar(operator);
                    dos.writeDouble(valueTwo);


                    Double res  = dis.readDouble();
                    displayRes.setText(res + "");

                } catch (IOException e) {
                    finish();
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();
        }
    };
}

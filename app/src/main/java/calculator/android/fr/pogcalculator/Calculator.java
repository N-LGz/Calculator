package calculator.android.fr.pogcalculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Calculator extends AppCompatActivity implements Operand_Fragment.FragmentActivity, DisplayFragment.FragmentActivity {

    private static Calculator sInstance = null;


    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, buttonEqual;

    EditText displayOp, displayRes;

    float valueOne=0, valueTwo=0;

    char operator = 'o';

    boolean ifAddition, ifSubtract, ifMultiplication, ifDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sInstance = this;

        DisplayFragment display = new DisplayFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.display_layout, display).commit();

        Operand_Fragment opFragment = new Operand_Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.operand_layout, opFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu:
                    Intent intent = new Intent(this, NewActivity.class);
                    startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            case R.id.equal:
                try{
                    valueTwo = Float.parseFloat(displayOp.getText().toString());
                    new Task().execute();
                } catch (NumberFormatException e) { displayOp.setText(""); }
        }
    }

    class Task extends AsyncTask<Void,Void,Double> {
        @Override
        protected Double doInBackground(Void... voids) {
            try {
                Socket s = new Socket("192.168.43.188", 9876);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                dos.writeDouble(valueOne);
                dos.writeChar(operator);
                dos.writeDouble(valueTwo);

                return dis.readDouble();

            } catch (IOException e) {
                Log.e("Calculator", "Impossible de se connecter", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Double res) {
            if (res != null) {
                displayRes.setText(res.toString());
            }
        }
    }

    @Override
    public void sendID() {
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
        buttonEqual = findViewById(R.id.equal);
        displayOp = findViewById(R.id.operand);
        displayRes = findViewById(R.id.result);
    }
}

package calculator.android.fr.pogcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NewActivity extends AppCompatActivity implements AckFragment.OnFragmentInteractionListener {

    EditText text, ackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        text = findViewById(R.id.display_text);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ackText.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AckFragment ack = new AckFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.AckLayout, ack).commit();
    }

    @Override
    public void onFragmentInteraction() {
        ackText = findViewById(R.id.ackText);
    }
}

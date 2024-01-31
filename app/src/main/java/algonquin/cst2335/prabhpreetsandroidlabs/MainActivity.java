package algonquin.cst2335.prabhpreetsandroidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    TextView mytext;
    EditText myedit;

    Button btn;

    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        mytext = variableBinding.textView;
        myedit = variableBinding.myedittext;
        btn = variableBinding.button;

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){

                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has : " + editString);

            }
        });

    }
}
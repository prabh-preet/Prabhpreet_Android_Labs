package algonquin.cst2335.prabhpreetsandroidlabs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import android.text.Html;

import algonquin.cst2335.prabhpreetsandroidlabs.data.MainViewModel;
import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private ActivityMainBinding variableBinding;
    TextView mytext;
    EditText myedit;
    Button btn;
    ImageButton img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        mytext = variableBinding.textView;
        myedit = variableBinding.myedittext;
        btn = variableBinding.button;
        img = variableBinding.myimagebutton;

        // Initialize ViewModel
        model = new ViewModelProvider(this).get(MainViewModel.class);

        model.editString.observe(this,s -> {

            variableBinding.textView.setText("Welcome "+model.editString.getValue());

        });

        btn=variableBinding.button;

        btn.setOnClickListener((View v)->{

            model.editString.postValue( variableBinding.myedittext.getText().toString());


        });
        /*btn.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){

                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has : " + editString);

            }
        });*/

        // Observe the LiveData
        model.isSelected.observe(this,selected-> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
        });


        variableBinding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toast.makeText(MainActivity.this, "Meme---- "+model.getIsSelected().getValue(), Toast.LENGTH_SHORT).show();

            //Respond to the checked state change
            if (isChecked) {
                // Checkbox is checked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> Checkbox is Checked </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            } else {
                // Checkbox is unchecked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> Checkbox is Unchecked </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            }


        });
        variableBinding.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toast.makeText(MainActivity.this, "Meme---- "+model.getIsSelected().getValue(), Toast.LENGTH_SHORT).show();

            //Respond to the checked state change
            if (isChecked) {
                // Checkbox is checked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> RadioButton is Checked </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            } else {
                // Checkbox is unchecked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> RadioButton is Unchecked </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            }


        });
        variableBinding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toast.makeText(MainActivity.this, "Meme---- "+model.getIsSelected().getValue(), Toast.LENGTH_SHORT).show();

            //Respond to the checked state change
            if (isChecked) {
                // Checkbox is checked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> Switch is On </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            } else {
                // Checkbox is unchecked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b> Switch is Off </b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            }


        });

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int width = img.getWidth();
                int height = img.getHeight();

                // Show Toast message when ImageButton is clicked
                Toast toast = Toast.makeText(MainActivity.this, Html.fromHtml("<font color='#FFFFFF' ><b>The width = "+ width + " and height = "+ height + "</b></font>"), Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#000000"));
                toast.show();
            }
        });
    }
}
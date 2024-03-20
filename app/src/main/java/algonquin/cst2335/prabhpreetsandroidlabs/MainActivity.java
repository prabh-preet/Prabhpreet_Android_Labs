package algonquin.cst2335.prabhpreetsandroidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the application for checking the password that it fulfills the requirements
 * or not. So, it will show that your password is successful or not successful with the
 * messages at the bottom of the screen showing what is needed to complete the password.
 * @author Prabhpreet Kaur
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the label at the centre of the screen */
    private TextView box1 = null;

    /** This holds the password below the label */
    private EditText box2 = null;

    /** This holds the button at the bottom of the screen */
    private Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        box1 = findViewById(R.id.textView);
        box2 = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener( clk -> {
            String password = box2.getText().toString();

            if(checkPasswordComplexity(password)){
                box1.setText("Your password meets the requirements");
            }
            else{
                box1.setText("You shall not pass!");
            }
        });

    }

    /**
     * This method is designed to check if the password is having a special character.
     * @param c: The character object that we are checking
     * @return Returns true if the password has special character and false it not having it
     */
    public static boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;

        }
    }

    /**
     * This method checks all the requirements of the password are fulfilled.
     * It shows a message explaining what is missing if not met the requirements.
     * @param pw: The string object that we are checking
     * @return Returns true if the password is complex otherwise if not it will return false
     */
    private boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i = 0; i < pw.length(); i++){
            char c = pw.charAt(i);
            if(Character.isUpperCase(c)){
                foundUpperCase = true;
            }
            else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            }
            else if(Character.isDigit(c)) {
                foundNumber = true;
            }
            else if(isSpecialCharacter(c)){
                foundSpecial = true;
            }
        }

        // Checking if the password is not matching the requirements and displays a toast.
        if(!foundUpperCase)
        {
            Toast.makeText(this, "Missing an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundLowerCase)
        {
            Toast.makeText(this, "Missing an lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundNumber) {
            Toast.makeText(this, "Missing a number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundSpecial) {
            Toast.makeText(this, "Missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}
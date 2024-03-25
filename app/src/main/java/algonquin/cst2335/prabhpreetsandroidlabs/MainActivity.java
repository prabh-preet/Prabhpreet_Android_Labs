package algonquin.cst2335.prabhpreetsandroidlabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected RequestQueue queue = null;
    final protected  String API_KEY="36b4329a4cb275ace057179797f76ed4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = "";


            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" +  URLEncoder.encode(cityName, "UTF-8") +
                        "&appid=" + API_KEY + "&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> { },
                    (error) -> {  } );
            queue.add(request);
        });
    }
}
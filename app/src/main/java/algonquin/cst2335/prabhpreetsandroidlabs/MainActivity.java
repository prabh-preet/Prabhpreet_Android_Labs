package algonquin.cst2335.prabhpreetsandroidlabs;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    protected RequestQueue queue = null;
    protected String cityName;
    final protected String API_KEY="36b4329a4cb275ace057179797f76ed4";
    private ImageLoader imageLoader;
    private JSONObject response;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.forecastButton.setOnClickListener(e->{


            cityName = binding.cityName.getText().toString();

            queue = Volley.newRequestQueue(this);

            imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {

                }
            });

            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName,  "UTF-8") + "&appid="+API_KEY+ "&units=metric";
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }



            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {

                        JSONObject mainObject = null;

                        JSONObject weatherObject = null;

                        double current ;
                        double min ;
                        double max ;
                        int humidity ;

                        String iconCode;

                        try {

                            mainObject = response.getJSONObject("main");
                            current = mainObject.getDouble("temp");
                            min = mainObject.getDouble("temp_min");
                            max = mainObject.getDouble("temp_max");
                            humidity = mainObject.getInt("humidity");

                            JSONArray weatherArray = response.getJSONArray("weather");
                            if (weatherArray.length() > 0) {
                                weatherObject = weatherArray.getJSONObject(0);
                                iconCode = weatherObject.getString("icon");
                                Log.d( "Prabh ", iconCode);
                                String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
                                ImageLoader.ImageListener listener = ImageLoader.getImageListener(binding.image, 0, 0);
                                imageLoader.get(iconUrl, listener);

                            }


                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }


                        runOnUiThread( ( ) -> {
                            binding.temp.setText("The current temperature is:  " + current);
                            binding.temp.setVisibility(View.VISIBLE);

                            binding.minTemp.setText("The min temperature is:  " + min);
                            binding.minTemp.setVisibility(View.VISIBLE);

                            binding.maxTemp.setText("The max temperature is:  " + max);
                            binding.maxTemp.setVisibility(View.VISIBLE);

                            binding.humidity.setText("The humidity temperature is:  " + humidity);
                            binding.humidity.setVisibility(View.VISIBLE);

                        });

                    }, (error) -> { });
            queue.add (request);

        });

    }
}
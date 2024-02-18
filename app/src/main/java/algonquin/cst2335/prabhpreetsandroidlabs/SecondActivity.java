package algonquin.cst2335.prabhpreetsandroidlabs;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String telephoneNumber = prefs.getString("PhoneNumber", "");

        TextView text = findViewById(R.id.wlcmtext);
        EditText phone = findViewById(R.id.phone);
        Button btn1 = findViewById(R.id.btn1);
        ImageView camera = findViewById(R.id.camera);
        Button btn2 = findViewById(R.id.btn2);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        text.setText(String.format("Welcome back: %s", emailAddress));

        phone.setText(telephoneNumber);

        SharedPreferences.Editor editor = prefs.edit();

        btn1.setOnClickListener(View -> {
            String phoneNumber = phone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));

            editor.putString("PhoneNumber" , phone.getText().toString());
            editor.apply();

            startActivity(call);
        });

        //String filename = "Picture.png";
        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
            camera.setImageBitmap( theImage );
        }

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            camera.setImageBitmap(thumbnail);
                            FileOutputStream fOut = null;
                            try {
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        btn2.setOnClickListener(View -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });
    }
}
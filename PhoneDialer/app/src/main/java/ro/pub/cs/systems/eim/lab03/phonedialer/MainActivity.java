package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView phoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
            if (((Button)view).getText().toString().equals("#") || ((Button)view).getText().toString().equals("*")) {
                String currentPhone = phoneNumber.getText().toString();
                phoneNumber.setText(currentPhone + ((Button)view).getText().toString());
            }
            else {
                int number = Integer.parseInt(((Button) view).getText().toString(), 10);
                if (number >= 0 && number < 10) {
                    String currentPhone = phoneNumber.getText().toString();
                    phoneNumber.setText(currentPhone + String.valueOf(number));
                }
            }

            Log.d("TAG", "onClick: " + String.valueOf(view.getId()));
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        if (display.getWidth() <= display.getHeight()) {
            // create graphic user interface for portrait mode
            setContentView(R.layout.activity_main);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.d("TAG", "portrait");
        }
        else {
            // create graphic user interface for landscape mode
            setContentView(R.layout.activity_phone_dialer);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.d("TAG", "Landscape");
        }

        final Intent[] call = {new Intent()};

        TextView phoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        Button zeroButton = (Button)findViewById(R.id.buttonZero);
        zeroButton.setOnClickListener(buttonClickListener);
        Button oneButton = (Button)findViewById(R.id.buttonOne);
        oneButton.setOnClickListener(buttonClickListener);
        Button twoButton = (Button)findViewById(R.id.buttonTwo);
        twoButton.setOnClickListener(buttonClickListener);
        Button threeButton = (Button)findViewById(R.id.buttonThree);
        threeButton.setOnClickListener(buttonClickListener);
        Button fourButton = (Button)findViewById(R.id.buttonFour);
        fourButton.setOnClickListener(buttonClickListener);
        Button fiveButton = (Button)findViewById(R.id.buttonFive);
        fiveButton.setOnClickListener(buttonClickListener);
        Button sixButton = (Button)findViewById(R.id.buttonSix);
        sixButton.setOnClickListener(buttonClickListener);
        Button sevenButton = (Button)findViewById(R.id.buttonSeven);
        sevenButton.setOnClickListener(buttonClickListener);
        Button eightButton = (Button)findViewById(R.id.buttonEight);
        eightButton.setOnClickListener(buttonClickListener);
        Button nineButton = (Button)findViewById(R.id.buttonNine);
        nineButton.setOnClickListener(buttonClickListener);
        ImageButton backspace = (ImageButton)findViewById(R.id.imageButtonBackspace);
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneNumber.getText().toString();
                if(phone.length() > 0)
                    phoneNumber.setText(phone.substring(0, phone.length() - 1));
            }
        });
        Button diezButton = (Button)findViewById(R.id.buttonDiez);
        diezButton.setOnClickListener(buttonClickListener);
        Button starButton = (Button)findViewById(R.id.buttonStar);
        starButton.setOnClickListener(buttonClickListener);
        ImageButton callButton = (ImageButton)findViewById(R.id.imageButtonCall);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
                    call[0] = intent;
                    startActivity(intent);
                }
            }
        });

        ImageButton contactButton = (ImageButton)findViewById(R.id.imageButtonContacts);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneString = phoneNumber.getText().toString();
                if (phoneString.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.MainActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneString);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                }
                else {
                    Toast.makeText(getApplication(), "PHONE ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });
        ImageButton cancelButton = (ImageButton)findViewById(R.id.imageButtonCancelCall);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
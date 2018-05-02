package itp341.shah.tanay.smile;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.LocalActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
/*

*/

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111 ;
    private static final int MY_PERMISSIONS_MAKE_CALL = 1;
    private Button testBtn;
    private Button badBtn;
    private Button joinBtn;
    private Button funBtn;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i  = getIntent();
        u = (User) i.getSerializableExtra("user");

        testBtn = findViewById(R.id.testBtn);
        badBtn = findViewById(R.id.badBtn);
        //joinBtn = findViewById(R.id.joinBtn);
        funBtn = findViewById(R.id.funBtn);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),TestActivity.class);
                i.putExtra("user",u);
                startActivityForResult(i,0);
            }
        });

        funBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),feelHappy.class);
                startActivityForResult(i,1);
            }
        });

        badBtn.setEnabled(false);
        String[] requestedPermissions = {Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE};
        if(checkPermission(requestedPermissions)){
            badBtn.setEnabled(true);
        }else{
           ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_SEND_SMS);
          //  ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},MY_PERMISSIONS_MAKE_CALL);
        }








        badBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] requestedPermissions = {Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE};
                if(checkPermission(requestedPermissions)){
                    Log.d("DEBUG:","Creating and Sending message");
                    String dial = "tel:"+"555";
                    startActivityForResult(new Intent(Intent.ACTION_CALL, Uri.parse(dial)),1);
                    Log.d("DEBUG:","Message Sent!");
                }else{
                    Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),JoinActivity.class);
                startActivityForResult(i,2);
            }
        });*/


    }


    private boolean checkPermission(String[] permission){
        for(String permissions : permission){
            int checkPermission = ContextCompat.checkSelfPermission(this,permissions);
            if(checkPermission != PackageManager.PERMISSION_GRANTED){
                return false;
            }
            //return checkPermission == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        badBtn.setEnabled(true);

                } else {

                }
                return;
            }
            case MY_PERMISSIONS_MAKE_CALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    badBtn.setEnabled(true);

                } else {

                }
                return;
        }
    }
}


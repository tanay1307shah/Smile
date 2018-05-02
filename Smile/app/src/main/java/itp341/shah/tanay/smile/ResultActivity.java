package itp341.shah.tanay.smile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tanay on 4/23/2018.
 */

public class ResultActivity extends AppCompatActivity {
    private ImageView low;
    private ImageView medium;
    private ImageView high;
    private ImageView background;

    private Button callBtn;
    private TextView cmts;
    private User u;

    private final String helpline = "tel:555";

    private float score;
    private float magnitude;

    private final String low_comment = "Do no worry in your life, your are doing good so far and you will always do good." +
            "Do not worry if something goes wrong in life, just wait life always has something good and better ahead for you." ;
    private final String medium_comment = "There is nothing to  worry about, though you should probably talk about what is bothering you in life to your close ones." +
            "It can be your friend, parents or teachers, to whom you are comfortable with.";
    private final String high_comment = "Do not worry my friend, everyone is with you. Everyone loves you." +
            "you should talk to your parents and teachers and why you are feeling depressed and you should get help." +
            "If you do not feel like talking to your parents or teachers, call the above number and have a secret talk which know ill know of." +
            "But you should talk about your problems before huritng yourself";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i = getIntent();
        u = (User) i.getSerializableExtra("user");

        score = i.getFloatExtra("score",1);
        magnitude = i.getFloatExtra("magnitude",1);

        low = findViewById(R.id.risk_low);
        medium = findViewById(R.id.risk_medium);
        high = findViewById(R.id.risk_high);
        background = findViewById(R.id.risk_background);

        low.setVisibility(View.INVISIBLE);
        medium.setVisibility(View.INVISIBLE);
        high.setVisibility(View.INVISIBLE);

        callBtn = findViewById(R.id.callBtn);
        cmts = findViewById(R.id.cmts);

        showResults();


        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.CALL_PHONE};
                if(checkPermission(permissions)){
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(helpline)));
                }else{
                    Toast.makeText(getApplicationContext(),"Please give permission to call to the app!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void showResults() {
        if(score <= -0.25){
          //  low.setVisibility(View.VISIBLE);
          //  medium.setVisibility(View.VISIBLE);
            high.setVisibility(View.VISIBLE);
            sendSMSParent(u,"HIGH");
            cmts.setText(high_comment);
        }
        else if(score > -0.25 && score <= 0){
         //   low.setVisibility(View.VISIBLE);
            medium.setVisibility(View.VISIBLE);
          //  high.setVisibility(View.INVISIBLE);
            sendSMSParent(u,"MEDIUM");
            cmts.setText(medium_comment);
        }
        else{
            low.setVisibility(View.VISIBLE);
            medium.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
            cmts.setText(low_comment);
            sendSMSParent(u,"LOW");
        }
    }


    private boolean checkPermission(String[] permission){
        for(String permissions : permission){
            int checkPermission = ContextCompat.checkSelfPermission(this,permissions);
            if(checkPermission != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void sendSMSParent(User u,String result){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(u.getFnum(),null,"Your child " + u.getName() +" the suicide risk test and the result for the risk is " + result ,null,null );
        smsManager.sendTextMessage(u.getMnum(),null,"Your child " + u.getName() +" the suicide risk test and the result for the risk is " + result ,null,null );
        smsManager.sendTextMessage(u.getTnum(),null,"Your Student " + u.getName() +" the suicide risk test and the result for the risk is " + result ,null,null );
    }

}

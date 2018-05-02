package itp341.shah.tanay.smile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tanay on 5/1/2018.
 */

public class homeActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button joinBtn;
    private EditText username;
    private EditText pwd;
    private TextView error;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginBtn = findViewById(R.id.lbtn);
        joinBtn = findViewById(R.id.joinBtn);
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pswd);
        error = findViewById(R.id.error);
        error.setText("");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginThread lt = new loginThread(username.getText().toString(), pwd.getText().toString(), new response() {
                    @Override
                    public void callback(boolean isLoggedIn,User u) {
                        if(isLoggedIn == true){
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            i.putExtra("user",u);
                            startActivity(i);
                        }
                        else{
                            //Toast.makeText(getApplicationContext(),"The combination of email and password is wrong!",Toast.LENGTH_LONG).show();
                            //showError();
                           // error.setEnabled(true);
                        }
                    }
                });
                lt.start();
            }


        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(i);
            }
        });
    }

    private void showError() {
        error.setText("The combination of email and password is wrong!");
    }

}

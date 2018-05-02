package itp341.shah.tanay.smile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by tanay on 4/21/2018.
 */

public class JoinActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText name;
    private EditText age;
    private EditText classNum;
    private EditText race;
    private EditText fNumber;
    private EditText mNumber;
    private EditText tNumber;
    private Spinner genderSelctor;
    private ImageView img;
    private EditText imgUrl;
    private EditText emailAdd;
    private EditText pwd;

    private String gender;
    private String[] genderTypes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Intent i = getIntent();
        genderTypes = getResources().getStringArray(R.array.genderArr);

        saveBtn = findViewById(R.id.saveBtn);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        classNum = findViewById(R.id.classNum);
        race = findViewById(R.id.race);
        fNumber = findViewById(R.id.fNumber);
        mNumber = findViewById(R.id.mNumber);
        tNumber = findViewById(R.id.tNumber);
        genderSelctor = findViewById(R.id.genderSpinner);
        img = findViewById(R.id.img);
        imgUrl = findViewById(R.id.imgUrl);
        emailAdd = findViewById(R.id.emailAdd);
        pwd = findViewById(R.id.pswd);

        imgUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Picasso.get().load(v.getText().toString()).into(img);
                return false;
            }
        });


        genderSelctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Database db = new Database(name.getText().toString(),emailAdd.getText().toString(),pwd.getText().toString(),age.getText().toString(),classNum.getText().toString(),race.getText().toString(),gender,fNumber.getText().toString(),mNumber.getText().toString(),tNumber.getText().toString(),imgUrl.getText().toString());
                db.start();
                User u = new User(name.getText().toString(),age.getText().toString(),classNum.getText().toString(),race.getText().toString(),gender,fNumber.getText().toString(),mNumber.getText().toString(),tNumber.getText().toString(),imgUrl.getText().toString());
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("user",u);
                startActivity(i);
            }
        });





    }

}

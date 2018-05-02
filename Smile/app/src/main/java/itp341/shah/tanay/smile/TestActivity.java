package itp341.shah.tanay.smile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequest;
import com.google.api.services.language.v1.CloudNaturalLanguageScopes;
import com.google.api.services.language.v1.model.AnalyzeSentimentRequest;
import com.google.api.services.language.v1.model.AnalyzeSentimentResponse;
import com.google.api.services.language.v1.model.Document;



import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by tanay on 4/22/2018.
 */

public class TestActivity extends AppCompatActivity implements Callback{

    private EditText a1;
    private EditText a2;
    private Spinner a3;
    private Spinner a4;
    private Spinner a5;
    private EditText a6;
    private EditText a7;
    private EditText a8;
    private Spinner a9;
    private Button submit;


    private String[] yesNo;
    private String[] intent;
    private String ans3 = "No, i have not been wishing to be dead in past few weeks.";
    private String ans4 = "No, i feel my family will not be better off without me.";
    private String ans5 = "No i am not thinking of ending my life";
    private String ans9 = "No i do not intend to end my life soon.";
    //Send to analyze sentiment in NLP ... {1,2,6,7,8}

    private static final int LOADER_ACCESS_TOKEN = 1;

    private StringBuilder text = new StringBuilder();

    private static final String TAG = "ApiFragment";

    private GoogleCredential mCredential;

    private CloudNaturalLanguage mApi = new CloudNaturalLanguage.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    mCredential.initialize(request);
                }
            }).build();

    private final BlockingQueue<CloudNaturalLanguageRequest<? extends GenericJson>> mRequests
            = new ArrayBlockingQueue<>(3);

    private Thread mThread;
    private User u;
  //  private Callback mCallback ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent i  = getIntent();
        u = (User) i.getSerializableExtra("user");

        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        a6 = findViewById(R.id.a6);
        a7 = findViewById(R.id.a7);
        a8 = findViewById(R.id.a8);
        a9 = findViewById(R.id.a9);
        submit = findViewById(R.id.submitBtn);
        yesNo = getResources().getStringArray(R.array.yesNo);
        intent = getResources().getStringArray(R.array.intend);

        //Log.d("DEBUG:", "Preparing the api!");
        prepareApi();


        a3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(yesNo[position].equalsIgnoreCase("yes")){
                    ans3 = "In past few weeks i have felt that i have wished to be dead.";
                }
                else if(yesNo[position].equalsIgnoreCase("unsure")){
                    ans3 = "I am not sure, I might want to end my life";
                }else{
                    ans3 = "No, i have not been wishing to be dead in past few weeks.";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        a4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(yesNo[position].equalsIgnoreCase("yes")){
                    ans4 = "I think my family is better of without me.";
                }
                else if(yesNo[position].equalsIgnoreCase("unsure")){
                    ans4 = "I am not sure, I think my family may be better of without me but then i feel that they will be devastated as well";
                }else{
                    ans4 = "No, i feel my family will not be better off without me.";
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        a5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(yesNo[position].equalsIgnoreCase("yes")){
                    ans5 = "yes i have been thinking of ending my life.";
                }
                else if(yesNo[position].equalsIgnoreCase("unsure")){
                    ans5 = "I am unsure if i want to end my life or not.";
                }else{
                    ans5 = "No i am not thinking of ending my life";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        a9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(intent[position].equalsIgnoreCase("yes")){
                    ans9 = "yes, i intend to end y life soon.";
                }
                else if(intent[position].equalsIgnoreCase("no")){
                    ans9 = "No i do not intend to end my life soon.";
                }else{
                    ans9 = "I am not sure if I will end my life soon or later at some other point in life.";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent i = new Intent(getApplicationContext(),ResultActivity.class);
               // startActivityForResult(i,0);

                text.append(a1.getText().toString());
                text.append(a2.getText().toString());
                text.append(ans3);
                text.append(ans4);
                text.append(ans5);
                text.append(a6.getText().toString());
                text.append(a7.getText().toString());
                text.append(a8.getText().toString());
                text.append(ans9);

                //calling the GCP NLP api
                analyzeSentiment(text.toString());
            }
        });




    }

    private void prepareApi() {
        // Initiate token refresh
        Log.d("DEBUG:", "Inside the prepare api!");
        getSupportLoaderManager().initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {
                    @Override
                    public Loader<String> onCreateLoader(int id, Bundle args) {
                        return new AccessTokenLoader(getApplicationContext());
                    }

                    @Override
                    public void onLoadFinished(Loader<String> loader, String token) {
                        setAccessToken(token);
                    }

                    @Override
                    public void onLoaderReset(Loader<String> loader) {
                    }
                });
    }

    public void setAccessToken(String token) {
        mCredential = new GoogleCredential()
                .setAccessToken(token)
                .createScoped(CloudNaturalLanguageScopes.all());
        Log.d("DEBUG:","Starting The call thread");
        startWorkerThread();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void analyzeSentiment(String text) {
        try {
            mRequests.add(mApi
                    .documents()
                    .analyzeSentiment(new AnalyzeSentimentRequest()
                            .setDocument(new Document()
                                    .setContent(text)
                                    .setType("PLAIN_TEXT"))));
        } catch (IOException e) {
           // Log.e(TAG, "Failed to create analyze request.", e);
        }
    }

    private void startWorkerThread() {
        if (mThread != null) {
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mThread == null) {
                        break;
                    }
                    try {
                        // API calls are executed here in this worker thread
                        //Log.d("DEBUG:", "Stargin to diliver response");
                        deliverResponse(mRequests.take().execute());
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Interrupted.", e);
                        break;
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to execute a request.", e);
                    }
                }
            }
        });
        mThread.start();
    }

    @Override
    public void onSentimentReady(SentimentInfo sentiment) {


        Toast.makeText(getApplicationContext(),"Your score is " + sentiment.score, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),ResultActivity.class);
        i.putExtra("score",sentiment.score);
        i.putExtra("magnitude",sentiment.magnitude);
        i.putExtra("user",u);
        startActivityForResult(i,0);
    }

    private void deliverResponse(GenericJson response) {
        final Activity activity = TestActivity.this;
        if (response instanceof AnalyzeSentimentResponse) {
            final SentimentInfo sentiment = new SentimentInfo(((AnalyzeSentimentResponse) response)
                    .getDocumentSentiment());
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  if (mCallback != null) {
                      //  Log.d("DEBUG:", "MAking the callback function or calling it!");
                        onSentimentReady(sentiment);
                    //}
                }
            });
        }
    }


}

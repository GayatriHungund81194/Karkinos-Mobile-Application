package karkinos.gayatri.com.kankinos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ChatActivity extends AppCompatActivity implements AIListener {

    AIService aiService;
    //  AIRequest aiRequest;
    AIDataService aiDataService;
    Result res;
    TextToSpeech tts;
    String tvResult;
    TextView t;
    String request23;
    EditText message;
    Button b, send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        b=(Button)findViewById(R.id.listen);
        t=(TextView)findViewById(R.id.response);
        // send=(Button)findViewById(R.id.send);
        // message=(EditText)findViewById(R.id.request);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                tts.setLanguage(Locale.ENGLISH);
            }
        });
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Error:", "Permission to record denied");
            makeRequest();
        }

        final AIConfiguration config = new AIConfiguration("4849216d676842cbbb2d20b73e789f80", AIConfiguration.SupportedLanguages.English, AIConfiguration.RecognitionEngine.System);
        aiDataService = new ai.api.android.AIDataService(this,config);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        final AIRequest aiRequest = new AIRequest();


        aiRequest.setQuery("hi");
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                message.setText("Hi");
//                 request23 = message.getText().toString();
//                Log.d("@@@@@@@@",request23);
//
//
//
//
//            }
//
//
//        });
//
//        Log.d("$%$#%",request23);
        if(aiRequest==null) {
            throw new IllegalArgumentException("aiRequest must be not null");
        }

        @SuppressLint("StaticFieldLeak")
        final AsyncTask<AIRequest, Integer, AIResponse> task = new AsyncTask<AIRequest, Integer, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                try {
                    AIResponse response= aiDataService.request(aiRequest);
                    // Return
                    return response;
                } catch (Exception e) {
                    //  aiError = new AIError(e);
                    e.printStackTrace();
                    // return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(final AIResponse response) {
                if (response != null) {
                    onResult(response);
                } else {
                    Log.d("Ta@@@@@@g","some error");
                    // onError(aiError);
                }
            }
        };
        task.execute(aiRequest);
///till here


    }

    public void buttonClicked(View V){
        Log.d("hey","there");
        aiService.startListening();
    }

    @Override
    public void onResult(AIResponse result) {
        res = result.getResult();
        String reply = res.getFulfillment().getSpeech();
        tvResult= res.toString();
        Log.d("######Result",tvResult);
        Log.d("@@@@@@@@@@@@",reply);
        t.setText(reply);
        tts.speak(reply,TextToSpeech.QUEUE_FLUSH, null);
        // t.setText("Query: "+res.getResolvedQuery()+" Action: ");
    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("TAG", "Permission has been denied by user");
                } else {
                    Log.i("TAG", "Permission has been granted by user");
                }
                return;
            }
        }
    }
}

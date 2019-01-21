package com.example.user.texttospeach;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btnspaek;
    EditText editText;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);
                    if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Toast.makeText(MainActivity.this,"This Language is not Supported",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        btnspaek.setEnabled(true);
                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(1.0f);
                        speak();
                    }
                }


            }
        });
        editText=(EditText) findViewById(R.id.edt_speak);
        btnspaek=(Button)findViewById(R.id.btn_speak);
        btnspaek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        String text=editText.getText().toString();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            textToSpeech.speak(text,textToSpeech.QUEUE_FLUSH,null,null);
        }
        else
            textToSpeech.speak(text,textToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    protected void onDestroy() {
       if(textToSpeech != null)
       {
           textToSpeech.stop();
           textToSpeech.shutdown();
       }

        super.onDestroy();
    }
}

package com.example.brainteaser;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayAgain extends AppCompatActivity {


    //Button playAgainButton;
    TextView finalTextView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void playAgain(View view) {




        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);

        //playAgain(findViewById(R.id.playAgainButton));
        finalTextView = findViewById(R.id.finalTextView);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String getFinalScore = bundle.getString("points");

        finalTextView.setText("Your Score is : " +getFinalScore);
    }


}

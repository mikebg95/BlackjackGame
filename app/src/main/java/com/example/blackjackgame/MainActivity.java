package com.example.blackjackgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

// activity instead of appcompatactivity -> no action bar
public class MainActivity extends Activity {

    TextView playBtn, nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make game full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.name_edittext);
        playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            if (name.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

//        // set gameview
//        setContentView(new GameView(this));
//
//        Deck deck = new Deck();
//
//        Card testCard = deck.dealTop();
//
//        int path = Integer.parseInt(testCard.getImagePathName());
//
//        CardSprite cardSprite = new CardSprite(BitmapFactory.decodeResource(getResources(), path));

    }

    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
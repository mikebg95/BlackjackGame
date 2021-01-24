package com.example.blackjackgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Card {
    private static String TAG = "GAMEPLAY";

    private int value;
    private char coat;
    private int score;
    private String imagePathName;

    public Card(int value, char coat) {
        // set value and coat of card
        this.value = value;
        this.coat = coat;

        // calculate card score from value
        if (value == 11 || value == 12 || value == 13) {
            this.score = 10;
        }
        else if (value == 14) {
            this.score = 11;
        }
        else {
            this.score = value;
        }

        // get image of card
        String imgName = "";

        imgName += Character.toLowerCase(coat);
        imgName += "_";

        // add value to card name
        if (value == 11) {
            imgName += "j";
        }
        else if (value == 12) {
            imgName += "q";
        }
        else if (value == 13) {
            imgName += "k";
        }
        else if (value == 14) {
            imgName += "a";
        }
        else {
            imgName += Integer.toString(value);
        }
        imagePathName = imgName;
    }

    public String getImagePathName() {
        return imagePathName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

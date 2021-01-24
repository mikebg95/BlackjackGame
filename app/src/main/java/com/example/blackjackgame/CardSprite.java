package com.example.blackjackgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CardSprite {
    private Bitmap image;
    
    public CardSprite(Bitmap bmp) {
        Bitmap resized = Bitmap.createScaledBitmap(bmp, 200, 300, false);
        image = resized;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 100, 100, null);
    }
}

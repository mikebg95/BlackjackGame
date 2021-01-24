package com.example.blackjackgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements Runnable {

    private static String TAG = "GAMEPLAY";
    private static int NUM_CARDS = 2;

    private Thread thread;
    private boolean isPlaying;
    private Paint paint;
    private Background background;

    private int chips;
    private Context context;

    private Player player, dealer;
    private ArrayList<Card> deck;

    int screenX, screenY;

    private boolean dealt;

    // constructor for gameview
    public GameView(Context context, int screenX, int screenY, String name, int chips) {
        super(context);

        this.context = context;

        this.screenX = screenX;
        this.screenY = screenY;

        player = new Player(name);
        dealer = new Player("Dealer");
        player.setChips(1000);

        dealt = false;

        // create instance of Paint class
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);

        // create instance of background (to draw on canvas)
        background = new Background(screenX, screenY, getResources());
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    public void update() {

    }

    private void draw() {
        // check if surfaceview object has succesfully been initiated
        if (getHolder().getSurface().isValid()) {

            // returns current canvas (locked)
            Canvas canvas = getHolder().lockCanvas();

            // draw background on canvas
            canvas.drawBitmap(background.background, 0, 0, paint);

            // draw player info on canvas
            setPlayerInfo(canvas);

            setCards(canvas);

            // show on screen
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep() {
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // resume game
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    // pause game
    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerInfo(Canvas canvas) {
        paint.setTextAlign(Paint.Align.LEFT);

        int x = screenX / 8;
        int y = (int) (screenY * 0.6);

        canvas.drawText("Name: " + player.getName(), x, y, paint);
        canvas.drawText("Chips: $" + player.getChips(), x, y+65, paint);
    }

    public void setDealerScore(Canvas canvas) {
        paint.setTextAlign(Paint.Align.CENTER);

        dealer.calculateScore();

        String scoreInfo = "Score: " + dealer.getScore();

        int x = screenX / 2;
        int y = screenY / 3;

        canvas.drawText(scoreInfo, x, y, paint);
    }

    public void setPlayerScore(Canvas canvas) {
        paint.setTextAlign(Paint.Align.CENTER);

        player.calculateScore();


        String scoreInfo = "Score: " + player.getScore();

        int x = screenX / 2;
        int y = screenY * 14/15;

        canvas.drawText(scoreInfo, x, y, paint);
    }

    public void setCards(Canvas canvas) {
        // deal cards to player and dealer
        if (!dealt) {
            dealCards(player, dealer);
            dealt = true;
        }
        setPlayerCards(canvas);
        setDealerCards(canvas);
    }

    public void setDealerCards(Canvas canvas) {
        // get cards of dealer
        ArrayList<Card> dealerCards = dealer.getCards();
        ArrayList<Bitmap> dealerCardBitmaps = getCardBitmaps(dealerCards);

        // draw dealer cards on canvas
        int x = screenX / 3;
        int y = screenY / 10;

        for (int i = 0; i < dealerCardBitmaps.size(); i++) {
            canvas.drawBitmap(dealerCardBitmaps.get(i), x, y, paint);
            x += (screenX / 8) + 20;
        }

        setDealerScore(canvas);
    }

    public void setPlayerCards(Canvas canvas) {
        // get cards of player
        ArrayList<Card> playerCards = player.getCards();
        ArrayList<Bitmap> playerCardBitmaps = getCardBitmaps(playerCards);

        // draw player cards on canvas
        int x = screenX / 3;
        int y = screenY * 17/24;
        for (int i = 0; i < playerCardBitmaps.size(); i++) {
            canvas.drawBitmap(playerCardBitmaps.get(i), x, y, paint);
            x += (screenX / 8) + 20;
        }

        setPlayerScore(canvas);
    }

    public ArrayList<Bitmap> getCardBitmaps(ArrayList<Card> cards) {
        ArrayList<Bitmap> cardBitmaps = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            String path = cards.get(i).getImagePathName();
            Log.wtf(TAG, path);


            // convert path string to id int
            int pathId = getResources().getIdentifier(path, "drawable", context.getPackageName());

            // fetch bitmap from drawable
            Bitmap card = BitmapFactory.decodeResource(getResources(), pathId);
            int width = screenX / 4;
            int height = (int) (width * 1.53);
            card = Bitmap.createScaledBitmap(card, width, height, false);
            cardBitmaps.add(card);
        }

        return cardBitmaps;
    }

    public void dealCards(Player player, Player dealer) {

        // create instance of deck
        deck = new Deck().getDeck();

        // deal cards
        for (int i = 0; i < NUM_CARDS; i++) {
            player.addCard(deck.get(0));
            deck.remove(0);
            dealer.addCard(deck.get(0));
            deck.remove(0);
        }
    }
}

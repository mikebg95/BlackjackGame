package com.example.blackjackgame;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private int chips;
    private int bet;
    private int score;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int calculateScore() {
        score = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            score += this.cards.get(i).getScore();
        }

        while (score > 21) {
            for (int i = 0; i < this.cards.size(); i++) {
                if (this.cards.get(i).getScore() == 11) {
                    cards.get(i).setScore(1);
                    score -= 10;
                }
            }
            if (score > 21) {
                break;
            }
        }
        return score;
    }

    public int getScore() {
        return score;
    }
}

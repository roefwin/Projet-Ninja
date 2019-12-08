package com.example.sandboxretro.Fragment.Regis.Model;

import android.widget.Button;

public class Player {
    private int score;
    private String name;
    private Button btn_player;

    public Button getBtn_player() {
        return btn_player;
    }

    public void setBtn_player(Button btn_player) {
        this.btn_player = btn_player;
    }

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

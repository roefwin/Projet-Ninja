package com.example.sandboxretro.Fragment.Clement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.sandboxretro.Fragment.Clement.Model.Horse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public  class  GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameLoopThread;
    private Horse horse1 = new Horse(0,this.getContext());
    private Horse horse2 = new Horse(250,this.getContext());
    private Horse horse3  = new Horse(500,this.getContext());
    private Horse horse4 = new Horse(750,this.getContext());

    private  Horse winner;

    private ArrayList<Horse> resultat = new ArrayList<>();


    public  GameView(Context context,ArrayList<Horse> horsetest){
        super(context);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);
        horse1.setPlayerTab(horsetest.get(0).getPlayerTab());
        horse2.setPlayerTab(horsetest.get(1).getPlayerTab());
        horse3.setPlayerTab(horsetest.get(2).getPlayerTab());
        horse4.setPlayerTab(horsetest.get(3).getPlayerTab());

    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getHolder().addCallback(this);
        gameLoopThread = new GameLoopThread(this);
        horse1 = new Horse(0,this.getContext());
        horse2 = new Horse(250,this.getContext());
        horse3 = new Horse(500,this.getContext());
        horse4 = new Horse(750,this.getContext());
    }

    public void doDraw(Canvas canvas){
        if(canvas==null) {return;}

        // on efface l'écran, en blanc
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setColor(Color.RED);
        horse1.draw(canvas);
        horse2.draw(canvas);
        horse3.draw(canvas);
        horse4.draw(canvas);
        canvas.drawLine(0,1400,2000,1400,paint);

    }

    int random(){
        Random rand = new Random();
        int n  = rand.nextInt(4);
        return  n;

    }

    public void  update(){
        int n = random();

        if(n == 0) {
            horse1.move();
        }
        if(n == 1) {
            horse2.move();
        }
        if(n == 2) {
            horse3.move();
        }
        if(n == 3) {
            horse4.move();
        }

        if(horse1.getY() == 1400 && horse1.getFin_position() == false){
           resultat.add(horse1);
           horse1.setFin_position(true);

        }
        if(horse2.getY() == 1400 && horse2.getFin_position() == false){
            resultat.add(horse2);
            horse2.setFin_position(true);
        }
        if(horse3.getY() == 1400&& horse3.getFin_position() == false){
            resultat.add(horse3);
            horse3.setFin_position(true);
        }
        if(horse4.getY() == 1400 && horse4.getFin_position() == false){
            resultat.add(horse4);
            horse4.setFin_position(true);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(gameLoopThread.getState()==Thread.State.TERMINATED) {
            gameLoopThread=new GameLoopThread(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
       horse1.resize(i1,i2);
       horse2.resize(i1,i2);
       horse3.resize(i1,i2);
       horse4.resize(i1,i2);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int)event.getX();
        int currentY = (int)event.getY();

        switch (event.getAction()) {

            // code exécuté lorsque le doigt touche l'écran.
            case MotionEvent.ACTION_DOWN:
                // si le doigt touche la balle :
                if(currentX >= horse1.getX() &&
                        currentX <= horse1.getX() + horse1.getIconx()&&
                        currentY >= horse1.getY() && currentY <= horse1.getY()+horse1.getIcony() ) {
                    // on arrête de déplacer la balle
                    //horse1.setPlayerTab();
                }
                break;


        }

        return true;  // On retourne "true" pour indiquer qu'on a géré l'évènement
    }
}

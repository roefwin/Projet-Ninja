package com.example.sandboxretro.Fragment.Clement.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.sandboxretro.R;
import com.google.android.filament.Texture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.filament.Texture.Format.R;

public class Horse implements Serializable {
    private BitmapDrawable icon = null;
    private int x,y;
    private  int iconx,icony;
    private int wEcran,hEcran;
    private boolean fin_position;
    private ArrayList<Player> PlayerTab;

    private static  final int INCREMENT = 20;
    private int speedX = INCREMENT ,speedY = INCREMENT;
    private final Context mContext;


    public Horse(int x,final Context mContext) {
        this.x = x;
        this.y = 0;
        this.fin_position = false;
        PlayerTab = new ArrayList<Player>();
        this.mContext = mContext;
    }

    public BitmapDrawable setImage(final Context mContext, final int ressource , final int w, final int h)
    {
        Drawable dr = mContext.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIconx() {
        return iconx;
    }

    public void setIconx(int iconx) {
        this.iconx = iconx;
    }

    public int getIcony() {
        return icony;
    }

    public void setIcony(int icony) {
        this.icony = icony;
    }


    public boolean getFin_position() {
        return fin_position;
    }

    public void setFin_position(boolean fin_position) {
        this.fin_position = fin_position;
    }

    public ArrayList<Player> getPlayerTab() {
        return PlayerTab;
    }

    public void setPlayerTab(ArrayList<Player> playerTab) {
        PlayerTab = playerTab;
    }

    public void setNewPlayerInTab(Player player) {
        PlayerTab.add(player);
    }

    public  void move(){

        if(this.y < 1400){
            y+=speedY;
        }
    }
    public void resize(int wScreen, int hScreen) {
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations en variable globale, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        wEcran=wScreen;
        hEcran=hScreen;

        // on définit (au choix) la taille de la balle à 1/5ème de la largeur de l'écran
        iconx=wScreen/5;
        icony=wScreen/5;
        icon = setImage(mContext, com.example.sandboxretro.R.mipmap.poule,iconx,icony);

    }

    public void  draw(Canvas canvas){
        if(icon==null) {return;}
        canvas.drawBitmap(icon.getBitmap(),x,y,null);
    }
}

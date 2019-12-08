package com.example.sandboxretro.Fragment.Enzo.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sandboxretro.Fragment.Enzo.interfaces.CarouselInterface;
import com.example.sandboxretro.R;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject, final Context context, final CarouselInterface mCallback) {
        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setText(libraryObject.getTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());

        final Button btnContinuer = view.findViewById(R.id.btn_carousel_continue);
        final GifImageView gifCarousel = view.findViewById(R.id.gif_carousel);
        final CardView cardCarousel = view.findViewById(R.id.card_carousel);
        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rl_card_carousel);

        switch (libraryObject.getMposition()) {
            case 0 :

                break;
            case 1:

                break;
            case 2:


                break;
            case 3 :
                if(libraryObject.getMposition()==3) {
                    cardCarousel.setCardBackgroundColor(context.getResources().getColor(R.color.regis));
                    btnContinuer.setVisibility(View.VISIBLE);
                    gifCarousel.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    txt.setVisibility(View.GONE);

                    //On recupere la taille des cards pour l'adapter à celle du gif
                    final ViewTreeObserver vto = layout.getViewTreeObserver();
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            int width  = layout.getMeasuredWidth();
                            int height = layout.getMeasuredHeight();
                            Log.i("size", "width : "+width);
                            Log.i("size", "height : "+height);

                            ViewGroup.LayoutParams params = cardCarousel.getLayoutParams();
                            params.height = (int) Math.round(height/1.5);
                            params.width = width;
                            cardCarousel.setLayoutParams(params);
                        }
                    });

                    btnContinuer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCallback.onCarouselContinueClick();
                        }
                    });
                }
                break;
        }
    }


    public static class LibraryObject {

        private String mTitle;
        private int mRes;



        private int mposition;

        public LibraryObject(final int res, final String title, final int position) {
            mRes = res;
            mTitle = title;
            mposition = position;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public int getRes() {
            return mRes;
        }

        public void setRes(final int res) {
            mRes = res;
        }

        public int getMposition() {
            return mposition;
        }

        public void setMposition(int mposition) {
            this.mposition = mposition;
        }
    }
}

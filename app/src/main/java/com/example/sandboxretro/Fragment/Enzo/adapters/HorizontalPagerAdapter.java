package com.example.sandboxretro.Fragment.Enzo.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandboxretro.Fragment.Enzo.Model.Utils;
import com.example.sandboxretro.Fragment.Enzo.interfaces.CarouselInterface;
import com.example.sandboxretro.R;


import static com.example.sandboxretro.Fragment.Enzo.Model.Utils.setupItem;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.ic_strategy,
                    "0",
                    0
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_design,
                    "1",
                    1
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_development,
                    "2",
                    2
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_qa,
                    "3",
                    3
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private CarouselInterface mCallback;

    private boolean mIsTwoWay;

    //On recupere l'interface
    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay, CarouselInterface listener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
        mCallback = listener;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        //On passe le callback au modele
        setupItem(view, LIBRARIES[position], mContext, mCallback);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}

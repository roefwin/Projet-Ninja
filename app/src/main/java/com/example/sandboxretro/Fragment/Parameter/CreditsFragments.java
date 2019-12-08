package com.example.sandboxretro.Fragment.Parameter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.example.sandboxretro.Fragment.Enzo.EnzoFragment;
import com.example.sandboxretro.Fragment.MediaPlayer.MediaPlayerHolder;
import com.example.sandboxretro.Fragment.MediaPlayer.PlaybackInfoListener;
import com.example.sandboxretro.Fragment.MediaPlayer.PlayerAdapter;
import com.example.sandboxretro.MainActivity;
import com.example.sandboxretro.R;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnzoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnzoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditsFragments extends Fragment implements SeekBar.OnSeekBarChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final float SCROLL_ANIM_DURATION = 30000;    // [ms] = 40 s
    private OnFragmentInteractionListener mListener;
    private CreditsRollView mCreditsRollView;
    private boolean mScrolling;
    private ValueAnimator mScrollAnimator;
    private SeekBar mSeekBar;

    public static final int MEDIA_RES_ID = R.raw.jazz_in_paris;
    private PlayerAdapter mPlayerAdapter;

    public CreditsFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnzoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreditsFragments newInstance() {
        CreditsFragments fragment = new CreditsFragments();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_credits, container, false);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mCreditsRollView = (CreditsRollView) view.findViewById(R.id.creditsroll);
        animateScroll();

        MediaPlayerHolder mMediaPlayerHolder = new MediaPlayerHolder(getContext());

        mPlayerAdapter = mMediaPlayerHolder;
        mPlayerAdapter.loadMedia(MEDIA_RES_ID);
        mPlayerAdapter.play();

        mCreditsRollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mScrolling) {
                    animateScroll();
                    mPlayerAdapter.play();
                }
                else {
                    stopScrollAnimation();
                    mPlayerAdapter.pause();
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPlayerAdapter.release();
        mListener = null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCreditsRollView.setScrollPosition(progress / 100000f); // We have increments of 1/100000 %

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mScrolling) {
            stopScrollAnimation();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void animateScroll() {
        mScrolling = true;
        mScrollAnimator = ObjectAnimator.ofInt(mSeekBar, "progress", mSeekBar.getProgress(), mSeekBar.getMax());
        mScrollAnimator.setDuration((long) (SCROLL_ANIM_DURATION * (1 - (float) mSeekBar.getProgress() / mSeekBar.getMax())));
        mScrollAnimator.setInterpolator(new LinearInterpolator());
        mScrollAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mScrolling = false;
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        mScrollAnimator.start();
    }

    private void stopScrollAnimation() {
        if (mScrollAnimator != null) {
            mScrollAnimator.cancel();
            mScrollAnimator = null;
        }
    }


}

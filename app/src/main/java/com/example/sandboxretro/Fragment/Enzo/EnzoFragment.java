package com.example.sandboxretro.Fragment.Enzo;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sandboxretro.ChangeStateListener;
import com.example.sandboxretro.Fragment.Enzo.adapters.HorizontalPagerAdapter;
import com.example.sandboxretro.Fragment.Enzo.interfaces.CarouselInterface;
import com.example.sandboxretro.MainActivity;
import com.example.sandboxretro.R;
import com.example.sandboxretro.SandBoxRetroApplication;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.OnInfiniteCyclePageTransformListener;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnzoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnzoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnzoFragment extends Fragment implements CarouselInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ChangeStateListener mListener;

    public EnzoFragment() {
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
    public static EnzoFragment newInstance() {
        EnzoFragment fragment = new EnzoFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enzo, container, false);

        horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        YoYo.with(Techniques.FadeIn)
                .duration(40)
                .playOn(view);

        //On passe l'interface en parametre de l'adapter
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), false, this));


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
        if (!(context instanceof ChangeStateListener)) {
            throw new ClassCastException(context.toString() + " must implement ChangeStateListener");
        }
        mListener = (ChangeStateListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //ca c'est une déchirure heuuu une interface
    @Override
    public void onCarouselContinueClick() {
        //horizontalInfiniteCycleViewPager.setVisibility(View.GONE);
        YoYo.with(Techniques.FadeOutUp)
                .duration(900)
                .playOn(horizontalInfiniteCycleViewPager);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onChangeState(EnzoFragmentAfterCarousel.newInstance());
                SandBoxRetroApplication.getInstance().setCarouselViewed(true);
            }
        }, 1000);
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
}

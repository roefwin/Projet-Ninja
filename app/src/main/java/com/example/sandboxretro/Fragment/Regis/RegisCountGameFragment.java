package com.example.sandboxretro.Fragment.Regis;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandboxretro.Fragment.Regis.Model.Picture;
import com.example.sandboxretro.Fragment.Regis.Model.Player;
import com.example.sandboxretro.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisCountGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisCountGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisCountGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    private Button btn_player1;
    private Button btn_player2;
    private Button btn_player3;
    private Button btn_player4;

    private  int count1 = 0;
    private  int count2 = 0;
    private  int count3 = 0;
    private  int count4 = 0;

    private TextView countdown1;
    private TextView countdown2;
    private TextView countdown3;
    private TextView countdown4;

    private Player player1 = new Player(0,"player1");
    private Player player2 = new Player(0,"player2");
    private Player player3 = new Player(0,"player3");
    private Player player4 = new Player(0,"player4");
    private Picture[] pictureTab = new Picture[]{
            new Picture("poires",47,R.drawable.poires),
            new Picture("bananes",74,R.drawable.bananes),
            new Picture("pommes",20,R.drawable.pommes),
            new Picture("bananes2",48,R.drawable.bananes2),
            new Picture("aubergines",16,R.drawable.aubergines),
            new Picture("avocats",16,R.drawable.avocats),
            new Picture("comcombres",15,R.drawable.comcombres),
            new Picture("poivrons",33,R.drawable.poivrons),
            new Picture("raisins",47,R.drawable.raisins)};

    private  Player[] playerTab = new Player[] {player1,player2,player3,player4};

    private Player winner = new Player(0,"Empty");
    private Player looser ;

    int random_number;


    private TextView textwinner;
    private TextView textlooser;

    private static ImageView img ;

    private  Button next_btn;

    public RegisCountGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisCountGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisCountGameFragment newInstance(String param1, String param2) {
        RegisCountGameFragment fragment = new RegisCountGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_regis_count_game, container, false);

        countdown1 = (TextView) view.findViewById(R.id.countdown1);
        countdown2 = (TextView) view.findViewById(R.id.countdown2);
        countdown3 = (TextView) view.findViewById(R.id.countdown3);
        countdown4 = (TextView) view.findViewById(R.id.countdown4);

        textwinner = (TextView) view.findViewById(R.id.textwinner);
        textlooser = (TextView) view.findViewById(R.id.textlooser);

        img = (ImageView) view.findViewById(R.id.img);

        next_btn = (Button) view.findViewById(R.id.next_btn);
        random_number = random();
        looser = new Player(pictureTab[random_number].getNbr_elem(),"Empty");
        img.setImageResource(pictureTab[random_number].getResId());

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished / 1000 < 4) {

                    countdown1.setTextColor(-65536);
                    scaleViewAnimation(countdown1,200);
                    countdown1.setText("    "+ millisUntilFinished / 1000);

                    countdown2.setTextColor(-65536);
                    scaleViewAnimation(countdown2,200);
                    countdown2.setText( millisUntilFinished / 1000 + "    ");

                    countdown3.setTextColor(-65536);
                    scaleViewAnimation(countdown3,200);
                    countdown3.setText("    "+ millisUntilFinished / 1000);

                    countdown4.setTextColor(-65536);
                    scaleViewAnimation(countdown4,200);
                    countdown4.setText(millisUntilFinished / 1000 + "    ");
                    //here you can have your logic to set text to edittext

                }else
                countdown1.setText("    "+ millisUntilFinished / 1000);
                countdown2.setText(millisUntilFinished / 1000 + "    ");
                countdown3.setText("    "+ millisUntilFinished / 1000);
                countdown4.setText(millisUntilFinished / 1000 + "    ");

            }

            public void onFinish() {
                playerTab[0].getBtn_player().setEnabled(false);
                playerTab[1].getBtn_player().setEnabled(false);
                playerTab[2].getBtn_player().setEnabled(false);
                playerTab[3].getBtn_player().setEnabled(false);

                for (Player i: playerTab
                     ) {
                    if(i.getScore()<pictureTab[random_number].getNbr_elem()){
                        if(i.getScore()>= winner.getScore()) {
                            winner = i;
                        }
                        if(pictureTab[random_number].getNbr_elem()- i.getScore() >= pictureTab[random_number].getNbr_elem()-looser.getScore()){
                            looser = i;
                        }
                    }else{
                        looser = i ;

                    }
                }


                //mTextField.setText("done!");
                winner.getBtn_player().setBackgroundColor(-16711936);
                looser.getBtn_player().setBackgroundColor(-65536);


                img.setVisibility(View.INVISIBLE);
                textwinner.setText(winner.getName() + " donnes 3 gorgées");
                alphaViewAnimation(textwinner,200);
                textlooser.setText(looser.getName() + " bois 3 gorgées");
                textlooser.requestFocus();
                alphaViewAnimation(textlooser,200);

                next_btn.setVisibility(View.VISIBLE);

                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//2 - Create a new fragment and add it into activty
                        Fragment fragment = new RegisCountGameFragment();
                        fragmentTransaction.replace(R.id.fragment_main, fragment);
                        fragmentTransaction.commit();
                    }
                });



            }

        }.start();

        playerTab[0].setBtn_player((Button) view.findViewById(R.id.btn_player1));
        playerTab[0].getBtn_player().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 playerTab[0].setScore( playerTab[0].getScore() + 1);
                 playerTab[0].getBtn_player().setText(Integer.toString(playerTab[0].getScore()));
            }
        });

        playerTab[1].setBtn_player((Button) view.findViewById(R.id.btn_player2));
        playerTab[1].getBtn_player().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerTab[1].setScore( playerTab[1].getScore() + 1);
                playerTab[1].getBtn_player().setText(Integer.toString(playerTab[1].getScore()));
            }
        });

        playerTab[2].setBtn_player((Button) view.findViewById(R.id.btn_player3));
        playerTab[2].getBtn_player().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerTab[2].setScore( playerTab[2].getScore() + 1);
                playerTab[2].getBtn_player().setText(Integer.toString(playerTab[2].getScore()));
            }
        });

        playerTab[3].setBtn_player((Button) view.findViewById(R.id.btn_player4));
        playerTab[3].getBtn_player().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerTab[3].setScore( playerTab[3].getScore() + 1);
                playerTab[3].getBtn_player().setText(Integer.toString(playerTab[3].getScore()));
            }
        });



        return view;
    }

    int random(){
        Random rand = new Random();
        int n  = rand.nextInt(pictureTab.length);
        return  n;

    }

    private void scaleViewAnimation(View view, int startDelay){
        // Reset view
        view.setScaleX(0);
        view.setScaleY(0);
        // Animate view
        view.animate()
                .scaleX(2f)
                .scaleY(2f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(startDelay)
                .setDuration(100)
                .start();
    }

    private void alphaViewAnimation(View view, int startDelay){
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(startDelay);
        view.startAnimation(animation);
    }

    private void fromBottomAnimation(View view, int startDelay) {
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setStartOffset(startDelay);
        view.startAnimation(animation);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

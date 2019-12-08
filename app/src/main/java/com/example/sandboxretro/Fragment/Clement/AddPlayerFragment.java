package com.example.sandboxretro.Fragment.Clement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sandboxretro.Fragment.Clement.Model.Horse;
import com.example.sandboxretro.Fragment.Clement.Model.Player;
import com.example.sandboxretro.Fragment.Regis.RegisCountGameFragment;
import com.example.sandboxretro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_Ok;
    private  Button go;
    private EditText player_name;
    private ImageButton poule1;
    private ImageButton poule2;
    private ImageButton poule3;
    private ImageButton poule4;

    private Horse horse1 = new Horse(0,this.getContext());
    private Horse horse2 = new Horse(250,this.getContext());
    private Horse horse3 = new Horse(500,this.getContext());
    private Horse horse4 = new Horse(750,this.getContext());

    private ArrayList<Horse> horsetab = new ArrayList<Horse>();

    private OnFragmentInteractionListener mListener;

    public AddPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlayerFragment newInstance() {
        AddPlayerFragment fragment = new AddPlayerFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
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
        View view = inflater.inflate(R.layout.fragment_add_player, container, false);
        player_name = (EditText) view.findViewById(R.id.player_name);
        btn_Ok = (Button) view.findViewById(R.id.btn_Ok);
        go = (Button) view.findViewById(R.id.go);

        poule1 = (ImageButton) view.findViewById(R.id.poule1);
        poule2 = (ImageButton) view.findViewById(R.id.poule2);
        poule3 = (ImageButton) view.findViewById(R.id.poule3);
        poule4 = (ImageButton) view.findViewById(R.id.poule4);

        poule1.setEnabled(false);
        poule2.setEnabled(false);
        poule3.setEnabled(false);
        poule4.setEnabled(false);

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player player = new Player(player_name.getText().toString());

                poule1.setEnabled(true);
                poule2.setEnabled(true);
                poule3.setEnabled(true);
                poule4.setEnabled(true);
                poule1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        player_name.setText("");
                        horse1.setNewPlayerInTab(player);

                        poule1.setEnabled(false);
                        poule2.setEnabled(false);
                        poule3.setEnabled(false);
                        poule4.setEnabled(false);
                    }
                });

                poule2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        player_name.setText("");
                        horse2.setNewPlayerInTab(player);

                        poule1.setEnabled(false);
                        poule2.setEnabled(false);
                        poule3.setEnabled(false);
                        poule4.setEnabled(false);
                    }
                });

                poule3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        player_name.setText("");
                        horse3.setNewPlayerInTab(player);

                        poule1.setEnabled(false);
                        poule2.setEnabled(false);
                        poule3.setEnabled(false);
                        poule4.setEnabled(false);
                    }
                });

                poule4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        player_name.setText("");
                        horse4.setNewPlayerInTab(player);

                        poule1.setEnabled(false);
                        poule2.setEnabled(false);
                        poule3.setEnabled(false);
                        poule4.setEnabled(false);
                    }
                });

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                horsetab.add(horse1);
                horsetab.add(horse2);
                horsetab.add(horse3);
                horsetab.add(horse4);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//2 - Create a new fragment and add it into activty
                Fragment fragment = new ClementFragement();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ClementFragement.HORSE, horsetab );
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_main, fragment);
                fragmentTransaction.commit();
            }
        });

        return  view;

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

package com.example.sandboxretro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sandboxretro.Fragment.Clement.AddPlayerFragment;
import com.example.sandboxretro.Fragment.Clement.ClementFragement;
import com.example.sandboxretro.Fragment.Enzo.EnzoFragment;
import com.example.sandboxretro.Fragment.Enzo.EnzoFragmentAfterCarousel;
import com.example.sandboxretro.Fragment.Parameter.ParameterFragment;
import com.example.sandboxretro.Fragment.Regis.RegisFragment;
import com.example.sandboxretro.Fragment.Theo.TheoFragment;

public class MainActivity extends AppCompatActivity implements ChangeStateListener{

    public SandBoxRetroApplication mSandBoxRetroApplication;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_enzo:
                    if(mSandBoxRetroApplication.getcurrentScreen() != null && !mSandBoxRetroApplication.getcurrentScreen().equals("carouselEnzo") && !mSandBoxRetroApplication.getcurrentScreen().equals("AftercarouselEnzo")) {
                        if(SandBoxRetroApplication.getInstance().isCarouselViewed()) {
                            mSandBoxRetroApplication.setcurrentScreen("AftercarouselEnzo");
                            onChangeState(EnzoFragmentAfterCarousel.newInstance());

                        } else {
                            mSandBoxRetroApplication.setcurrentScreen("carouselEnzo");
                            onChangeState(EnzoFragment.newInstance());
                        }
                    }
                    return true;
                case R.id.navigation_theo:
                    if(mSandBoxRetroApplication.getcurrentScreen() != null && !mSandBoxRetroApplication.getcurrentScreen().equals("fragmentTheo")) {
                        mSandBoxRetroApplication.setcurrentScreen("fragmentTheo");
                        onChangeState(TheoFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_clement:
                    if(mSandBoxRetroApplication.getcurrentScreen() != null && !mSandBoxRetroApplication.getcurrentScreen().equals("fragmentAddPlayer")) {
                        mSandBoxRetroApplication.setcurrentScreen("fragmentAddPlayer");
                        onChangeState(AddPlayerFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_regis:
                    if(mSandBoxRetroApplication.getcurrentScreen() != null && !mSandBoxRetroApplication.getcurrentScreen().equals("fragmentRegis")) {
                        mSandBoxRetroApplication.setcurrentScreen("fragmentRegis");
                        onChangeState(RegisFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_parameter:
                    if(mSandBoxRetroApplication.getcurrentScreen() != null && !mSandBoxRetroApplication.getcurrentScreen().equals("fragmentParametre")) {
                        mSandBoxRetroApplication.setcurrentScreen("fragmentParametre");
                        onChangeState(ParameterFragment.newInstance());
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSandBoxRetroApplication = new SandBoxRetroApplication();
        mSandBoxRetroApplication.setcurrentScreen("carouselEnzo");
        setContentView(R.layout.activity_main);
        onChangeState(EnzoFragment.newInstance());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onChangeState(Fragment fragment) {
        replaceFragment(R.id.fragment_main, fragment, false);
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * Replace a {@link Fragment} in this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     * @param addBackStack    true if we want this fragment to be added to the backstack
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getCanonicalName());
        if (addBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}

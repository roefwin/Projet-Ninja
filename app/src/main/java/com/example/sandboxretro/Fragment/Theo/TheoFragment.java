package com.example.sandboxretro.Fragment.Theo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sandboxretro.ChangeStateListener;
import com.example.sandboxretro.Fragment.Enzo.EnzoFragment;
import com.example.sandboxretro.Fragment.Theo.Helper.CameraPermissionHelper;
import com.example.sandboxretro.R;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnzoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnzoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TheoFragment extends Fragment {

    private static final String TAG = "TheoFragment";

    private Button mArButton;
    private Session mSession;

    private boolean mUserRequestedInstall;

    private ChangeStateListener mListener;

    public TheoFragment() {
    }

    public static TheoFragment newInstance() {
        TheoFragment fragment = new TheoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theo, container, false);

        mArButton = view.findViewById(R.id.btn_theo_ar_activation);

        mArButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ARActivity.class);
                startActivity(intent);
            }
        });
        maybeEnableArButton();
        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(getActivity())) {
            CameraPermissionHelper.requestCameraPermission(getActivity());
            return;
        }

        // Make sure Google Play Services for AR is installed and up to date.
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(getActivity(), mUserRequestedInstall)) {
                    case INSTALLED:
                        // Success, create the AR session.
                        mSession = new Session(getActivity());
                        break;
                    case INSTALL_REQUESTED:
                        // Ensures next invocation of requestInstall() will either return
                        // INSTALLED or throw an exception.
                        mUserRequestedInstall = false;
                        return;
                }
            }
        } catch (UnavailableUserDeclinedInstallationException | UnavailableArcoreNotInstalledException
                | UnavailableDeviceNotCompatibleException | UnavailableSdkTooOldException
                | UnavailableApkTooOldException e) {
            // Display an appropriate message to the user and return gracefully.
            Toasty.error(getActivity(),"TODO: handle exception " + e, Toasty.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSession != null) {
            mSession.pause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasCameraPermission(getActivity())) {
            Toasty.error(getActivity(), "Camera permission is needed to run this application", Toasty.LENGTH_LONG).show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(getActivity())) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(getActivity());
            }
        }
    }

    private void maybeEnableArButton() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getActivity());
        if (availability.isTransient()) {
            // Re-query at 5Hz while compatibility is checked in the background.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    maybeEnableArButton();
                }
            }, 200);
        }
        if (availability.isSupported() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mArButton.setText("Launch AR");
            mArButton.setVisibility(View.VISIBLE);
            mArButton.setEnabled(true);
            // indicator on the button.
        } else { // Unsupported or unknown.
            mArButton.setText("AR not available for your phone");
            mArButton.setVisibility(View.VISIBLE);
            mArButton.setEnabled(false);
        }
    }
}

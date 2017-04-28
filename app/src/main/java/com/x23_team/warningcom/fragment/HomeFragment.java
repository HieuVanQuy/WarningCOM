package com.x23_team.warningcom.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.x23_team.warningcom.R;


/**
 * Created by weste on 4/21/2017.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private ProgressDialog progressDialog;
    private FloatingActionButton fabAdd, fabLocation;
    private FragmentDrawer.FragmentDrawerListener drawerListener;
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        fabLocation = (FloatingActionButton) rootView.findViewById(R.id.fab_my_location);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_add_charity);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CharityActivity.class);
//                startActivity(intent);
            }
        });

        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                currentMyLocation();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_home);
        supportMapFragment.getMapAsync(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Map Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                progressDialog.dismiss();

            }
        });
        map.getUiSettings().setZoomGesturesEnabled(true);

    }

}

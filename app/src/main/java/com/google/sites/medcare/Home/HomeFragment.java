package com.google.sites.medcare.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.sites.medcare.BloodBank.BloodBank;
import com.google.sites.medcare.Hospital.FilterHospital;
import com.google.sites.medcare.MaternalCare.MaternalCare;
import com.google.sites.medcare.PathologyLab.PathologyLab;
import com.google.sites.medcare.Pharmacy.Pharmacy;
import com.google.sites.medcare.R;
import com.google.sites.medcare.Schemes.FilterPage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    private CardView hosp, path, pharm, bb, ambu, appo;

    private ViewFlipper medFlip;

    private int featureImages[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        hosp = view.findViewById(R.id.cardViewHosp);
        pharm = view.findViewById(R.id.cardViewPharm);
        path = view.findViewById(R.id.cardViewPath);
        bb = view.findViewById(R.id.cardViewBB);
        ambu = view.findViewById(R.id.cardViewAmbu);
        appo = view.findViewById(R.id.cardViewApp);


        medFlip = view.findViewById(R.id.medFeatures);

        hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), FilterHospital.class);
                startActivity(openHosp);
            }
        });

        pharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), Pharmacy.class);
                startActivity(openHosp);
            }
        });

        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), PathologyLab.class);
                startActivity(openHosp);
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), BloodBank.class);
                startActivity(openHosp);
            }
        });

        ambu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), FilterPage.class);
                startActivity(openHosp);
            }
        });

        appo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openHosp = new Intent(getActivity(), MaternalCare.class);
                startActivity(openHosp);
            }
        });

        for (int featureImage: featureImages){
            setFeatureImages(featureImage);
        }

        return view;
    }

    public void setFeatureImages (int featureImage) {

        ImageView imageView = new ImageView(getActivity());

        imageView.setBackgroundResource(featureImage);

        medFlip.addView(imageView);
        medFlip.setFlipInterval(4000);
        medFlip.setAutoStart(true);

        medFlip.setInAnimation(getActivity(), R.anim.slide_in_left);
        medFlip.setOutAnimation(getActivity(), R.anim.slide_out_right);

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

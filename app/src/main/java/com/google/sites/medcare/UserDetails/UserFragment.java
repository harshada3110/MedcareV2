package com.google.sites.medcare.UserDetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.sites.medcare.Home.CircleTransform;
import com.google.sites.medcare.Home.Home;
import com.google.sites.medcare.Hospital.BookAppointment;
import com.google.sites.medcare.R;
import com.google.sites.medcare.SignInSignUp.SignIn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView DPimage;
    private EditText emer;
    private TextView name;
    private TextView BMI;
    private EditText age;
    private EditText height;
    private EditText weight;
    private Spinner spinnerBG;
    private Button save;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        SharedPreferences userDetails = this.getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();

        String uname = userDetails.getString("Name", "MedCare");
        String DP = userDetails.getString("DP", null);
        String nage = userDetails.getString("Age", null);
        String nheight = userDetails.getString("Height", null);
        String nweight = userDetails.getString("Weight", null);
        String nBMI = userDetails.getString("BMI", null);
        int nBG = userDetails.getInt("BloodG", 0);
        String nEmer = userDetails.getString("EmerContact", null);

        List<String> bloodG=new ArrayList<>();
        bloodG.add(0,"Choose");
        bloodG.add(1,"A+ve");
        bloodG.add(2,"A-ve");
        bloodG.add(3,"B+ve");
        bloodG.add(4,"B-ve");
        bloodG.add(5,"O+ve");
        bloodG.add(6,"O-ve");
        bloodG.add(7,"AB+ve");
        bloodG.add(8,"AB-ve");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bloodG);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        name = view.findViewById(R.id.textViewName);
        age = view.findViewById(R.id.ageEditText);
        height = view.findViewById(R.id.heightEditText);
        weight = view.findViewById(R.id.weightEditText);
        emer = view.findViewById(R.id.emerEditText);
        BMI = view.findViewById(R.id.textViewBMIValue);
        spinnerBG = view.findViewById(R.id.spinnerBloodGroup);
        save = view.findViewById(R.id.saveBtn);
        DPimage = view.findViewById(R.id.imageViewDP);


        name.setText(uname);
        Picasso.get().load(DP).transform(new CircleTransform()).fit().into(DPimage);

        age.setText(nage);
        height.setText(nheight);
        weight.setText(nweight);
        BMI.setText(nBMI);
        emer.setText(nEmer);

        spinnerBG.setAdapter(dataAdapter);
        spinnerBG.setSelection(nBG);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (age.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Please enter your age", Toast.LENGTH_SHORT).show();
                }
                else if (height.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Please enter your height", Toast.LENGTH_SHORT).show();
                }
                else if (weight.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Please enter your weight", Toast.LENGTH_SHORT).show();
                }
                else if (spinnerBG.getSelectedItemPosition() == 0){
                    Toast.makeText(getActivity(), "Please select your blood group", Toast.LENGTH_SHORT).show();
                }
                else if (emer.getText().toString().matches("")){
                    Toast.makeText(getActivity(), "Please enter your emergency contact", Toast.LENGTH_SHORT).show();
                }
                else {
                    int BMIvalue = (Integer.parseInt(weight.getText().toString())/((Integer.parseInt(height.getText().toString())/100)^2));
                    String BMIstate = null;
                    if (BMIvalue<15){
                        BMIstate = "Very severely underweight";
                    }
                    else if (BMIvalue<=16){
                        BMIstate = "Severely underweight";
                    }
                    else if (BMIvalue<=18.5){
                        BMIstate = "Underweight";
                    }
                    else if (BMIvalue<=25){
                        BMIstate = "Normal (healthy weight)";
                    }
                    else if (BMIvalue<=30){
                        BMIstate = "Overweight";
                    }
                    else if (BMIvalue<=35){
                        BMIstate = "Moderately obese";
                    }
                    else if (BMIvalue<=40){
                        BMIstate = "Severely obese";
                    }
                    else {
                        BMIstate = "Very severely obese";
                    }

                    int spinnerPos = spinnerBG.getSelectedItemPosition()+1;
                    editDetails.putString("Age", age.getText().toString());
                    editDetails.putString("Height", height.getText().toString());
                    editDetails.putString("Weight", weight.getText().toString());
                    editDetails.putString("BMI", String.valueOf(BMIvalue)+" ["+BMIstate+"]");
                    editDetails.putInt("BloodG", spinnerPos);
                    editDetails.putString("EmerContact", emer.getText().toString());
                    editDetails.apply();

                    BMI.setText(String.valueOf(BMIvalue)+" ["+BMIstate+"]");

                    Toast toast = Toast.makeText(getActivity(), "Details Saved Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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

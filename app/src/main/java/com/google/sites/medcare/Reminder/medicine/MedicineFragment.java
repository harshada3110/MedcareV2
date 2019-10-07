package com.google.sites.medcare.Reminder.medicine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.sites.medcare.R;
import com.google.sites.medcare.Reminder.addmedicine.AddMedicineActivity;
import com.google.sites.medcare.Reminder.report.MonthlyReportActivity;
import com.google.sites.medcare.Reminder.source.MedicineAlarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MedicineFragment extends Fragment implements MedicineContract.View{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView rvMedList;
    private ImageView noMedIcon;
    private TextView noMedText;
    private TextView addMedNow;
    private View noMedView;
    private ProgressBar progressLoader;
    private FloatingActionButton fab_add_med;
    private FloatingActionButton history_med;

    private CompactCalendarView mCompactCalendarView;
    private LinearLayout calendarContainer;
    private TextView datePickerTextView;
    private RelativeLayout datePickerButton;
    private FrameLayout contentFrame;
    private FloatingActionButton fabAddTask;
    private CoordinatorLayout coordinatorLayout;
    private ImageView arrow;

    private MedicineContract.Presenter presenter;
    private MedicineAdapter medicineAdapter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", /*Locale.getDefault()*/Locale.ENGLISH);
    private boolean isExpanded = false;

    public MedicineFragment() {
        // Required empty public constructor
    }

    public static MedicineFragment newInstance(String param1, String param2) {
        MedicineFragment fragment = new MedicineFragment();
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
        medicineAdapter = new MedicineAdapter(new ArrayList<MedicineAlarm>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        rvMedList = view.findViewById(R.id.medicine_list);
        noMedIcon = view.findViewById(R.id.noMedIcon);
        noMedText = view.findViewById(R.id.noMedText);
        addMedNow = view.findViewById(R.id.add_med_now);
        noMedView = view.findViewById(R.id.no_med_view);
        progressLoader = view.findViewById(R.id.progressLoader);
        fab_add_med = view.findViewById(R.id.add_med_fab);
        history_med = view.findViewById(R.id.med_stat_fab);

        mCompactCalendarView = view.findViewById(R.id.compactcalendar_view);
        calendarContainer = view.findViewById(R.id.compactcalendar_view_container);
        datePickerTextView = view.findViewById(R.id.date_picker_text_view);
        datePickerButton = view.findViewById(R.id.date_picker_button);
        contentFrame = view.findViewById(R.id.contentFrame);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        arrow = view.findViewById(R.id.date_picker_arrow);

        setAdapter();

        fab_add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.addNewMedicine();
                Intent intent = new Intent(getActivity(), AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        history_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MonthlyReportActivity.class);
                startActivity(intent);
            }
        });

        addMedNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMedicine();
            }
        });

        mCompactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);
        mCompactCalendarView.setShouldDrawDaysHeader(true);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateClicked);

                int day = calendar.get(Calendar.DAY_OF_WEEK);

                /*if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                }
                isExpanded = !isExpanded;*/
                presenter.reload(day);
                calendarContainer.setVisibility(View.GONE);
                ViewCompat.animate(arrow).rotation(0).start();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarContainer.getVisibility() == View.GONE) {
                    calendarContainer.setVisibility(View.VISIBLE);
                    ViewCompat.animate(arrow).rotation(180).start();

                } else {
                    calendarContainer.setVisibility(View.GONE);
                    ViewCompat.animate(arrow).rotation(0).start();
                }
            }
        });

        setCurrentDate(new Date());

        return view;
    }

    public void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        mCompactCalendarView.setCurrentDate(date);
    }

    public void setSubtitle(String subtitle) {
        datePickerTextView.setText(subtitle);
    }


    private void setAdapter() {
        rvMedList.setAdapter(medicineAdapter);
        rvMedList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMedList.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        presenter.onStart(day);
    }

    @Override
    public void setPresenter(MedicineContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        progressLoader.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMedicineList(List<MedicineAlarm> medicineAlarmList) {
        medicineAdapter.replaceData(medicineAlarmList);
        rvMedList.setVisibility(View.VISIBLE);
        noMedView.setVisibility(View.GONE);
    }

    @Override
    public void showAddMedicine() {
        Intent intent = new Intent(getContext(), AddMedicineActivity.class);
        startActivityForResult(intent, AddMedicineActivity.REQUEST_ADD_TASK);
    }


    @Override
    public void showMedicineDetails(long taskId, String medName) {
        Intent intent = new Intent(getContext(), AddMedicineActivity.class);
        intent.putExtra(AddMedicineActivity.EXTRA_TASK_ID, taskId);
        intent.putExtra(AddMedicineActivity.EXTRA_TASK_NAME, medName);
        startActivity(intent);
    }


    @Override
    public void showLoadingMedicineError() {
        showMessage(getString(R.string.loading_tasks_error));
    }

    @Override
    public void showNoMedicine() {
        showNoTasksViews(
                getResources().getString(R.string.no_medicine_added),
                R.drawable.icon_my_health
        );
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_me_message));
    }

    private void showMessage(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void showNoTasksViews(String mainText, int iconRes) {
        rvMedList.setVisibility(View.GONE);
        noMedView.setVisibility(View.VISIBLE);
        noMedText.setText(mainText);
        noMedIcon.setImageDrawable(getResources().getDrawable(iconRes));
        addMedNow.setVisibility(View.VISIBLE);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

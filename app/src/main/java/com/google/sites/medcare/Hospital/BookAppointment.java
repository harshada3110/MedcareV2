package com.google.sites.medcare.Hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.sites.medcare.Home.Home;
import com.google.sites.medcare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookAppointment extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private Button Send;
    private TextInputLayout UName,UNumber,UEmail,UDate,UTime;
    private TextInputEditText Datex,Timex;
    TextView HospName,SpecialityName;
    DatabaseReference mydB;
    BookAppointmentList bookAppointmentList;
    String date, time;
    String Speciality, LocationS;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences userDetails = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String email = userDetails.getString("Email", null);
        String name = userDetails.getString("Name", null);

        Send=findViewById(R.id.pushbtn);
        UName=findViewById(R.id.edittextusername);
        UNumber=findViewById(R.id.edittextusernumber);
        UEmail=findViewById(R.id.edittextuseremail);
        UDate=findViewById(R.id.edittextAppoDate);
        UTime=findViewById(R.id.edittextAppoTime);
        Datex=findViewById(R.id.dateAppo);
        Timex=findViewById(R.id.timeAppo);
        HospName=findViewById(R.id.AppointmentHospName);
        SpecialityName=findViewById(R.id.AppointmentSpecialityName);
        bookAppointmentList = new BookAppointmentList();

        mydB= FirebaseDatabase.getInstance().getReference().child("Appointment");

        //Shared Preferenced for getting Speciaility value from MainActivity(ie Cardiology,Dentist etc)
        SharedPreferences mySharedPreferences = this.getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);
        Speciality = mySharedPreferences.getString("Speciality", "");
        LocationS = mySharedPreferences.getString("Location", "");

        //Get HospName from intent HospitalAdapter
        String AppoHospName=getIntent().getStringExtra("HospName");

        //Setting textview value
        HospName.setText(AppoHospName);
        SpecialityName.setText(Speciality);

        UEmail.getEditText().setText(email);
        UName.getEditText().setText(name);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validateUserNumber() | !validateEmail() |!validateDate() | !validateTime()

                ) {
                    return;
                }
                mydB= FirebaseDatabase.getInstance().getReference().child("Appointments");
                int Status=0;
                bookAppointmentList.setName(UName.getEditText().getText().toString().trim());
                bookAppointmentList.setNumber(UNumber.getEditText().getText().toString().trim());
                bookAppointmentList.setEmail(UEmail.getEditText().getText().toString().trim());
                bookAppointmentList.setDate(date);
                bookAppointmentList.setTime(time);
                bookAppointmentList.setSpecialist(Speciality);
                bookAppointmentList.setStatus(Status);
                bookAppointmentList.setComments("");
                bookAppointmentList.setPrescription("");
                bookAppointmentList.setHospitalName(AppoHospName);
                bookAppointmentList.setAvisited(0);
                bookAppointmentList.setLocation(LocationS);
                bookAppointmentList.setEmail(email);
                mydB.push().setValue(bookAppointmentList);
                //  Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_LONG);
                Toast toast = Toast.makeText(BookAppointment.this, "Appointment Requsted Successfully", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent=new Intent(BookAppointment.this, Home.class);
                startActivity(intent);

                //to clesr input field after button click
                UName.getEditText().setText("");
                UNumber.getEditText().setText("");
                UEmail.getEditText().setText("");
                UDate.getEditText().setText("");
                UTime.getEditText().setText("");

            }
        });

        //for date selection
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //Onclick for DatePickerDialog
        UDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookAppointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Datex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookAppointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Timex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");


            }
        });
    }
    private boolean validateUsername() {
        String usernameInput = UName.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            UName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 18) {
            UName.setError("Username too long");
            return false;
        } else {
            UName.setError(null);
            return true;
        }
    }

    //for checking usernumber
    public boolean validateUserNumber(){
        String usernumber=UNumber.getEditText().getText().toString().trim();

        if(usernumber.isEmpty()){
            UNumber.setError("Phone No. cant't be empty");
            return false;
        }else if (usernumber.length() != 10 ) {
            UNumber.setError("Must contain 10 digits");
            return false;
        } else {
            UNumber.setError(null);
            return true;
        }
    }


    //for checking user email
    private boolean validateEmail() {
        String emailInput = UEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            UEmail.setError("Field can't be empty");
            return false;
        }else if (emailInput.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && emailInput.length() > 0 ) {
            UEmail.setError(null);
            return true;
        } else {
            UEmail.setError("Invalid Email");
            return false;
        }

    }

    private boolean validateDate() {
        String date = UDate.getEditText().getText().toString().trim();

        if (date.isEmpty()) {
            UDate.setError("Field can't be empty");
            return false;
        } else {
            UDate.setError("");
            return true;
        }

    }

    private boolean validateTime() {
        String time = UTime.getEditText().getText().toString().trim();

        if (time.isEmpty()) {
            UTime.setError("Field can't be empty");
            return false;
        } else {
            UTime.setError("");
            return true;
        }

    }

    //fucntion for setting date
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        // UDate.setHint(sdf.format(myCalendar.getTime()));
        date=sdf.format(myCalendar.getTime());
        UDate.getEditText().setText(date);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        //   UTime.setHint(""+hourOfDay+":"+minute);
        time=hourOfDay+":"+minute;
        UTime.getEditText().setText(time);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

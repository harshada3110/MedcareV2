package com.google.sites.medcare.Hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.sites.medcare.R;

public class SelectSpecialist extends AppCompatActivity {

    ListView listView;
    String mTitle[]={"Cardiologist","Dentist","ENT","Gynaecologist","Neurologist","Oncologist","Gastroenterologists"};
    int images[]={R.drawable.pulmonology,R.drawable.radiology,R.drawable.neurology,R.drawable.oncology,R.drawable.opthamology,R.drawable.cancer,R.drawable.nephrologist};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_specialist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.domain_list);

        MyAdapter adapter = new MyAdapter(this, mTitle, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Getting listview click value into String variable.
                String TempListViewClickedValue = mTitle[position];

                Intent intent = new Intent(SelectSpecialist.this, Hospital.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter(Context c, String title[], int imgs[]) {
            super(c, R.layout.specialist_row, R.id.domain_name, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.specialist_row, parent, false);
            ImageView images = row.findViewById(R.id.domain_image);
            TextView myTitle = row.findViewById(R.id.domain_name);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

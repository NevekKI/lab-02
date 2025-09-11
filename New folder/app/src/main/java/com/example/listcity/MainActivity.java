package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton;
    Button deleteButton;
    EditText itemEdit;
    //selectedCity keeps track of which city is selected (-1 = none)
    int selectedCity = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        itemEdit = findViewById(R.id.addCityText);

        cityList = findViewById(R.id.city_list);
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // set up adapter to show all the cities in the listView
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);
        cityList.setAdapter(cityAdapter);

        // this only allows one city to be clicked at the same time
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // disable the delete button until a city is selected
        deleteButton.setEnabled(false);

        // this stores the position of the city we click and turns on the delete button
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = position;
            deleteButton.setEnabled(true);
            cityList.setItemChecked(position, true);
        });


        // This is our add button, allows us to enter text into the input and add it as a city
        addButton.setOnClickListener(v -> {
            String cityName = itemEdit.getText().toString();

            if (!cityName.isEmpty()) {
                cityAdapter.add(cityName);
                cityAdapter.notifyDataSetChanged();
            }

        });

        // this is our delete button, it checks if a city is selected and removes it from the list
        deleteButton.setOnClickListener(v -> {
            if (selectedCity < 0 || selectedCity >= dataList.size()) {
                return;
            }

            dataList.remove(selectedCity);
            cityAdapter.notifyDataSetChanged();

            selectedCity = -1;
            deleteButton.setEnabled(false);
            // clears the highlight of the city we selected
            cityList.clearChoices();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }
}
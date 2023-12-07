package com.example.customadapterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;
    ArrayList<User> users;
    Spinner spinner;
    EditText name, phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        spinner = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);

        String[] avatars = Arrays.stream(Avatar.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);

        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerAdapter.addAll(new ArrayList<>(Arrays.asList(avatars)));

        spinner.setAdapter(SpinnerAdapter);

        try {
            InputStream stream = getAssets().open("entries.json");
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            Type movieListType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(reader, movieListType);
        } catch(IOException e){
            users = null;
        }

        adapter = new UserListAdapter(this, users);
        listView.setAdapter(adapter);
    }

    public void onClickAdd(View view){

        User user = new User(name.getText().toString(), phoneNumber.getText().toString(), Avatar.valueOf(spinner.getSelectedItem().toString()));
        users.add(user);
        adapter.notifyDataSetChanged();

    }
    public void nameSort(View view){
        users.sort(Comparator.comparing(obj -> obj.name));
        adapter.notifyDataSetChanged();

    }

    public void phoneNumberSort(View view){
        users.sort(Comparator.comparing(obj -> obj.phoneNumber));
        adapter.notifyDataSetChanged();
    }

    public void avatarSort(View view){
        users.sort(Comparator.comparing(obj -> obj.avatar));
        adapter.notifyDataSetChanged();
    }
}

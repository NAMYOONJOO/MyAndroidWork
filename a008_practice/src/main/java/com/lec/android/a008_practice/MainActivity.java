package com.lec.android.a008_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    PersonAdapter adapter;
    EditText etName,etAge,etAddress;
    Button btnAdd;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        etAddress= findViewById(R.id.etAddress);
        etName= findViewById(R.id.etName);
        etAge= findViewById(R.id.etAge);

        btnAdd = findViewById(R.id.btnAdd);

        adapter = new PersonAdapter();

        rv.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(etName.getText().toString(),etAge.getText().toString(),etAddress.getText().toString());
                adapter.addPerson(person);
                etAddress.setText("");
                etName.setText("");
                etAge.setText("");


            }
        });

    }



}

package com.example.sqlite_for_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonViewAll, buttonAdd;
    EditText editTextName, editTextAge;
    Switch switchActive;
    ListView listViewCustomerList;
    ArrayAdapter customerArrayAdapter;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonViewAll = findViewById(R.id.buttonViewAll);
        buttonAdd = findViewById(R.id.buttonAdd);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        switchActive = findViewById(R.id.switchActive);
        listViewCustomerList = findViewById(R.id.listViewCustomerList);
        dataBase = new DataBase(MainActivity.this);
        showCustomersOnListView(dataBase);


        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase = new DataBase(MainActivity.this);

                showCustomersOnListView(dataBase);

                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomerModel customerModel;

                try {
                    customerModel = new CustomerModel(1, editTextName.toString(), Integer.parseInt(editTextAge.getText().toString()), switchActive.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "Error", 0, false);
                }

                DataBase dataBase = new DataBase(MainActivity.this);

                boolean success = dataBase.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                showCustomersOnListView(dataBase);
            }
        });

        listViewCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBase.deleteOne(clickedCustomer);
                showCustomersOnListView(dataBase);
                Toast.makeText(MainActivity.this, "Deleted!" + clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomersOnListView(DataBase dataBase2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBase2.getEveryone());
        listViewCustomerList.setAdapter(customerArrayAdapter);
    }
}
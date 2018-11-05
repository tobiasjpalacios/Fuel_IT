package com.fuel.app.exe.fuel_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private ListView dataListView;
    private EditText vehiculoName;
    private EditText editConsumo;
    private EditText editTanque;
    private Button deleteButton;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Vehiculo");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = (ListView) findViewById(R.id.dataListView);
        vehiculoName = (EditText) findViewById(R.id.vehiculoName);
        editConsumo = (EditText)findViewById(R.id.editConsumo);
        editConsumo = (EditText)findViewById(R.id.editConsumo);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setEnabled(false);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listItems);
        dataListView.setAdapter(adapter);
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dataListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                    }
                });

        addChildEventListener();
    }

    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add(
                        (String) dataSnapshot.child("description").getValue());

                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);
                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);
    }



    public void AddItem(View view) {
        vehiculoName = (EditText)findViewById(R.id.vehiculoName);
        editConsumo = (EditText)findViewById(R.id.editConsumo);
        editTanque = (EditText)findViewById(R.id.editConsumo);
        String nombre = vehiculoName.getText().toString();
        String id = dbRef.push().getKey();
        vehiculoName.setText("");
        dbRef.child(id).child("description").setValue(nombre);
        adapter.notifyDataSetChanged();
    }

    public void DeleteItem(View view) {
        dataListView.setItemChecked(selectedPosition, false);
        dbRef.child(listKeys.get(selectedPosition)).removeValue();
    }


}

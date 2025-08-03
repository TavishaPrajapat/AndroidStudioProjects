package com.tavisha.assign3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Friends_770> friendsArrayList;
    private static MyCustomAdapter_770 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview770);

        friendsArrayList = new ArrayList<>();

        // Adding friends to the list with images
        Friends_770 friend1 = new Friends_770("Simer", "Brampton", R.drawable.simer);
        Friends_770 friend2 = new Friends_770("Tanmay", "Mississauga", R.drawable.tanmay);
        Friends_770 friend3 = new Friends_770("Ajay", "Vaughan", R.drawable.ajay);
        Friends_770 friend4 = new Friends_770("Suhani", "Indiana, USA", R.drawable.suhani);
        Friends_770 friend5 = new Friends_770("Kartik", "Brampton", R.drawable.kartik);
        Friends_770 friend6 = new Friends_770("Yuvraj", "Hamilton", R.drawable.yuvraj);
        friendsArrayList.add(friend1);
        friendsArrayList.add(friend2);
        friendsArrayList.add(friend3);
        friendsArrayList.add(friend4);
        friendsArrayList.add(friend5);
        friendsArrayList.add(friend6);


        adapter = new MyCustomAdapter_770(friendsArrayList, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        MainActivity.this,
                        "Friend's Name: " + adapter.getItem(position).getName() +
                                "\nLocation: " + adapter.getItem(position).getLocation(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
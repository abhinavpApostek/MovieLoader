package com.example.abhinav.movieloader;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String uri[]={"https://s-media-cache-ak0.pinimg.com/originals/44/a4/ed/44a4ed1d4eb3c15d1c4ee989c531e501.jpg",
    "https://1.bp.blogspot.com/-XXpisFgwEDM/VXmVE5WvI5I/AAAAAAAACyc/eVK-v4tC6pg/s1600/Dabangai%2BBhojpuri%2BMovie%2BNew%2BPoster%2BRavi%2BKishan.jpg",
    "http://img01.ibnlive.in/ibnlive/uploads/2010/03/dhoom2_630x420.jpg",
    "https://i.ytimg.com/vi/MU1wKKB5REM/hqdefault.jpg",
    "https://i.ytimg.com/vi/iBFrKgaMYv4/0.jpg",
    "https://pbs.twimg.com/media/DGxjFjrXsAAMsKm.jpg",
    "https://i.ytimg.com/vi/WKvBHUCVssk/maxresdefault.jpg"};
    String movie_name[]={"Dangal","Dabangai","Dhoom2","Karan Arjun 2","Best movies","Bhoomi","Raees"};
    ArrayList<String> data;
    ArrayList<String> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        data=new ArrayList<>(Arrays.asList(uri));
         movies=new ArrayList<>(Arrays.asList(movie_name));

        if(PreferenceManager.getDefaultSharedPreferences(this).getString("style","0").equals("1")) {
            CustomAdapter customAdapter = new CustomAdapter(this.data, null, 1, this);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(customAdapter);
        }
        else
        {
            CustomAdapter customAdapter = new CustomAdapter(data, movies, 0, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(customAdapter);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_favorite)
        {
            Intent intent=new Intent(this,CustomSettings.class);
            startActivityForResult(intent,0);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {
            if(data.getExtras().getString("style","0").equals("1")) {
                CustomAdapter customAdapter = new CustomAdapter(this.data, null, 1, this);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(customAdapter);
            }
            else if(data.getExtras().getString("style","0").equals("0"))
            {
                CustomAdapter customAdapter=new CustomAdapter(this.data,movies,0,this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(customAdapter);
            }
        }
    }
}

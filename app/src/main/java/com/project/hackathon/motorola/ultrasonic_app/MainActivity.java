package com.project.hackathon.motorola.ultrasonic_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.project.hackathon.motorola.ultrasonic_app.adapter.SpaceCustomAdapter;
import com.project.hackathon.motorola.ultrasonic_app.handler.DatabaseHandler;
import com.project.hackathon.motorola.ultrasonic_app.model.Space;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertSpaceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHandler(this);

        ListView listViewProduto;
        listViewProduto = (ListView) findViewById(R.id.listSpace);

        ArrayList<Space> space = (ArrayList<Space>) db.getAllSpaces();

        SpaceCustomAdapter rankingCustomAdapter;
        rankingCustomAdapter = new SpaceCustomAdapter(space, this);

        listViewProduto.setAdapter(rankingCustomAdapter);
    }
}

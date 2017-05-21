package com.project.hackathon.motorola.ultrasonic_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hackathon.motorola.ultrasonic_app.adapter.SpaceCustomAdapter;
import com.project.hackathon.motorola.ultrasonic_app.handler.DatabaseHandler;
import com.project.hackathon.motorola.ultrasonic_app.model.Space;

import java.util.ArrayList;

public class InformationSpaceActivity extends AppCompatActivity {

    private int id;
    private TextView name, position1, position2, position3, position4, metro_qdr, paredeAC, paredeBD, AB, BD;
    private ImageView iv_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent();
        id = Integer.parseInt(myIntent.getStringExtra("id"));

        populate(id);
    }

    DatabaseHandler db;

    protected void populate(int id) {
        db = new DatabaseHandler(this);

        Log.d("xxxx", ""+ id);

        Space space = db.getSpace(id);

        name = (TextView) findViewById(R.id.tv_name);
        position1 = (TextView) findViewById(R.id.tv_position1);
        position2 = (TextView) findViewById(R.id.tv_position2);
        position3 = (TextView) findViewById(R.id.tv_position3);
        position4 = (TextView) findViewById(R.id.tv_position4);
        metro_qdr = (TextView) findViewById(R.id.tv_metro_qdr);
        paredeAC = (TextView) findViewById(R.id.tv_paredeAC);
        paredeBD = (TextView) findViewById(R.id.tv_paredeBD);
        AB = (TextView) findViewById(R.id.tv_AB);
        BD = (TextView) findViewById(R.id.tv_BD);

        name.setText("" + space.getName());
        position1.setText("" + space.getPosition1());
        position2.setText("" + space.getPosition2());
        position3.setText("" + space.getPosition3());
        position4.setText("" + space.getPosition4());
        paredeAC.setText("" + space.getPosition1() + space.getPosition3());
        paredeBD.setText("" + space.getPosition2() + space.getPosition4());
        AB.setText("" + space.getPosition1() + space.getPosition3() + "m");
        BD.setText("" + space.getPosition2() + space.getPosition4() + "m");

        metro_qdr.setText("" + (space.getPosition1() * space.getPosition2()) + "m2");

        iv_copy = (ImageView) findViewById(R.id.iv_copy);
        iv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(metro_qdr.getText().toString());   // Assuming that you are copying the text from a TextView
                Toast.makeText(getApplicationContext(), "Copiado para o Clipboard: " + metro_qdr.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.project.hackathon.motorola.ultrasonic_app;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by matheuscatossi on 5/20/17.
 */

public class SuperActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

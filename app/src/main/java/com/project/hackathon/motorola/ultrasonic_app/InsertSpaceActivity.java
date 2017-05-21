package com.project.hackathon.motorola.ultrasonic_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.motorola.mod.ModManager;
import com.project.hackathon.motorola.ultrasonic_app.blinky.ModAssistant;
import com.project.hackathon.motorola.ultrasonic_app.blinky.ModRawProtocol;
import com.project.hackathon.motorola.ultrasonic_app.handler.DatabaseHandler;
import com.project.hackathon.motorola.ultrasonic_app.model.Space;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class InsertSpaceActivity extends AppCompatActivity {
    private EditText name, position1, position2, position3, position4;
    private Button add, pass;
    private ImageView iv_position1;
    private ImageView iv_position2;
    private ImageView iv_position3;
    private ImageView iv_position4;
    private DatabaseHandler db;
    private int count = 1;

    private ModAssistant modAssistant;
    private ModRawProtocol modRawProtocol;
    private static String TAG = "ModBlinky";
    private final int REQUEST_RAW_PERMISSION = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.input_name);
        position1 = (EditText) findViewById(R.id.input_position1);
        position2 = (EditText) findViewById(R.id.input_position2);
        position3 = (EditText) findViewById(R.id.input_position3);
        position4 = (EditText) findViewById(R.id.input_position4);
        add = (Button) findViewById(R.id.btn_add);

        db = new DatabaseHandler(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addSpace(new Space(name.getText().toString(), Double.parseDouble(position1.getText().toString()), Double.parseDouble(position2.getText().toString()), Double.parseDouble(position3.getText().toString()), Double.parseDouble(position4.getText().toString())));
                Toast.makeText(getApplicationContext(),
                        "Adicionado com sucesso", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        pass = (Button) findViewById(R.id.btn_pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count < 5){
                    takeImageFromCamera(v);
                    switch (count){
                        case 1:
                            position1.setText("" + 5);
                            break;
                        case 2:
                            position2.setText("" + 15);
                            break;
                        case 3:
                            position3.setText("" + 12);
                            break;
                        case 4:
                            position4.setText("" + 3);
                            break;
                        default:
                            break;
                    }

                    count ++;
                    if(count != 5){
                        pass.setText("" + count);
                    } else {
                        pass.setVisibility(View.INVISIBLE);
                        add.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        // Blinky

        // MOTOMOD: Verifying that the app is not compatible
        if (!ModAssistant.isSmartphoneCompatible(getApplicationContext())) {
            Toast.makeText(getBaseContext(), "Smartphone is not compatible with Mods architecture", Toast.LENGTH_SHORT).show();
        }

        // MOTOMOD: Check and grant permission to use Raw Protocol.
        if (this.checkSelfPermission(ModManager.PERMISSION_USE_RAW_PROTOCOL)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ModManager.PERMISSION_USE_RAW_PROTOCOL},
                    REQUEST_RAW_PERMISSION);
        }

        Log.i(TAG, "onCreate");

        modAssistant = ModAssistant.getInstance(this);
        modAssistant.registerListener(handler);

        modRawProtocol = ModRawProtocol.getInstance(this);
        modAssistant.setModRawProtocol(modRawProtocol);
        modRawProtocol.registerListener(handler);

        updateDeviceInfo();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case (ModAssistant.ACTION_MOD_ATTACH):
                    Log.i(TAG, "ModAssistant.ACTION_MOD_ATTACH");
                    updateDeviceInfo();
                    break;
                case (ModAssistant.ACTION_MOD_DETACH):
                    Log.i(TAG, "ModAssistant.ACTION_MOD_DETACH");
                    updateDeviceInfo();
                    break;
                case (ModRawProtocol.MSG_RAW_DATA):
                    byte[] content = (byte[]) (msg.obj);
                    Log.i(TAG, "MSG_RAW_DATA content: " + content[0]);
            }
        }
    };


    private void updateDeviceInfo() {
        if (modAssistant.getModDevice() == null) {
            Toast.makeText(getBaseContext(), "DETACHED", Toast.LENGTH_SHORT).show();
        } else {
            String modDescription = "Vendor/Product: " + modAssistant.getModDevice().getVendorId() + " / " + modAssistant.getModDevice().getProductId() + " - "
                    + modAssistant.getModDevice().getProductString();
            Toast.makeText(getBaseContext(), "ATTACH: "+modDescription, Toast.LENGTH_SHORT).show();
            boolean rawAvailable = ModRawProtocol.checkRawProtocolAvailable(modAssistant.getModManager(), modAssistant.getModDevice());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        modAssistant.unregisterListener(handler);
        modRawProtocol.unregisterListener(handler);
    }

    private static final int CAMERA_REQUEST = 1888;

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            modRawProtocol.sendRawData(new byte[] {0x01});
            switch (count){
                case 2:
                    iv_position1 = (ImageView) findViewById(R.id.iv_position1);
                    iv_position1.setImageBitmap(mphoto);
                    break;
                case 3:
                    iv_position2 = (ImageView) findViewById(R.id.iv_position2);
                    iv_position2.setImageBitmap(mphoto);
                    break;
                case 4:
                    iv_position3 = (ImageView) findViewById(R.id.iv_position3);
                    iv_position3.setImageBitmap(mphoto);
                    break;
                case 5:
                    iv_position4 = (ImageView) findViewById(R.id.iv_position4);
                    iv_position4.setImageBitmap(mphoto);
                    break;
                default:
                    break;
            }
        }
    }
}

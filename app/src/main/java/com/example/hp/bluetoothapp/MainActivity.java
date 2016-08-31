package com.example.hp.bluetoothapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Switch aSwitch;
    BluetoothAdapter bAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bAdapter = BluetoothAdapter.getDefaultAdapter();

        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setChecked(bAdapter.isEnabled());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bAdapter.enable();
                } else {
                    bAdapter.disable();
                }
            }
        });

    }

    public void getbtdevices(View v) {
        bAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(new MyReceiver(),filter);
    }

    public void getbtpaireddevices(View v) {
        Set<BluetoothDevice> devices=bAdapter.getBondedDevices();
        for(BluetoothDevice device:devices){
            Toast.makeText(getApplicationContext(),device.getName()+"\n"+device.getAddress(),Toast.LENGTH_SHORT).show();
        }

    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Toast.makeText(getApplicationContext(),device.getName(),Toast.LENGTH_SHORT).show();
        }
    }
}
package com.eywa_kitchen.Eywa_client;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.eywa_kitchen.Eywa_client.Controllers.NotificationController;
import com.eywa_kitchen.Eywa_client.Controllers.SendController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static com.eywa_kitchen.Eywa_client.MainActivity.MainContext;
import static xdroid.toaster.Toaster.toast;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View View;
    private BluetoothSocket socket;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference SensorRef = database.getReference("alarm");
    private NotificationManager notificationManager;
    private BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter =null;
    private BluetoothDevice device;
    private UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    public MainPresenter(MainContract.View View) {
        this.View = View;
    }

    @Override
    public void initPresenter() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        notificationManager = (NotificationManager) MainContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        SensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("FIRE")) NotificationController.SendNotif_fire(MainContext, notificationManager);
                if (value.equals("GAS"))NotificationController.SendNotif_gas(MainContext, notificationManager);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    public void BluetoothConnect() {
        mmDevice = device;
        BluetoothSocket tmp = null;
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            View.showButtonState("Подключить");
            toast("Ошибка при подключении");
        }
        socket = tmp;
        mBluetoothAdapter.cancelDiscovery();
        try {
            socket.connect();
            View.showButtonState("Отключить");
            toast("Подключено");
        } catch (IOException e) {
            View.showButtonState("Подключить");
            toast("Ошибка при подключении");
            try {
                socket.close();
                socket = null;
            } catch (IOException e2) {
                toast("Ошибка при подключении");
                View.showButtonState("Подключить");
                socket = null;
            }
        }
    }

    @Override
    public void BluetoothDisconnect() {
        try {
            socket.close();
            View.showButtonState("Подключить");
            device=null;
            toast("Отключено");
        } catch (IOException e) {
            View.showButtonState("Подключить");
        }

    }

    @Override
    public void sendButtonClicked(int Code) {
        SendController.write(Code, socket);
    }

    @Override
    public void ConnectBtnClicked(int State) {
       switch (State){
           case 0:
               Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
               View.showChooseAlert(pairedDevices, device);
               break;
           case 2:
               View.showDisconnectAlert();
               break;
       }
    }
}

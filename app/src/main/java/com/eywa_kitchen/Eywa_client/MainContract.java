package com.eywa_kitchen.Eywa_client;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.TextView;

import java.util.Set;

public interface MainContract {
    interface View {
        void showButtonState(String state);
        void showDisconnectAlert();
        void showChooseAlert(Set<BluetoothDevice> pairedDevices, BluetoothDevice mDevice);
    }

    interface Presenter {
        void initPresenter();
        void BluetoothConnect();
        void BluetoothDisconnect();
        void sendButtonClicked(int Code);
        void ConnectBtnClicked(int State);

    }

    interface Model {
    }
}

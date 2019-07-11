package com.eywa_kitchen.Eywa_client;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.Set;

import static xdroid.toaster.Toaster.toast;
import static xdroid.toaster.Toaster.toastLong;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private Button BtnUp;
    private Button BtnDown;
    private Button BtnLeft;
    private Button BtnRight;
    private Button BtnEnter;
    private Button BtnConnect;
    private Button BtnMicro;
    private Button BtnPrev;
    private Button BtnNext;
    private Button BtnPlayPause;
    private Button BtnHome;
    private Button BtnBack;
    private BluetoothDevice device;

    public static Context MainContext;
    private MainContract.Presenter Presenter;
    private int ButtonState=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();
        findViews();
        setListners();
        initPresenter();
    }

    private void initActivity(){
        getSupportActionBar().hide();
        MainContext = this;
    }

    private void findViews(){
        BtnConnect = (Button)findViewById(R.id.BtnConnect);
        BtnUp = (Button)findViewById(R.id.BtnUp);
        BtnDown = (Button)findViewById(R.id.BtnDown);
        BtnLeft = (Button)findViewById(R.id.BtnLeft);
        BtnRight = (Button)findViewById(R.id.BtnRight);
        BtnEnter = (Button)findViewById(R.id.BtnEnter);
        BtnBack = (Button)findViewById(R.id.BtnBack);
        BtnHome = (Button)findViewById(R.id.BtnHome);
        BtnPlayPause = (Button)findViewById(R.id.BtnPlayPause);
        BtnMicro = (Button)findViewById(R.id.BtnMicro);
        BtnPrev = (Button)findViewById(R.id.BtnPrev);
        BtnNext = (Button)findViewById(R.id.BtnNext);

        BtnConnect.setBackgroundResource(R.color.green);
    }

    private void setListners(){
        BtnUp.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(1);

            }
        });

        BtnDown.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(2);

            }
        });

        BtnLeft.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(3);

            }
        });

        BtnRight.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(4);

            }
        });

        BtnEnter.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(5);

            }
        });

        BtnBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(6);

            }
        });

        BtnHome.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(7);

            }
        });

        BtnPlayPause.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) { Presenter.sendButtonClicked(8);
            }
        });

        BtnMicro.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(9);

            }
        });

        BtnPrev.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(10);

            }
        });

        BtnNext.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.sendButtonClicked(11);

            }
        });

        BtnConnect.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Presenter.ConnectBtnClicked(ButtonState);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Presenter.sendButtonClicked(14);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Presenter.sendButtonClicked(15);
                return true;
        }
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showButtonState(String state) {
        switch (state){
            case "Подключить":
                setBtnConnect();
                break;
            case "Подключение":
                setBtnConnection();
                break;
            case "Отключить":
                setBtnConnected();
                break;
        }
    }

    @Override
    public void showDisconnectAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.MainContext);
        alert.setTitle("EYWA client");
        alert.setMessage("Вы уверены?");
        alert.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Presenter.BluetoothDisconnect();
                    }
                });
        alert.setNegativeButton("Нет",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        alert.show();
    }

    @Override
    public void showChooseAlert(Set<BluetoothDevice> pairedDevices, BluetoothDevice mDevice) {
        this.device = mDevice;
        if (pairedDevices.size() > 0) {
            final BluetoothDevice blueDev[] = new BluetoothDevice[pairedDevices.size()];
            String[] items = new String[blueDev.length];
            int i =0;
            for (BluetoothDevice devicel : pairedDevices) {
                blueDev[i] = devicel;
                items[i] = blueDev[i].getName() + ": " + blueDev[i].getAddress();
                i++;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.MainContext);
            builder.setTitle("Выберите EYWA:");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    dialog.dismiss();
                    if (item >= 0 && item <blueDev.length) {
                        device = blueDev[item];
                    }
                    setBtnConnection();
                    Presenter.BluetoothConnect();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    private void initPresenter(){
        Presenter = new MainPresenter(this);
        Presenter.initPresenter();
    }

    private void setBtnConnect(){
        BtnConnect.setText("Подключить");
        BtnConnect.setBackgroundResource(R.color.green);
        BtnConnect.setEnabled(true);
        ButtonState=0;
    }
    private void setBtnConnection(){
        BtnConnect.setText("Подключение");
        BtnConnect.setBackgroundResource(R.color.yellow);
        BtnConnect.setEnabled(true);
        ButtonState=1;
    }
    private void setBtnConnected(){
        BtnConnect.setText("Отключить");
        BtnConnect.setBackgroundResource(R.color.black);
        BtnConnect.setEnabled(true);
        ButtonState=2;
    }
}

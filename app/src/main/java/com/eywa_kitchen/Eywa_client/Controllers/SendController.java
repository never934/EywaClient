package com.eywa_kitchen.Eywa_client.Controllers;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SendController {
    private static PrintWriter out;

    public static void write(int i, BluetoothSocket socket) {
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            switch(i){
                case 1:
                    out.println("UP");
                    break;
                case 2:
                    out.println("DOWN");
                    break;
                case 3:
                    out.println("LEFT");
                    break;
                case 4:
                    out.println("RIGHT");
                    break;
                case 5:
                    out.println("ENTER");
                    break;
                case 6:
                    out.println("BACK");
                    break;
                case 7:
                    out.println("HOME");
                    break;
                case 8:
                    out.println("PLAYPAUSE");
                    break;
                case 9:
                    out.println("MICRO");
                    break;
                case 10:
                    out.println("PREV");
                    break;
                case 11:
                    out.println("NEXT");
                    break;
                case 14:
                    out.println("VOLDOWN");
                    break;
                case 15:
                    out.println("VOLUP");
                    break;
            }
            out.flush();

        } catch (Exception e) {
            Log.e("EywaLogger ", "Error happened sending/receiving\n");
        }
    }
}

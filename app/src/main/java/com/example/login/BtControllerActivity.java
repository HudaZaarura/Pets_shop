package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class BtControllerActivity extends AppCompatActivity {

    private String deviceName = null;
    private String deviceAddress;
    public static Handler handler;
    public static BluetoothSocket mmSocket;
    public static ConnectedThread connectedThread;
    public static CreateConnectThread createConnectThread;
    private final static int CONNECTING_STATUS = 1; // used in bluetooth handler to identify message status
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ2 = 3; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ3 = 4; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ4 = 5; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ5 = 6; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ6 = 7; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ7 = 8; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ8 = 9; // used in bluetooth handler to identify message update
    private final static int MESSAGE_READ9 = 10; // used in bluetooth handler to identify message update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcontroller);
        Utils utils = Utils.getInstance(this);
        // UI Initialization
        final Button buttonConnect = findViewById(R.id.buttonConnect);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        final TextView textViewInfo = findViewById(R.id.textViewInfo);
        final Button buttonToggle = findViewById(R.id.buttonToggle);
        buttonToggle.setEnabled(false);
        final ImageView imageView = findViewById(R.id.imageView);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));

        final Button buttonRed = findViewById(R.id.buttonred);
        buttonToggle.setEnabled(false);


        final Button buttonGreen = findViewById(R.id.buttongreen);
        buttonToggle.setEnabled(false);


        final Button buttonBlue = findViewById(R.id.buttonblue);
        buttonToggle.setEnabled(false);


        final Button buttonOff = findViewById(R.id.buttonoff);
        buttonToggle.setEnabled(false);


        final Button buttonMusic = findViewById(R.id.buttonmusic);
        buttonToggle.setEnabled(false);
        final ImageView imageView4 = findViewById(R.id.imageView4);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));

        final Button buttonFan = findViewById(R.id.buttonfan);
        buttonToggle.setEnabled(false);
        final ImageView imageView2 = findViewById(R.id.imageView2);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));

        final Button buttonDoor = findViewById(R.id.buttondoor);
        buttonToggle.setEnabled(false);
        final ImageView imageView3 = findViewById(R.id.imageView3);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));

        final Button buttonTemp = findViewById(R.id.buttontemp);
        buttonToggle.setEnabled(false);
        final ImageView imageView5 = findViewById(R.id.imageView5);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));


        // If a bluetooth device has been selected from SelectDeviceActivity
        deviceName = getIntent().getStringExtra("deviceName");
        if (deviceName != null) {
            // Get the device address to make BT Connection
            deviceAddress = getIntent().getStringExtra("deviceAddress");
            // Show progree and connection status
            toolbar.setSubtitle("Connecting to " + deviceName + "...");
            progressBar.setVisibility(View.VISIBLE);
            buttonConnect.setEnabled(false);

            /*
            This is the most important piece of code. When "deviceName" is found
            the code will call a new thread to create a bluetooth connection to the
            selected device (see the thread code below)
             */
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            createConnectThread = new CreateConnectThread(bluetoothAdapter, deviceAddress);
            createConnectThread.start();
        }

        /*
        Second most important piece of Code. GUI Handler
         */
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CONNECTING_STATUS:
                        switch (msg.arg1) {
                            case 1:
                                toolbar.setSubtitle("Connected to " + deviceName);
                                progressBar.setVisibility(View.GONE);
                                buttonConnect.setEnabled(true);
                                buttonToggle.setEnabled(true);
                                buttonRed.setEnabled(true);
                                buttonBlue.setEnabled(true);
                                buttonGreen.setEnabled(true);
                                buttonOff.setEnabled(true);
                                buttonMusic.setEnabled(true);
                                buttonFan.setEnabled(true);
                                buttonDoor.setEnabled(true);
                                buttonTemp.setEnabled(true);

                                break;
                            case -1:
                                toolbar.setSubtitle("Device fails to connect");
                                progressBar.setVisibility(View.GONE);
                                buttonConnect.setEnabled(true);
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg.toLowerCase()) {
                            case "led is turned on":
                                imageView.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                            case "led is turned off":
                                imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                        }
                    case MESSAGE_READ2:
                        String arduinoMsg2 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg2.toLowerCase()) {
                            case " Red led is turned on" :
                                textViewInfo.setText("Arduino Message : " + arduinoMsg2);
                                break;

                        }

                    case MESSAGE_READ3:
                        String arduinoMsg3 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg3.toLowerCase()) {
                            case "Green led is turned on":
                                textViewInfo.setText("Arduino Message : " + arduinoMsg3);
                                break;

                        }
                    case MESSAGE_READ4:
                        String arduinoMsg4 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg4.toLowerCase()) {
                            case "Blue led is turned on":
                                textViewInfo.setText("Arduino Message : " + arduinoMsg4);
                                break;

                        }

                    case MESSAGE_READ5:
                        String arduinoMsg5 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg5.toLowerCase()) {
                            case "Everything is turned off":
                                textViewInfo.setText("Arduino Message : " + arduinoMsg5);
                                break;

                        }

                    case MESSAGE_READ6:
                        String arduinoMsg6 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg6.toLowerCase()) {
                            case "Music is turned on":
                                imageView4.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg6);
                                break;

                        }

                    case MESSAGE_READ7:
                        String arduinoMsg7 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg7.toLowerCase()) {
                            case "Fan is turned on":
                                imageView2.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg7);
                                break;

                        }
                    case MESSAGE_READ8:
                        String arduinoMsg8 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg8.toLowerCase()) {
                            case "Door is opened":
                                imageView3.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg8);
                                break;

                        }
                    case MESSAGE_READ9:
                        String arduinoMsg9 = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg9.toLowerCase()) {
                            case "The temperature is shown on the screen":
                                imageView5.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                textViewInfo.setText("Arduino Message : " + arduinoMsg9);
                                break;

                        }

                        break;
                }
            }
        };

        // Select Bluetooth Device
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to adapter list
                Intent intent = new Intent(BtControllerActivity.this, SelectDeviceActivity.class);
                startActivity(intent);
            }
        });

        // Button to ON/OFF LED on Arduino Board
        buttonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonToggle.getText().toString().toLowerCase();
                switch (btnState) {
                    case "turn on":
                        buttonToggle.setText("TURN OFF");
                        // Command to turn on LED on Arduino. Must match with the command in Arduino code
                        cmdText = "n"; // <turn on>
                        break;
                    case "turn off":
                        buttonToggle.setText("TURN ON");
                        // Command to turn off LED on Arduino. Must match with the command in Arduino code
                        cmdText = "d"; // <turn off>
                        break;
                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to ON/OFF Fan on Arduino Board
        buttonFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonFan.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Turn fan on":
                        buttonFan.setText("TURN fan OFF");
                        // Command to turn on fan on Arduino. Must match with the command in Arduino code
                        cmdText = "f"; // <turn on>
                        break;
                    case "turn fan off":
                        buttonFan.setText("TURN fan ON");
                        // Command to turn off fan on Arduino. Must match with the command in Arduino code
                        cmdText = "z"; // <turn off>
                        break;
                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to ON Red LED on Arduino Board
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonRed.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Red":
                        buttonRed.setText("Red");
                        // Command to turn on LED on Arduino. Must match with the command in Arduino code
                        cmdText = "r"; // <turn on>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to ON Green LED on Arduino Board
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonGreen.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Green":
                        buttonGreen.setText("Green");
                        // Command to turn on LED on Arduino. Must match with the command in Arduino code
                        cmdText = "g"; // <turn on>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to ON Blue LED on Arduino Board
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonBlue.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Blue":
                        buttonBlue.setText("Blue");
                        // Command to turn on LED on Arduino. Must match with the command in Arduino code
                        cmdText = "b"; // <turn on>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to OFF everything on Arduino Board
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonOff.getText().toString().toLowerCase();
                switch (btnState) {
                    case "turn everything off":
                        buttonOff.setText("turn everything off");
                        // Command to turn off everything on Arduino. Must match with the command in Arduino code
                        cmdText = "l"; // <turn off>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to ON music on Arduino Board
        buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonMusic.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Turn Music on":
                        buttonMusic.setText("Turn Music on");
                        // Command to turn on music on Arduino. Must match with the command in Arduino code
                        cmdText = "m"; // <turn on>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        // Button to OPEN door on Arduino Board
        buttonDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonDoor.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Open the door":
                        buttonDoor.setText("Open the door");
                        // Command to open door on Arduino. Must match with the command in Arduino code
                        cmdText = "o"; // <open>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });
        // Button to Send temp on Arduino Board
        buttonTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                cmdText = "";
                String btnState = buttonTemp.getText().toString().toLowerCase();
                switch (btnState) {
                    case "Show the temperature on the screen":
                        buttonTemp.setText("Show the temperature on the screen");
                        // Command to show temp on Arduino. Must match with the command in Arduino code
                        cmdText = "t"; // <sent>
                        break;

                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

    }

    /* ============================ Thread to Create Bluetooth Connection =================================== */
    public static class CreateConnectThread extends Thread {

        public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address) {
            /*
            Use a temporary object that is later assigned to mmSocket
            because mmSocket is final.
             */
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
            BluetoothSocket tmp = null;

            Utils u = Utils.getInstance();
            if (ActivityCompat.checkSelfPermission(u.getMainAct(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return null;
                return;
            }
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();

            try {
                /*
                Get a BluetoothSocket to connect with the given BluetoothDevice.
                Due to Android device varieties,the method below may not work fo different devices.
                You should try using other methods i.e. :
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                 */
                tmp = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);

            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Utils u = Utils.getInstance();

            if (ActivityCompat.checkSelfPermission(u.getMainAct(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                int REQUEST_PERMISSION_CONNECT_BLUETOOTH = 1;
                ActivityCompat.requestPermissions(u.getMainAct(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_PERMISSION_CONNECT_BLUETOOTH);
            }
            //bluetoothAdapter.cancelDiscovery();
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Log.e("Status", "Device connected");
                handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                    Log.e("Status", "Cannot connect to device");
                    handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            connectedThread = new ConnectedThread(mmSocket);
            connectedThread.run();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    /* =============================== Thread for Data Transfer =========================================== */
    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes = 0; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    /*
                    Read from the InputStream from Arduino until termination character is reached.
                    Then send the whole String message to GUI Handler.
                     */
                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;
                    if (buffer[bytes] == '\n'){
                        readMessage = new String(buffer,0,bytes);
                        Log.e("Arduino Message",readMessage);
                        handler.obtainMessage(MESSAGE_READ,readMessage).sendToTarget();
                        bytes = 0;
                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error","Unable to send message",e);
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    /* ============================ Terminate Connection at BackPress ====================== */
    @Override
    public void onBackPressed() {
        // Terminate Bluetooth Connection and close app
        super.onBackPressed();
        if (createConnectThread != null) {
            createConnectThread.cancel();
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
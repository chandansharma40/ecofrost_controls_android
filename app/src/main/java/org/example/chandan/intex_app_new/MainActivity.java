package org.example.chandan.intex_app_new;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends FragmentActivity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;

    Thread workerThread;

    final int handlerState = 0;

    private StringBuilder recDataString = new StringBuilder();

    public static int pow=1,mode=1,crop=1,crpH=0, crpL=0, crpt=2, crph=90, weighH=0, weighL=0, flag=0, flag_connect=0, flag_pair=1;
    ImageView p_on,p_off, auto_img, solar_img, grid_img, logo, auto_on, grid_on, sol_on, start;
    RadioButton auto,solar,grid;
    ViewPager viewPager = null;
    TextView crop_txt, sys_number;
    String msg, weight;
    String crp_txt = "", mode_text = "", pwr_text = "", crptext="";
    String weight_txt = "0000";
    String data="";
    static String sysname = "";

    int croprec, moderec, powrec, temprec, humrec;
    String mode1="";

    byte[] readBuffer;
    int bufferposition;
    volatile boolean stopWorker=false;

    private ProgressDialog progress;

    boolean doubleBackToExitPressedOnce = false;

    public static Boolean ENABLE_RESTART = false;

    int data_block = 100;

    String TAG = "com.example.aa.onepageapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        sys_number = (TextView) findViewById(R.id.sys_no);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    finish();
                }

                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
            }

            private void finish() {
            }
        });

        AlertDialog ad = new AlertDialog.Builder(MainActivity.this).create();
        ad.setCancelable(false);
        ad.setTitle("mCS Controls");

        ad.setMessage("This Application will change the Settings of the mCS! Please Stay near the system while changing the Settings!");

        ad.setButton("START APPLICATION!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if ((pairedDevices.size() > 0)) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getName().equals(sysname)) {          //Device Name
                            mmDevice = device;
                            break;
                        }
                    }
                }

                try {
                    openBT();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                sys_number.setText("System Number : " + sysname);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog a1 = new AlertDialog.Builder(MainActivity.this).create();
                        a1.setMessage("Temperature: " + temprec + " °C" + "\n" +
                                "Humidity: " + humrec + " %" + "\n" );
                        a1.setCancelable(false);
                        a1.setTitle("Current Statistics");
                        //progress.setIndeterminate(true);
                        a1.show();

                        final Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            public void run() {
                                a1.dismiss(); // when the task active then close the dialog
                                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                            }
                        }, 5000);
                    }
                }, 1000);


/*
                try {
                    File myFile = new File("/sdcard/Mode.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    mode_text = aBuffer;
                    moderec = Integer.parseInt(mode_text);
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


                try {
                    File myFile = new File("/sdcard/Power.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    pwr_text = aBuffer;
                    powrec = Integer.parseInt(pwr_text);
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    File myFile = new File("/sdcard/Crop.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    crptext = aBuffer;
                    croprec = Integer.parseInt(crptext);
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    File myFile = new File("/sdcard/Weight.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    weight = aBuffer;
                    int weightrec = Integer.parseInt(weight);
                    weighH = weightrec/100;
                    weighL = weightrec-(weighH*100);
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                process_mode();
                process_power();
                process_crop();
                process_weight();*/
            }
        });
        ad.show();

        /*start = (ImageView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setVisibility(View.GONE);

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if ((pairedDevices.size() > 0)) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getName().equals(sysname)) {          //Device Name
                            mmDevice = device;
                            break;
                        }
                    }
                }

                try {
                    openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    File myFile = new File("/sdcard/Mode.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    mode_text = aBuffer;
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


                try {
                    File myFile = new File("/sdcard/Power.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    pwr_text = aBuffer;
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    File myFile = new File("/sdcard/Crop.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    crptext = aBuffer;
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    File myFile = new File("/sdcard/Weight.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow;
                    }
                    weight = aBuffer;
                    myReader.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                process_mode();
                process_power();
                process_crop();
                process_weight();

            }
        });*/

        viewPager = (ViewPager) findViewById(R.id.viewer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fragmentManager));
        viewPager.setOffscreenPageLimit(6);

        p_on=(ImageView) findViewById(R.id.off);
        p_off=(ImageView) findViewById(R.id.on);
        auto=(RadioButton) findViewById(R.id.auto);
        solar=(RadioButton) findViewById(R.id.solar);
        grid=(RadioButton) findViewById(R.id.grid);

        logo = (ImageView) findViewById(R.id.conn);

        auto_img = (ImageView) findViewById(R.id.imageView4);
        solar_img = (ImageView) findViewById(R.id.imageView5);
        grid_img = (ImageView) findViewById(R.id.imageView6);

        auto_on = (ImageView) findViewById(R.id.imageView2);
        sol_on = (ImageView) findViewById(R.id.imageView3);
        grid_on = (ImageView) findViewById(R.id.imageView7);

        /*
        p_on.setOnClickListener(this);
        p_off.setOnClickListener(this);
        auto.setOnClickListener(this);
        solar.setOnClickListener(this);
        grid.setOnClickListener(this);
        */
        /*logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Trying to Connect with System Again!!",Toast.LENGTH_SHORT).show();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if ((pairedDevices.size() > 0)) {
                    for (BluetoothDevice device : pairedDevices) {

                        if (device.getName().equals(sysname)) {          //Device Name
                            mmDevice = device;
                            break;
                        }
                    }
                }

                try {
                    //Toast.makeText(this,"Bluetooth Connected", Toast.LENGTH_SHORT).show();
                    openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/

        crop_txt = (TextView) findViewById(R.id.crop_text);

        p_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_connect == 0) {
                    Toast.makeText(getBaseContext(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    pow = 1;
                    p_on.setVisibility(v.GONE);
                    p_off.setVisibility(v.VISIBLE);

                    AlertDialog ad = new AlertDialog.Builder(MainActivity.this).create();
                    ad.setCancelable(false);
                    //ad.setTitle("Turning system off...");

                    ad.setMessage("Turning the micro Cold Storage ON...");

                    ad.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            progress = new ProgressDialog(MainActivity.this);
                            progress.setMessage("Processing...Please wait for a while...");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setCancelable(false);
                            //progress.setIndeterminate(true);
                            progress.show();

                            final Timer t = new Timer();
                            t.schedule(new TimerTask() {
                                public void run() {
                                    progress.dismiss(); // when the task active then close the dialog
                                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                                }
                            }, 7000);

                            try {
                                mmOutputStream.write('(');
                                mmOutputStream.write('+');
                                mmOutputStream.write(crpH);
                                mmOutputStream.write(crpL);
                                mmOutputStream.write(crpt);
                                mmOutputStream.write(crph);
                                mmOutputStream.write(mode);
                                mmOutputStream.write(pow);
                                mmOutputStream.write(weighH);
                                mmOutputStream.write(weighL);

                                Toast.makeText(getApplicationContext(), "System Turning On...", Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            /*try {
                                File myFile = new File("/sdcard/Power.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append("1");
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getBaseContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                        }

                    });

                    ad.show();
                }
            }
        });

        p_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_connect == 0) {
                    Toast.makeText(getBaseContext(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    pow = 0;
                    p_on.setVisibility(v.VISIBLE);
                    p_off.setVisibility(v.GONE);

                    AlertDialog ad1 = new AlertDialog.Builder(MainActivity.this).create();
                    ad1.setCancelable(false);
                    //ad.setTitle("Turning system off...");

                    ad1.setMessage("Turning the micro Cold Storage OFF...");

                    ad1.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            progress = new ProgressDialog(MainActivity.this);
                            progress.setMessage("Processing...Please wait for a while...");
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progress.setCancelable(false);
                            //progress.setIndeterminate(true);
                            progress.show();

                            final Timer t = new Timer();
                            t.schedule(new TimerTask() {
                                public void run() {
                                    progress.dismiss(); // when the task active then close the dialog
                                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                                }
                            }, 7000);

                            try {
                                mmOutputStream.write('(');
                                mmOutputStream.write('+');
                                mmOutputStream.write(crpH);
                                mmOutputStream.write(crpL);
                                mmOutputStream.write(crpt);
                                mmOutputStream.write(crph);
                                mmOutputStream.write(mode);
                                mmOutputStream.write(pow);
                                mmOutputStream.write(weighH);
                                mmOutputStream.write(weighL);
                                Toast.makeText(getApplicationContext(), "System Turning Off...", Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            /*try {
                                File myFile = new File("/sdcard/Power.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append("0");
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getBaseContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                        }

                    });

                    ad1.show();
                }
            }
        });

        auto_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_connect == 0) {
                    Toast.makeText(getBaseContext(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }
                if (pow == 0) {
                    Toast.makeText(getBaseContext(), "Switch On the System First", Toast.LENGTH_SHORT).show();
                    return;
                }
                mode = 1;

                auto_img.setVisibility(View.GONE);
                auto_on.setVisibility(View.VISIBLE);
                solar_img.setVisibility(View.VISIBLE);
                sol_on.setVisibility(View.GONE);
                grid_img.setVisibility(View.VISIBLE);
                grid_on.setVisibility(View.GONE);

                    progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Processing...Please wait for a while...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    //progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();

                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progress.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 7000);

                    try {
                        mmOutputStream.write('(');
                        mmOutputStream.write('+');
                        mmOutputStream.write(crpH);
                        mmOutputStream.write(crpL);
                        mmOutputStream.write(crpt);
                        mmOutputStream.write(crph);
                        mmOutputStream.write(mode);
                        mmOutputStream.write(pow);
                        mmOutputStream.write(weighH);
                        mmOutputStream.write(weighL);
                        Toast.makeText(getApplicationContext(), "Auto Mode", Toast.LENGTH_SHORT).show();

                        Page1.mode = 1;
                        Page2.mode = 1;
                        Page3.mode = 1;
                        Page4.mode = 1;
                        Page5.mode = 1;
                        Page6.mode = 1;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                /*try {
                    File myFile = new File("/sdcard/Mode.txt");
                    myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter myOutWriter =
                            new OutputStreamWriter(fOut);
                    myOutWriter.append("1");
                    myOutWriter.close();
                    fOut.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }*/

            }

        });

        solar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag_connect == 0) {
                    Toast.makeText(getBaseContext(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                } if (pow == 0) {
                    Toast.makeText(getBaseContext(), "Switch On the System First", Toast.LENGTH_SHORT).show();
                    return;
                }
                mode = 2;

                auto_img.setVisibility(View.VISIBLE);
                auto_on.setVisibility(View.GONE);
                solar_img.setVisibility(View.GONE);
                sol_on.setVisibility(View.VISIBLE);
                grid_img.setVisibility(View.VISIBLE);
                grid_on.setVisibility(View.GONE);

                    progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Processing...Please wait for a while...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    //progress.setIndeterminate(true);
                    progress.show();

                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progress.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 7000);

                    try {
                        mmOutputStream.write('(');
                        mmOutputStream.write('+');
                        mmOutputStream.write(crpH);
                        mmOutputStream.write(crpL);
                        mmOutputStream.write(crpt);
                        mmOutputStream.write(crph);
                        mmOutputStream.write(mode);
                        mmOutputStream.write(pow);
                        mmOutputStream.write(weighH);
                        mmOutputStream.write(weighL);
                        Toast.makeText(getApplicationContext(), "Solar Mode", Toast.LENGTH_SHORT).show();

                        Page1.mode = 2;
                        Page2.mode = 2;
                        Page3.mode = 2;
                        Page4.mode = 2;
                        Page5.mode = 2;
                        Page6.mode = 2;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                /*try {
                    File myFile = new File("/sdcard/Mode.txt");
                    myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter myOutWriter =
                            new OutputStreamWriter(fOut);
                    myOutWriter.append("2");
                    myOutWriter.close();
                    fOut.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        grid_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_connect == 0) {
                    Toast.makeText(getBaseContext(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                } if (pow == 0) {
                    Toast.makeText(getBaseContext(), "Switch On the System First", Toast.LENGTH_SHORT).show();
                    return;
                }
                mode = 3;

                auto_img.setVisibility(View.VISIBLE);
                auto_on.setVisibility(View.GONE);
                solar_img.setVisibility(View.VISIBLE);
                sol_on.setVisibility(View.GONE);
                grid_img.setVisibility(View.GONE);
                grid_on.setVisibility(View.VISIBLE);


                    progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Processing...Please wait for a while...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setCancelable(false);
                    //progress.setIndeterminate(true);
                    progress.show();

                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progress.dismiss(); // when the task active then close the dialog
                            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        }
                    }, 7000);

                    try {
                        mmOutputStream.write('(');
                        mmOutputStream.write('+');
                        mmOutputStream.write(crpH);
                        mmOutputStream.write(crpL);
                        mmOutputStream.write(crpt);
                        mmOutputStream.write(crph);
                        mmOutputStream.write(mode);
                        mmOutputStream.write(pow);
                        mmOutputStream.write(weighH);
                        mmOutputStream.write(weighL);
                        Toast.makeText(getApplicationContext(), "Grid Mode", Toast.LENGTH_SHORT).show();

                        Page1.mode = 3;
                        Page2.mode = 3;
                        Page3.mode = 3;
                        Page4.mode = 3;
                        Page5.mode = 3;
                        Page6.mode = 3;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                /*try {
                    File myFile = new File("/sdcard/Mode.txt");
                    myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter myOutWriter =
                            new OutputStreamWriter(fOut);
                    myOutWriter.append("3");
                    myOutWriter.close();
                    fOut.close();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        /*auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;

                progress=new ProgressDialog(MainActivity.this);
                progress.setMessage("Processing...Please wait for a while...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                //progress.setIndeterminate(true);
                progress.show();

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        progress.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 7000);

                try {
                    mmOutputStream.write('(');
                    mmOutputStream.write('+');
                    mmOutputStream.write(crpH);
                    mmOutputStream.write(crpL);
                    mmOutputStream.write(crpt);
                    mmOutputStream.write(crph);
                    mmOutputStream.write(mode);
                    mmOutputStream.write(pow);
                    mmOutputStream.write(weighH);
                    mmOutputStream.write(weighL);
                    Toast.makeText(getApplicationContext(), "Auto Mode", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });


        solar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;

                progress=new ProgressDialog(MainActivity.this);
                progress.setMessage("Processing...Please wait for a while...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                //progress.setIndeterminate(true);
                progress.show();

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        progress.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 7000);

                try {
                    mmOutputStream.write('(');
                    mmOutputStream.write('+');
                    mmOutputStream.write(crpH);
                    mmOutputStream.write(crpL);
                    mmOutputStream.write(crpt);
                    mmOutputStream.write(crph);
                    mmOutputStream.write(mode);
                    mmOutputStream.write(pow);
                    mmOutputStream.write(weighH);
                    mmOutputStream.write(weighL);
                    Toast.makeText(getApplicationContext(), "Solar Mode", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 3;

                progress=new ProgressDialog(MainActivity.this);
                progress.setMessage("Processing...Please wait for a while...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                //progress.setIndeterminate(true);
                progress.show();

                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        progress.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 7000);


                try {
                    mmOutputStream.write('(');
                    mmOutputStream.write('+');
                    mmOutputStream.write(crpH);
                    mmOutputStream.write(crpL);
                    mmOutputStream.write(crpt);
                    mmOutputStream.write(crph);
                    mmOutputStream.write(mode);
                    mmOutputStream.write(pow);
                    mmOutputStream.write(weighH);
                    mmOutputStream.write(weighL);
                    Toast.makeText(getApplicationContext(), "Grid Mode", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });*/

    }

    void process_mode()
    {
        if(moderec == 2){
            mode=2;
            auto_img.setVisibility(View.VISIBLE);
            auto_on.setVisibility(View.GONE);
            solar_img.setVisibility(View.GONE);
            sol_on.setVisibility(View.VISIBLE);
            grid_img.setVisibility(View.VISIBLE);
            grid_on.setVisibility(View.GONE);

            Page1.mode = mode;
            Page2.mode = mode;
            Page3.mode = mode;
            Page4.mode = mode;
            Page5.mode = mode;
            Page6.mode = mode;
        }
        else if(moderec == 3){
            mode=3;
            auto_img.setVisibility(View.VISIBLE);
            auto_on.setVisibility(View.GONE);
            solar_img.setVisibility(View.VISIBLE);
            sol_on.setVisibility(View.GONE);
            grid_img.setVisibility(View.GONE);
            grid_on.setVisibility(View.VISIBLE);

            Page1.mode = mode;
            Page2.mode = mode;
            Page3.mode = mode;
            Page4.mode = mode;
            Page5.mode = mode;
            Page6.mode = mode;
        }
        else{
            mode=1;
            auto_img.setVisibility(View.GONE);
            auto_on.setVisibility(View.VISIBLE);
            solar_img.setVisibility(View.VISIBLE);
            sol_on.setVisibility(View.GONE);
            grid_img.setVisibility(View.VISIBLE);
            grid_on.setVisibility(View.GONE);

            Page1.mode = mode;
            Page2.mode = mode;
            Page3.mode = mode;
            Page4.mode = mode;
            Page5.mode = mode;
            Page6.mode = mode;
        }
    }

    void process_power(){
        if(powrec == 0){
            pow = 0;
            p_on.setVisibility(View.VISIBLE);
            p_off.setVisibility(View.GONE);
        }
        else {
            pow = 1;
            p_on.setVisibility(View.GONE);
            p_off.setVisibility(View.VISIBLE);
        }
    }

    void process_crop(){
        if(croprec == 6||croprec == 34||croprec == 37||croprec == 45||croprec == 19||croprec == 39){
            viewPager.setCurrentItem(1, true);
            if(croprec == 6){
                crpH = 0;
                crpL = 6;
                crpt = 1;
                crph = 97;
                Page2.i2.setVisibility(View.VISIBLE);
                Page2.i1.setVisibility(View.GONE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 34){
                crpH = 0;
                crpL = 34;
                crpt = 8;
                crph = 93;
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.VISIBLE);
                Page2.i3.setVisibility(View.GONE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 37){
                crpH = 0;
                crpL = 37;
                crpt = 12;
                crph = 93;
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.VISIBLE);
                Page2.i5.setVisibility(View.GONE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 45){
                crpH = 0;
                crpL = 45;
                crpt = 9;
                crph = 85;
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.VISIBLE);
                Page2.i7.setVisibility(View.GONE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 19){
                crpH = 0;
                crpL = 19;
                crpt = 15;
                crph = 90;
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.VISIBLE);
                Page2.i9.setVisibility(View.GONE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 39){
                crpH = 0;
                crpL = 39;
                crpt = 6;
                crph = 93;
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.VISIBLE);
                Page2.i11.setVisibility(View.GONE);
            }

            else {
                Page2.i2.setVisibility(View.GONE);
                Page2.i1.setVisibility(View.VISIBLE);
                Page2.i4.setVisibility(View.GONE);
                Page2.i3.setVisibility(View.VISIBLE);
                Page2.i6.setVisibility(View.GONE);
                Page2.i5.setVisibility(View.VISIBLE);
                Page2.i8.setVisibility(View.GONE);
                Page2.i7.setVisibility(View.VISIBLE);
                Page2.i10.setVisibility(View.GONE);
                Page2.i9.setVisibility(View.VISIBLE);
                Page2.i12.setVisibility(View.GONE);
                Page2.i11.setVisibility(View.VISIBLE);
            }

        }

        else if(croprec == 10||croprec == 14||croprec == 36||croprec == 18||croprec == 42||croprec == 2) {
            viewPager.setCurrentItem(2,true);
            if(croprec == 10){
                crpH = 0;
                crpL = 10;
                crpt = 9;
                crph = 93;
                Page3.i2.setVisibility(View.VISIBLE);
                Page3.i1.setVisibility(View.GONE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 14){
                crpH = 0;
                crpL = 14;
                crpt = 6;
                crph = 93;
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.VISIBLE);
                Page3.i3.setVisibility(View.GONE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 36){
                crpH = 0;
                crpL = 36;
                crpt = 1;
                crph = 93;
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.VISIBLE);
                Page3.i5.setVisibility(View.GONE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 18){
                crpH = 0;
                crpL = 18;
                crpt = 15;
                crph = 90;
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.VISIBLE);
                Page3.i7.setVisibility(View.GONE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 42){
                crpH = 0;
                crpL = 42;
                crpt = 1;
                crph = 93;
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.VISIBLE);
                Page3.i9.setVisibility(View.GONE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 2){
                crpH = 0;
                crpL = 2;
                crpt = 0;
                crph = 93;
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.VISIBLE);
                Page3.i11.setVisibility(View.GONE);
            }

            else {
                Page3.i2.setVisibility(View.GONE);
                Page3.i1.setVisibility(View.VISIBLE);
                Page3.i4.setVisibility(View.GONE);
                Page3.i3.setVisibility(View.VISIBLE);
                Page3.i6.setVisibility(View.GONE);
                Page3.i5.setVisibility(View.VISIBLE);
                Page3.i8.setVisibility(View.GONE);
                Page3.i7.setVisibility(View.VISIBLE);
                Page3.i10.setVisibility(View.GONE);
                Page3.i9.setVisibility(View.VISIBLE);
                Page3.i12.setVisibility(View.GONE);
                Page3.i11.setVisibility(View.VISIBLE);
            }
        }

        else if(croprec == 9||croprec == 5||croprec == 16||croprec == 4||croprec == 41||croprec == 11) {
            viewPager.setCurrentItem(3,true);
            if(croprec == 9){
                crpH = 0;
                crpL = 9;
                crpt = 1;
                crph = 98;
                Page4.i2.setVisibility(View.VISIBLE);
                Page4.i1.setVisibility(View.GONE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 5){
                crpH = 0;
                crpL = 5;
                crpt = 1;
                crph = 93;
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.VISIBLE);
                Page4.i3.setVisibility(View.GONE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 16){
                crpH = 0;
                crpL = 16;
                crpt = 3;
                crph = 85;
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.VISIBLE);
                Page4.i5.setVisibility(View.GONE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 4){
                crpH = 0;
                crpL = 4;
                crpt = 1;
                crph = 93;
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.VISIBLE);
                Page4.i7.setVisibility(View.GONE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 41){
                crpH = 0;
                crpL = 41;
                crpt = 1;
                crph = 93;
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.VISIBLE);
                Page4.i9.setVisibility(View.GONE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 11){
                crpH = 0;
                crpL = 11;
                crpt = 10;
                crph = 93;
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.VISIBLE);
                Page4.i11.setVisibility(View.GONE);
            }

            else {
                Page4.i2.setVisibility(View.GONE);
                Page4.i1.setVisibility(View.VISIBLE);
                Page4.i4.setVisibility(View.GONE);
                Page4.i3.setVisibility(View.VISIBLE);
                Page4.i6.setVisibility(View.GONE);
                Page4.i5.setVisibility(View.VISIBLE);
                Page4.i8.setVisibility(View.GONE);
                Page4.i7.setVisibility(View.VISIBLE);
                Page4.i10.setVisibility(View.GONE);
                Page4.i9.setVisibility(View.VISIBLE);
                Page4.i12.setVisibility(View.GONE);
                Page4.i11.setVisibility(View.VISIBLE);
            }
        }

        else if(croprec == 40||croprec == 25||croprec == 13||croprec == 32||croprec == 44||croprec == 24) {
            viewPager.setCurrentItem(4,true);
            if(croprec == 40){
                crpH = 0;
                crpL = 40;
                crpt = 12;
                crph = 93;
                Page5.i2.setVisibility(View.VISIBLE);
                Page5.i1.setVisibility(View.GONE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 25){
                crpH = 0;
                crpL = 25;
                crpt = 4;
                crph = 93;
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.VISIBLE);
                Page5.i3.setVisibility(View.GONE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 13){
                crpH = 0;
                crpL = 13;
                crpt = 9;
                crph = 93;
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.VISIBLE);
                Page5.i5.setVisibility(View.GONE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 32){
                crpH = 0;
                crpL = 32;
                crpt = 6;
                crph = 93;
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.VISIBLE);
                Page5.i7.setVisibility(View.GONE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 44){
                crpH = 0;
                crpL = 44;
                crpt = 9;
                crph = 65;
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.VISIBLE);
                Page5.i9.setVisibility(View.GONE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 24){
                crpH = 0;
                crpL = 24;
                crpt = 2;
                crph = 93;
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.VISIBLE);
                Page5.i11.setVisibility(View.GONE);
            }

            else {
                Page5.i2.setVisibility(View.GONE);
                Page5.i1.setVisibility(View.VISIBLE);
                Page5.i4.setVisibility(View.GONE);
                Page5.i3.setVisibility(View.VISIBLE);
                Page5.i6.setVisibility(View.GONE);
                Page5.i5.setVisibility(View.VISIBLE);
                Page5.i8.setVisibility(View.GONE);
                Page5.i7.setVisibility(View.VISIBLE);
                Page5.i10.setVisibility(View.GONE);
                Page5.i9.setVisibility(View.VISIBLE);
                Page5.i12.setVisibility(View.GONE);
                Page5.i11.setVisibility(View.VISIBLE);
            }
        }

        else if(croprec == 35||croprec == 20||croprec == 22||croprec == 26||croprec == 12||croprec == 47) {
            viewPager.setCurrentItem(5,true);
            if(croprec == 35){
                crpH = 0;
                crpL = 35;
                crpt = 1;
                crph = 93;
                Page6.i2.setVisibility(View.VISIBLE);
                Page6.i1.setVisibility(View.GONE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 20){
                crpH = 0;
                crpL = 20;
                crpt = 0;
                crph = 97;
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.VISIBLE);
                Page6.i3.setVisibility(View.GONE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 22){
                crpH = 0;
                crpL = 22;
                crpt = 5;
                crph = 93;
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.VISIBLE);
                Page6.i5.setVisibility(View.GONE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 26){
                crpH = 0;
                crpL = 26;
                crpt = 2;
                crph = 88;
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.VISIBLE);
                Page6.i7.setVisibility(View.GONE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 12){
                crpH = 0;
                crpL = 12;
                crpt = 5;
                crph = 63;
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.VISIBLE);
                Page6.i9.setVisibility(View.GONE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 47){
                crpH = 0;
                crpL = 47;
                crpt = 0;
                crph = 93;
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.VISIBLE);
                Page6.i11.setVisibility(View.GONE);
            }

            else {
                Page6.i2.setVisibility(View.GONE);
                Page6.i1.setVisibility(View.VISIBLE);
                Page6.i4.setVisibility(View.GONE);
                Page6.i3.setVisibility(View.VISIBLE);
                Page6.i6.setVisibility(View.GONE);
                Page6.i5.setVisibility(View.VISIBLE);
                Page6.i8.setVisibility(View.GONE);
                Page6.i7.setVisibility(View.VISIBLE);
                Page6.i10.setVisibility(View.GONE);
                Page6.i9.setVisibility(View.VISIBLE);
                Page6.i12.setVisibility(View.GONE);
                Page6.i11.setVisibility(View.VISIBLE);
            }
        }

        else {
            if(croprec == 1){
                crpH = 0;
                crpL = 1;
                crpt = 2;
                crph = 93;
                Page1.i2.setVisibility(View.VISIBLE);
                Page1.i1.setVisibility(View.GONE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 110){
                crpH = 01;
                crpL = 10;
                crpt = 6;
                crph = 70;
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.VISIBLE);
                Page1.i3.setVisibility(View.GONE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 3){
                crpH = 0;
                crpL = 3;
                crpt = 1;
                crph = 93;
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.VISIBLE);
                Page1.i5.setVisibility(View.GONE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 7){
                crpH = 0;
                crpL = 7;
                crpt = 1;
                crph = 99;
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.VISIBLE);
                Page1.i7.setVisibility(View.GONE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 51){
                crpH = 0;
                crpL = 51;
                crpt = 2;
                crph = 65;
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.VISIBLE);
                Page1.i9.setVisibility(View.GONE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }

            else if (croprec == 15){
                crpH = 0;
                crpL = 15;
                crpt = 13;
                crph = 93;
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.VISIBLE);
                Page1.i11.setVisibility(View.GONE);
            }

            else {
                Page1.i2.setVisibility(View.GONE);
                Page1.i1.setVisibility(View.VISIBLE);
                Page1.i4.setVisibility(View.GONE);
                Page1.i3.setVisibility(View.VISIBLE);
                Page1.i6.setVisibility(View.GONE);
                Page1.i5.setVisibility(View.VISIBLE);
                Page1.i8.setVisibility(View.GONE);
                Page1.i7.setVisibility(View.VISIBLE);
                Page1.i10.setVisibility(View.GONE);
                Page1.i9.setVisibility(View.VISIBLE);
                Page1.i12.setVisibility(View.GONE);
                Page1.i11.setVisibility(View.VISIBLE);
            }
        }
    }

    void process_weight(){
        int wt = Integer.parseInt(weight);
        weighH = wt/100;
        weighL = wt - (weighH*100);

        Page1.weighL = weighL;
        Page2.weighL = weighL;
        Page3.weighL = weighL;
        Page4.weighL = weighL;
        Page5.weighL = weighL;
        Page6.weighL = weighL;

        Page1.weighH = weighH;
        Page2.weighH = weighH;
        Page3.weighH = weighH;
        Page4.weighH = weighH;
        Page5.weighH = weighH;
        Page6.weighH = weighH;
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        Toast.makeText(this,"Connected with System",Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mmOutputStream.write('~');
                    mmOutputStream.write('@');
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listenfordata();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        process_mode();
                        process_power();
                        process_crop();
                    }
                },100);
            }
        }, 500);

        flag_connect=1;

    }

    void listenfordata(){

        final Handler h = new Handler();
        final byte delim = 124;
        final StringBuilder sb = new StringBuilder();

        bufferposition = 0;
        readBuffer = new byte[30];

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker){
                    try {
                        int bytesavail = mmInputStream.available();
                        if(bytesavail > 0){
                            byte[] packets = new byte[bytesavail];
                            mmInputStream.read(packets);
                            for (int i = 0; i < bytesavail; i++){
                                byte b = packets[i];
                                if(b==delim){
                                    for (int q=0; q<bufferposition;q++){
                                        sb.append(readBuffer[q]).append(':');
                                    }
                                    bufferposition=0;
                                    data = sb.toString();
                                    final String values[] = data.split(":");


                                    temprec = Integer.parseInt(values[0]);
                                    humrec = Integer.parseInt(values[1]);
                                    croprec = Integer.parseInt(values[2]);
                                    moderec = Integer.parseInt(values[3]);
                                    powrec = Integer.parseInt(values[4]);
                                    weighH = Integer.parseInt(values[7]);
                                    weighL = Integer.parseInt(values[8]);

                                }
                                else {
                                    readBuffer[bufferposition++]=b;
                                }
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        stopWorker=true;
                    }
                }
            }
        });

        workerThread.start();
    }

    /*void listenfordatapages(){

        final Handler h = new Handler();
        final byte delim = 13;
        final StringBuilder sb = new StringBuilder();

        bufferposition = 0;
        readBuffer = new byte[30];

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker){
                    try {
                        int bytesavail = mmInputStream.available();
                        if(bytesavail > 0){
                            byte[] packets = new byte[bytesavail];
                            mmInputStream.read(packets);
                            for (int i = 0; i < bytesavail; i++){
                                byte b = packets[i];
                                if(b==delim){
                                    Toast.makeText(getBaseContext(),"3",Toast.LENGTH_LONG).show();
                                    for (int q=0; q<bufferposition;q++){
                                        sb.append(readBuffer[q]).append(':');
                                    }
                                    bufferposition=0;
                                    data = sb.toString();

                                    if(!(data.isEmpty())){
                                        Toast.makeText(getBaseContext(),"Data Received",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    readBuffer[bufferposition++]=b;
                                }
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        stopWorker=true;
                    }
                }
            }
        });

        workerThread.start();
    }*/

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            //Log.d("Chan", "getItem is called");

            android.support.v4.app.Fragment fragment = null;
            if (i == 0) {
                fragment = new Page1();
            }

            if (i == 1) {
                fragment = new Page2();
            }

            if (i == 2) {
                fragment = new Page3();
            }

            if (i == 3) {
                fragment = new Page4();
            }

            if (i == 4) {
                fragment = new Page5();
            }

            if (i == 5) {
                fragment = new Page6();
            }

/*
            if (i == 6) {
                fragment = new Page7();
            }
*/

            return fragment;
        }

        @Override
        public int getCount() {
            //Log.d("Chan", "getCount is called");
            return 6;
        }

    }



    public void setcrop(int crp){
        this.crop = crp;
    }

    public void setcroptext(String crp){
        this.msg = crp;
        try{
            File sdcard = Environment.getExternalStorageDirectory();
            File directory = new File(sdcard.getAbsolutePath()+"/testdata");
            File file = new File(directory,"Crop.txt");
            FileOutputStream fou = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fou);
            try
            {
                osw.write(msg);
                osw.flush();
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void settemp (int temp){
        this.crpt = temp;
    }

    public void setcropH (int crp) { this.crpH = crp;}

    public void setcropL (int crp) { this.crpL = crp;}

    public void sethum (int hum){
        this.crph = hum;
    }

    public void setweight(String weight_txt){
        this.weight_txt = weight_txt;
    }

    public void setweighH(int wt){
        this.weighH = wt;
    }

    public void setweighL(int wt){
        this.weighL = wt;
    }

    public static void setname(String name){
        MainActivity.sysname = name;

        if (name.equals("E011501A001")|name.equals("E011501A003")|name.equals("E011501A004")
                |name.equals("E011501A005")|name.equals("E011501A006")|name.equals("E011501A007")|name.equals("E011501A008")
                |name.equals("E011501A009")|name.equals("E011501A0010")|name.equals("E011601A012")
                |name.equals("E011501A013")){
            flag = 1;

        }
        else
        {
            flag = 0;
        }
    }

    public int getflag() {
        return flag;
    }

    public int getmode(){
        return mode;
    }

    public int getpower(){
        return pow;
    }

    public String getcrptext(){
        return crp_txt;
    }

    public String get_crop_txt(){
        return crptext;
    }

    public int getconflag(){
        return flag_connect;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}





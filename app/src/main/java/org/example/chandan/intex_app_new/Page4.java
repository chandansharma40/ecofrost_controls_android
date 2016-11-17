package org.example.chandan.intex_app_new;

/**
 * Created by Chandan on 6/28/2016.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Page4 extends Fragment {

    static ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12;
    public static int crp,crpH,crpL,hum,hum1,tem1,tem, mode, power, crpt, crph, weighH, weighL, conflag;
    String croptext,txt,crop_text,kgs;
    int weigh,flag = 0;

    String emptyweight_message = "Enter weight";
    String wrongweight_message = "Weight Limit exceeded";

    EditText input;
    private ProgressDialog progress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view;
        view = inflater.inflate(R.layout.page_4, container, false);

        i1 = (ImageView) view.findViewById(R.id.apple);
        i2 = (ImageView) view.findViewById(R.id.apple_off);
        i3 = (ImageView) view.findViewById(R.id.baigan);
        i4 = (ImageView) view.findViewById(R.id.baigan_off);
        i5 = (ImageView) view.findViewById(R.id.bellpepper);
        i6 = (ImageView) view.findViewById(R.id.bellpepper_off);
        i7 = (ImageView) view.findViewById(R.id.carrot);
        i8 = (ImageView) view.findViewById(R.id.carrot_off);
        i9 = (ImageView) view.findViewById(R.id.banana);
        i10 = (ImageView) view.findViewById(R.id.banana_off);
        i11 = (ImageView) view.findViewById(R.id.grape);
        i12 = (ImageView) view.findViewById(R.id.grape_off);

        crop_text=((MainActivity) getActivity()).get_crop_txt();

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                croptext = "BROCCOLI";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);


                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : BROCCOLI");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                                crp = 1;
                                crpH = 0;
                                crpL = 9;
                                crpt = 1;
                                crph = 97;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("BROCCOLI");
                                i2.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.GONE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();


                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Broccoli selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                /*try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("9");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/
                            }

                    }
                });

                ad.show();
            }
        });


        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                croptext = "PEAS";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);


                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEAS");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                                crp = 1;
                                crpH = 0;
                                crpL = 5;
                                crpt = 1;
                                crph = 97;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("PEAS");
                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.VISIBLE);
                                i3.setVisibility(View.GONE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Peas selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                /*try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("5");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/
                            }

                    }
                });

                ad.show();
            }
        });

        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }


                croptext = "BRINJAL";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);

                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : BRINJAL");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                                crp = 4;
                                crpH = 0;
                                crpL = 16;
                                crpt = 9;
                                crph = 93;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("BRINJAL");
                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.VISIBLE);
                                i5.setVisibility(View.GONE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();


                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Brinjal selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                /*try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("16");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/

                        }
                    }
                });

                ad.show();
            }
        });

        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }


                croptext = "PEAR";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);


                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEAR");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                                crp = 1;
                                crpH = 0;
                                crpL = 4;
                                crpt = 1;
                                crph = 93;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("PEAR");
                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.VISIBLE);
                                i7.setVisibility(View.GONE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Pear selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                /*try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("4");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/

                        }
                    }
                });

                ad.show();

            }
        });

        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }


                croptext = "PEACH";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);


                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEACH");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                                crp = 1;
                                crpH = 0;
                                crpL = 41;
                                crpt = 1;
                                crph = 93;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("PEACH");
                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.VISIBLE);
                                i9.setVisibility(View.GONE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();


                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Peach selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                               /* try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("41");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/
                            }

                    }
                });

                ad.show();
            }
        });

        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                conflag = ((MainActivity) getActivity()).getconflag();

                if(conflag==0){
                    Toast.makeText(getActivity(),"System did not connect, Restart Application...",Toast.LENGTH_LONG).show();
                    return;
                }

                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }


                croptext = "CAPSICUM";
                ((MainActivity)getActivity()).setcroptext(croptext);
                ((MainActivity) getActivity()).setcrop(crp);


                mode = ((MainActivity)getActivity()).getmode();
                power = ((MainActivity)getActivity()).getpower();
                flag = ((MainActivity)getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : CAPSICUM");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if ((Integer.parseInt(txt) > 5000)){
                            Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                                crp = 4;
                                crpH = 0;
                                crpL = 11;
                                crpt = 10;
                                crph = 88;

                                ((MainActivity) getActivity()).setcropH(crpH);
                                ((MainActivity) getActivity()).setcropL(crpL);
                                ((MainActivity) getActivity()).settemp(crpt);
                                ((MainActivity) getActivity()).sethum(crph);

                                progress = new ProgressDialog(view.getContext());
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

                                Page1.i1.setVisibility(View.VISIBLE);
                                Page1.i2.setVisibility(View.GONE);
                                Page1.i3.setVisibility(View.VISIBLE);
                                Page1.i4.setVisibility(View.GONE);
                                Page1.i5.setVisibility(View.VISIBLE);
                                Page1.i6.setVisibility(View.GONE);
                                Page1.i7.setVisibility(View.VISIBLE);
                                Page1.i8.setVisibility(View.GONE);
                                Page1.i9.setVisibility(View.VISIBLE);
                                Page1.i10.setVisibility(View.GONE);
                                Page1.i11.setVisibility(View.VISIBLE);
                                Page1.i12.setVisibility(View.GONE);

                                Page2.i1.setVisibility(View.VISIBLE);
                                Page2.i2.setVisibility(View.GONE);
                                Page2.i3.setVisibility(View.VISIBLE);
                                Page2.i4.setVisibility(View.GONE);
                                Page2.i5.setVisibility(View.VISIBLE);
                                Page2.i6.setVisibility(View.GONE);
                                Page2.i7.setVisibility(View.VISIBLE);
                                Page2.i8.setVisibility(View.GONE);
                                Page2.i9.setVisibility(View.VISIBLE);
                                Page2.i10.setVisibility(View.GONE);
                                Page2.i11.setVisibility(View.VISIBLE);
                                Page2.i12.setVisibility(View.GONE);

                                Page3.i1.setVisibility(View.VISIBLE);
                                Page3.i2.setVisibility(View.GONE);
                                Page3.i3.setVisibility(View.VISIBLE);
                                Page3.i4.setVisibility(View.GONE);
                                Page3.i5.setVisibility(View.VISIBLE);
                                Page3.i6.setVisibility(View.GONE);
                                Page3.i7.setVisibility(View.VISIBLE);
                                Page3.i8.setVisibility(View.GONE);
                                Page3.i9.setVisibility(View.VISIBLE);
                                Page3.i10.setVisibility(View.GONE);
                                Page3.i11.setVisibility(View.VISIBLE);
                                Page3.i12.setVisibility(View.GONE);

                                Page5.i1.setVisibility(View.VISIBLE);
                                Page5.i2.setVisibility(View.GONE);
                                Page5.i3.setVisibility(View.VISIBLE);
                                Page5.i4.setVisibility(View.GONE);
                                Page5.i5.setVisibility(View.VISIBLE);
                                Page5.i6.setVisibility(View.GONE);
                                Page5.i7.setVisibility(View.VISIBLE);
                                Page5.i8.setVisibility(View.GONE);
                                Page5.i9.setVisibility(View.VISIBLE);
                                Page5.i10.setVisibility(View.GONE);
                                Page5.i11.setVisibility(View.VISIBLE);
                                Page5.i12.setVisibility(View.GONE);

                                Page6.i1.setVisibility(View.VISIBLE);
                                Page6.i2.setVisibility(View.GONE);
                                Page6.i3.setVisibility(View.VISIBLE);
                                Page6.i4.setVisibility(View.GONE);
                                Page6.i5.setVisibility(View.VISIBLE);
                                Page6.i6.setVisibility(View.GONE);
                                Page6.i7.setVisibility(View.VISIBLE);
                                Page6.i8.setVisibility(View.GONE);
                                Page6.i9.setVisibility(View.VISIBLE);
                                Page6.i10.setVisibility(View.GONE);
                                Page6.i11.setVisibility(View.VISIBLE);
                                Page6.i12.setVisibility(View.GONE);

                                int wt = new Integer(txt).intValue();
                                weighH = wt / 100;
                                weighL = wt - (weighH * 100);

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("CAPSICUM");
                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.VISIBLE);
                                i11.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();


                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Capsicum selected!!", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                /*try {
                                    File myFile = new File("/sdcard/Crop.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append("11");
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                kgs = Integer.toString(wt);
                                try {
                                    File myFile = new File("/sdcard/Weight.txt");
                                    myFile.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(myFile);
                                    OutputStreamWriter myOutWriter =
                                            new OutputStreamWriter(fOut);
                                    myOutWriter.append(kgs);
                                    myOutWriter.close();
                                    fOut.close();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }*/

                        }
                    }
                });

                ad.show();

            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crp = 1;
                crpH = 0;
                crpL = 9;
                crpt = 1;
                crph = 98;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : BROCOLLI");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                ((MainActivity) getActivity()).crop_txt.setText("APPLE");
                                i2.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.GONE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crpH = 0;
                crpL = 5;
                crpt = 1;
                crph = 93;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEAS");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.VISIBLE);
                                i3.setVisibility(View.GONE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crpH = 0;
                crpL = 16;
                crpt = 03;
                crph = 85;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : BRINJAL");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.VISIBLE);
                                i5.setVisibility(View.GONE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crpH = 0;
                crpL = 04;
                crpt = 01;
                crph = 93;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEAR");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.VISIBLE);
                                i7.setVisibility(View.GONE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crpH = 0;
                crpL = 41;
                crpt = 01;
                crph = 93;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : PEACH");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.VISIBLE);
                                i9.setVisibility(View.GONE);
                                i12.setVisibility(View.GONE);
                                i11.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                power = ((MainActivity) getActivity()).getpower();
                if(power==0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();

                    return;
                }

                crpH = 0;
                crpL = 11;
                crpt = 10;
                crph = 93;
                mode = 1;
                power = 1;

                mode = ((MainActivity) getActivity()).getmode();
                power = ((MainActivity) getActivity()).getpower();
                flag = ((MainActivity) getActivity()).getflag();

                AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
                ad.setCancelable(true);
                ad.setTitle("Crop Selected : CAPSICUM");
                ad.setIcon(R.drawable.wt);

                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //input.setHint("Add more weight...");
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                ad.setView(input);


                ad.setButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        txt = input.getText().toString();

                        if (txt.isEmpty()) {
                            Toast.makeText(getActivity(), emptyweight_message, Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Double vajan = Double.parseDouble(txt);
                            Double bhaar = vajan + ((weighH*100)+weighL);
                            if (vajan > 5000) {

                                Toast.makeText(getActivity(), wrongweight_message, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                progress = new ProgressDialog(view.getContext());
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

                                weighH = (int) (vajan / 100);
                                weighL = (int) (vajan - (weighH * 100));

                                ((MainActivity) getActivity()).setweighH(weighH);
                                ((MainActivity) getActivity()).setweighL(weighL);

                                i2.setVisibility(View.GONE);
                                i1.setVisibility(View.VISIBLE);
                                i4.setVisibility(View.GONE);
                                i3.setVisibility(View.VISIBLE);
                                i6.setVisibility(View.GONE);
                                i5.setVisibility(View.VISIBLE);
                                i8.setVisibility(View.GONE);
                                i7.setVisibility(View.VISIBLE);
                                i10.setVisibility(View.GONE);
                                i9.setVisibility(View.VISIBLE);
                                i12.setVisibility(View.VISIBLE);
                                i11.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Weight: " + txt + " kg", Toast.LENGTH_SHORT).show();

                                try {
                                    ((MainActivity) getActivity()).setweight(txt);
                                    ((MainActivity) getActivity()).mmOutputStream.write('(');
                                    ((MainActivity) getActivity()).mmOutputStream.write('+');
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpL);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crpt);
                                    ((MainActivity) getActivity()).mmOutputStream.write(crph);
                                    ((MainActivity) getActivity()).mmOutputStream.write(mode);
                                    ((MainActivity) getActivity()).mmOutputStream.write(power);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighH);
                                    ((MainActivity) getActivity()).mmOutputStream.write(weighL);

                                    Toast.makeText(getActivity(), "Weight changed", Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            /*int wt = weighH*100 + weighL;
                            kgs = Integer.toString(wt);
                            try {
                                File myFile = new File("/sdcard/Weight.txt");
                                myFile.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(myFile);
                                OutputStreamWriter myOutWriter =
                                        new OutputStreamWriter(fOut);
                                myOutWriter.append(kgs);
                                myOutWriter.close();
                                fOut.close();
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            }
                        }
                    }
                });

                ad.show();
            }

        });

        return view;
    }

}





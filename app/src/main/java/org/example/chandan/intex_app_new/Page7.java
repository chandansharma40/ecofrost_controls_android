package org.example.chandan.intex_app_new;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Chandan on 10/7/2016.
 */
public class Page7 extends Fragment {

    Button submit;
    EditText temp, hum;
    public static int power,mode;
    ProgressDialog progress;

    String t,h;
    int temps=02, hums=90;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view;
        view = inflater.inflate(R.layout.page_7,container,false);

        temp = (EditText) view.findViewById(R.id.editText);
        hum = (EditText) view.findViewById(R.id.editText2);
        submit = (Button) view.findViewById(R.id.button);

        mode = ((MainActivity)getActivity()).getmode();
        power = ((MainActivity)getActivity()).getpower();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (power == 0){
                    Toast.makeText(getActivity(), "Switch On the System first", Toast.LENGTH_SHORT ).show();
                    return;
                }

                t = temp.getText().toString();
                h = hum.getText().toString();

                if(t.isEmpty()|| h.isEmpty()){
                    Toast.makeText(getActivity(), "Temperature/Humidity field cannot be empty!", Toast.LENGTH_SHORT ).show();
                    return;
                }

                temps = Integer.parseInt(t);
                hums = Integer.parseInt(h);

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

                try {
                    ((MainActivity)getActivity()).mmOutputStream.write('(');
                    ((MainActivity)getActivity()).mmOutputStream.write('+');
                    ((MainActivity)getActivity()).mmOutputStream.write(02);
                    ((MainActivity)getActivity()).mmOutputStream.write(50);
                    ((MainActivity)getActivity()).mmOutputStream.write(temps);
                    ((MainActivity)getActivity()).mmOutputStream.write(hums);
                    ((MainActivity)getActivity()).mmOutputStream.write(mode);
                    ((MainActivity)getActivity()).mmOutputStream.write(power);
                    ((MainActivity)getActivity()).mmOutputStream.write(10);
                    ((MainActivity)getActivity()).mmOutputStream.write(00);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}

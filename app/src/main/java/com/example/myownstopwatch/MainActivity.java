package com.example.myownstopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning; // untuk mencari tahu status activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) { // mengambil state stopwatch sebelumnya jika activity telah di destroy atau recreate
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer(); // mulai menjalankan/menampilkan stopwatch
    }

    @Override // akan menyimpan state stopwatch bila akan ter destroy
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }



    @Override
    protected void onResume(){ // jika activity berhenti, akan menjalankan lagi
        super.onResume();

        if (wasRunning){
            running = true;
        }
    }

    @Override
    protected void onStop(){ // jika activity berjalan maka akan memberhentikannya
        super.onStop();
        wasRunning = running;
        running = false;
    }

    public void onClickStart(View view){ // yang akan terpanggil saat di click tombol start
        running = true;
    }
    public void onClickStop(View view){// yang akan terpanggil saat di click tombol stop
        running = false;
    }
    public void onClickReset(View view){ // yang akan terpanggil saat di click tombol reset
        running = false;
        seconds = 0;
    }


    // menggunakan handler untuk menambah second dan mengupdate text view
    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                    if (running) {
                        seconds++;
                    }
                    handler.postDelayed( this, 1000);
            }
        });
    }

}
package com.example.application2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private TextView txtt;
    private Thread counterThread;

    private TextView timerTextView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private boolean isRunning = false;
    private int seconds = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                seconds++;
                int minutes = seconds / 60;
                int secs = seconds % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, secs));
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtt = findViewById(R.id.timerTextView);
    }

    public void startCount(View view) {
        counterThread = new Thread(() -> {
            try {
                while (true) {
                    i++;
                    txtt.setText(i + "");
                    Thread.sleep(1000);
                }
            } catch (Exception e) {}
            timerTextView = findViewById(R.id.timerTextView);
            startButton = findViewById(R.id.start);
            stopButton = findViewById(R.id.stop);
            resetButton = findViewById(R.id.resetButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isRunning = true;
                    handler.post(runnable);
                }
            });
            counterThread.start();
        });

        public void stopCount (View view) {
            counterThread.interrupt();
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isRunning = false;
                }
            });
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isRunning = false;
                    seconds = 0;
                    timerTextView.setText("00:00");
                }
            });
        }
    }

}

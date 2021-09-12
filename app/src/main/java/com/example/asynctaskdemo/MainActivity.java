package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textCounter;
    SeekBar startPoint;
    private ProgressBar pbar;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnStart);
        textCounter = findViewById(R.id.textCounter);
        startPoint = findViewById(R.id.startPos);
        pbar = findViewById(R.id.pbar);
        btn.setOnClickListener(view -> {
            int progress = startPoint.getProgress();
            new ExampleAsyncTask().execute(progress);
        });
    }

    class ExampleAsyncTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
            btn.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int start = integers[0];
            for (int i = start; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            textCounter.setText(String.valueOf(value));
        }

        @Override
        protected void onPostExecute(Void unused) {
            pbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Task done", Toast.LENGTH_SHORT).show();
            btn.setEnabled(true);
        }
    }
}
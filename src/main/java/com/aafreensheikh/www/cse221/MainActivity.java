package com.aafreensheikh.www.cse221;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Context;
import java.io.*;
import java.lang.Object;
import java.util.*;


public class MainActivity extends Activity {

    public class Dummy implements Runnable{
        public void run(){

        }
    }
    public class MyTime{
        long time;
        String unit;
        public MyTime(long time, String unit){
            this.time = time;
            this.unit = unit;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLoopOverhead(View v){
        if(v.getId() == R.id.b1){
            TextView label1 = (TextView)findViewById(R.id.textView1);
            int i;
            int count = 1000;
            long startTime;
            long stopTime;
            long avgOverhead=0;

            startTime = System.nanoTime();
            for(i=0;i<=count;i++){
            }
            stopTime = System.nanoTime();

            avgOverhead = (stopTime-startTime)/count;
            MyTime elapsedTime = getTimeString(avgOverhead);
            label1.setText(elapsedTime.time+" "+elapsedTime.unit);
        }
    }

    public long getTime(){
        return System.nanoTime();
    }

    public MyTime getTimeString(long time){
        long convertedTime = time;
        if(time<=999) {
            return new MyTime(convertedTime,"ns");
        }
        else if(time<=999999){
            convertedTime = time/1000;
            return new MyTime(convertedTime,"us");
        }
        else return new MyTime(convertedTime,"ns");
    }

    public void getReadingOverhead(View v){
        if(v.getId() == R.id.b2){
            Button button2 = (Button)findViewById(R.id.b2);
            TextView label2 = (TextView)findViewById(R.id.textView2);
            int count = 1000;
            long avgOverhead=0;
            long t[] = new long[count+1];
            long delta[] = new long[count+1];

            for(int i=0;i<=count;i++){
                t[i] = System.nanoTime();
            }

            for(int i=1;i<=count;i++){
                delta[i] = t[i] - t[i-1];
            }

            for(int i=1;i<=count;i++){
                avgOverhead = avgOverhead + delta[i];
            }

            avgOverhead = avgOverhead/count;
            MyTime elapsedTime = getTimeString(avgOverhead);
            label2.setText(elapsedTime.time+" "+elapsedTime.unit);
        }
    }

    public void getSysCallOverhead(View v){
        if(v.getId() == R.id.b4){
            //Button button4 = (Button)findViewById(R.id.b4);
            TextView label41 = (TextView)findViewById(R.id.textView41);
            TextView label42 = (TextView)findViewById(R.id.textView42);

            //
            int i;
            int count = 50;
            long startTime;
            long stopTime;
            long avgOverhead=0;


//Write SysCall
            try {
                //InputStream fos = openFileInput("syscall");
                File file;
                startTime = System.nanoTime();
                for(i=0;i<=count;i++) {
                file = File.createTempFile("MyFile", null, this.getCacheDir());
                    file.delete();
                }
                stopTime = System.nanoTime();
                avgOverhead = (stopTime-startTime)/(2*count);
                //fos.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

            MyTime elapsedTime = getTimeString(avgOverhead);
            label41.setText(elapsedTime.time+" "+elapsedTime.unit);

//Time SysCall

            startTime = System.nanoTime();

            for(i=0;i<=count;i++) {
                System.currentTimeMillis();
            }
            stopTime = System.nanoTime();
            avgOverhead = (stopTime-startTime)/count;
            MyTime elapsedTime2 = getTimeString(avgOverhead);
            label42.setText(elapsedTime2.time+" "+elapsedTime2.unit);
        }

    }

    public void getProcessCreationTime(View v){
        if(v.getId() == R.id.b5){
            TextView label = (TextView)findViewById(R.id.textView5);

            int count = 50;
            long avgOverhead=0;

            List<String> command = new ArrayList<String>();
            command.add("cmd.exe");
            ProcessBuilder builder = new ProcessBuilder(command);
            Map<String, String> environ = builder.environment();

            long startTime;
            long stopTime;

            startTime = System.nanoTime();
            for(int i=0;i<=count;i++) {
                try {

                    final Process process = builder.start();
                    //InputStream is = process.getInputStream();
                    process.destroy();

                } catch (IOException fnf) {
                    fnf.getMessage();
                }
            }
            stopTime =  System.nanoTime();
            avgOverhead = stopTime - startTime;
            avgOverhead = avgOverhead/count;
            MyTime elapsedTime = getTimeString(avgOverhead);
            label.setText(elapsedTime.time+" "+elapsedTime.unit);

        }
    }

    public void getThreadCreateTime(View v){
        if(v.getId() == R.id.b6) {
            TextView label = (TextView) findViewById(R.id.textView6);

            int count = 500;
            long avgOverhead = 0;
            long startTime;
            long stopTime;
            Dummy dummy = new Dummy();
            startTime = System.nanoTime();
            for (int i = 0; i <= count; i++) {
                try {
                    Thread t = new Thread(dummy);
                    t.start();
                    //t.join();
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            stopTime = System.nanoTime();
            avgOverhead = stopTime - startTime;
            avgOverhead = avgOverhead / count;
            MyTime elapsedTime = getTimeString(avgOverhead);
            label.setText(elapsedTime.time + " " + elapsedTime.unit);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

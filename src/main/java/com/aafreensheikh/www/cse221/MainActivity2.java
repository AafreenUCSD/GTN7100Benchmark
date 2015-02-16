package com.aafreensheikh.www.cse221;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Context;
import java.io.*;
import java.lang.Object;
import java.util.*;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import android.util.Log;

public class MainActivity2 extends Activity {
    //public static long[] times = new long[4];
    //public static int counter=0;

    //Create the 15 differently sized arrays
    //public static int[][] arrays = new int[15][];
    //Create 15 differently sized virtual arrays in one gigantic array of size 8MB
    public static int[] arrays = new int[8388608];

    //To store the RAM access time for each of the 15 array sizes from 512 bytes to 8 MB
    public static long[][] times = new long[16][9];

    //Enum to index the names of the arrays
    public static enum ArraySize{
        b512, kb1, kb2, kb4, kb8, kb16, kb32, kb64, kb128, kb256, kb512, mb1, mb2, mb4, mb8
    }

    public static int base = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
    }

    public class MyTime{
        long time;
        String unit;
        public MyTime(long time, String unit){
            this.time = time;
            this.unit = unit;
        }
    }

    public class Node{
        int data;
        Node next;
        public Node(int data){
            this.data = data;
        }
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
        else if(time<=999999999){
            convertedTime = time/1000000;
            return new MyTime(convertedTime,"ms");
        }
        else return new MyTime(convertedTime,"ns");
    }

    public void getRAMAccessTime(View v){
        //create arrays of different sizes (initialized with zeroes by default)
        /*
        int base = 128;
        for(int i=0; i<15; i++, base *=2){
            arrays[i] = new int[base];
        }
        */

        //pass some of them to the RAM access calculator
        for(int stride=1; stride<9; stride++){
            for(int i=1; i<16; i++) {
                //RAMAccessCalculator(arrays[i], i, stride);
                RAMAccessCalculator(arrays, i, stride);
            }
        }
        //printArray(times,v);
        printArrays(times,v);
    }

    public void printArrays(long[][] arr, View v){
        TextView label = (TextView) findViewById(R.id.RAMAccessTimes);
        for(int i=1; i<arr.length; i++){
//            label.append(String.valueOf(ArraySize.values()[i]));
            for(int j=1; j<arr[1].length;j++) {
                String s = String.valueOf(arr[i][j]);
                label.append(s + " ");
            }
            label.append("\n");
        }
    }

    public void printArray(long[] arr, View v){
        TextView label = (TextView) findViewById(R.id.RAMAccessTimes);
        for(int i=0; i<arr.length; i++){
            String s = String.valueOf(arr[i]);
            label.append(s+" ");
        }
    }

    public void RAMAccessCalculator(int[] arr, int index, int stride){
        //Create the linked list
        int count = 0;
            Node head = new Node(arr[0]);
            Node prev = head;
            Node p;
        int readUpto = base*index;

            for(int i=0;i<readUpto-stride;i=i+stride){
                p = new Node(arr[i]);
                prev.next = p;
                prev = p;
                count++;
            }

        //Traverse/read the linked list
        long startTime;
        long stopTime;
        long difftime;
        long AccessTime=0;

            p = head;
        startTime = System.nanoTime();
            while (p.next!=null) {
                p = p.next;
            }
        stopTime = System.nanoTime();

        difftime=stopTime-startTime;
        //difftime is taken for reading X numbers
        AccessTime=difftime/count;
        times[index][stride]=AccessTime;
        System.gc();
    }

    public void getRAMBandwidth(View v) {
        if (v.getId() == R.id.b1) {

            Log.d("Trial","Inside the logcat console now");

            TextView label1 = (TextView) findViewById(R.id.textView1);//read
            TextView label2 = (TextView) findViewById(R.id.textView2);//write
            TextView label3 = (TextView) findViewById(R.id.textView3);//copy

            long startTime;
            long stopTime;
            long diffTime;

            long bandwidthRead = 0;
            long bandwidthWrite = 0;
            long bandwidthCopy = 0;

            int ArraySize = 1024 * 512;//  2MB=> 2^21 /4= 2^19 int
            int Arrayhalf = ArraySize / 2;

            int array[] = new int[ArraySize + 1];
            int array2[] = new int[ArraySize + 1];
            int array3[] = new int[ArraySize + 1];

            int dummy[] = new int[ArraySize + 1];
            int cnst = 0;
            int stride = 1;


//init

            for (int i = 0; i < ArraySize; i++) {
                array[i] = 1;
                array2[i] = 2;
                array3[i] = 3;

                dummy[i] = 0;
            }

//access one: stride 8-read
            startTime = System.nanoTime();
            for (int i = 0; i < ArraySize - stride; i += stride) {
                cnst = array[i];
            }

            stopTime = System.nanoTime();
            diffTime = stopTime - startTime;

// now we have accessed 2^19/2^3=2^16
            //bandwidthRead= 2^16/diffTime; //byte per nanosecond
            //lable1 .setText((int) bandwidthRead);
            MyTime elapsedTime = getTimeString(diffTime);
            label1.setText(elapsedTime.time + " " + elapsedTime.unit);
//access one: stride 8-writeﬂ

            startTime = System.nanoTime();
            for (int i = 0; i < ArraySize - stride; i += stride) {
                array2[i] = 10;
            }

            stopTime = System.nanoTime();
            diffTime = stopTime - startTime;

// now we have accessed 2^19/2^3=2^16
            //bandwidthWrite= 2^16/diffTime; //byte per nanosecond
            MyTime elapsedTime2 = getTimeString(diffTime);
            label2.setText(elapsedTime2.time + " " + elapsedTime2.unit);
            //   -----
//access one: stride 8-Copy

            startTime = System.nanoTime();
            for (int i = 0; i < ArraySize-4; i += 4) {
                //System.arraycopy(array3, i, dummy, i, 1);
                dummy[i] = array3[i];
                dummy[i+1] = array3[i+1];
                dummy[i+2] = array3[i+2];
                dummy[i+3] = array3[i+3];
            }

            stopTime = System.nanoTime();
            diffTime = stopTime - startTime;

// now we have accessed 2^19/2^2=2^16
            bandwidthCopy = 2 ^ 16 / diffTime; //byte per nanosecond
            //label3.setText((int) bandwidthCopy);
            MyTime elapsedTime3 = getTimeString(diffTime);
            label3.setText(elapsedTime3.time + " " + elapsedTime3.unit);
//do the same with stride i=1 to see pure sequential bandwidth.

            //
        }
    }

        public void getPageFaultTime(View v) throws IOException {
        if (v.getId() == R.id.b2) {
            TextView label1 = (TextView) findViewById(R.id.textView4);//write

            long startTime;
            long stopTime;
            long diffTime = 0;
            int count = 2*1024*1024;
            RandomAccessFile memoryMappedFile = new RandomAccessFile(this.getFilesDir().getPath().toString() + "largeFile.txt", "rw");
            MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
            startTime = System.nanoTime();

            for (int i = 0; i < count; i += (1024*4)) {
                out.put(i, (byte) 'A');
           }
            stopTime = System.nanoTime();
            diffTime = stopTime - startTime;//for 2^11 accesses.
            diffTime = diffTime/(512);
            MyTime elapsedTime3 = getTimeString(diffTime);
            label1.setText(elapsedTime3.time + " " + elapsedTime3.unit);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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

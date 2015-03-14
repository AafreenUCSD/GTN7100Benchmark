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

    //Cache line size is probably 64 bytes = 16 ints, so we will create strideLengths as 8,16,32,64 (in different programs)

    //Fix the stride in this program here:
    int strideLength = 8;

    //Enum to index the names of the arrays
    public static enum ArraySize{
        b512, kb1, kb2, kb4, kb8, kb16, kb32, kb64, kb128, kb256, kb512, mb1, mb2, mb4, mb8
    }

    //Base used for declaring the size of arrays
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
        int[] array0 = new int[base];
        int[] array1 = new int[base*2];
        int[] array2 = new int[base*4];
        int[] array3 = new int[base*8];
        int[] array4 = new int[base*16];
        int[] array5 = new int[base*32];
        int[] array6 = new int[base*64];
        */

        int[] array0 = new int[base*128];
        int[] array1 = new int[base*256];
        int[] array2 = new int[base*512];
        int[] array3 = new int[base*1024];
        int[] array4 = new int[base*2*1024];
        int[] array5 = new int[base*4*1024];
        int[] array6 = new int[base*8*1024];

        long[] avgTimeTakenForEachArray = new long[7];
        long startTime;
        long stopTime;

        
        //array0
        int numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array0.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array0[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[0] = stopTime - startTime;

        //array1
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array1.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array1[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[1] = stopTime - startTime;

        //array2
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array2.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array2[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[2] = stopTime - startTime;

        //array3
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array3.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array3[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[3] = stopTime - startTime;

        //array4
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array4.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array4[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[4] = stopTime - startTime;

        //array5
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array5.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array5[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[5] = stopTime - startTime;

        //array6
        numberOfJumps=0;
        startTime = System.nanoTime();
        for(int i=0; i<array6.length-strideLength; i=i+strideLength, numberOfJumps++) {
            array6[i]++;
        }
        stopTime = System.nanoTime();
        avgTimeTakenForEachArray[6] = stopTime - startTime;

        printArray(avgTimeTakenForEachArray, numberOfJumps, v);
    }

    public void printArrays(long[][] arr, View v){
        TextView label = (TextView) findViewById(R.id.RAMAccessTimes);
        for(int i=1; i<arr.length; i++){
//            label.append(String.valueOf(ArraySize.values()[i]));
            for(int j=0; j<arr[1].length;j++) {
                String s = String.valueOf(arr[i][j]);
                label.append(s + ", ");
            }
            label.append("\n");
        }
    }

    public void printArray(long[] arr, int numberOfJumps, View v){
        TextView label = (TextView) findViewById(R.id.RAMAccessTimes);
        for(int i=0; i<arr.length; i++){
            String s = String.valueOf(arr[i]);
            label.append(s+", ");
        }
    }

    public void printTimeTakenForEachJump(int[] arr, int numberOfJumps, View v){
        TextView label = (TextView) findViewById(R.id.RAMAccessTimes);
        label.append("Check out the logcat console.");
        for(int i=0; i<numberOfJumps; i++){
            String s = String.valueOf(arr[i]);
            Log.d("Jumps = "+numberOfJumps, ", "+s);
        }
       // System.gc();
    }

    /*
    public void RAMAccessCalculator(int[] arr, int index, int stride, int strideNo){
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
        //AccessTime=difftime/count;

        AccessTime=difftime/1000;
        times[index][strideNo]=AccessTime;
        System.gc();
    }
    */

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
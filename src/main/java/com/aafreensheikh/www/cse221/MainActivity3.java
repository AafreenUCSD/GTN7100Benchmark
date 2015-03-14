package com.aafreensheikh.www.cse221;
import android.app.Activity;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
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
import java.nio.channels.FileChannel;
import java.io.*;
import java.nio.ByteBuffer;
import java.io.Writer;


public class MainActivity3 extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);
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

    private String createDataSize(int msgSize) {
        // Java chars are 2 bytes
        //msgSize = msgSize/2;
        //msgSize = msgSize * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=0; i<msgSize; i++) {
            sb.append('a');
        }
        return sb.toString();
    }
    private String createDataSizeNewLine(int msgSize) {
        // Java chars are 2 bytes
        //msgSize = msgSize/2;
        //msgSize = msgSize * 1024;
        StringBuilder sb = new StringBuilder(msgSize);
        for (int i=0; i<msgSize; i++) {
            sb.append("a");
            sb.append("\n");
        }
        return sb.toString();
    }

   /* public void createFile(View v){ //create a file with specific size, we need to reboot the system (or drop the cache after that). Not for the first part.
        if(v.getId()==R.id.b4){/////////b1


            try
            {

                myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                myFile.createNewFile();
                // Adds a line to the trace file
                BufferedWriter writer = new BufferedWriter(new FileWriter(myFile, false));
                writer.write(createDataSize(size));
                writer.close();


            }
            catch (IOException e)
            {
                Log.e("FileTest", "Unable to write to the myFile.txt file.");
            }

        }
    }*/

    int size = 1024*512;
    int size1 = 1024*512;
    int size2 = 1024*512;
    int size3 = 1024*512;
    int size4 = 1024*512;
    int size5 = 1024*512;
    int size6 = 1024*512;
    int size7 = 8;
    int size8 = 16;
    int size9 = 32;

File myFile,myFile1,myFile2,myFile3,myFile4,myFile5,myFile6,myFile7,myFile8,myFile9;


    public void createFile(View v){

        if(v.getId()==R.id.b4){


            try

            {  myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");

                myFile1 = new File(((Context)this).getExternalFilesDir(null), "myFile1.txt");
                myFile2 = new File(((Context)this).getExternalFilesDir(null), "myFile2.txt");
                myFile3 = new File(((Context)this).getExternalFilesDir(null), "myFile3.txt");

                myFile4 = new File(((Context)this).getExternalFilesDir(null), "myFile4.txt");
                myFile5 = new File(((Context)this).getExternalFilesDir(null), "myFile5.txt");
                myFile6 = new File(((Context)this).getExternalFilesDir(null), "myFile6.txt");

                myFile7 = new File(((Context)this).getExternalFilesDir(null), "myFile7.txt");
                myFile8 = new File(((Context)this).getExternalFilesDir(null), "myFile8.txt");
                myFile9 = new File(((Context)this).getExternalFilesDir(null), "myFile9.txt");


                myFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(myFile, false));
                writer.write(createDataSize(size));
                writer.close();
                //for contention part
                myFile1.createNewFile();
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(myFile1, false));
                writer1.write(createDataSize(size1));
                writer1.close();
                //second 1
                myFile2.createNewFile();
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(myFile2, false));
                writer2.write(createDataSize(size2));
                writer2.close();
                //third
                myFile3.createNewFile();
                BufferedWriter writer3 = new BufferedWriter(new FileWriter(myFile3, false));
                writer3.write(createDataSize(size3));
                writer3.close();
                //
                myFile4.createNewFile();
                BufferedWriter writer4 = new BufferedWriter(new FileWriter(myFile4, false));
                writer4.write(createDataSize(size4));
                writer4.close();
                //
                myFile5.createNewFile();
                BufferedWriter writer5 = new BufferedWriter(new FileWriter(myFile5, false));
                writer5.write(createDataSize(size5));
                writer5.close();
//
                myFile6.createNewFile();
                BufferedWriter writer6 = new BufferedWriter(new FileWriter(myFile6, false));
                writer6.write(createDataSize(size6));
                writer6.close();
//
                myFile7.createNewFile();
                BufferedWriter writer7 = new BufferedWriter(new FileWriter(myFile7, false));
                writer7.write(createDataSize(size7));
                writer7.close();
//
                myFile8.createNewFile();
                BufferedWriter writer8 = new BufferedWriter(new FileWriter(myFile8, false));
                writer8.write(createDataSize(size8));
                writer8.close();
//
                myFile9.createNewFile();
                BufferedWriter writer9 = new BufferedWriter(new FileWriter(myFile9, false));
                writer9.write(createDataSize(size9));
                writer9.close();

            }

            catch (IOException e)
            {
            }
        }
    }





    public void getFileCacheSize(View v){ //file read write for now
        if(v.getId()==R.id.b1){
            TextView label1=(TextView) findViewById(R.id.textView1);
            long start_time;
            long end_time;
            long measured_time;
//            int size = 64; //the same as the created file, make it global?

            /************ READING-assuming data is cached if cache is large enough-measuring *******/
            try
            {
                //File myFile = new  File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                //nothing is created. the previous file
                myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                BufferedReader reader = new BufferedReader(new FileReader(myFile));
                start_time = System.nanoTime();
                while(reader.read()=='a');
                end_time = System.nanoTime();
                measured_time = (end_time - start_time) / size;// we just need a scale
                MyTime elapsedTime=getTimeString(measured_time);
                label1.setText(elapsedTime.time+" "+elapsedTime.unit);
                reader.close();
                /*
                MediaScannerConnection.scanFile((Context)(this),
                        new String[] { myFile.toString() },
                        null,
                        null);
                 */
            }
            catch (IOException e)
            {
                Log.e("FileTest", "Unable to read the myFile.txt file.");
            }
        }
    }

    public void getFileSequentialReadTime(View v){
        if(v.getId()==R.id.b2) {
            TextView label1 = (TextView) findViewById(R.id.textView2);

            long start_time;
            long end_time;
            long measured_time;
            int numOfblocks = 5;
            int numOfBytes=4*1024;//1blcok=4KB
            int size = 64; //the same as created file/
            //nothing is created

            /************************ READING SEQUENTIALLY-cache is empty ****************/
            try {
           //     File myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                //nothing is created. the previous file
                myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                BufferedReader reader = new BufferedReader(new FileReader(myFile));
                start_time = System.nanoTime();

                for(int block=0;block<numOfblocks;block++){
                    for(int bayt=0;bayt<numOfBytes;bayt++){
                        reader.read();
                    }
                }
                end_time = System.nanoTime();
                measured_time = (end_time - start_time) / numOfblocks;
                MyTime elapsedTime=getTimeString(measured_time);
                label1.setText(elapsedTime.time+" "+elapsedTime.unit);
                reader.close();

                /*
                MediaScannerConnection.scanFile((Context)(this),
                        new String[] { traceFile.toString() },
                        null,
                        null);
                 */

            }
            catch (IOException ex) {
                Log.e("Read file sequentially", "Something went wrong :(");
            }
        }
    }

    public void getFileRandomReadTime(View v){
        if(v.getId()==R.id.b3) {
            TextView label1 = (TextView) findViewById(R.id.textView3);

            long start_time;
            long end_time;
            long measured_time;
            int  rand_array[]={1,12,23,34,4};//just a random array
            int numOfblocks = 5;
            int numOfBytes=4*1024;//1blcok=4KB            int size = 64; //the same as created file/
            int i=0;
            //nothing is created
           // File myFile = new File(((Context) this).getExternalFilesDir(null), "myFile.txt");
            /************************ READING SEQUENTIALLY-cache is empty ****************/
            try {
                myFile = new File(((Context)this).getExternalFilesDir(null), "myFile.txt");
                LineNumberReader rdr = new LineNumberReader(new FileReader(myFile));
                start_time=System.nanoTime();
                for(int block=0;block<numOfblocks;block++){
                    rdr.setLineNumber((rand_array[i]*numOfBytes));//ponit to 10th line in the random block number!
                    for(int bayt=0;bayt<numOfBytes;bayt++){
                        //read a block
                        rdr.readLine();
                    }
                    //jump to another block
                    i++;
                }
                end_time = System.nanoTime();
                measured_time = (end_time - start_time)/numOfblocks;
                MyTime elapsedTime=getTimeString(measured_time);
                label1.setText(elapsedTime.time+" "+elapsedTime.unit);
            } catch (IOException ex) {
                Log.e("Read file randomly", "Something went wrong :(");
            }
        }
    }

    public void getTimeContention(View v){
        if(v.getId()==R.id.b5) {
            TextView label1 = (TextView) findViewById(R.id.textView8);
            //  TextView label2 = (TextView) findViewById(R.id.textView);
            //  TextView label3 = (TextView) findViewById(R.id.textView3);


            class myThread implements Runnable {
                private File myFile;
                public long myTime;
                public int isFinished;
                long start_time;
                long end_time;
                BufferedReader reader;

                public myThread(File myfile) {
                    this.myFile = myfile;
                    isFinished = 0;
                }

                public void run() {
                    try {

                        reader = new BufferedReader(new FileReader(myFile));
                        start_time = System.nanoTime();
                        while (reader.read() == 'a') ;
                        end_time = System.nanoTime();
                        isFinished = 1;
                        myTime = (end_time - start_time);
                        reader.close();

                    }
                    catch(IOException e){ }

                }
            }

            myFile1 = new File(((Context)this).getExternalFilesDir(null), "myFile1.txt");
            myFile2 = new File(((Context)this).getExternalFilesDir(null), "myFile2.txt");
            myFile3 = new File(((Context)this).getExternalFilesDir(null), "myFile3.txt");
            myFile4 = new File(((Context)this).getExternalFilesDir(null), "myFile4.txt");
            myFile5 = new File(((Context)this).getExternalFilesDir(null), "myFile5.txt");
            myFile6 = new File(((Context)this).getExternalFilesDir(null), "myFile6.txt");
            myThread T1=new myThread(myFile1);
            Thread t1=new Thread(T1);
            myThread T2=new myThread(myFile2);
            Thread t2=new Thread(T2);
            myThread T3=new myThread(myFile3);
            Thread t3= new Thread(T3);
            myThread T4=new myThread(myFile4);
            Thread t4=new Thread(T4);
            myThread T5=new myThread(myFile5);
            Thread t5=new Thread(T5);
            myThread T6=new myThread(myFile6);
            Thread t6= new Thread(T6);
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            while(T1.isFinished*T2.isFinished*T3.isFinished*T4.isFinished*T5.isFinished*T6.isFinished!=1);
            long ave_time=(T1.myTime+T2.myTime+T3.myTime+T4.myTime+T5.myTime+T6.myTime)/6 ;
            MyTime elapsedTime1 = getTimeString(T1.myTime);
            label1.setText(elapsedTime1.time + " " + elapsedTime1.unit);

        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
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


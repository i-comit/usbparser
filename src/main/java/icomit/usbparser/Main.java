/*
Copyright 2022 Khiem G Luong

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
(the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package icomit.usbparser;
import java.io.*;
import java.util.Scanner;


public class Main implements Runnable  
{   
    public static int threadIterator; 
    public void run()  
    {    
        try {
            Process getSize = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Size");
            Process getTotalSectors = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalSectors");
            Process getBytesPerSector = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get BytesPerSector");
            Process getSectorsPerTrack = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get SectorsPerTrack");
    
            Process getTotalHeads = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Totalheads");
            Process getTotalCylinders = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalCylinders");
    
            Process getTotalTracks = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalTracks");
            Process getTracksPerCylinder = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TracksPerCylinder");
            Process getSignature = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Signature");
    
            Process[] getAllProperties = {getSize, getTotalSectors, getBytesPerSector, getSectorsPerTrack, getTotalHeads, getTotalCylinders, getTotalTracks, getTracksPerCylinder, getSignature};
            String[]  getAllNames = {"Total Size: ", "Total Sectors: ", "Bytes per Sector: ", "SectorsPerTrack: ", "Total Heads: ", "Total Cylinders: ", "Total Tracks: ", "Tracks Per Cylinder: ", "Signature: "};
            USBParseAll.main(getAllProperties, getAllNames, threadIterator);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        System.out.println("Parsing USB Device " + USBParseAll.staticDeviceID + "...");
    }      

    //Set this value to 0 or 1
    public static int parseOption = 1;

    public static void PrintResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);

        }
    }

    private static void RunUSBParseOne() throws IOException {
        USBParseOneT runnable = new USBParseOneT();
        Thread t = new Thread(runnable);
        t.start();
    }

    private static void RunUSBParseAll() throws IOException{
        Main m1=new Main();    
        Thread t1 =new Thread(m1);    
        t1.start();

        // System.out.println("dcount " + USBParseAll.GetDeviceCount());
        for(int i=0; i< USBParseAll.GetDeviceCount(); i++){
            threadIterator++;
            Main m=new Main();    
            Thread t =new Thread(m);    
            t.start();
            // System.out.println("DevuceUD from USBParseAll" + USBParseAll.staticDeviceID);

        }
    }
    public static final Scanner in = new Scanner(System.in);
    public static void main( String[] args ) throws IOException
    {
        System.out.print("INPUT 0 to parse ALL USBs | INPUT 1 to parse ONE USB: ");
        String input1;
        input1 = in.nextLine();
        parseOption = Integer.parseInt(input1);
        // Process format1drive = Runtime.getRuntime().exec("format " + USBParseOne.staticDeviceID +" /q /fs:NTFS /p:1 /v:");
        // try {
        //     format1drive.waitFor();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // PrintResults(format1drive);
        // if(format1drive.isAlive()){
        //     return;
        // }
        // else{
        // }
        switch(parseOption){
            case 0:
            RunUSBParseAll();
            break;
            case 1:
            RunUSBParseOne();
            break;
            default:
            System.out.println("You did not enter a correct value.");
            break;
        }   

    }
}

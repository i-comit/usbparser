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

public class Main
{   
    public static int parseOption;
    public static int OSInt;

    public static void PrintResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void RunUSBParse0() throws IOException{
        switch(OSInt) {
            case 0:
            usbparser.windows.USBParse0T runnable0 = new usbparser.windows.USBParse0T();
            runnable0.threadIterator = 0; 
            Thread t1 =new Thread(runnable0);    
            t1.start();
    
            for(int i=0; i< usbparser.windows.USBParse0.GetDeviceCount(); i++){
                runnable0.threadIterator++;
                Thread t =new Thread(runnable0);    
                t.start();
            }
            break;
            case 1:
            usbparser.linux.USBParse0T runnableLinux = new usbparser.linux.USBParse0T();
            runnableLinux.threadIterator = 0; 
            Thread tLinux =new Thread(runnableLinux);    
            tLinux.start();
    
            for(int i=0; i< usbparser.linux.USBParse0.GetDeviceCount(); i++){
                runnableLinux.threadIterator++;
                Thread tLinux1 =new Thread(runnableLinux);    
                tLinux1.start();
            }
            break;
        }
    }

    private static void RunUSBParse1() throws IOException {
        switch(OSInt) {
            case 0:
            USBParse1T runnable1 = new USBParse1T();
            Thread t = new Thread(runnable1);
            t.start();
            break;
        }
    }

    public static final Scanner in = new Scanner(System.in);
    public static void main( String[] args ) throws IOException
    {
        String OSString = System.getProperty("os.name");
        //System.out.println("OSString" + OSString);
        if(OSString.startsWith("Windows")){
            OSInt = 0;
        }
        if(OSString.startsWith("Linux")){
            OSInt = 1;
            System.out.println("Linux OS active");
            
        }

        FileInputStream fstream = new FileInputStream("icomitascii.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null)   {
        System.out.println (strLine);
        }
        fstream.close();

        System.out.println("");
        System.out.print("INPUT 0 to parse ALL USBs | INPUT 1 to parse ONE USB: ");
        String input1;
        input1 = in.nextLine();
        System.out.println("");
        parseOption = Integer.parseInt(input1);

        switch(parseOption){
            case 0:
            RunUSBParse0();
            break;
            case 1:
            RunUSBParse1();
            break;
            default:
            System.out.println("You did not enter a correct value.");
            break;
        }   

    }
}

package usbparser.linux;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//Thread class for parsing

public class USBParse0T implements Runnable  
{   
    public static String[] deviceCountArr;
    public static String drivePath;
    public static String mountPoint;
    public static String fileSystem;
    public static String driveSize;
    public int threadIterator; 
    
    public void run()  
    {    
        try {           
            USBParse0.main();

        } catch (IOException ex) {
            Logger.getLogger(USBParse1T.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
   
    public static void PrintFileSystem(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        ArrayList<String> deviceCount = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            drivePath = line.substring(0,14);
            System.out.println("DrivePath: " + drivePath);
            fileSystem = line.substring(15, 24);
            System.out.println("FileSystem: " + fileSystem);   
            drivePath = drivePath.replaceAll("\\s", "");
            deviceCount.add(drivePath);
            if(line ==  null){
            System.out.println("No USB Devices Found");
            }
        }
        deviceCountArr = deviceCount.toArray(new String[0]);
        USBParse1.deviceIDArr = deviceCountArr;
    }

    public static void PrintSize(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        Boolean runOnce = false;
        while ((line = reader.readLine()) != null) {
            if(!runOnce){
                driveSize = line.substring(24, 29);
                System.out.println("Drive Size: " + driveSize);   
                mountPoint = line.substring(38, line.length());
                System.out.println("Mount Point: " + mountPoint);
                runOnce = true;
            }

        }
    } 
}   
package usbparser.linux;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

//Thread class for parsing

public class USBParse1T implements Runnable  
{   
    public static String drivePath;
    public static String fileSystem;
    public static String driveSize;
    public void run()  
    {    

        //grep finds all mount points starting with sdb (USBs)
        //Because sgdisk apparently doesn't work.
        String[] cmd1 = new String[] {"bash", "-c", " df -Th | grep sdb"};
        String[] cmd2 = new String[] {"bash", "-c", " lsblk | grep sdb"};
        try {
            System.out.println("");
            Process proc1 = new ProcessBuilder(cmd1).start();
            PrintFileSystem(proc1);

            Process proc2 = new ProcessBuilder(cmd2).start();
            PrintSize(proc2);
        } catch (IOException ex) {
            Logger.getLogger(USBParse0T.class.getName()).log(Level.ALL, null, ex);
        }	
    } 
   
    public static void PrintFileSystem(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            drivePath = line.substring(0,14);
            System.out.println("DrivePath: " + drivePath);
            fileSystem = line.substring(15, 20);
            System.out.println("FileSystem: " + fileSystem);   
        }
        if(line ==  null){
            System.out.println("No USB Devices Found");
        }
    }

    public static void PrintSize(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        Boolean runOnce = false;
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            if(!runOnce){
                driveSize = line.substring(24, 29);
                System.out.println("Drive Size: " + driveSize);   
                runOnce = true;
            }

        }
    } 
}   
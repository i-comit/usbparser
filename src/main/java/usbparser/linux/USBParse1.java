package usbparser.linux;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import icomit.usbparser.Main;
import static usbparser.linux.USBParse1T.deviceCountArr;

//This class implements the scanner object from Main.java in order for the user to select a specific USB for parsing.

public class USBParse1 {

    public static int index;
    public static String[] deviceIDArr;
    public static String staticDeviceID;

    public static int GetDeviceCount() throws IOException {
        String[] cmd1 = new String[] {"bash", "-c", " df -Th | grep sdb"};
        String[] cmd2 = new String[] {"bash", "-c", " lsblk | grep sdb | grep part"};
        Process proc1 = new ProcessBuilder(cmd1).start();
        Process proc2 = new ProcessBuilder(cmd2).start();
        
        USBParse1T.PrintFileSystem(proc1);
        USBParse1T.PrintSize(proc2);

        System.out.print(deviceCountArr.length + " USB devices found " + Arrays.toString(deviceCountArr) + " Enter index of USB (0-" + (deviceCountArr.length -1)+ "): "); 
        String input1 = Main.in.nextLine();
        System.out.println("");
        Main.in.close();
        index = Integer.parseInt(input1);  
        return index; 
    }

    public static String GetDeviceID(String[] deviceIDArr, int index) throws IOException {
        // System.out.println("Device ID: " + deviceIDArr[index]);
        staticDeviceID = deviceIDArr[index];
        return deviceIDArr[index];
    }
    public static void GetReadWriteSpeed(String[] deviceIDArr, int index) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "runas /user:administrator cmd /savecred");
        // Process process = processBuilder.start();
    }

    public static void MoveDataToDrive(int index) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "mv driverproperties.txt " + USBParse1T.mountPoint);
        processBuilder.start();
  
        ProcessBuilder processBuilder1 = new ProcessBuilder();
        processBuilder1.command("bash", "-c", "cp -p Autorun.inf " + USBParse1T.mountPoint);
        processBuilder1.start();
        
        ProcessBuilder processBuilder2 = new ProcessBuilder();
        processBuilder2.command("bash", "-c", "cp -p Driver.ico " + USBParse1T.mountPoint);
        processBuilder2.start();

        // ProcessBuilder processBuilder3 = new ProcessBuilder();
        // processBuilder3.command("cmd.exe", "/c", "runas /profile /user:administrator /savecred \"mountvol " + staticDeviceID + " /p\"" );
        // processBuilder3.start();
    }

    public static String ConvertToGB(String deviceSize) throws IOException {
        deviceSize = deviceSize.replaceAll("\\s", "");
        Long sizeLong = Long.parseLong(deviceSize);
        Long gigaByte = 1024L*1024L*1024L;
        String GB = String.valueOf(sizeLong/gigaByte);
        return GB;
    }

    public static void main() throws IOException {
        index = GetDeviceCount();
        FileWriter fw = new FileWriter("driverproperties.txt");
        SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z");
        Date date = new Date(System.currentTimeMillis());

        Boolean deviceSizeBool = false;
        fw.write("i-SECURE" + "| Parsed " + formatter.format(date) + "\n\n");
        fw.write("File System: " + USBParse1T.fileSystem + "\n");
        fw.write("Drive Size: " + USBParse1T.driveSize + "\n\n");
        for (int i = 0; i < index; i++){
            
        }
        fw.write("\ngithub.com/i-comit/icomit-usbparser");
        fw.close();
        MoveDataToDrive(index);
        System.out.println("Parsing USB Device " + GetDeviceID(deviceIDArr, index) + " ..");
    }
}

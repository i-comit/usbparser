package usbparser.linux;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//This class queries all devices and parses each one iterative on a separate thread.

public class USBParse0 {

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
        index = USBParse0T.deviceCountArr.length;
        System.out.println("deviceCount from Parse 0: " + index);
        return index; 
    }

    public static String GetDeviceID(String[] deviceIDArr, int index) throws IOException {
        // System.out.println("Device ID: " + deviceIDArr[index]);
        staticDeviceID = deviceIDArr[index];
        return deviceIDArr[index];
    }

    public static void MoveDataToDrive(int index) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "move driverproperties.txt " + GetDeviceID(deviceIDArr, index) + "/" );
        // Process process = processBuilder.start();
        processBuilder.start();
        
        ProcessBuilder processBuilder1 = new ProcessBuilder();
        processBuilder1.command("cmd.exe", "/c", "xcopy Autorun.inf " + GetDeviceID(deviceIDArr, index) + " /h /y" );
        processBuilder1.start();

        ProcessBuilder processBuilder2 = new ProcessBuilder();
        processBuilder2.command("cmd.exe", "/c", "xcopy Driver.ico " + GetDeviceID(deviceIDArr, index) + " /h /y /n" );
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
        for (int i = 0; i < index; i++){
            FileWriter fw = new FileWriter("driverproperties.txt");
            SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z");
            Date date = new Date(System.currentTimeMillis());

            Boolean deviceSizeBool = false;
            fw.write("i-SECURE" + "| Parsed " + formatter.format(date) + "\n\n");

            fw.write("\ngithub.com/i-comit/icomit-usbparser");
            fw.close();
            MoveDataToDrive(index);
            System.out.println("Parsing USB Device " + staticDeviceID + " ..");
        }
    }
}

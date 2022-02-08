package usbparser.windows;

import icomit.usbparser.Main;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//This class implements the scanner object from Main.java in order for the user to select a specific USB for parsing.

public class USBParse1 {

    public static int index;
    public static String deviceSize;
    public static String[] deviceIDArr;
    public static String staticDeviceID;

    public static int GetDeviceCount() throws IOException {
        Process pr = Runtime.getRuntime().exec("wmic logicaldisk where drivetype=2 get DeviceID");
        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        ArrayList<String> deviceCount = new ArrayList<String>();

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\s", "");
            if(line.endsWith(":")){
                deviceCount.add(line);
            }
        }
        String[] deviceCountArr = deviceCount.toArray(new String[0]);
        deviceIDArr = deviceCountArr;
        System.out.println("DeviceIDaArr " + Arrays.toString(deviceIDArr));
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
        processBuilder.command("cmd.exe", "/c", "move driverproperties.txt " + GetDeviceID(deviceIDArr, index) + "/" );
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

    public static void main(Process[] process, String[] processName) throws IOException {
        index = GetDeviceCount();
        FileWriter fw = new FileWriter("driverproperties.txt");
        SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z");
        Date date = new Date(System.currentTimeMillis());

        Boolean deviceSizeBool = false;
        fw.write("i-SECURE" + "| Parsed " + formatter.format(date) + "\n\n");
        for (int i = 0; i < process.length; i++){
            BufferedReader reader = new BufferedReader(new InputStreamReader(process[i].getInputStream()));
            String line = "";
            ArrayList<String> selectedLines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                // System.out.println(line); 
                if(line.matches("^[0-9].*")){
                    line = line.replaceAll("\\s", "");
                    selectedLines.add(line);
                }
            }
            if(!deviceSizeBool){
                // System.out.println("deviceSize" + "=" + selectedLines.get(index));
                fw.write("Usable Space (in GBs): " + ConvertToGB(selectedLines.get(index)) + "\n\n");
                deviceSizeBool = true;
            }
            fw.write(processName[i] + selectedLines.get(index) + "\n");
            // GetReadWriteSpeed(deviceIDArr, index);
        }
        fw.write("\ngithub.com/i-comit/icomit-usbparser");
        fw.close();
        MoveDataToDrive(index);
        System.out.println("Parsing USB Device " + staticDeviceID + " ..");
    }
}

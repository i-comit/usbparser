package usbparser.linux;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//This class queries all devices and parses each one iterative on a separate thread.

public class USBParse0 {

    public static String[] deviceIDArr;
    public static String staticDeviceID;

    public static int GetDeviceCount() throws IOException {
        String[] cmd = {"/bin/bash","-c","echo password| sudo -S ls"};
        Process pb = Runtime.getRuntime().exec(cmd);
        ArrayList<String> deviceCount = new ArrayList<String>();
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        while ((line = input.readLine()) != null) {
            line = line.replaceAll("\\s", "");
            if(line.endsWith("Flash Drive")){
                deviceCount.add(line);
            }
        }

        //String[] deviceCountArr = deviceCount.toArray(new String[0]);
        //deviceIDArr = deviceCountArr;
        int dcount = 1;
        return dcount; 
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

    public static void main(Process[] process, String[] processName, int mainindex) throws IOException {
        GetDeviceCount();
        for (int i = 0; i < 1; i++){
            FileWriter fw = new FileWriter("driverproperties.txt");
            SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm z");
            Date date = new Date(System.currentTimeMillis());

            Boolean deviceSizeBool = false;
            fw.write("i-SECURE" + "| Parsed " + formatter.format(date) + "\n\n");
            for (int j = 0; j < process.length; j++){
                BufferedReader reader = new BufferedReader(new InputStreamReader(process[j].getInputStream()));
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
                    fw.write("Usable Space (in GBs): " + ConvertToGB(selectedLines.get(mainindex)) + "\n\n");
                    deviceSizeBool = true;
                }
                fw.write(processName[j] + selectedLines.get(mainindex) + "\n");
            }
            fw.write("\ngithub.com/i-comit/icomit-usbparser");
            fw.close();
            MoveDataToDrive(mainindex);
            System.out.println("Parsing USB Device " + staticDeviceID + " ..");
        }
    }
}

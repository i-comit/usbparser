package icomit.usbparser;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class USBParseOne {

    public static int index;
    public static String[] deviceIDArr;

    public static int GetDeviceCount() throws IOException {
        Process pr = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Index");
        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        ArrayList<String> deviceCount = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            if(line.matches("^[0-9].*")){
                line = line.replaceAll("\\s", "");
                deviceCount.add(line);
            }
        }
        String[] deviceCountArr = deviceCount.toArray(new String[0]);
        String[] letterIndice = {"A", "B", "C", "D", "E", "F", "G"};

        ArrayList<String> deviceChar = new ArrayList<String>();
        for (int i = 0; i < deviceCountArr.length; i++) {
            if(Integer.parseInt(deviceCountArr[i]) == 0) {
                deviceChar.add(letterIndice[0] + ":");
            } 
            if(Integer.parseInt(deviceCountArr[i]) == 1){
                deviceChar.add(letterIndice[1]);
            } 
            if(Integer.parseInt(deviceCountArr[i]) == 2){
                deviceChar.add(letterIndice[2] + ":");
            } 
            if(Integer.parseInt(deviceCountArr[i]) == 3){
                deviceChar.add(letterIndice[3] + ":");
            } 
            if(Integer.parseInt(deviceCountArr[i]) == 4){
                deviceChar.add(letterIndice[4] + ":");
            } 
            if(Integer.parseInt(deviceCountArr[i]) == 5){
                deviceChar.add(letterIndice[5] + ":");
            }  
            if(Integer.parseInt(deviceCountArr[i]) == 6){
                // System.out.println("Matched Char " + letterIndice[6]);
                deviceChar.add(letterIndice[6] + ":");
            } 
        }
        
        deviceIDArr = deviceChar.toArray(new String[0]);
 

        System.out.print(deviceCountArr.length + " USB devices found " + Arrays.toString(deviceIDArr) + " Enter index of USB (0-" + (deviceCountArr.length -1)+ "): "); 
        Scanner scanner1 = new Scanner(System.in);
        String input1;
        input1 = scanner1.nextLine();
        
        scanner1.close();
        index = Integer.parseInt(input1);
        return index; 
    }

    public static String GetDeviceID(String[] deviceIDArr, int index) throws IOException {
        System.out.println("Device ID: " + deviceIDArr[index]);
        return deviceIDArr[index];
    }
    public static void GetReadWriteSpeed(String[] deviceIDArr, int index) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "runas /user:administrator cmd /savecred");
        // Process process = processBuilder.start();
    }

    public static void MovePropertiesFile(int index) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "move driverproperties.txt " + GetDeviceID(deviceIDArr, index) + "/" );
        processBuilder.start();
        
        ProcessBuilder processBuilder1 = new ProcessBuilder();
        processBuilder1.command("cmd.exe", "/c", "xcopy Autorun.inf " + GetDeviceID(deviceIDArr, index) + " /h /y" );
        processBuilder1.start();

        ProcessBuilder processBuilder2 = new ProcessBuilder();
        processBuilder2.command("cmd.exe", "/c", "xcopy icomit.ico " + GetDeviceID(deviceIDArr, index) + " /h /y" );
        processBuilder2.start();
    }

    public static void main(Process[] process, String[] processName) throws IOException {
        index = GetDeviceCount();
        FileWriter fw = new FileWriter("driverproperties.txt");
        fw.write("i-SECURE" + "\n\n");
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
            fw.write(processName[i] + selectedLines.get(index) + "\n");
            // GetReadWriteSpeed(deviceIDArr, index);
        }
        fw.write("\ngithub.com/i-comit/icomit-usbparser");
        fw.close();
        MovePropertiesFile(index);
    }
}

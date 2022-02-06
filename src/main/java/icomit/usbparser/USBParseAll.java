package icomit.usbparser;
import java.io.*;
import java.util.ArrayList;

public class USBParseAll {

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
        // Scanner myObj = new Scanner(System.in);
        // String input1;

        // System.out.println(deviceCountArr.length + " USB devices found " + Arrays.toString(deviceIDArr) + " Enter index of USB (0-" + (deviceCountArr.length -1)+ "):"); 
        // input1 = myObj.nextLine();   
        // myObj.close();

        int dcount = deviceCountArr.length -1;
        return dcount; 
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
    
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        // ArrayList<String> selectedLines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            System.out.println(line); 
            // if(line.matches("^[0-9].*")){
            //     line = line.replaceAll("\\s", "");
            //     selectedLines.add(line);
            // }

        }
    }

    public static void main(Process[] process, String[] processName, int mainindex) throws IOException {
        GetDeviceCount();
        for (int i = 0; i < 1; i++){
            FileWriter fw = new FileWriter("driverproperties.txt");
            fw.write("i-SECURE" + "\n\n");
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
                fw.write(processName[j] + selectedLines.get(mainindex) + "\n");
                // GetReadWriteSpeed(deviceIDArr, index);
            }
            fw.write("\nOSS -> github.com/i-comit/icomit-usbparser");
            fw.close();
            MovePropertiesFile(mainindex);
        }



        // break;
    }
}
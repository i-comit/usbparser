package icomit.usbparser;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiProp {
    public static void GetMultipleVolumes(Process[] process, String[] processName) throws IOException {
        int index = GetDeviceCount();
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
            System.out.println(processName[i] + selectedLines);
            fw.write(processName[i] + selectedLines.get(index) + "\n");
        }
        fw.close();

        // try {
        //     // Move file from source to target using the defined
        //     // configuration (REPLACE_EXISTING)
        //     Files.move(Paths.get("C:\\Users\\User1\\github\\icomit-usbparser\\driverproperties.txt"), Paths.get("A:\\New\\"), ATOMIC_MOVE);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }


    public static int GetDeviceCount() throws IOException {
        Process pr = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Index");
        
        int index = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        ArrayList<String> deviceCount = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            if(line.matches("^[0-9].*")){
                line = line.replaceAll("\\s", "");
                deviceCount.add(line);

            }
        }
        System.out.println("deviceCount" + deviceCount);
        Scanner myObj = new Scanner(System.in);
        String userName;
        
        // Enter username and press Enter
        System.out.println("Enter index of USB (0-2)"); 
        userName = myObj.nextLine();   
        myObj.close();
        index = Integer.parseInt(userName);
  
        return index; 

    }
}

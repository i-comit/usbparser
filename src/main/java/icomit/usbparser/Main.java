/**
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{     
    public static void main( String[] args ) throws IOException
    {

        String[] cmds = {
            "cmd.exe",
            "vol",
            "c:"
        };

        Process pr = Runtime.getRuntime().exec("wmic logicaldisk where drivetype=2 get DeviceID, VolumeName, Description");

        Process getDeviceIndex = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Index");

        Process getSize = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Size");
        Process getTotalSectors = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalSectors");
        Process getBytesPerSector = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get BytesPerSector");
        Process getSectorsPerTrack = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get SectorsPerTrack");

        Process getTotalHeads = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Totalheads");
        Process getTotalCylinders = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalCylinders");

        Process getTotalTracks = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalTracks");
        Process getTracksPerCylinder = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TracksPerCylinder");
        Process getSignature = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Signature");


        Process[] getAllProperties = {getSize, getTotalSectors, getBytesPerSector, getSectorsPerTrack, getTotalHeads, getTotalCylinders, getTotalTracks, getTracksPerCylinder, getSignature};
        String[]  getAllNames = {"Total Size: ", "Total Sectors: ", "Bytes per Sector: ", "SectorsPerTrack: ", "Total Heads: ", "Total Cylinders: ", "Total Tracks: ", "Tracks Per Cylinder: ", "Signature: "};


        Process pr2 = Runtime.getRuntime().exec("wmic logicaldisk where drivetype=2 get Size");
        Process pr3 = Runtime.getRuntime().exec("wmic logical where drivetype=2 get freeSpace");
        Process sizeQuery = Runtime.getRuntime().exec("wmic logicaldisk where DeviceID='E:' get Size");

  
        MultiProp.GetMultipleVolumes(getAllProperties, getAllNames);
        // GetDevice(sizeQuery);

    }

    public static void PrintResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);

        }
    }

    public static void GetDevice(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        int filenum = 0;
        Boolean runOnce = false;
        // Long volLong = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); 
            if(line.startsWith("1") && !runOnce){
                // line2 = line;
                System.out.println("volume line " + line);            
                try {
                    FileWriter myWriter = new FileWriter(String.format("./driveVolume_%d.txt", filenum));
                    myWriter.write(line);
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                    } 
                    catch (IOException e){
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                } 
            }
        }
        GetVolume(line, filenum);

    }
    public static long GetVolume(String line, int filenum) throws IOException {
        Path fileName = Paths.get(String.format("./driveVolume_%d.txt", filenum));
	    String actual = Files.readString(fileName);
	    System.out.println("actual" + actual);

        String s = actual;
        s = s.replaceAll("\\s", "");

        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(Character.isWhitespace(s.charAt(i))) count++;
        }
        System.out.println("Whitespace " + count);

        long ls = Long.valueOf(s);
        System.out.println("ls" + ls);
        return ls;
    }

}

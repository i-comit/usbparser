package usbparser.linux;
import java.io.IOException;

//Thread class for parsing

public class USBParse0T implements Runnable  
{   
    public int threadIterator; 
    public void run()  
    {    
        try {
        String[] cmd = {"/bin/bash","-c","echo password| sudo -S ls"};

            Process getSize = Runtime.getRuntime().exec("sudo sgdisk --print /dev/sdb1");
            // Process getTotalSectors = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalSectors");
            // Process getBytesPerSector = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get BytesPerSector");
            // Process getSectorsPerTrack = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get SectorsPerTrack");
    
            // Process getTotalHeads = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Totalheads");
            // Process getTotalCylinders = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalCylinders");
    
            // Process getTotalTracks = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TotalTracks");
            // Process getTracksPerCylinder = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get TracksPerCylinder");
            // Process getSignature = Runtime.getRuntime().exec("wmic diskdrive where InterfaceType='USB' get Signature");
    
            // Process[] getAllProperties = {getSize, getTotalSectors, getBytesPerSector, getSectorsPerTrack, getTotalHeads, getTotalCylinders, getTotalTracks, getTracksPerCylinder, getSignature};
            // String[]  getAllNames = {"Total Size: ", "Total Sectors: ", "Bytes per Sector: ", "SectorsPerTrack: ", "Total Heads: ", "Total Cylinders: ", "Total Tracks: ", "Tracks Per Cylinder: ", "Signature: "};
            Process[] getAllProperties = {getSize};
            String[] getAllNames = {"Total Size: "};
            USBParse0.main(getAllProperties, getAllNames, threadIterator);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        System.out.println("USB Device " + USBParse0.staticDeviceID +" Parsed.\n");
    }     
}   
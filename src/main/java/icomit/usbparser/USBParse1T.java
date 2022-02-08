package icomit.usbparser;
import java.io.IOException;

//Thread class for parsing

public class USBParse1T implements Runnable  
{   
    public void run()  
    {    
        try {
            Process getSize = Runtime.getRuntime().exec("wmic logicaldisk where drivetype=2 get Size");
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
            USBParse1.main(getAllProperties, getAllNames);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        System.out.println("USB Device " + USBParse1.staticDeviceID + " Parsed.\n");
    }   
}   
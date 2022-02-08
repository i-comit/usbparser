package usbparser.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

//Thread class for parsing

public class USBParse0T implements Runnable  
{   
    public int threadIterator; 
    public void run()  
    {    
        //grep finds all mount points starting with sdb (USBs)
        //Because sgdisk apparently doesn't work.
        String[] cmd1 = new String[] {"bash", "-c", " df -Th | grep sdb"};
        String[] cmd2 = new String[] {"bash", "-c", " lsblk | grep sdb"};
        try {
            Process proc1 = new ProcessBuilder(cmd1).start();
            PrintResults(proc1);
            System.out.println("");
            Process proc2 = new ProcessBuilder(cmd2).start();
            PrintResults(proc2);
        } catch (IOException ex) {
            Logger.getLogger(USBParse0T.class.getName()).log(Level.ALL, null, ex);
        }
	
    }    
    public static void PrintResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

}   
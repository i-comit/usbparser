# i-COMIT | USB PARSER
Terminal based app which parses USB devices and returns hardware information to the USB device via wmic. <br>


### This is a Java application, so you will require Java Runtime Environment 8. <br>


Intended as a tool for logging device information for a patent pending USB device.
You will be prompted to enter a number which will automate the parsing process via terminal.<br>

## It will parse: 
    * Date of Parse
    * Usable memory in Gigabytes
    * Total Byte Size
    * Bytes Per Sector
    * Bytes Per Track
    * and a few other hardware info
    

Multithreading is implemented, so each USB is parsed on a separate thread. <br>

### Only works on Windows at the moment. Linux version is in development<br>

Will also change the drive icon, and a user can easily change the image to one of their own,
granted it is an .ico filed named Driver.ico<br>

A GUI is probably not necessary, but I would like to develop one eventually. <br>

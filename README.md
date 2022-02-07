# icomit-usbparser

Written in Java. You will require Java Runtime Environment 8. <br>
Parses USB devices and returns hardware information via wmic. <br>


You can configure it to parse a specific USB or all detected ones. Default is the latter. <br>
Multithreading is implemented, so each USB is parsed on a separate thread. <br>

Only works on Windows at the moment. <br>
Will also change the drive icon, working on a user setting to disable it or put their own image in (this will not work if the drive is formatted in FAT)<br>
Maybe a GUI even. <br>





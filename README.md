# i-COMIT | USB PARSER | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
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

## Installation :
<br><br>
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
1. Clone or fork this repository
2. Click on runjar.bat

<br><br>
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
1. Only Option 1 (parse specific drive) works at the moment.
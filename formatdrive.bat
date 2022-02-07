@if (@CodeSection == @Batch) @then
@ECHO OFF
SETLOCAL

rem Use %SendKeys% to send keys to the keyboard buffer
set SendKeys=CScript //nologo //E:JScript "%~F0"

rem Start the other program in the same Window
@REM start "" /B cmd

%SendKeys% "format E: /q /fs:NTFS /p:1 /v: {ENTER}"

%SendKeys% "{ENTER}"

goto :EOF

@end

// JScript section

var WshShell = WScript.CreateObject("WScript.Shell");
WshShell.SendKeys(WScript.Arguments(0));
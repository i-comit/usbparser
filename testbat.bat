@REM @if (@CodeSection == @Batch) @then
@REM @ECHO OFF
@REM SETLOCAL

@REM rem Use %SendKeys% to send keys to the keyboard buffer
@REM set SendKeys=CScript //nologo //E:JScript "%~F0"

@REM rem Start the other program in the same Window
@REM start /b ""

@REM @REM %SendKeys% "format D: /v:i-SECURE /q /fs:NTFS /p:1 {ENTER}"
@REM %SendKeys% "{ENTER}"

@REM %SendKeys% "exit"

@REM %SendKeys% "{ENTER}"

@REM @end

@REM // JScript section

@REM var WshShell = WScript.CreateObject("WScript.Shell");
@REM WshShell.SendKeys(WScript.Arguments(0));

start "" "icomitascii.txt"
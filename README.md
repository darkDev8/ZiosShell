# ZiosShell
Desktop Linux and UNIX  bash shell

(Simple shell that works with linux shell)
The commands are similar to bash shell but some of them are easier.

The project is written with intellij Idea.
The manual command is in the ide, You can use it to compile the software.

 ```
 First before even compile go to this address in the file "xuggle-xuggler-5.4.txt", then download the first 35MB
 jar file and add to the project.
 This is necessary, otherwise it gives error even may in compile time
 ```

 ## What's new
 
 * Add cd command.
 * Add cd.. command.
 * Add new man pages.
 * Add rn command.
 * Add get command.
 * Shows build number(starting)
 * Add tan command.
 * Add taf command.
 * Add ping command.
 * Add mkdir command.
 * Add clear command.
 * More man pages.
 * New futures in ls command.
 * Database future
 * New methods
 
 ## Bug fixes
 
 * Fixed change directory command.
 * Fixed cat command printing other directory file.
 * Increase performance command.
 * New line between hidden files and normal files(directories)
 * Info command showed previous directory information
 * Problem with " / " directory when using cd.. to get back
 * Negative numbers with clear and ping command.
 
 ## Kown bugs
 
 * Video length(info command).
 * Get file size(readable).
 * Used ram(os command).

##  Classes

## OSListener (interface)

 Operating system and user methods.
 Implementation(RootOS class).

 ## 1. Shell
Main class the engine of software.
All the commands executes here.

## 2. ManPage
Shows the man page of each command.
 
## 3. Internet
Give access to download and get information about files and pages from intrnet.
 
## 4. Software
The intraction between the engine(shell) and user.
The core based class
 
## 5. FileSYS
The base of file system in the shell.
Intracting with files and manipulating them.
 
## 6. DirectorySYS
The base of directory system in the shell.
Intracting with directories and manipulating them.
 
## 7. Audio
Works with audio files.
 
## 8. Image
Works with image files.
 
## 9. Video
Works with video files.
 
## 10. Documents
Works with document files.
 
## 11. PDF
Works with pdf files.
 
## 12. Unkown
Show the file type by it's extension.

## 13. RootOS
Give access to operating system and user information.
 
 ## 14. Strings
 Gives external option to work with strings.
 
  ## 15. ToolBox
 Brings external functionality to output shell.
 Some of the methods used in other way.
 
 ## 16. DatabaseListener (interface)
 The database repository
 
 ## 17. Database
 Tools to work with database.

## Pay attention
 ```
 This source code will update every 5 days or less.
 This project is completely open source with no license.
 Everyone can use it and enjoy soucre code. :)
```

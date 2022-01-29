Readme File:

----------------------------------------------------------------------------------------------------------------------
Software Requirements :

* Java JDK : https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
* Maven : http://maven.apache.org/download.cgi#
* Selenium : https://www.selenium.dev/downloads/
----------------------------------------------------------------------------------------------------------------------
Dependencies : 

* Apache POI -> artifactID : poi-ooxml, Version : 3.9 
* Extent Report -> artifactID : extentreports, Version : 4.0.9
* testNG -> artifactID : testng, Version : 7.4.0
* Selenium -> artifactID : selenium-java, Version : 3.141.59
* WebDriverManager -> artifactID : webdrivermanager, Version : 4.4.0
----------------------------------------------------------------------------------------------------------------------
Plugins :

* Maven-surefire -> artifactID : maven-surefire-plugin, Version : 3.0.0-M5
* Maven-compiler -> artifactID : maven-compiler-plugin, Version : 1.8
----------------------------------------------------------------------------------------------------------------------
Steps to Use KeyWord Driven Framework :

1> Configure excel sheet :
	* Excel sheet is provided inside KeyWordMaven folder with the name of "DataEnginemacro"
	* Open Excel file and open sheet "ConfigureSheet".
		--> In configureSheet mention all the pre configuration information like Browser name, Browser Head,
		    URL, Group name etc.
		--> After that save the excel sheet and move to next sheet.   
	
	* Open Sheet "MasterTestSheet" 
		--> In this sheet you have to mention TestCaseName and whether that needs to be executed or not.
		--> In the group column mention the Group name. 
		--> In order to give muliple group into same TestCaseName, separate groupname with comma.
 
	* Open Sheet "testCaseSheet"
		--> In this mention all the test case Information like TestCaseName, TestID, Description, Loator, LocatorValue etc.
		--> Make sure no cell is empty and if don't want to fill anything in the cell just fill it with NA.
		--> For Action column dropdown is available with the list of all the action, make sure to choose from that list.

	* Open Sheet "Manual"
		--> Action description and the parameters that we need to access perticular action is mentioned in the manual.
		--> For more information about the structure of action keywords, you can check Java doc.
		--> for accessing java doc, Open KeyWordMaven folder inside that open doc folder and then open file index.html.

2> Run the batch file named "testrun".

3> For getting the extent report run the batch file named "OpenExtentReport".

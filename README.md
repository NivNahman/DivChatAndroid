# DivChatAndroid
Third assignment of Advanced Programming 2 course at BIU.

Our Server localhost:7261

In this project we have a database that will contain our data for the users, contacts and messages.
To install the maria-db please follow the next steps:
<li>
please download it from here: https://mariadb.org/
</li>
 Open the package manager (PM) console and install:
<li>
"Install-Package Pomelo.EntityFrameworkCore.MySql -Version 6.0.1"
(without the quotation marks)
</li>
Then, install 
<li>
"Install-Package Microsoft.EntityFrameworkcore.Tools -version 6.0.1"
</li>
Finally create the database:
	1) First create a migration by typing: "add-migration InitialMigration" 
	   in the PM console. 
	2) Then apply the migration using: "update-database"

<u>Next:</u> please install the firebase 
if you have problems you can watch how to connect the client with the firebase here: 
https://objectstorage.eu-frankfurt-1.oraclecloud.com/n/fry9ihsciykj/b/video_bucket/o/f6/f623648d50da068a280c7a2032265d37b9bafeea.mp4

# DivChatAndroid
Third assignment of Advanced Programming 2 course at BIU.

Our Server localhost:7261

In this project we have a database that will contain our data for the users, contacts and messaging.
To install the maria-db data base please follow the next steps:
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


Dependecies:
React-bootstrap
React-router-dom

How to run it? Please first install the following packages:
npm install react-bootstrap
npm install react-router-dom
npm init -y
npm install @microsoft/signalr

Run Both RankPage, WebApiServer
Start by using npm start.
Have Fun :)

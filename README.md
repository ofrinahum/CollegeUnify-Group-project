HI!!

Here are some instructions for you!

HERE I'M ASSUMING THAT YOU ARE USING VSCODE AND ALREADY HAVE A JAVA COMPILER INSTALLED, IF YOU DON'T PLEASE DO THAT FIRST

GITHUB INSTRUCTIONS:

To start off, in your VSCode you'll need to install the github pull requests extension

Next, you'll need to download GitDesktop: https://desktop.github.com/download/ 
The Windows download is obvious, to download for MacOS just look down to the right a little and you'll find it
Once that's downloaded and set up, github desktop allows you to clone the repository (URL) and open the code directly in VSCode (which is oh so convenient).

Once you have access to the code, I want you to navigate through the folders project/src/main/java/com/CollegeUnify/project (I know its an insane amount of folders), 
in here you'll find all the main code, including a file called TestFile.txt. Make any change to this file that you want.
Changing it should now create a "1" on the little branch image on the left of VSCode. Go there, you'll see a blue commit button, and a box above it that says message.
Write a random message about the text you changed, and commit it. 

That way we can ensure that you can properly commit and sync any code changes you or someone else makes!

MYSQL INSTRUCTIONS AND EXPLANATION:

Our web application runs registration and login functionality using a backend database. I learned way too late, that apparently most web development softwares have this database connection built in.
But springboot does not! So, what I was working on, was connecting our springboot code to a remote online database (AWS). This database can be accessed locally on your device using mySQL workbench.

I don't believe you NEED to download this, the code should connect to the server either way. But if you WANT to see how the database functions and what happens when someone registers, feel free to do the following:

Download mySQL workbench https://dev.mysql.com/downloads/workbench/
Set up workbench
Create a new connection in mySQL by pressing the +
Connection name can be anything
Connection method should be Standard(TCP/IP)
Under hostname copy and paste the following: collegeunifyid.cv0qio6wcijz.us-east-1.rds.amazonaws.com
Port should automatically be 3306, if it isn't change it to 3306
username: admin
Where password is, press Store in vault, password: 4CJpassw0rdOS4#Press ok 
To view registered users, expand collegeunifydb, expand tables, expand users, expand columns, right click on any of the given columns and press Select Rows

To start the only registered user is john doe

CODE OVERVIEW:

Alright! Now to the relatively interesting part. 
I can confidently say, I don't exactly know how a lot of the code I wrote runs.
I know that we need all of the java code to make the registration, login, and logout functions work, but I'm not entirely sure... how they work.
BUT I CAN SAY THIS:

1. Don't touch SecurityConfiguration.java. Just don't. Unless you KNOW WHAT YOU'RE DOING, changing one tiny thing very well may throw off everything.
   basically, springboot's security feature has an automatic override page for the login. SecurityConfiguration.java changed that page to our own customized one connected to the database.
   changing anything in this code, may cause that override page to come back, in which case as I said, I don't know why the code functions, it just does, so I (probably) won't be able to fix it.

2. You run the app from ProjectApplication.java. To see what the code does, go into a browser and type http://localhost:8080, and whatever page you want to see (i.e. /registration or /login). 
   This will run the web application! You can actually interact with it, press buttons, register yourself, login, logout. Any changes that you make to the frontend side (html), you WILL see here!

3. Try to familiarize yourself with the three html files we currently have, namely: index.html, login.html, and registration.html. These forms do currently work and are active! For the time being
   index.html is our menu screen, so feel free to start desigining the home page!

4. Role.java and User.java are integral to how the whole thing runs! They're a big part of what our database is storing. I have a feeling that in order to create the scheduling input system, we'll likely
   be needing some similar classes. Feel free to navigate throughout the code and just see how and when Roles and Users are being used. 

5. Youtube and chatgpt are your best friends. I have no idea how to make any of these things, but a lot of people (or AI) very well may! All of this code was written through following a lot of youtube tutorials.
   The main one being this series: https://www.youtube.com/watch?v=hPCynjmapSo (All of the database functionality was also done using youtube videos, you just don't need to know how that works)

If you have any other questions or need any help feel free to reach out!







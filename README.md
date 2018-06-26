# LoginLogoutAudit

* Login Page: 

login page = http://localhost:8080/
home page = http://localhost:8080/LoginServlet
On AWS Login Page = http://mandy.us-east-2.elasticbeanstalk.com/
 
 * MongoDB Properties:

Database used= MongoDB

valid Username= admin

password= admin

some more valid sample users {username,password}

user1,user1

user2,user2

user3,user3

user4,user4

user5,user5

mongoDb URI with credentials are in DAO/MongoClientPool.java:29
you can edit this uri to use your hosted mongoDB.
The mongoDB is hosted on google compute engine and it's very limited, So, I recommend you to use your own MongoDB
And Run the Below Script:

mongo

use users

db.createCollection("userCredentials")

db.userCredentials.createIndex("userId", { unique: true });

db.userCredentials.insert({"userId":"admin","password":"admin"})

db.userCredentials.insert({"userId":"user1","password":"user1"})

db.userCredentials.insert({"userId":"user2","password":"user2"})

db.userCredentials.insert({"userId":"user3","password":"user3"})

db.userCredentials.insert({"userId":"user4","password":"user4"})

db.userCredentials.insert({"userId":"user5","password":"user5"})

db.createCollection("userAudits")

db.userAudits.createIndex({"userId":1})


* session validity is of 30 Minutes
* preferably use tomcat as deployment server.

Steps to Deploy the project Locally

1. take clone
2. make artifact from module
3. build the project
4. make run configuration and select server as tomcat

Steps to Deploy the project on AWS
1. make a .war file by building artifact MyProject_war.war
2. aws home > services > Elastic Beanstalk >  Create New Application > Select environment tier (Web server environment)
3. Platform > Preconfigured platform select tomcat from dropdown
4. click create Environment.
5. now you can select the recently generated .war file and upload it;

currently the application is hosted on http://mandy.us-east-2.elasticbeanstalk.com/LoginSuccess.jsp

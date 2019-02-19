# 1. 	Starting the application
## 1.1. 	Backend
1) To start the backend part of the application in development mode:<br/>
open command prompt where gradlew.bat is located and type `gradlew.bat bootRun`<br/>

## 1.2. 	Frontend<br/>
1) 	Install node.js (latest)<br/>
2) 	Install project dependencies:<br/>
open command prompt in \src\main\frontend\react-front and type `npm install`<br/>
3) 	Start the project in development<br/>
open command prompt in \src\main\frontend\react-front and type `npm start`<br/>
Node will start the application, the application should open in your browser or check the console<br/>

## 1.3. Database
To view the database, go to http://localhost:8080/h2-console/ <br/>
JDBC URL: jdbc:h2:mem:testdb

## 1.4. Rest endpoints
Check out at http://localhost:8080/api

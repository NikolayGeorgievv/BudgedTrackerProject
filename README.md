# BudgedTrackerProject
This is a Budged tracking web application.
It uses Java/Spring Boot, MYSQL for database and Bootstrap/JS for the front end.

## How to use
Clone this repository, run it and open the app in the browser.
Register and Log in.
You are now able to track your finances by creating accounts, expenses and even goals.
For more detailed information check "FAQs" or "How to use" in the application.(Located on the nav-bar)

## Features
User is able to create different accounts(based on his membership plan)
User is able to create expenses. Each expense contains different information that the user is able to set himself, like assigning an account , date, period(e.g. weekly, monthly, custom).
User is able to create goals. You can track your goal via progress-bar and check your completed goals and the information related to them.

## More details
The app has both front-end and back-end user input validation with spring security and thymeleaf security.

The app has implementation for rest-api email validation(spring email validation is also used).

The app has roles(ADMIN, USER). The very first registered user will be ADMIN also. The ADMIN can controll the roles in-app and view various information about both users and application itself in terms of logs stored in AWS cloud.

Please note that there is no hard-coded data, api keys and paths due to safety reasons. What you have to configure before starting:

1. https://apilayer.com/marketplace/email_verification-api //Email validation:
 - schema=${https}
 - host=${://api.apilayer.com/email_verification/}
 - apiKey=${Your API Key goes here}
 - In order to enable the api, you must set the boolean ENABLED in "RegisterUserDTO to true.
  
2. https://eu-west-2.console.aws.amazon.com/  //AWS cloud storage:
 + AWS_ACCESS_KEY_ID as environment variable
 + AWS_SECRET_ACCESS_KEY as environment variable
 + bucket.name=${BUCKET_NAME} //This is the name of the bucket
 + key.name=${KEY_NAME} //This is the name of the key(log file)
 + DOWNLOAD_DIRECTORY as environment variable  //This is the directory where you would like the .log files to be downloaded.
 + my.app.log.path=${MY_APP_LOG_PATH}  //This is usually the root directory of the application. This is where the myApp.log file will be created.

3. rememberMe=${remember.Me}  //This is the key for the remember me implementation.
4. JDBC: ${username} and ${password} to access your local mysql server.
  
 
 


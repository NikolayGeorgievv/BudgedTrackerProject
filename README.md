# BudgedTrackerProject
This is a Budged tracking web application.
It uses Java/Spring Boot, MYSQL for database and Bootstrap/JS for the front end.

##How to use
Clone this repository, run it and the app in the browser.
Register and Log in.
You are now able to track your finances by creating accounts, expenses and even goals.
For more detailed information check "FAQs" or "How to use" in the application.(Located on the nav-bar)

##Features
User is able to create different accounts(based on his membership plan)
User is able to create expenses. Each expense contains different information that the user is able to set himself, like assigning an account , date, period(e.g. weekly, monthly, custom).
User is able to create goals. You can track your goal via progress-bar and check your completed goals and the information related to them.

##More details
The app has both front-end and back-end user input validation with spring security and thymeleaf security.
The app has implementation for rest-api email validation(spring email validation is also used).
The app has roles(ADMIN, USER). The very first registered user will be ADMIN also. The ADMIN can controll the roles in-app and view various information about both users and application itself.
 


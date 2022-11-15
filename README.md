# job4j_cinema

This project is a web application - simple cinema implementation.
User can:
- Sign up, log in, log out and change account
- Choose movies and seats
- View purchased tickets
- View and change personal account data. 
If two or more users try to buy a same ticket on a same session then an error page will be shown.
If there are no available seats, user will be prompted to select another session.

# Used technologies

Implemented with:
<ul>
 <li>JDK 17</li>
 <li>Maven 3.8.5</li>
 <li>Spring Boot 2.5.2</li>
 <li>JDBC 4</li>
 <li>Bootstrap 4.4.1</li>
 <li>Thymeleaf</li>
 <li>PostgreSQL 42.2.16</li>
 <li>Liquibase 3.6.2</li>
</ul>

# Environment requirements

<ul>
 <li>Create db "cinema". Login: postgres, password: password</li>
 <li>Create .jar file via maven command "mvn package"</li>
 <li>Go to the Target folder and check the presence of "job4j_cinema-1.0-SNAPSHOT.jar" file</li>
 <li>Open the command line, go to the Target folder</li>
 <li>Run this file through "java -jar job4j_cinema-1.0-SNAPSHOT.jar" command</li>
 <li>Then go to the http://localhost:8080/index page</li>


# Screenshots
<li> Login page:
  ![Login page](src/main/resources/app_screenshots/1login_page.png) </li>
<li> User adding:
  ![User adding](src/main/resources/app_screenshots/2add_user.png) </li>
<li> Successful user adding:
  ![Successful user adding](src/main/resources/app_screenshots/3success.png) </li>
<li> Failed user adding:
  ![Failed user adding](src/main/resources/app_screenshots/4fail_sign_up.png) </li>
<li> Failed log in:
  ![Failed log in](src/main/resources/app_screenshots/5fail_true_incorrect_login.png) </li>
<li> Index page:
  ![Index page](src/main/resources/app_screenshots/6index.png) </li>
<li> Sessions (movies) page:
  ![Sessions page](src/main/resources/app_screenshots/7sessions.png) </li>
<li> Ticket adding form:
  ![Ticket adding form](src/main/resources/app_screenshots/8add_ticket.png) </li>
<li> Ticket adding form (list):
  ![Ticket adding form list](src/main/resources/app_screenshots/9add_ticket_list.png) </li>
<li> Ordered ticket:
  ![Ordered ticket](src/main/resources/app_screenshots/10ordered_ticket.png) </li>
<li> User tickets:
  ![User tickets](src/main/resources/app_screenshots/11tickets.png) </li>
<li> Seat occupied:
  ![Seat occupied](src/main/resources/app_screenshots/12order_fail.png) </li>
<li> All seats sold:
  ![All seats sold](src/main/resources/app_screenshots/13all_seats_sold.png) </li>
<li> Profile page:
  ![Profile page](src/main/resources/app_screenshots/14profile.png) </li>
<li> Profile updating page:
  ![Profile updating page](src/main/resources/app_screenshots/15update_profile.png) </li>
</ul>

Contact me: itfedorovsa@gmail.com


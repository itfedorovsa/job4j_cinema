# job4j_cinema

# About

This project is a web application - simple cinema implementation.  
The user can:

- Sign up, log in, log out and change account
- Choose movies and seats
- View tickets purchased by user
- View and change personal account data.
  Оnly free seats are available for selection.
  If two or more users try to buy a same ticket on a same session then an error page will be shown.
  If there are no available seats, user will be prompted to select another session.

***

# Used technologies

<ul>
 <li>JDK 17</li>
 <li>Maven 3.8.5</li>
 <li>Spring Boot 2.5.2</li>
 <li>Spring Web 2.5.2</li>
 <li>JDBC 4</li>
 <li>Bootstrap 4.4.1</li>
 <li>Thymeleaf</li>
 <li>PostgreSQL 42.2.27</li>
 <li>Liquibase 3.6.2</li>
 <li>JUnit 4</li>
 <li>H2 2.1.214</li>
</ul>

***

# Environment requirements

<ul>
 <li>Create db "cinema". Login: postgres, password: password</li>
 <li>Create .jar file via maven command "mvn package"</li>
 <li>Go to the Target folder and check the presence of "job4j_cinema-1.0-SNAPSHOT.jar" file</li>
 <li>Open the command line, go to the Target folder</li>
 <li>Run this file through "java -jar job4j_cinema-1.0-SNAPSHOT.jar" command</li>
 <li>Go to the http://localhost:8080/index page</li>
</ul>

***

# Screenshots

- Login page:
  ![Login page](src/main/resources/app_screenshots/1login_page.png)
- User adding:
  ![User adding](src/main/resources/app_screenshots/2add_user.png)
- Successful user adding:
  ![Successful user adding](src/main/resources/app_screenshots/3success.png)
- Failed user adding:
  ![Failed user adding](src/main/resources/app_screenshots/4fail_sign_up.png)
- Failed log in:
  ![Failed log in](src/main/resources/app_screenshots/5fail_true_incorrect_login.png)
- Index page:
  ![Index page](src/main/resources/app_screenshots/6index.png)
- Sessions (movies) page:
  ![Sessions page](src/main/resources/app_screenshots/7sessions.png)
- Ticket adding form:
  ![Ticket adding form](src/main/resources/app_screenshots/8add_ticket.png)
- Ticket adding form (list):
  ![Ticket adding form list](src/main/resources/app_screenshots/9add_ticket_list.png)
- Ordered ticket:
  ![Ordered ticket](src/main/resources/app_screenshots/10ordered_ticket.png)
- User tickets:
  ![User tickets](src/main/resources/app_screenshots/11tickets.png)
- Seat occupied:
  ![Seat occupied](src/main/resources/app_screenshots/12order_fail.png)
- All seats sold:
  ![All seats sold](src/main/resources/app_screenshots/13all_seats_sold.png)
- Profile page:
  ![Profile page](src/main/resources/app_screenshots/14profile.png)
- Profile updating page:
  ![Profile updating page](src/main/resources/app_screenshots/15update_profile.png)

***

# Todo list

- Add ticket booking logic
- Cover the code to unit tests (add more tests)

***

# Contact

Contact me if you have any questions: itfedorovsa@gmail.com


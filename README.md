# How to use single plane balancing application

Single plane balancing application is a tool used for balancing rotating
machinery by adding or removing weights on a single correction plane.
It helps reduce vibrations caused by unbalanced rotating components,
improving machine performance, efficiency, and extending component life.

<details open>
<summary><b>Technology stack</b></summary>

1. Framework: Spring boot
2. Build: Gradle
3. ORM: Hibernate
4. DB: postgres
5. Containers: Docker
6. Swagger: springdoc-openapi
7. Front: thymeleaf
8. Auth: Spring Security
</details>

<details open>
<summary><b>Run guide</b></summary>

1. docker build -t balancingdb . <br>
2. docker run --name balancingdb -p 5432:5432 -d balancingdb
3. frontend address by default is http://localhost:8080/
4. backend swagger is available at http://localhost:8080/swagger-ui/index.html
5. no default pre-cofigurated users

Optional to check DB:

- docker exec -it balancingdb bash <br>
- psql -l <br>
- psql -d balancing <br>
- SELECT * FROM balancing; <br>
- INSERT INTO users (username, role, roles, password)
  VALUES ('admin', 'ADMIN', 'ADMIN', 'insert your bcrypt with strength by 12');
</details>


<details open>
<summary><b>User registration</b></summary>

Before using the application, you need to register by providing
the following information (http://localhost:8080/register) :

- username
- password
</details>

<details open>
<summary><b>Instructions for balancing an unit</b></summary>

1. Fill in the fields in the table:
- *Mode*: Type of generator/motor load.
- *Magnitude and phase of vibration*: Value and phase of vibration from peak to peak.
- *Magnitude and phase of weight*: Value and phase of weight installed on the shaft.
- *Reference number*: Measurement number as a reference to the previous state of the machine.
- *State*: True/false switch. It is used to take into account in the averaging of values when calculating the total
  load.

2. *Perform measurements*: Take vibration readings with the machine running at the desired speed. Record the vibration
   magnitude and phase for each measurement point.

3. *Enter data*: Input the measured vibration data, along with the mode, weight information, and reference number into
   the application's interface.

4. *Run calculations*: The application will analyze the data and calculate the required weight and position to balance
   the machine on the single correction plane.

5. *Apply corrections*: Follow the application's recommendations to add or remove the specified weight at the
   calculated position on the correction plane.

6. *Verify results*: After applying the corrections, run the machine again and take new vibration measurements to
   verify that the vibrations have been reduced to acceptable levels.

7. *Iterate if needed*: If the vibrations are still outside the desired limits, repeat the process with the new
   measurements until satisfactory balance is achieved.

By following these steps and using the single plane balancing application, you can effectively balance your rotating
machinery, reducing vibrations and extending its operational life.
</details>




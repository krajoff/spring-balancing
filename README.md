# Multiplanes balancing application

A multi-plane balancing application is a tool used for balancing rotating machinery by adding or removing weights on 
multiple correction planes. It helps reduce vibrations caused by unbalanced rotating components across different planes, 
improving machine performance, efficiency, and extending the life of various components.

<details open>
<summary><b>UML</b></summary>

<div style="text-align: center;">
  <img src="pictures/User_Vibration.drawio.png" 
   alt="UML" style="width: 50%;">
</div>

</details>

<details open>
<summary><b>Technology stack</b></summary>

1. Framework: Spring boot
2. Build: Gradle
3. ORM: Hibernate
4. DB: Postgres
5. Containers: Docker
6. Swagger: springdoc-openapi
7. Front: thymeleaf
8. Auth: Spring Security
</details>

<details open>
<summary><b>Run guide</b></summary>

1. docker-compose up
2. frontend address by default is http://localhost:8080/
3. backend swagger is available at http://localhost:8080/swagger-ui/index.html
4. pgadmin is available at http://localhost:5050

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

<details open>
<summary><b>Deploy at fly.io</b></summary>

According to https://renanfranca.github.io/deploy-jhipster-monolithic-angularjs-and-spring-boot-at-fly.io.html,
https://medium.com/@vergil333/deploy-spring-boot-to-fly-iof-d54d5ca05243 and
https://coder.com/blog/remote-developer-environments-on-fly-io

1. Install fly: https://fly.io/docs/hands-on/install-flyctl/.
   PowerShell script: pwsh -Command "iwr https://fly.io/install.ps1 -useb | iex"
2. Run signup script: fly auth signup.
3. Run signing script: fly auth login.
4. Run script: fly apps create --name balancing-app
5. Run script to create postgres database:
   fly postgres create --name balancing-postgres. After the postgres app was
   created you will receive an output:
   - username: postgres
   - password: password
   - hostname: balancing-postgres.internal
   - proxy Port: 5432
   - postgres Port: 5433
   - postgres://postgres:password@balancing-postgres.flycast:5432
6. Run script: fly postgres connect -a balancing-postgres
7. Run script: fly scale memory 1024 --app balancing-app
8. Run script: /gradlew build
9. Run script: docker build -t krajoffon/spring-balancing .
10. Run script: fly deploy
11. Run script: fly apps list -> you will see "balancing-app" and "balancing-postgres"
</details>




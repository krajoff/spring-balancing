# Getting Started

### Run dockerfile
docker build -t balancingdb . <br>
docker run --name balancingdb -p 5432:5432 -d balancingdb

### To check (optional) 
docker exec -it balancingdb bash <br>
psql -l <br>
psql -d balancing <br>
SELECT * FROM balancing; <br>

### To add admin (optional)
INSERT INTO users (username, role, roles, password)
VALUES ('admin', 'ADMIN', 'ADMIN', 'insert your bcrypt with strength by 12');

### Swagger (only with admin role)
http://localhost:8080/swagger-ui/index.html
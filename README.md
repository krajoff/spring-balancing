# Getting Started

### Run dockerfile
docker build -t balancingdb . <br>
docker run --name balancingdb -p 5432:5432 -d balancingdb

### To check
docker exec -it balancingdb bash <br>
psql -l <br>
psql -d balancing <br>
SELECT * FROM balancing; <br>

### To add date
DELETE /record/delete/{id}

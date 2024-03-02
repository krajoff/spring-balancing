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
INSERT INTO tasks (description, status, date)
VALUES ('clean', 1, '2022-03-22');
============================= <br>
GET /api/tasks <br>
GET /api/tasks/{id} <br>
GET /api/tasks/status/{status} <br>
POST /api/tasks (ex. {"description": "clean a house", "status": "NOT_LUANCH"}) <br>
PUT /api/tasks/{id} <br>
PUT /api/tasks/{task_id}/doer/{doer_id} <br>
DELETE /api/tasks/{id} <br>
============================= <br>
GET /api/doers <br>
GET /api/doers/{id} <br>
POST /api/doers (ex. {"name": "Nikolay"}) <br>
PUT /api/doers/{id} <br>
DELETE /api/doers/{id} <br>

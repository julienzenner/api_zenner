==> Veillez à ce que dans le dossier du projet il y a un fichier .env sinon : cp env.dist .env

Lancement de l'application :
 - étape 1 : lancer les services de gestion (consul, rabbitMQ, bases de données) 
==> docker-compose up -d
 - étape 2 : lancer les microservices Java (gateway, utilisateur, cours, springadmin)
(Cette étape requiert des ressources Docker importantes, 2GB n'est pas suffisant, 6GB si possible)
(Un .env se trouve dans le dossier afin de gérer les ports des microservices)
==> mvn clean install -DskipTests sur chaque service (utilisateur-service, cours-service, oauth-service, spring-boot-admin, gateway-service)
==> docker-compose -f docker-compose.microservices.yml up -d
 - étape 3 : utiliser l'api
 - étape 4 : fermer tous les docker
==> docker-compose -f docker-compose.microservices.yml stop
==> docker-compose -f docker-compose.microservices.yml rm -fv
==> docker-compose stop
==> docker-compose rm -fv

Authorization:
 - type : OAuth 2.0
 - Grant Type : Password Credentials
 - Access Token URL : http://localhost:9010/oauth/token
 - Client ID : api-client
 - Client Secret : password
 - Username : operrin / jzenner
 - Password : password
 - Scope : read, write

Routes disponibles par swagger:
localhost:9000/swagger-ui.html

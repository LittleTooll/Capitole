# Backend Challenge - Phone App

Exercise 1: Create an endpoint to retrieve the phone catalog, and pricing.
 * it returns a collection of phones, and their prices.
 * Each phone should contain a reference to its image, the name, the description,
and its price.

Exercise 2: Create endpoints to check and create an order.
 * receives and order that contains the customer information name, surname, and
email, and the list of phones that the customer wants to buy.
 * Calculate the total prices of the order.
 * Log the final order to the console. 


## Getting Started

El software está subido en este repositorio de GitHub y su instalación y prueba es muy sencilla.

### Prerequisites

Se recomienda que el sistema operativo sea Linux (en las pruebas se ha utilizado Fedora).

Como imprescindibles debemos tener instalado:
* JDK 8
* Maven
* Git

Como opcionales:
* MongoDB (nos servirá para que funcionen los test unitarios de JUnit al mavenizar)
* Docker / Docker-compose (nos servirá para levantar la aplicación con su propio contenedor MongoDB y que los servicios queden escuchando peticiones)


### Installing

## Descargar el SW de GitHub:
```
git clone https://github.com/LittleTooll/Capitole.git
```

## Construir y ejecutar las pruebas unitarias:
Ejecutar desde el directorio raíz del repositorio
```
mvn clean install	
```

En la salida del proceso podremos ver si las pruebas han ido bien:
```
CREATED ORDER: Order(customer=Customer(name=Felipe, surname=Perea, email=felipe.perea.toledo@gmail.com), listIdsPhones=[1, 3, 5], total=9)
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 39.736 sec - in com.capitole.challengue.ChallengeTest
2018-10-12 20:20:35.761  INFO 10723 --- [       Thread-3] ationConfigEmbeddedWebApplicationContext : Closing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@3571b748: startup date [Fri Oct 12 20:19:59 CEST 2018]; root of context hierarchy
2018-10-12 20:20:35.804  INFO 10723 --- [       Thread-3] org.mongodb.driver.connection            : Closed connection [connectionId{localValue:2, serverValue:2}] to localhost:27017 because the pool has been closed.

Results :

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ challenge ---
[INFO] Building jar: /home/feyman/Documents/Capitole/target/challenge-0.1.0.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:1.5.10.RELEASE:repackage (default) @ challenge ---
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ challenge ---
[INFO] Installing /home/feyman/Documents/Capitole/target/challenge-0.1.0.jar to /home/feyman/.m2/repository/com/capitole/challenge/0.1.0/challenge-0.1.0.jar
[INFO] Installing /home/feyman/Documents/Capitole/pom.xml to /home/feyman/.m2/repository/com/capitole/challenge/0.1.0/challenge-0.1.0.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 59.713 s
[INFO] Finished at: 2018-10-12T20:20:37+02:00
[INFO] Final Memory: 36M/299M
[INFO] ------------------------------------------------------------------------
```

Para ello será imprescindible tener un mongoDB instalado y escuchando en el puerto por defecto (27017). Como vemos en la salida anterior, se creará un pedido válido:
```
CREATED ORDER: Order(customer=Customer(name=Felipe, surname=Perea, email=felipe.perea.toledo@gmail.com), listIdsPhones=[1, 3, 5], total=9)
```

## Dockerizar
Ejecutar desde el directorio raíz del repositorio
```
docker-compose up -d
```

De esta forma, creamos y arrancamos el servicio y el mongoDb.

## Running the tests

Ejecutaremos los siguientes test desde consola:

```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":"1","image":"image1.jpg","name":"Phone1","description":"Description1","price":25}' \
  http://localhost:2080//challenge/catalog
```

```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"id":"2","image":"image2.jpg","name":"Phone2","description":"Description2","price":10}' \
  http://localhost:2080//challenge/catalog
```

Este será el test que cree el pedido y lo devolverá en formato texto por lo que podremos verla por consola:
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"customer":{"name":"Felipe", "surname":"Perea", "email":"felipe.perea.toledo@gmail.com"}, "listIdsPhones":["1","2"]}' \
  http://localhost:2080//challenge/order
```

Visualizaremos lo siguiente por consola:
```
Order(customer=Customer(name=Felipe, surname=Perea, email=felipe.perea.toledo@gmail.com), listIdsPhones=[1, 2], total=35)
```


## Questions
- How would you improve the system?
	+ Cachear el catálogo de teléfonos, es decir, dicha información no debe variar demasiado, y en caso de hacerlo deberemos refrescar la caché, pero evitaríamos accesos a BD. 
	+ No recuperar todo el catálogo de teléfonos al ir a crear / validar un pedido, sino ir consultando uno a uno los teléfonos. Lo habitual será que un pedido sea de pocos teléfonos y el catálogo sea muy grande.

- How would you avoid your order API to be overflow?
	+ Limitar el número de request por minuto / ip
		+ A nivel de NGINX / APACHE
			+ https://www.nginx.com/blog/rate-limiting-nginx/
			+ https://httpd.apache.org/docs/2.4/mod/mod_ratelimit.html
		+ Programáticamente:
			+ https://stackoverflow.com/questions/50650763/rest-api-method-to-avoid-many-requests-to-download-images
			+ https://github.com/Ensembl/ensembl-rest/wiki/Rate-Limits


## Author
* **Felipe Perea Toledo**

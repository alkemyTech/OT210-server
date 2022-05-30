# OT210-JAVA: ONG PROJECT

## Build and Run

Step 1: Build the project.

```sh
mvn clean install -DskipTests
```

Step 2: (Optional) run mysql on Docker.

```sh
docker container run -d --name mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=True -p 3306:3306 mysql
```

Step 3: run the application.

```sh
 mvn spring-boot:run 
```
## Datos de los usuarios creados

### **Para el Usuario Regular Nº 1**

* Nombre: nombreUsuario1
* Apellido: apellidoUsuario1
* Email: usuario1@email.com
* Password: usuario1

Para el Usuario Regular Nº 2 y hasta el Nº 10, tanto en el nombre, 
apellido, email y password hay que cambiar el 1 por el número de
usuario que corresponda.

### **Para el Administrador Nº 1**

* Nombre: nombreAdministrador1
* Apellido: apellidoAdministrador1
* Email: administrador1@email.com
* Password: administrador1

Para el Administrador Nº 2 y hasta el Nº 10, tanto en el nombre,
apellido, email y password hay que cambiar el 1 por el número de
administrador que corresponda.






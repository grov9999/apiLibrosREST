# Proyecto para evaluacion

El proyecto es una Biblioteca Virtual desarrollada usando Java con Spring y Maven
conectada a una base de datos Microsoft SQL Server
Este proyecto uso las siguientes dependencias de Spring.
* Lombok
* Spring Boot JDBC
* Driver JDBC para SQL Server
* JUnit
* Mockito
* Thymeleaf
* Swagger
Se uso el modelo MVC para el desarrollo del proyecto
El cual se desarrollo en la siguiente ruta
apiLibrosREST\src\main\java\syn\capa\apiLibrosREST
En el paquete beans se crearon las clases que guardan las variables privadas y su  Getters ans Setters
En el paquete de controller se encuentran las siguentes clases
* LibroController, es un controlador REST la cual contiene los siguientes Endpoints
1. getLibroById, obtiene un libro por su id
2. listarLibros, lista todos los libros
4. insertarLibro, inserta un nuevo libro
5. actualizarLibro, actualiza los datos de un libro existente
6. borrarLibro, borra un libro de manera lógica

* LibroDetalleController, es un controlador que contiene el siguiente Endpoint
1. listLibroDetalles, lista los libros de manera detallada

* LibroViewController, es un controlador de vistas el cual contiene los siguientes Endpoints
1. getLibroById, el cual obtiene un libro por su id
2. listarLibros,lista todos los libros
3. listarLibrosDetalles, lista los libros de manera detallada
4. borrarLibro, borra un libro de manera lógica
Tambien tiene las dependencias para trabajar con Swagger

En el paquete service/impl
* LibroDetalleServiceImpl, es una clase que tiene los siguientes métodos
1. listarLibrosDetalle, llama un procedimiento sql para listar los libros de manera detallada
2. LibroDetalleRowMapper, método que impelementa el RowMapper para retornar los campos de la clase LibroDetalle

* LibroServiceImpl, es una clase que tiene los siguientes métodos
1. searchById, llama un procedimiento sql para buscar un libro por su id
2. listarLibros, llama un procedimiento para listar todos los libros
3. insertarLibros, llama un procedimiento para insertar un nuevo libro
4. actualizarLibro, llama un procedimiento para actualizar los datos de un libro existente
5. borrarLibro, llama un procedimiento para borrar de manera lógica un libro por su id
6. ListarLibroDetalles, llama un procedmiento sql para listar un inner join de las tablas autores, generos y libros
7. maperarLibros, metodo para mapear un ResultSet a un objeto Libro
8. maperarLibros, metodo para mapear un ResultSet a un objeto LibroDetalle

En el paquete service/, estan las interfaces de los servicios

*En la ruta apiLibrosREST\src\test\java\syn\capa\apiLibrosREST\service\impl
estan los test usando las dependencias de mockito donde se hizo un test para mapear datos y buscar por su id en la clase Libro

En el paquete apiLibrosREST\src\main\resources\templates
Se encuentran los archivos html para borrarLibro, libroId, listarLibros, listarLibrosDetalles
los cuales interactuan con los Endppoint de LibroViewController como plantillas de Thymeleaf


### Correr Proyecto
* Tener instalado Spring Tool Suite 4, junto con Maven y el jdk17 de Java
* Tener una base de datos Microsoft SQL Server
* Descargar el proyecto y configurar la siguiente ruta ..src/main/resources
En el archivo application.properties
spring.application.name=apiLibrosREST
spring.datasource.url=jdbc:sqlserver://(localhost);trustServerCertificate=true;databaseName=(dbname)
spring.datasource.username=(user)
spring.datasource.password=(password)
1. Cambiar (localhost) por el nombre de tu servidor
2. Cambiar (dbname) por el nombre de tu base de datos
3. Cambiar (user) por el nombre de tu usuario
4. Cambiar (password) por la constraseña de tu usuario

![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/202329.png)

### Ruta del script de la bd (tablas y store procedures)

En la siguiente ruta ..sql/
buscar los archivos
/dbtest03.bak
/script.sql

### Configurar Servicor Sql

1. Ingresa a tu SSMS con SQL Server Authentication y loguearte
2. Crear una base de datos vacia con el nombre dbtest03
3. Clic derecho en la base de datos, selecionar Tasks/Restore/Database
4. Seleccionar tu archivo dbtest.bak y aceptar

## Preview del Swagger
![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/92021.png))
![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/214521.png)
![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/214545.png)
![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/214604.png)
![](https://github.com/grov9999/apiLibrosREST/blob/main/assets/214503.png)


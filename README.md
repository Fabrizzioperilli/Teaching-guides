# Teaching Guides (Guías Docentes)

--- 
## Descripción

El siguiente programa en Java es un sistema para poder acceder a 
las guías docentes de las asignaturas que se imparten en la Universidad de la Laguna. También
se pueden realizar modificaciones en las guías docentes, dependiendo de los permisos que
disponga el usuario al iniciar sesión en la aplicación. Todos los datos están almacenados en 
una base de datos SQLite ```/src/main/resources/database/db_teaching_guide.db```, se estable una conexión para recuperar 
la información y mostrarla al usuario.

---
## Funcionamiento

Para poder utilizar la aplicación debes ubicarte en la raíz del proyecto y buscar el siguiente fichero:
````/Teaching_guides-1.0-SNAPSHOT.jar````, puedes  abrir una consola y ejecutar el siguiente comando: ````java -jar Teaching_guides-1.0-SNAPSHOT.jar````,
si estás usando Windows puedes abrirlo directamente desde el explorador de archivos.

Como se mencionó anteriormente para poder iniciar sesión se necesitan unas credenciales, por tanto
se dispone de una lista de usuarios con acceso al sistema:

| Usuario | Contraseña | Permisos |
|---------|-----------|----------|
| admin   | admin     | 0        |
| user1   | user1     | 1        |
| user11  | user11    | 1        |
| user2   | user2     | 2        |
| user22  | user22    | 2        |

Los permisos de los usuarios dependen del rol de cada uno:

- Permiso 0: El administrador del sistema puede consultar y modificar las guías docentes, además tiene acceso
al listado de usuarios y los datos personales de cada uno de ellos.
- Permiso 1: El usuario puede consultar y modificar sólo las guías docentes en las que sea
coordinador de la asignatura.
- Permiso 2: El usuario sólo puede consultar las guías docentes disponibles.

---

## Requistos
El proyecto está desarrollado en Java 11, por lo que es necesario tener instalado JDK 11 
o superior en tu sistema operativo para poder utilizar el programa, para comprobar la versión
de Java que tienes instalado puedes usar el siguiente comando: ```java --version```, para más
información pulsa [aquí. ](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html) 

También es importante mencionar que el proyecto fue desarrollado con Maven, 
es una herramienta de gestión de proyectos y construcción de software ampliamente utilizada en el desarrollo de aplicaciones Java.
Si deseas compilar y ejecutar el proyecto es de gran utilidad tener Maven instalado en tu sistema, 
puedes usar el siguiente comando para comprobar si lo tienes instalado: ```java --version```,
para más información pulsa [aquí.](https://maven.apache.org/download.cgi)

Con Maven puedes usar los siguientes comandos en el directorio raíz del proyecto 
para realizar diversas acciones:

- ```mvn compile``` Compila el código fuente del proyecto.
- ``mvn test``  Ejecuta las pruebas unitarias del proyecto
- ``mvn package`` Empaqueta el código compilado y los recursos en un archivo JAR, WAR o cualquier otro formato de empaquetado configurado.
- ``mvn site`` Se utiliza para generar el sitio de documentación del proyecto utilizando el complemento Maven Site.
- ``mvn clean`` Limpia los archivos generados en una compilación anterior, como los archivos de compilación y los archivos JAR/ZIP generados.
- ``mvn exec:java`` Ejecuta el programa.

Toda la configuración de las depedencias del proyecto se encuentran el fichero ``pom.xml``

Para más información acerca del proyecto puedes acceder a la documentación de JavaDoc ``/target/site/index.html``

---

## Autor

Fabrizzio Daniell Perilli Martín -- alu0101138589@ull.edu.es 

---
# Configuración de Visual Studio Code para prácticas de Servidor

Este documento describe cómo configurar **Visual Studio Code** para desarrollar y desplegar aplicaciones web con **Tomcat 9** y **Maven**.

---

## 1. Instalación de Tomcat 9

1. Descarga Tomcat 9 desde la página oficial:  
   [https://tomcat.apache.org/download-90.cgi](https://tomcat.apache.org/download-90.cgi)

2. Elige la versión **Core ZIP** para Windows o **tar.gz** para Linux/Mac.

3. Descomprime el archivo en una carpeta, por ejemplo:  
   - Windows: `C:\tomcat9`  
   - Linux/Mac: `/home/usuario/tomcat9`

4. Crea la variable de entorno `CATALINA_HOME` con la ruta de la carpeta de tomcat.

---

## 2. Configuración del usuario administrador de Tomcat

1. Ve a la carpeta `conf` de Tomcat.  
   Ejemplo: `C:\tomcat9\conf`

2. Edita el fichero `tomcat-users.xml` y añade lo siguiente dentro de `<tomcat-users>`:

   ```xml
   <role rolename="manager-gui"/>
   <role rolename="admin-gui"/>
   <user username="admin" password="admin123" roles="manager-gui,admin-gui"/>
   ```

3. Guarda el fichero.  
   Ahora podrás acceder a la consola de administración en:  
   [http://localhost:8080/](http://localhost:8080/)

---

## 3. Instalación de la extensión *Community Server Connector*

1. Abre Visual Studio Code.  
2. Abre el administrador de extensiones.
3. Busca **Community Server Connector**.  
4. Instala la extensión.

---

## 4. Añadir un servidor a la extensión

1. Aparecerá en el explorador un apartado `Servers`.

2. Selecciona el botón `Create new server`
3. Indica la carpeta donde instalaste Tomcat 9.  
4. Asigna un nombre al servidor (por ejemplo: `Tomcat 9`).

---

## 5. Creación de un proyecto web con Maven

1. Para crear un nuevo proyecto web, comienza seleccionando `Create Java Proyect`.
2. Posteriormente selecciona Maven.
3. Te ofrecerá distintos arquetipos. Comenzaremos con un proyecto en blanco seleccionando `No Archetype`. Más adelante podremos buscar uno que nos convenga o crear uno propio.
4. Crearemos las carpetas que necesitemos hasta conseguir una estructura como la siguiente:
```
mi-proyecto/
├── src/
│ ├── main/
│ │ ├── java/com/ejemplo/
│ │ │ └── servlets/
│ │ │ └── model/
│ │ ├── resources/ # Recursos de configuración
│ │ └── webapp/ # Contenido web
│ │    ├── META-INF/
│ │    │── WEB-INF/
│ │    │    └── web.xml # Descriptor de despliegue
│ │    └── index.jsp # Página de inicio
│ └── test/ # Pruebas unitarias
├── target/ # Archivos generados por Maven
│ └── mi-proyecto.war # Archivo WAR resultante
└── pom.xml # Configuración del proyecto Maven
```

---

## 6. Configuración del `pom.xml` para un servlet con empaquetado WAR

Edita el `pom.xml` y asegúrate de que incluye lo siguiente:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>holaservlet</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-web-api</artifactId>
            <version>7.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

---

## 7. Compilar y crear el WAR

En la sección `Maven` del explorador de visual, haciendo click en el desplegable y luego en `Lifecycle` encontraremos una opción llamada `Package`

Esto generará un archivo `.war` en la carpeta `target/`, por ejemplo:  
`target/practica1.war`

---

## 8. Añadir un despliegue al servidor

1. Abre la pestaña de **Community Server Connector** en VS Code.  
2. Haz clic derecho sobre el servidor `Tomcat 9` y selecciona:  
   `Add Deployment`  
3. Selecciona el archivo `.war` generado o el proyecto Maven.  

---

## 9. Correr el servidor

1. Haz clic derecho sobre el servidor `Tomcat 9`.  
2. Selecciona **Start Server**.  
3. Abre en el navegador:  
   [http://localhost:8080/practica1](http://localhost:8080/practica1)

Si aparece tu aplicación, la configuración está completa

---
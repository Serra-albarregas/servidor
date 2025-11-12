# Dónde guardar las imágenes en un proyecto JSP + Servlet con Maven

En un proyecto **JSP + Servlets**, el lugar correcto para guardar las imágenes depende de si son **recursos estáticos del sitio** (por ejemplo, iconos o logos) o **imágenes subidas por los usuarios**.

---

## 1. Imágenes estáticas (logos, iconos, fondos, etc.)

Estas imágenes forman parte de la aplicación web, así que deben ir dentro del **contexto del servidor**, normalmente en la carpeta `src/main/webapp`.

### Estructura recomendada

```
YourProject/
 ├── src/
 │   └── main/
 │       ├── java/
 │       └── webapp/
 │           ├── WEB-INF/
 │           ├── css/
 │           ├── js/
 │           └── images/
 │               ├── logo.png
 │               └── fondo.jpg
```

De esta forma, podrás acceder a ellas directamente desde tus JSP con una ruta relativa:

```html
<img src="images/logo.png" alt="Logo">
```

---

## 2. Imágenes subidas por los usuarios

Estas **no deben guardarse dentro del proyecto** (porque se sobrescriben al desplegar una nueva versión del WAR).  
En su lugar, se deben almacenar en una **carpeta fuera del contexto de la aplicación** o en un **almacenamiento persistente** (como una base de datos o un servicio de archivos).

### Ejemplo de estructura

```
C:/uploads/imagenes/
 ├── usuario1/
 │   └── perfil.jpg
 └── usuario2/
     └── producto.png
```

En el código del servlet, se guarda la **ruta del archivo** (no la imagen en sí) en la base de datos:

```java
String rutaImagen = "C:/uploads/imagenes/usuario1/perfil.jpg";
```

Para mostrar las imágenes, puedes crear un **Servlet específico** (por ejemplo, `ImageServlet`) que lea los bytes del archivo y los envíe como respuesta HTTP con el tipo MIME correcto (`image/jpeg`, `image/png`, etc.).

---

## Alternativa moderna

En proyectos más grandes o en producción, lo habitual es usar:
- Un **bucket de almacenamiento** (como Amazon S3, Azure Blob Storage, etc.)
- O un **servidor estático** separado del servidor de aplicación.

---

## 3. ¿Y qué pasa con la carpeta `src/main/resources`?

La carpeta `src/main/resources` es una carpeta especial de Maven donde se colocan **recursos internos del proyecto** que:

- Se empaquetan dentro del `.war` o `.jar`
- Se usan desde el **código Java**, no desde el navegador

Por ejemplo:
- Archivos de configuración (`.properties`, `.xml`, `.json`)
- Ficheros de mensajes (i18n)
- Plantillas internas

### Por qué **no** usarla para imágenes web

Cuando Maven construye tu proyecto, los recursos de `resources` se copian al **classpath**, no a la raíz pública del WAR.  
Por tanto, **el navegador no puede acceder directamente** a ellos con una URL como `/images/logo.png`.

Ejemplo:
```
src/main/resources/logo.png
```
se empaquetará como:
```
WEB-INF/classes/logo.png
```
y no será accesible vía HTTP.

---

## En resumen

| Tipo de recurso | Carpeta recomendada | Accesible desde navegador | Ejemplo de uso |
|-----------------|---------------------|---------------------------|----------------|
| Configuración interna | `src/main/resources` | ❌ No | `config.properties` |
| Imágenes estáticas (frontend) | `src/main/webapp/images` | ✅ Sí | `logo.png` |
| Archivos subidos por usuarios | Carpeta fuera del WAR | ⚠️ Sí, con servlet | `C:/uploads/...` |

---

**Conclusión:**  
- Usa `src/main/webapp/images` para imágenes estáticas visibles desde JSP.  
- Usa una carpeta externa para archivos subidos.  
- Usa `src/main/resources` solo para configuraciones internas del proyecto.

# Portafolio Universitario

Aplicación Java 21 + JSP + Servlets + Tomcat 10.1 + MongoDB GridFS.

## Requisitos locales
- JDK 21
- Maven 3.9 o superior (tu Maven 3.10.2 sirve)
- Tomcat 10.1.x (tu Tomcat 10.1.62 sirve)
- Cuenta de MongoDB Atlas y una cadena de conexión

## 1. Configurar MongoDB Atlas
1. Crea un cluster gratuito en https://www.mongodb.com/atlas.
2. En Database Access crea un usuario de base de datos y una contraseña segura.
3. En Network Access permite el acceso desde Render y tu IP actual para pruebas. Para una primera prueba puedes permitir `0.0.0.0/0`, pero usa una contraseña fuerte y restringe el acceso cuando sea posible.
4. Pulsa Connect > Drivers > Java y copia la URI.
5. Reemplaza usuario, contraseña y nombre del cluster. Codifica caracteres especiales de la contraseña URL-encoding.

La aplicación crea la base `portafolio_universitario` y el bucket GridFS `trabajos` al guardar el primer archivo.

## 2. Variables de entorno en Windows
En CMD, antes de iniciar Tomcat, define estas variables (solo duran en esa ventana CMD):

```bat
set MONGODB_URI=mongodb+srv://USUARIO:CLAVE@TU_CLUSTER.mongodb.net/?retryWrites=true&w=majority
set MONGODB_DATABASE=portafolio_universitario
set ADMIN_USER=admin
set ADMIN_PASSWORD=CAMBIA_ESTA_CLAVE_POR_UNA_LARGA
```

No publiques ni compartas tu URI ni tu contraseña. No las subas a GitHub.

## 3. Compilar
Abre CMD en la carpeta del proyecto y ejecuta:

```bat
mvn clean package
```

Debe generarse `target\portafolio.war`.

## 4. Ejecutar localmente con Tomcat
1. Copia `target\portafolio.war` a `C:\apache-tomcat-10.1.62\webapps\ROOT.war`.
2. Abre CMD y define las variables de entorno indicadas arriba.
3. Ejecuta:

```bat
C:\apache-tomcat-10.1.62\bin\catalina.bat run
```

4. Abre `http://localhost:8080/`.
5. Entra en Administración con el usuario y contraseña definidos en `ADMIN_USER` y `ADMIN_PASSWORD`.
6. Abre una semana y sube un documento de prueba.

Para detener Tomcat pulsa `Ctrl+C` en esa ventana.

## 5. Publicar en Render
1. Sube el proyecto a un repositorio privado de GitHub.
2. En Render crea un nuevo **Web Service** y conecta ese repositorio.
3. Selecciona **Docker** como Runtime.
4. Agrega las variables `MONGODB_URI`, `MONGODB_DATABASE`, `ADMIN_USER` y `ADMIN_PASSWORD` en Environment.
5. Despliega. El Dockerfile compila el WAR y ejecuta Tomcat escuchando en el puerto `PORT` de Render.
6. Prueba el inicio de sesión y la subida/descarga de un documento.

## Uso del portafolio
- El inicio muestra las 16 semanas y el total de documentos.
- Cada semana tiene una página propia.
- Solo el administrador autenticado puede subir o eliminar.
- Formatos permitidos: PDF, DOC, DOCX, PPT, PPTX, XLS, XLSX, JPG y PNG.
- Tamaño máximo por archivo: 20 MB.
- Los archivos se guardan en MongoDB GridFS, no en el disco temporal de Render.

## Notas importantes
- JSP no es un lenguaje distinto de HTML: los archivos JSP contienen marcado que el servidor procesa para generar la página. Este proyecto no utiliza archivos `.html` independientes.
- Para producción, usa una contraseña de administrador larga y única, un usuario de MongoDB con permisos mínimos y restringe el acceso de red de Atlas cuando sea posible.
- Esta versión es una base académica funcional. Antes de usarla para información sensible, conviene añadir protección CSRF, límites de intentos de inicio de sesión, registro de auditoría y controles adicionales de autorización para descargas.

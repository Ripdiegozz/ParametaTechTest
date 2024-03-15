# Prueba Técnica - Servicio REST para Registro de Empleados con Integración SOAP
==============================================================================

Este proyecto implementa un servicio REST en Java utilizando Spring Boot para el registro de empleados. El servicio recibe los atributos de un objeto empleado mediante una solicitud GET, valida los formatos de las fechas, verifica que los atributos no estén vacíos y asegura que el empleado sea mayor de edad. Posteriormente, integra un servicio web SOAP para almacenar la información en una base de datos MySQL. La respuesta del servicio REST es un objeto en formato JSON que contiene información adicional sobre el empleado, como el tiempo de vinculación a la compañía y la edad actual.

# Requisitos del Sistema
----------------------

-   Java JDK 17 o superior
-   Maven
-   Base de datos MySQL (para almacenar la información del empleado)

# Instalación y Uso
-----------------

1.  Clonar el Repositorio


    `git clone https://github.com/Ripdiegozz/ParametaTechTest.git`
    
    `cd ParametaTechTest`

3.  Configuración de la Base de Datos

    -   Cree una base de datos MySQL y configure las credenciales en el archivo `application.properties` en `src/main/resources`.
4.  Compilación y Ejecución

	`mvn install`
	
    `mvn spring-boot:run`

# Uso del Servicio REST
-----------------------

-   El servicio REST estará disponible en `http://localhost:8080/empleado/registrar`. Puede realizar solicitudes GET con los siguientes parámetros:
        -   `nombres`: Nombres del empleado (String)
        -   `apellidos`: Apellidos del empleado (String)
        -   `tipoDocumento`: Tipo de Documento del empleado (String)
        -   `numeroDocumento`: Número de Documento del empleado (String)
        -   `fechaNacimientoStr`: Fecha de Nacimiento del empleado (String en formato yyyy-MM-dd)
        -   `fechaVinculacionStr`: Fecha de Vinculación a la Compañía del empleado (String en formato yyyy-MM-dd)
        -   `cargo`: Cargo del empleado (String)
        -   `salario`: Salario del empleado (Double)
        
-	EJEMPLO: `http://localhost:8080/empleado/registrar?nombres=Edward&apellidos=Perez&tipoDocumento=CC&numeroDocumento=1232456789&fechaNacimientoStr=2006-01-01&fechaVinculacionStr=2020-01-01&cargo=Developer&salario=52`
   		
   		
-  Respuesta del Servicio:

   -   El servicio responderá con un objeto JSON que contiene la información del empleado, además de los siguientes datos adicionales:
        -   Tiempo de Vinculación a la compañía (años, meses y días).
        -   Edad actual del empleado (años, meses y días)

# Integración SOAP
----------------

Para integrar el servicio web SOAP para el almacenamiento de datos, se realiza automáticamente dentro del servicio REST mediante un cliente que invoca el servicio SOAP. No es necesario realizar ninguna acción adicional por parte del usuario.

Si desea verificar el archivo WSDL del servicio SOAP puede hacerlo mediante la URL: http://localhost:8080/ws/empleados.wsdl

# Pruebas Unitarias

Se han desarrollado pruebas unitarias para asegurar el correcto funcionamiento de las diferentes partes del servicio. Estas pruebas cubren los controladores, repositorios y utilidades del proyecto.

### Pruebas para Controladores

Se han creado pruebas para el controlador `EmpleadoController` para verificar el manejo adecuado de las solicitudes REST.

### Pruebas para Repositorios

También se han creado pruebas para el repositorio `EmpleadoRepository` para asegurar que las consultas a la base de datos se realicen correctamente.

### Pruebas para Utilidades

Se han desarrollado pruebas para la utilidad `EmpleadoConverter` para garantizar la correcta conversión entre objetos `Empleado` y `EmpleadoModel`.

Puedes ejecutar todas estas pruebas utilizando la herramienta de construcción Maven con el siguiente comando: `mvn test`

# Notas Adicionales
-----------------

-   Asegúrese de que la base de datos MySQL esté en funcionamiento y accesible antes de ejecutar el servicio.

# Contacto
--------

Para cualquier pregunta o consulta, no dude en ponerse en contacto conmigo: diegogarciag63@gmail.com

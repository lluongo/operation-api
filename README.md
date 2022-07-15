# operation-api


RESUMEN DE LA APP: 
==================

Es un desarrollo basado en tokens al portador que si bien no pueden ser vencidos, al realizar signout los token del usuario (que aun esten vigentes) pasan a una blacklist implementada con una cache persistente en memoria ( se va refrescando y va quitando los tokens a la medida que ya se van venciendo ) con lo cual los interceptores de la api no van a permitir su utilizacion emitiendo un codigo 401. Este mismo error la api lo informara si el token esta vencido por lo cual podemos decir que tiene funcionamiento en 2 niveles : por cierre forzado de parte del usuario o por vencimiento propio del token.

Los parametros que utiliza la api para la creacion de tokens tanto del secret_key o expiration_value, pueden ser modificados desde el archivo app-variables.env ubicando las claves JWT_SECRET_KEY y JWT_EXPIRATION_VALUE.

Algunos comentarios: 
    - La api gestiona los errores mediante @ControllerAdvice en los controladores.
    - Tiene un listener a la escucha de eventos logout con el fin de incorporar los tokens vigentes a la cache.
    - Las tecnologias mas destacadas utilizadas para el desarrollo fueron :  Java 11, Springboot 2.6.9 , Liquibase-core 4.5.0, Postgresql 13.



INSTALACION DE LA API:
======================

nota: Pre-Condiciones : Docker , Docker-Compose y postman instalados y funcionando.

            - desde la CLI ubicarse en el directorio raiz de la app ".../operation-api".
            - ejecutar el siguiente comando  --> "docker-compose up"

Al componer se descargaran de DockerHub las  imagenes lluongo/operation-api:v1.0.0  y postgres:13 e inicia la api automaticamente.



PRUEBAS:
=======

En el directorio raiz de la app existe un archivo api.postman_collection.json con el cual se podra importar en postman para la realizacion de las mismas.

La app cuenta con 5 endpoints para realizar  signin, signup, signout , operation(operacion para sumar 2 numeros) y historyLog (historial de peticiones).

Pasos :
    - Realizar signup con un usuario , email y password
    - Realizar sigin con el usuario , con lo que la app le devolvera un token bearer similar a este :
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdWMxMjEyIiwiaWF0IjoxNjU3ODk4NjI2LCJleHAiOjE2NTc5ODUwMjZ9.TwR2udF3DE77FMX1PJLXVm36Xd5IIJChfAVmQnzZKMmtdAoSzzAqudyBltxXc4Az-16-VFpzYwOUGFzb16KRog"

    - Luego con este token se podra realizar la operacion de suma ( previamente ingresando el token como Authorization Token )
    - Tambien se podra consultar el historial de las peticiones donde al igual que el punto anterior se debe setear el token.

    - Para cerrar sesion se debera setear el token como  Authorization y ademas agregar el token como parametro en el body de la peticion. ( si por casualidad se utilizan tokens de diferente usuario para la Auth y en el parametro la api emitira un mensaje de error indicando que no pude vencer tokens de otro usuario solo los propios.)
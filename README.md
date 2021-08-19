# Análisis y Diseño de Algoritmos

¡Bienvenido a tu repositorio de trabajo!

## Develop
Este proyecto está configurado con Gradle como _build tool_. Podés abrirlo directamente desde tu IDE de preferencia eligiendo esta carpeta y aceptando Gradle como tipo de proyecto.

Tu primera implementación será de los [Sorters](src/main/java/anaydis/sort/AbstractSorter.java).

## Compile & Test
Tu IDE va a quedar listo para compilar tu proyecto sin que tengas que configurar nada extra. **Recordá siempre manejar la configuración desde Gradle** ([build.gradle](build.gradle)) y actualizar el proyecto en el IDE, en lugar de hacerlo en el IDE directamente. Para compilar tu proyecto desde la terminal y correr los tests:
```
./gradlew clean test
```

Ya tenés [Tests](src/test/java/anaydis/sort) de ejemplo en el scope de _test_.

## Commit & Push
Recordá capturar los cambios que vayas realizando periodicamente en diferentes `commits` y llevá esos cambios al repositorio remoto vía `push`. Únicamente al _pushear_ esos cambios, se actualizará el CI (continuous integration) del TeamCity. 
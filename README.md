# Algoritmo Genético para Generación de Horarios

Este proyecto implementa un algoritmo genético para la generación automática de horarios académicos. Antes de profundizar en los detalles de la implementación, explicaremos brevemente qué son los algoritmos genéticos y luego describiremos la estructura del horario y las variables de configuración.

## Algoritmos Genéticos

Los algoritmos genéticos son una técnica de optimización inspirada en la teoría de la evolución y la genética. Se utilizan para resolver problemas de optimización y búsqueda donde se busca encontrar la mejor solución posible entre un conjunto de posibles soluciones. Estos algoritmos simulan el proceso de selección natural, reproducción y mutación para encontrar soluciones óptimas.

## Estructura del Horario

El horario se representa como una matriz bidimensional. El tamaño de la matriz es igual al número de días en la semana, y cada elemento de la matriz corresponde a un período en un aula. Cada período puede contener una asignatura o estar vacío. Esta estructura permite representar diferentes horarios de clases para diferentes cursos y asignaturas.

## Variables de Configuración

El algoritmo genético se puede configurar mediante un archivo de configuración que incluye las siguientes variables:

- Tamaño de la población
- Número de generaciones
- Probabilidad de cruce
- Probabilidad de mutación
- Elitismo
- Estrategia de reemplazo
- Operador de cruce
- Operador de mutación
- Algoritmo de selección
- Número de cursos
- Número de asignaturas por curso
- Número de aulas
- Número de días
- Número de periodos por día

Estas variables se pueden ajustar para adaptar el algoritmo genético a diferentes escenarios y requisitos del problema.

## Estrategia de Reemplazamiento

- **Estacionario (0):** En la estrategia de reemplazamiento estacionaria, los individuos reemplazados son seleccionados de la población actual, lo que significa que los nuevos individuos reemplazan directamente a los peores individuos de la población actual. Esta estrategia puede llevar a una convergencia más lenta, pero puede ser útil para mantener la diversidad genética en la población.
- **Generacional (1):** En la estrategia de reemplazamiento generacional, la población entera se reemplaza por completo en cada generación. Los nuevos individuos se generan mediante la combinación de los mejores individuos de la población actual (elitismo) y los individuos seleccionados a través de operadores genéticos como cruce y mutación. Esta estrategia tiende a converger más rápidamente hacia una solución óptima.

## Operadores de Cruce

- **Cruce en un Punto (0):** En el cruce en un punto, se selecciona un punto aleatorio en los cromosomas de los padres, y los genes de los padres se intercambian en ese punto para producir descendientes.
- **Cruce Multipunto (1):** El cruce multipunto implica seleccionar varios puntos aleatorios en los cromosomas de los padres y realizar intercambios de genes en esos puntos para producir descendientes.
- **Cruce Uniforme (2):** En el cruce uniforme, cada gen del cromosoma del hijo se elige de forma aleatoria entre los genes correspondientes de los padres, lo que permite una mayor diversidad genética en la descendencia.

## Operadores de Mutación

- **Operador 1 (0):** Este operador de mutación específico para el problema de generación de horarios consiste en seleccionar un día aleatorio y, sobre ese día, seleccionar dos periodos de forma aleatoria. Luego, se intercambian las asignaturas (o se deja uno vacío si no hay asignatura) entre los dos periodos seleccionados para introducir variabilidad en el horario.
- **Operador 2 (1):** En este operador de mutación, se selecciona un día aleatorio y un periodo aleatorio de ese día. Posteriormente, se selecciona otro día aleatorio y un periodo aleatorio de ese día. Finalmente, se intercambian las asignaturas (o se deja uno vacío si no hay asignatura) entre los dos periodos seleccionados para introducir variabilidad en el horario.

## Algoritmos de Selección

- **Método de Ruleta (0):** En el método de ruleta, la probabilidad de que un individuo sea seleccionado es proporcional a su fitness relativo en comparación con el resto de la población.
- **Método Rank (1):** En el método rank, los individuos se ordenan según su fitness, y la probabilidad de selección es inversamente proporcional a la posición que ocupan en el ranking.
- **Selección Competitiva (Tournament) (2):** En la selección competitiva, se seleccionan aleatoriamente dos parejas de individuos y se selecciona el de mayor fitness de cada pareja. Luego, se selecciona el de mayor fitness entre los dos finalistas.
- **Selección Truncada (3):** En la selección truncada, se descartan los n individuos con menor fitness de la población antes de realizar las selecciones aleatorias de individuos para reproducción.


# Average of Levels in Binary Tree - Java (BFS)

## Problema 
Dado el nodo raiz de un arbol binario, devolver el promedio de los valores de cada
nivel, como una lista simple de decimales (un numero por nivel, no lista de listas).

## Enfoque (BFS con Queue - mismo esqueleto que Level Order Traversal)
Identica mecanica de recorrido por niveles con Queue (cola FIFO), pero en vez de 
acumular los valores en una List<Integer> por nivel, se acumula una SUMA (int sumaNivel)
y al final de cada nivel se calcula el promedio dividiendo por cantidadEnNivel.

```java
public static List<Double> averageOfLevels(TreeNode raiz) {
    Queue<TreeNode> cola = new LinkedList<>();
    cola.offer(raiz);
    List<Double> resultado = new ArrayList<>();

    while (!cola.isEmpty()) {
        int cantidadEnNivel = cola.size();
        int sumaNivel = 0;

        for (int i = 0; i < cantidadEnNivel; i++) {
            TreeNode actual = cola.poll();
            sumaNivel += actual.val;
            if (actual.left != null) cola.offer(actual.left);
            if (actual.right != null) cola.offer(actual.right);
        }

        double promedio = (double) sumaNivel / cantidadEnNivel;
        resultado.add(promedio);
    }
    return resultado;
}
```

- Tiempo: O(n) - cada nodo se agrega y se saca de la cola una vez
- Espacio: O(n) - peor caso, el nivel mas ancho puede ocupar hasta la mitad de los nodos
en la cola simultaneamente

## Patron de acumulador de suma (reutilizable en muchos problemas) 
sumaNivel - 0 declarado ANTES del loop, y sumaNivel += valor dentro del loop, en cada
vuelta. Al terminar el loop, sumaNivel tiene el total acumulado. Distinto de un contador
de ocurrencias (que suma +1 cada vez) - aca se suma el VALOR real de cada elemento, no
solo se cuenta cuantas veces aparece algo.

## Division entera vs decimal en Java
sumaNivel y cantidadEnNivel son ambos int. Dividir dos int entre si trunca el resultado
(sin decimales): 29/2 = 14, no 14.5. Para obtener decimales, forzar que al menos uno
de los dos sea double ANTES de dividir, con un cast explicito:
    double promedio = (double) sumaNivel / cantidadEnNivel;

## Errores que cometi en el camino
- Nombre del metodo confundido con el nombre de una clase de Java (ArrayList) en vez
de un nombre propio (averageOfLevels).
- Parametro declarado como "node" pero usado como "raiz" dentro del metodo - nombres
inconsistentes.
- El calculo de promedio y el resultado.add() quedaron dentro del for en el primer
intento, mismo error que en el problema anterior (Level Order Traversal) - corregido
esta vez identificandolo yo mismo antes de que se sugiriera.
- LLAVES DESBALANCEADAS - el error mas costoso de este problema:
faltaba (y luego sobraba, en el intento de arreglarlo) una llave de cierre del metodo
averageOfLevels antes de que empezara main, causando que main quedara anidado DENTRO
de averageOfLevels. 
Leccion practica: cuando aparece un error de "reached end of file" o "class expected",
contar las llaves de apertura y cierre UNA POR UNA, bloque por bloque (metodo, while, for,
if), en vez de adivinar donde esta el problema.

## Leccion general
Segundo problema de BFS seguido, resuelto reconstruyendo la logica completa desde cero
en palabras (sin mirar la solucion del problema anterior primero) antes de escribir
codigo. El mismo esqueleto de Queue (offer/poll/size/isEmpty, while + for anidados)
se reutiliza para procesar niveles de formas distintas: lista de valores (problema 
anterior) o suma/promedio (este problema) - la mecanica de recorrido es la parte que
se vuelve reutilizable entre problemas de BFS, mientras que el procesamiento de cada
nodo cambia segun lo que pida el problema especifico.



# Number of Islands - Java

## Problema
Dada una cuadricula 2D de '1' (tierra) y '0' (agua), contar el numero de islas. 
Una sola isla se forma con tierra conectada solo horizontal o verticalmente
(NUNCA en diagonal).

## Enfoque (DFS: recursion en 2D + "hundir" celdas visitadas)
Dos funciones separadas, con roles distintos:

### explorarIsla(grid, fila, columna) - hunde una isla completa 
Dado un punto de partida de tierra, marca esa celda como visitada (cambiandola a '0')
y se llama a si misma recursivamente para sus CUATRO vecinos (arriba, abajo, izquierda,
derecha) - no diagonales.

Dos casos base (cortan sin hacer nada mas):
1. La posicion esta fuera de los limites de la cuadricula
2. La celda ya es '0' (agua original, o tierra ya visitada/hundida)

```java
public static void explorarIsla(char[][] grid, int fila, int columna) {
    if (fila < 0 || fila >= grid.length || columna < 0 || columna >= grid[0].length) return;
    if (grid[fila][columna] == '0') return;
    grid[fila][columna] = '0';
    explorarIsla(grid, fila-1, columna);
    explorarIsla(grid, fila+1, columna);
    explorarIsla(grid, fila, columna-1);
    explorarIsla(grid, fila, columna+1);
}
```

### numIslands(grid) - recorre toda la cuadricula y cuenta
Recorre CADA celda una sola vez con dos for anidados. Cada vez que encuentra un '1'
(tierra sin visitar todavia), incrementa el contador y llama explorarIsla para
hundir TODA esa isla de inmediato - asi, cuando el recorrido principal siga 
avanzando y tope con otra celda de la MISMA isla mas adelanta, ya la va a encontrar
como '0' y no la va a volver a contar.

```java
public static int numIslands(char[][] grid) {
    int contador = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                contador++;
                explorarIsla(grid, i, j);
            }
        }
    }
    return contador;
}
```

- Tiempo: O(m x n) - m= filas, n=columnas. Cada celda se procesa una cantidad constante
de veces en total, sumando ambas funciones
- Espacio: O(m x n) - peor caso (toda la cuadricula es una sola isla), la pila de
llamadas recursivas puede tener una entrada por cada celda

## Por que "hundir" en vez de HashMap/HashSet
No hace falta una estructura separada para "recordar posiciones visitadas" - se usa
la CUADRICULA MISMA como memoria: cambiar una celda de '1' a '0' apenas se visita
sirve como marca de "ya explorada, sin gastar espacio extra en otra estructura.

## Adyacencia: horizontal/vertical, NUNCA diagonal
Diferencia clave que costo entender al principio: dos celdas de tierra que se tocan
solo por la esquina (diagonal) NO se consideran conectadas. Solo cuentan como vecinos
directos: (fila-1, col), (fila+1, col), (fila, col-1), (fila,col+1).

## DFS en grafos/cuadriculas (concepto nuevo)
Este patron - explorar completamente en una direccion (profundizar) antes de retroceder
- se llama DFS (Depth-First Search). Es la version de "recorrer una estructura conectada"
analoga a como se recorrian arboles (ir hasta el fondo de una rama antes de volver).
La otra tecnica principal para grafos es BFS (explorar nivel por nivel con una cola
en vez de recursion), pendiente para una proxima sesion.

## Errores que cometi en el camino
- Intente usar TreeNode o HashMap para este problema, sin ver que la cuadricula 2D ya
es la estructura de datos completa - los "vecinos" se calculan matematicamente (+-1 en
fila/columna), no se acceden por campos con nombre como .left/.right.
- explorarIsla sin tipos en los parametros (grid, fila, columna sin sus tipos int/char[][]).
- Uso de variables filas/columnas nunca declaradas, en vez de grid.length y grid[0].length.
- Comparacion grid[fila][columna] == 0 (numero) en vez de == '0' (caracter, con comillas simples).
- Al declarar el array de prueba, comillas dobles ("1") en vez de simples ('1') - importante
porque char y String son tipos distintos en Java.
- Llave de cierre faltante en la declaracion del array 2D.

## Leccion general
Primer problema en una cuadricula 2D (no array lineal, no arbol).
La recursion se extiende a CUATRO direcciones posibles en vez de dos (like/right en arboles),
y la "memoria de visitados" se logra modificando la estructura misma en vez de usar
una estructura auxiliar - un patron eficiente en espacio que vale la pena recordar
para problemas similares de grip/matriz.

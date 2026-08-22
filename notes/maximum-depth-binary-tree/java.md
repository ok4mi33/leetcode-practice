# Maximun Depth of Binary Tree - Java

## Problema
Dado el nodo raiz de un arbol binario, devolver su profundidad maxima (cantidad de 
nodos en el camino mas largo desde la raiz hasta la hoja mas lejana).

## Estructura del nodo (dada por el problema)
```java
public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
        this.val = val;
    }
}
```

## Enfoque (recursion sobre la estructura del arbol)
Analogia: la profunidaad de un nodo es 1 (por si mismo) mas la mayor profundidad
entre sus dos hijos. Esto se calcula preguntando recursivamente la MISMA pregunta 
a cada hijo (left y right).

Caso base: si el nodo es null (no existe), su profundidad es 0 - no hay ningun nivel
que contar ahi. Esto permite que la funcion se llame SIEMPRE para left y right, sin
tener que verificar manualmente si existen - el caso base se encarga de "frenar"
automaticamente cuando llega a un lugar vacio.

```java
public static int profundidadMaxima(TreeNode nodo) {
    if (nodo == null) return 0;
    int profundidadIzquierda = profundidadMaxima(nodo.left);
    int profundidadDerecha = profundidadMaxima(nodo.right);
    return Math.max(profundidadIzquierda, profundidadDerecha) + 1;
}
```

- Tiempo: O(n) - cada nodo del arbol se visita exactamente una vez
- Espacio: O(h) - donde h es la ALTURA del arbol (no la cantidad de nodos), por la
pila de llamadas recursivas. Peor caso (arbol muy desbalanceado, como una lista): 
O(n). Mejor caso (arbol balanceado): O(log n)

## Diferencia clave con Fibonacci
Fibonacci: fib(n-1) y fib(n-2) son dos formas de "achicar el mismo numero". Arboles:
nodo.left y nodo.right son dos RAMAS DISTINTAS de la estructura, cada una explorando
una parte diferente del arbol, no el mismo dato con un numero menor.

## Concepto nuevo de Java: static en clases internas
Una clase interna (como TreeNode dentro de la clase principal) NO estatica esta "atada"
a un objeto ya creado de la clase que la contiene - no se puede usar libremente desde
metodos static (como main o profundidadMaxima). Agregando static a la clase interna
(public static class TreeNode), se vuelve independiente de cualquier objeto y se puede
usar desde cualquier lugar static.

Regla practica: si la clase auxiliar (nodo, en problemas de arboles o listas enlazadas)
se usa desde metodos static, la clase tambien debe ser static.

## Construccion manual del arbol de prueba (por ahora)
En LeetCode real, el arbol ya viene construido automaticamente antes de llamar a la
funcion - no hace falta escribir esa parte. Para probar localmente, se arma a mano:
crear cada TreeNode con new TreeNode(valor), y conectar con .left/.right del padre
correspondiente.

Pendiente para el futuro: escribir una funcion reutilizable que contruya un arbol 
automaticamente a partir de un array simple, para no repetir la construccion manual
en cada problema de arboles.

## Errores que cometi en el camino
- Firma del metodo invertida al principio: TreeNode como tipo de retorno e int como
parametro, cuando debia ser al reves (int retorno, TreeNode parametro).
- Intente llamar a la funcion con un numero suelto (profundidadMaxiam(4)) en vez de 
construir un TreeNode real y pasarle ese objeto.
- Intente crear un nodo con TreeNode nombre = new TreeNode (9) seguido de conectar
con .left o .right del padre.
- TreeNode declarada sin static, rompiendo al usarse desde metodos static (main,
profundidadMaxima).

## Leccion general
Primer problema con estructura de arbol (no lineal como arrays/strings). La recursion cambia
de proposito: en vez de achicar un numero (Fibonacci), explora RAMAS de una estructura
ramificada. El patron general - caso base (nodo null) + combinar resultados de 
llamadas recursivas sobre las partes mas chicas del problema (los hijos) - es el 
mismo patron de fondo que se va a repetir en todos los problemas de arboles y grafos
que vengan.


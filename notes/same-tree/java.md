# Same Tree - Java

## Problema
Dadas las raices de dos arboles binarios p y q, verificar si son el mismo arbol:
misma estructura y mismos valores en cada posicion correspondiente.

## Enfoque (recursion comparando dos estructuras en simultaneo)
A diferencia de Maximum Depth (que explora UN arbol), aca se recorren DOS arboles
al mismo tiempo, nodo por nodo, comparando cada para de posiciones correspondientes.

Tres casos base, cubriendo todas las combinaciones de "nodo existe / no existe" entre
p y q en la misma posicion:

1. Ambos son null: coinciden en que "se acabaron" en el mismo lugar -> return true

2. Solo uno de los dos es null (el otro no): un arbol tiene un nodo extra que
el otro no tiene ahi -> return false

3. Ninguno es null: comparar el valor de ambos (p.val == q.val) Y (con &&) que sus hijos
izquierdos sean iguales entre si Y sus hijos derechos sean iguales entre si, aplicando
la MISMA funcion recursivamente

```java
public static boolean SameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if ((p == null && q != null) || (p != null && q == null)) return false;
    return (p.val == q.val) && SameTree(p.left, q.left) && SameTree(p.right, q.right);
}
```

- Tiempo: O(n) - n =  cantidad de nodos del arbol mas chico (en el peor caso, ambos
del mismo tamaño). Cada par de nodos se visita una vez

- Espacio: O(h) - por la pila de llamadas recursivas, proporcional a la altura del 
arbol

## Cortocircuito de && (optimizacion automatica de Java)
En la linea final, si (p.val == q.val) ya da false, Java NO ejecuta las llamadas
recursivas siguientes (SameTree(p.left, q.left) ni la de la derecha) - el resultado
final del && ya se sabe que sera false sin necesidad de seguir evaluando. Esto corta
la comparacion apenas se encuentra la primera diferencia, sin explorar el resto
del arbol innecesariamente.

## Construccion de arboles de prueba - importante
Para probar la comparacion real, se necesitan DOS arboles construidos con nodos
COMPLETAMENTE SEPARADOS (aunque tengan los mismos valores), no reutilizar los mismos
objetos TreeNode para ambos - si no, no se esta probando la logica de comparacion, 
solo se compara un arbol contra si mismo.

## Errores que cometi en el camino
- Al principio intente construir un solo conjunto de nodos y "asignarlos" a p y q
con sintaxis invalida (p.raiz = raiz, un campo que no existe en TreeNode).

- Agregue lineas de calculo de profundidad (mezcladas del problema anterior, Maximum Depth)
que no correspondian a este problema - SameTree necesita DOS parametros, no uno.

- Falto un parentesis envolviendo toda la condicion del caso base con ||, dejando el
operador fuera del alcande del if.

## Leccion general
Primer problema recursivo que compara DOS estructuras en simultaneo, en vez de explorar
una sola. El patron de casos base se vuelve mas complejo (3 combinaciones de null en vez 
de 1), pero la logica de fondo sigue sinedo la misma: casos base que cubren las 
situaciones limite, y un caso recursivo que aplica la misma pregunta a partes
mas chicas del problema (aca, a los hijos de ambos arboles en simultaneo).

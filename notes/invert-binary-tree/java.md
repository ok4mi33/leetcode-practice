# Invert Binary Tree - Java

## Problema 
Dado el nodo raiz de un arbol binario, invertirlo (voltearlo como espejo): cada
nodo intercambia su hijo izquierdo con su hijo derecho, en TODOS los niveles del arbol.

## Enfoque (recursion + intercambio en cada nodo)
En cada nodo: intercambiar nodo.left con nodo.right, y despues aplicar la MISMA
operacion recursivamente a ambos subarboles (ya intercambiados), para que la inversion
llegue a todos los niveles, no solo a la raiz.

Caso base: si el nodo es null, no hay nada que invertir, return null.

```java
public static TreeNode invertirArbol(TreeNode nodo) {
    if (nodo == null) return null;
    TreeNode temp = nodo.left;
    nodo.left = nodo.right;
    nodo.right = temp;
    invertirArbol(nodo.left);
    invertirArbol(nodo.right);
    return nodo;
}
```

- Tiempo: O(n) - cada nodo se visita una vez, trabajo constante (el intercambio) por
nodo
- Espacio: O(h) - pila de llamadas recursivas, proporcional a la altura del arbol

## Patron de intercambio de dos variables (swap)
Intercambiar dos valores sin perder ninguno REQUIERE una variable temporal - no se puede
 hacer solo dos asignaciones directar (a = b; b = a;), porque la primera asignacion
 ya sobrescribe el valor original de a antes de poder guardarlo en b.

Patron correcto, en 3 pasos:
  TreeNode temp = nodo.left;   // guarda el valor ANTES de perderlo
  nodo.left = nodo.right;
  nodo.right = temp;           // usa el valor guardado, no el ya modificado

Este patron de swpa con variable temporal es general, se aplica a cualquier par de
valores (numeros, nodos, lo que sea), no solo a arboles.

## Primera vez modificando la estructura, no solo leyendola
A diferencia de Maximum Depth (lee profundidad) y Same Tree (compara sin modificar),
este problema MODIFICA el arbol original directamente ("en el lugar"), reasignando los
campos .left/.right de los nodos existentes, sin crear una copia nueva del arbol.

## Errores que cometi en el camino
- La clase TreeNode quedo sin cerrar (falto la llave de cierre), haciendo que 
invertirArbol quedara anidado DENTRO de TreeNode en vez de al mismo nivel.
- El metodo se declaro como int (tipo de retorno), cuando debia devolver TreeNode (o
null) - intentaba hacer return null y return nodo desde un metodo tipeado como int.
- temp usado sin declarar su tipo la primera vez.
- Intente crear y conectar un nodo en una sola linea invalida (TreeNode p.izq = new
TreeNode(3)) - hay que separar: primero crear el nodo con un nombre simple, despues
conectarlo con p.left = nombre (dos pasos, no uno).
- Intente usar p.der en vez de p.right - los campos de TreeNode se llaman left y right,
tal como se declararon en la clase.

## Leccion general
Primer problema de arboles que MODIFICA la estructura en vea de solo leerla o 
compararla. El patron recursivo de fondo (caso base null + aplicar la misma operacion
a ambos subarboles) se mantiene igual que en Maximum Depth y Same Tree, pero ahora
con un efeto secundario (side effect) sobre los datos originales, no solo un calculo
que se lee y se devuelve.

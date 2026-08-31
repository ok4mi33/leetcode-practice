# Binary Tree Level Order Traversal - Java (BFS)

## Problema
Dado el nodo raiz de un arbol binario, devolver sus valores nivel por nivel 
(izquierda a derecha), cada nivel como una lista separada. Ej: [[3], [9,20], [15,7]]

## Enfoque (BFS con Queue)
A diferencia de los problemas anteriores de arboles (recursion, que profundiza una rama
completa antes de pasar a la otra), este problema necesita procesar TODO un nivel
horizontal antes de pasar al siguiente - para eso se usa una Queue (cola FIFO: el
primero que entra es el primero en salir), no recursion.

Analogia: una fila de personas. Se "atiende" (poll) al primero de la fila, y sus 
hijos se agregan (offer) al FINAL de la fila para ser atendidos despues. Esto
garantiza que se procesan en el orden correcto: nivel por nivel, sin saltarse ninguno.

Truco clave: ANTES de empezar a procesar un nivel, se guarda cola.size() en tamañoNivel -
ese numero exacto de elementos pertenecen al nivel actual. Los hijos que se van agregando
a la cola DURANTE el procesamiento de ese nivel quedan reservados para la siguiente
vuelta del while externo, sin mezclarse con el nivel actual, aunque ya esten 
fisicamente en la cola. 

```java
public static List<List<Integer>> levelOrder(TreeNode raiz) {
    Queue<TreeNode> cola = new LinkedList<>();
    cola.offer(raiz);
    List<List<Integer>> resultado = new ArrayList<>();

    while (!cola.isEmpty()) {
        int tamañoNivel = cola.size();
        List<Integer> nivelActual = new ArrayList<>();
        for (int i = 0; i < tamañoNivel; i++) {
            TreeNode actual = cola.poll();
            nivelActual.add(actual.val);
            if (actual.left != null) cola.offer(actual.left);
            if (actual.right != null) cola.offer(actual.right);
        }
        resultado.add(nivelActual);
    }
    return resultado;
}
```

- Tiempo: O(n) - cada nodo se agrega y se saca de la cola exactamente una vez
- Espacio: O(n) - peor caso, el ultimo nivel de un arbol ancho puede tener hasta la
mitad de los nodos en la cola simultaneamente

## Por que dos loops con roles distintos (while + for)
while (externo): condicion DINAMICA (cola vacia o no), no se sabe de antemano cuantas
vueltas totales seran - depende de la estructura real del arbol.

for (internot): cantidad FIJA conocida de antemano en cada vuelta del while 
(tamañoNivel, calculado justo antes de empezar) - aunque ese numero cambie de nivel
a nivel, dentro de una sola vuelta del for es constante.

## Sintaxis nueva: Queue y List anidada

```java
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

Queue<TreeNode> cola = new LinkedList<>();
cola.offer(elemento);   // agrega al final
cola.poll();            // saca y devuelve el primero (o null si esta vacia)
cola.size();            // cantidad de elementos actual
cola.isEmpty();         // true/false

List<List<Integer>> resultado = new ArrayList<>();  // lista de listas
List<Integer> nivelActual = new ArrayList<>();
nivelActual.add(valor);         // agrega un elemento a la lista
resultado.add(nivelActual);     // agrega una lista COMPLETA dentro de la lista de listas
```

## Errores que cometi en el camino (varios, problema complejo)
- Confundi Queue con TreeNode/HashMap en los primeros intentos, usando metodos de una
estructura sobre otra (ej: actual.add(), cuando actual es TreeNode y quien tiene .add() 
es la lista).
- Reutilice la misma variable (nivelActual) para representar tanto el nodo sacado de 
la cola como la lista del nivel - deben ser variables separadas y de tipos distintos
(TreeNode vs List<Integer>).
- resultado.add(nivelActual) quedo DENTRO del for en vez de despues - causaba que cada
nivel se agregara varias veces a resultado, con la lista incompleta en las primeras
repeticiones (ej: [9] agregado primero, y [9, 20] agregado de nuevo).
- Se me olvido declarar la clase TreeNode completa en el archivo, causando 
"cannot find symbol".
- Quedaron lineas sueltas de un intento anterior sin limpiar (nivelActual declarada
dos veces, uso de una variables valor que nunca existio) - importante revisar el 
archivo completo despues de reescribir una seccion, no solo la parte que se esta
editando.

## Leccion general
Primer problema con BFS (Queue) en vez de DFS (recursion). BFS explora "por anchura"
(nivel por nivel), util quimicamente para problemas de "camino mas corto" en grafos
no ponderados - DFS explora "por profundidad" (una rama completa antes de la otra).
Ambas tecnicas se aplican sobre la misma clase de estructuras (arboles, grafos), pero
resuelven tipos de preguntas distintos.

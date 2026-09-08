# Binary Tree Right Side View - Java (BFS)

## Problema
Dado el nodo raiz de un arbol binario, devolver los valores de los nodos visibles
parado del lado DERECHO del arbol, ordenados de arriba hacia abajo. Es el ULTIMO
nodo procesado de cada nivel.

## Enfoque (BFS, mismo esqueleto + una condicion nueva)
Identica mecanica de recorrido por niveles con Queue que en Level Order Traversal
y Average of Leves. La unica pieza nueva: dentro del for interno, detectar cuando
i es la ULTIMA vuelta del nivel (i == cantidadEnNivel - 1) y en ese caso agregar
el valor de ese nodo al resultado - sin necesidad de guardar todos los valores del 
nivel en una lista intermedia.

```java
public static List<Integer> rightSideView(TreeNode raiz) {
    Queue<TreeNode> cola = new LinkedList<>();
    List<Integer> resultado = new ArrayList<>();
    cola.offer(raiz);
    while (!cola.isEmpty()) {
        int cantidadEnNivel = cola.size();
        for (int i = 0; i < cantidadEnNivel; i++) {
            TreeNode actual = cola.poll();
            if (i == cantidadEnNivel - 1) {
                resultado.add(actual.val);
            }
            if (actual.left != null) cola.offer(actual.left);
            if (actual.right != null) cola.offer(actual.right);
        }
    }
    return resultado;
}
```

- Tiempo: O(n) - cada nodo se agrega y se saca de la cola una vez
- Espacio: O(n) - peor caso, nivel mas ancho ocupa buena parte de la cola 
simultaneamente

## Por que i == cantidadEnNivel - 1 y no i == cantidadEnNivel
i va de 0 hasta cantidadEnNivel - 1 (nunca llega a valer cantidad exactamente, el for
se detiene antes). Ejemplo con cantidadEnNivel = 3: i toma los valores 0, 1, 2 - la
ultima vuelta es i=2, que es cantidadEnNivel - 1, no cantidadEnNivel.

## Este ejercicio SI se construyo desde cero (contraste con el anterior)
A diferencia de un ejercicio previo de refuerzo donde el codigo salio como copiar/adaptar
una soluacion muy parecida ya resuelta, este problema se escribio con errores genuinos
propios en cada paso (confundir cola con el nodo actual, orden de la firma del metodo,
variables sin definir) resueltos uno por uno con preguntas guiadas - la friccion real
de resolver un problema nuevo parece construir mas soltura que repetir un patron casi
identico ya visto.

## Errores que cometi en el camino
- Clases TreeNode con sintaxis de asignacion (TreeNode.raiz = raiz) en vez de declaracion
de campos simple.
- Nombre de metodo usando el nombre de una clase de Java (ArrayList) en vez de un nombre
propio.
- Orden de la firma del metodo invertido (tipo de retorno y nombre mezclados) - recordar:
TIPO_RETORNO NOMBRE(TIPO parametro).
- List<Integer> resultado = new List<>() - List es una interfaz, no se puede instanciar
directo, se usa una clase concreta como ArrayList.
- while (cola != null) y luego while (!cola == null) en vez de while (!cola.isEmpty()) - cola
en si nunca es null una vez creada, lo que puede estar vacio es su contenido.
- cola.poll() llamado dos veces seguidos sin guardar el resultado, perdiendo el primer
nodo sacado sin usarlo.
- actual.offer(node) - confundir la cola (quien tiene .offer) con el nodo sacado (quine
tiene .left/.right), y usar una variable node nunca definida en vez de actual.left/actual.right.
- Import de List faltante (solo se importo ArrayList).

## Leccion general 
Tercer problma de BFS. El esqueleto de Queue (offer/poll/size/ isEmpty + while/for anidados)
ya es reconocible como patron reutilizable; lo que cambia entre problemas de BFS es solo
el PROCESAMIENTO de cada nodo dentro del for (guardar en lista, sumar para promedio, o
detectar posicion como en este caso). Los errores de esta sesion fueron mayormente de sintaxis
y de mezclar que metodo pertenece a que objeto (cola vs nodo vs lista) - la logica 
de fondo (BFS nivel por nivel) ya se demuestra comprendida.

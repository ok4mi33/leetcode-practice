# Maximum Subarray - Java (DP, Algoritmo de Kadane)

## Problema 
Dado un array de enteros (puede tener negativos), encontrar el subarrelgo CONTIGUO
(elementos consecutivos, sin saltos) con la suma maxima, y devolver esa suma.

## Enfoque (recursion + memoizacion + maximo global)
Dos funciones con roles distintos:

### mejorTerminandoEn(nums, i) - resuelve el subproblema
"¿Cual es la suma maxima de un subarreglo contiguo que TERMINA exactamente en la posicion
i?" En cada posicion, dos opcions: 
- Extender el subarreglo anterior: mejorTerminandoEn(i-1) + nums[i]
- EMPEZAR DE NUEVO, solo con nums[i]

Se elige la mayor con Math.max. Verificado con ejemplo numerico:
si lo acumulado hasta i-1 es negativo (-3) y nums[i]=5, extender da 2 pero empezar de
nuevo da 5 - siempre conviene "cortar perdidas" cuando lo acumulado es negativo.

```java
static HashMap<Integer, Integer> calculo = new HashMap<>();

public static int mejorTerminandoEn(int[] nums, int i) {
    if (calculo.containsKey(i)) return calculo.get(i);
    if (i == 0) return nums[0];
    int resultado = Math.max(mejorTerminandoEn(nums, i-1) + nums[i], nums[i]);
    calculo.put(i, resultado);
    return resultado;
}
```

### maxSubArray(nums) - encuentra el maximo GLOBAL
A diferencia de House Robber (donde la respuesta final era simplemente mejorHasta(ultimo indice)),
aca el subarreglo maximo puede TERMINAR en cualquier posicion del array, no necesariamnte
en la ultima. Hay que recorrer TODAS las posiciones, calculando mejorTerminandoEn para
cada una, y quedarse con el mayor de todos (patron identico a maxGanancia en Best Time to Bus/Sell Stock).

```java
public static int maxSubArray(int[] nums) {
    int maximoGlobal = mejorTerminandoEn(nums, 0);
    for (int i = 0; i < nums.length; i++) {
        int llamada = mejorTerminandoEn(nums, i);
        maximoGlobal = Math.max(maximoGlobal, llamada);
    }
    return maximoGlobal;
}
```

- Tiempo: O(n) - mejorTerminandoEn memoizado (cada i una vez), mas un recorrido O(n) en
maxSubArray
- Espacio: O(n) - HashMap mas pila de llamadas recursivas

## Nombre famoso: Algoritmo de Kadane
Esta tecnica (llevar una suma acumulada, decidiendo en cada paso entre extender o reiniciar)
es un algoritmo clasico y muy conocido, vale la pena reconocer el nombre.

## Diferencia clave con House Robber
House Robber: la respuesta final coincidia con el resultado de la ULTIMA posicion
(mejorHasta(nums.length-1)). Maximum Subarray: el resultado optimo puede estar en
CUALQUIER posicion intermedia, asi que se necesita una funcion separada que recorra
y compare TODOS los resultados, no solo consultar el ultimo.

## Errores que cometi en el camino
- calculo.put(...) llamado como si calculo fuera una funcion en vez de una variable/objeto
(calculo(resultado, nums[i])).
- Argumentos de put() invertidos (nums[i] como valor en vez de resultado; la clave
real es i, no nums[i]).
- En maxSubArray, se repitio la formula completa de mejorTerminandoEn en vez de 
simplemente LLAMAR al metodo ya existent - reutilizar la funcion auxiliar es mas simple
y evita duplicar logica.
- Variable i usada fuera del for, donde no existe (i solo vive dentro del bloque del for
donde se declara).
- Condicion de actualizacion invertida (< en vez de >) al comparar contra el maximo
global.
- return dentro del for, cortando en la primera vuelta en vez de completar el recorrido
de todas las posicions antes de devolver el resultado final.
- Crear una variable nueva (resultado) en vez de REASIGNAR directamente 
maximoGlobal = Math.max(maximoGlobal, llamada) - patron de actualizar la misma 
variable acumuladora, no generar una copia con el nuevo valor.

## Leccion general
Tercer problema de DP. Introduce la distincion entre resolver un SUBPROBLEMA en una
posicion especifica (mejorTerminandoEn) versus encontrar la MEJOR RESPUESTA GLOBAL 
revisando todas las posiciones posibles (maxSubArray) - no siempre el ultimo indice
tiene la respuesta final, a veces hay que comparar explicitamente todos los resultado
intermedios.


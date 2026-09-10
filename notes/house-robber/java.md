# House Robber — Java (DP)

## Problema
Array de casas con dinero (nums[i]). No se pueden robar dos casas
adyacentes. Devolver el maximo dinero robable en total.

## Enfoque (recursion + memoizacion, con decision entre opciones)
Igual que en Climbing Stairs, se piensa en la ULTIMA decision:
parado en la casa i, hay exactamente dos opciones:

- ROBARLA: gano nums[i], pero no puedo haber robado la casa i-1
  (adyacente) — mi mejor resultado en ese caso es
  nums[i] + mejorHasta(i-2)
- NO ROBARLA: mi resultado es simplemente mejorHasta(i-1), sin cambios

Se elige la opcion que da el MAYOR resultado (verificado a mano con
nums=[2,7,9,3,1]: en i=3, robar da 3+7=10, no robar da 11 — se
elige no robar, 11 > 10).

```java
static HashMap<Integer, Integer> calculo = new HashMap<>();

public static int mejorHasta(int[] nums, int i) {
    if (calculo.containsKey(i)) {
        return calculo.get(i);
    }
    if (i == 0) return nums[0];
    if (i == 1) return Math.max(nums[0], nums[1]);
    int resultado = Math.max(nums[i] + mejorHasta(nums, i-2), mejorHasta(nums, i-1));
    calculo.put(i, resultado);
    return resultado;
}
```

Llamada inicial: mejorHasta(nums, nums.length - 1) — el indice del
ULTIMO elemento, no la cantidad total de casas (cuidado con el
desfase de -1).

- Tiempo: O(n) — con memoizacion, cada indice i se calcula una vez
- Espacio: O(n) — HashMap mas pila de llamadas recursivas

## Casos base
- mejorHasta(0) = nums[0] — una sola casa, se roba directo
- mejorHasta(1) = Math.max(nums[0], nums[1]) — dos casas adyacentes,
  no se pueden robar ambas, se elige la de mayor valor

## Diferencia clave con Climbing Stairs
Climbing Stairs SIEMPRE sumaba ambos subproblemas
(climbStairs(n-1) + climbStairs(n-2)), porque las formas de llegar
nunca se solapaban y todas contaban. Aca hay que DECIDIR entre dos
opciones EXCLUYENTES (robar o no robar) y quedarse con el maximo
(Math.max), no sumar ambas — patron de "elegir la mejor opcion en
cada paso", ya visto antes en Best Time to Buy/Sell Stock.

## Errores que cometi en el camino
- calculo.isEmpty() en vez de calculo.containsKey(i) — isEmpty()
  pregunta si el mapa completo esta vacio, no si una clave
  ESPECIFICA ya esta guardada. Con el mapa parcialmente lleno (de
  otras llamadas ya calculadas), isEmpty() daria false para
  cualquier i, incluso uno nunca calculado antes.
- Intente poner return dentro de los argumentos de otro metodo
  (calculo.get(return ...)) — return no puede usarse como argumento,
  es una instruccion que termina el metodo, no un valor a pasar.
- Uso de una variable n que no existia en el metodo (el parametro
  se llama i).
- Al llamar el metodo desde main, pase nums.length (4) en vez de
  nums.length - 1 (3) como indice — con un array de 4 elementos,
  los indices validos van de 0 a 3, no hasta 4.

## Leccion general
Segundo problema formal de DP. Confirma el patron de "pensar en la
ultima decision" como estrategia general para reconocer la
recurrencia. La variante nueva aca: no todos los problemas de DP
suman subproblemas (como Fibonacci/Climbing Stairs) — muchos
requieren ELEGIR entre opciones excluyentes con Math.max/Math.min,
segun si el problema pide maximizar o minimizar algo.

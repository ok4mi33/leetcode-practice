# Contains Duplicate - Java

## Problema
Dado un array de enteros, devolver true si algun valor aparece al menos dos veces,
false si todos son distintos.

## Enfoque (HashSet)
Un solo loop. Por cada numero, pregunto si ya esta guardado en el HashSet. Si esta,
hay un duplicado y corto de inmediato con return true. Si no esta, lo agrego al 
HashSet y sigo. Si termino de recorrer todo el array sin encontrar ninguno, return false.

- Tiempo: O(n) - Un solo recorrido, contains/add son O(1) promedio
- Espacio: O(n) - en el peor caso (sin duplicados) el HashSet termina con los n 
elementos guardados.

## HashSet vs HashMap
Aca no necesitaba un valor asociado a cada numero (como el indice en Two Sum), solo
saber si ya lo habia visto. Por eso HashSet (guarda valores sueltos) en vez de 
HashMap (guarda pares clave-valor).

- HashSet: .add(x), .contains(x)
-HashMap: .put(clave, valor), .containsKey(x), .get(x)

## Return dentro y fuera del loop
El return true va DENTRO del loop, en el momento exacto que se encuentra el duplicado
(no hace falta seguir buscando). El return false va FUERA del loop, porque solo se
puede afirmar que no hay duplicados una vez que se recorrio el array completo - 
no se puede decidir eso a mitad de camino.

### Errores que cometi en el camino
- Declare un metodo dentro de otro metodo (containsDuplicate anidado dentro de main).
En java los metodos van uno al laod del otro de la clase, nunca uno dentro de otro.

- Todo el codigo (imports, variables, metodos) estaba fuera de cualquier class al 
principio.

- Error de sintaxis en el for: use coma en vez de punto y coma para separar sus tres
partes.

- Olvide el punto y coma al final de numerosVistos.add(nums[i]).

## Leccion general
Mismo patron de fondo que Two Sum (usar una estructura hash para evitar recorrer el
array dos veces, O(n)en vez de O(n^2)), pero con la herramienta ajustada segun la 
necesidad: HashSet cuando solo importa "¿ya lo vi?", HashMap cuando ademas necesito recuperar
un valor asociado.

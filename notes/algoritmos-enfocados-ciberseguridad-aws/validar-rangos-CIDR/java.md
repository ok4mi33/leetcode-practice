# Validar Rangos CIDR

> **Tercer problema del nuevo criterio de seleccion** (conexion con seguridad/AWS).
No corresponde a un numero especifico de LeetCode tan conocido como los anteriores,
pero es un ejercicio real y comun en entrevistas de sistemas/redes.
>
> Conexion directa con AWS: asi es exactamente como se expresan los rangos de IP en
las reglas de un Security Group o de una tabla de rutas de una VPC 
(ejemplos reales: `0.0.0.0/0` para "todo el internet", `10.0.0.0/16` para una VPC 
completa).
> 
> Completa la serie de validacion de IPs: IPv4 → IPv6 → CIDR.

## Problema

Dado un string con formato `"IP/prefijo"` (ej. `"192.168.1.0/24"`), determinar si 
representa un rango CIDR valido.

- La parte `IP` debe ser una **IPv4 valida**.
- La parte `prefijo` debe ser un entero entre `0` y `32` (inclusive).

**Conexion con seguridad/AWS:**
Un Security Group con una regla mal validad como `"0.0.0.0/-1"` o `"10.0.0.0/99"` podria,
en un sistema real, interpretarse de forma inesperada - abriendo acceso mucho mas amplio
del pretendido, o siendo rechazada de forma silenciosa cuando deberia haber alertado a alguien.

## Pseudocogio (planteado antes de escribir Java)

función esCIDRValido(cidr):
partes = separar cidr por "/"
si cantidad de partes != 2:
devolver false

ipParte = partes[0]
prefijoParte = partes[1]

si NO esIPv4Valida(ipParte):
    devolver false

intentar:
    prefijo = convertir prefijoParte a número
    si prefijo < 0 o prefijo > 32:
        devolver false
si falla la conversión:
    devolver false

devolver true

## Enfoque
1. Separar el string por `/` con `split("/")`. La barra normal (`/`) **no es un caracter
especial en regex** (a diferencia del punto `.`), asi que no necesita escaparse. Se 
obtienen 2 partes: la IP y el prefijo.

2. Verificar que el array tenga exactamente 2 elementos - si no, `return false`.

3. **Reutilizar el metodo `esIPv4Valida` ya construido y probado** para validar la
parte de la IP, en vez de reescribir esa logica desde cero. Si `!esIPv4Valida(ipParte)`, `return false`.

4. Convertir la parte del prefijo a `int` con `Integer.parseInt(...)`, dentro de un
`try`, porque el input puede no ser numerico. Verificar el rango `0-32` dentro del
mismo `try`. En el `catch`, loguear el error y `return false`.

5. Si todo pasa, `return true`.

```java
public static boolean esCIDRValido(String cidr) {

    String[] partes = cidr.split("/");

    if (partes.length != 2) {
        return false;
    }

    String ipParte = partes[0];
    String prefijoParte = partes[1];

    if (!esIPv4Valida(ipParte)) {
        return false;
    }

    try {
        int prefijo = Integer.parseInt(prefijoParte);

        if (prefijo < 0 || prefijo > 32) {
            return false;
        }

    } catch (NumberFormatException e) {
        System.out.println("Parte invalida (no numerica): " + prefijoParte);
        return false;
    }

    return true;
}
```

## Casos de prueba verificados

| Caso | Input | Motivo | Esperado | Obtenido |
|---|---|---|---|---|
| 1. Válido simple | `"192.168.1.0/24"` | — | true | true ✅ |
| 2. Sin barra | `"192.168.1.0"` | `split` da 1 parte, no 2 | false | false ✅ |
| 3. IP inválida | `"256.168.1.0/24"` | reutiliza `esIPv4Valida`, detecta `256` fuera de rango | false | false ✅ |
| 4. Prefijo fuera de rango | `"192.168.1.0/33"` | `33 > 32` | false | false ✅ |
| 5. Prefijo no numérico | `"192.168.1.0/abc"` | dispara `NumberFormatException`, capturada | false | false ✅ |
| 6. Borde inferior | `"0.0.0.0/0"` | prefijo `0` es válido y común en AWS ("todo el internet") | true | true ✅ |
| 7. Borde superior | `"192.168.1.1/32"` | prefijo `32` es válido y común en AWS ("una sola IP exacta") | true | true ✅ |

Los casos 6 y 7 tienen relevancia directa en AWS: `/0` y `/32` son prefijos extermos
que aparecen constantemente en reglas reales de Security Groups, y un error de `<` vs
`<=` en los limites del rango los habria rechazado incorrectamente.

## Complejidad

**Tiempo:** O(1).
El metodo hace tres cosas en secuencia: un `split` sobre un string de tamaño acotado, una llamada
a `esIPv4Valida` (ya establecida como O(1)), y una conversion + comparacion del prefijo
(trabajo fijo). La suma de varias piezas O(1) sigue siendo O(1) - Big O descarta la 
cantidad exacta de pasos y solo expresa el trabajo crece con el tamaño de la entrada, 
que aqui nunca ocurre.

**Espacio:** O(1).
El array `partes` siempre tiene 2 elementos, y las variables `ipParte`/`prefijoParte`
no dependen de ningun tamaño de entrada variable.

Nota conceptual: que una pieza del metodo sea una **llamada a otra funcion** (en vez
de codigo escrito directamente ahi) no cambia el analisis de complejidad. La funcion
que llama "hereda" la complejidad de lo que llama - si `esIPv4Valida` hubiera sido O(n),
`esCIDRValido` tambien lo habria sido. Como era O(1), no agrega ningun crecimiento.

## Errores cometidos en el camino

1. Parentesis faltante un `if(!esIPv4Valida(ipParte){` - faltaba cerrar el parentesis
de la llamada al metodo antes de abrir la llave del `if`.
2. El bloque `try` nunca se cerro con `}` antes de escribir `catch`, dejando el `if`
de rango del prefijo mal anidado.
3. Dentro del `catch`, se reutilizo por error la variable `parte` (que pertenece a 
`esIPv4Valida`, dentro de su `for`) en vez de `prefijoParte`, que es la variable relevante
en este metodo.
4. Falta el punto y coma en `return true`.
5. Llave de cierre duplicada al final del metodo (una de mas, sobrante de una iteracion
anterior del codigo).
6. Caso de prueba 4 definido conceptualmente como "prefijo fuera de rango" pero probado
inicialmente con `/32` (un valor valido, limite superior permitido) en vez de `/33`.

## Leccion general

Reutilizar un metodo ya construido y **probado** (`esIPv4Valida`) en vez de reescribir
su logica desde cero evita duplicar errores ya corregidos y reduce drasticamente el 
codigo a mantener. En un sistema real de validacion de reglas de seguridad, si la 
misma logica estuviera copiada en varios lugares, un bug descubierto mas adelante
tendria que corregirse en cada copia por separado - y es facil de olvidar alguna,
dejando un hueco de seguridad sin resolver en un lugar que nadie reviso. Construir 
piezas pequeñas, probarlas una vez, y componerlas (como aqui `esCIDRValido` compone
`esIPv4Valida`) es mas seguro y mas mantenible que reescribir todo cada vez.

A nivel de complejidad, quedo reforzada la idea de que sumar varias piezas O(1)
sigue siendo O(1), y que la complejidad de un metodo que deleda trabajo a otra funcion
depende del trabajo real que esa funcion hace, no de si el codigo esta "a la vista" o no.




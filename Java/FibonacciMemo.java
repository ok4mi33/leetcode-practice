
import java.util.HashMap;

public class FibonacciMemo{

  // declaro un HashMap vacio (n -> resultado), FUERA de la funcion recursiva
  // ( o se lo paso como parametro extra a la funcion)
  // HashMap a nivel de clase, compartido por todas las llamadas
  public static HashMap<Integer, Integer> memo = new HashMap<>();

  //funcion fib(n):
  public static int fib(int n) {

    // si n ya esta en el HashMap:
    if(memo.containsKey(n)){

      // return el valor guardado (sin volver a calcular nada)
      return memo.get(n);


    }

    // si n es caso base (0 o 1):
    if(n == 0 || n == 1) {
      
      return n;
    }

    // (si no es caso base y no esta en el mapa)
    int resultado = fib(n-1) + fib(n-2);

    // gurdado en el HashMap: n -> resultado
    memo.put(n, resultado);

    return resultado;

  }

  public static void main(String[] args) {

    System.out.println(fib(10));
  }


}

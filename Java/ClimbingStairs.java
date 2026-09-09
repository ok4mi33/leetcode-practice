import java.util.HashMap;


public class ClimbingStairs {

  static HashMap<Integer, Integer> calculo = new HashMap<>();


  public static int climbStairs(int n) {


   
    if(calculo.containsKey(n)){

      return calculo.get(n);
    }

    // si n es 1: return 1 (caso base)
    if(n == 1) {
      return 1;
    }
    if(n == 2) {
      return 2;
    }

    int resultado = climbStairs(n-1) + climbStairs(n-2);
    calculo.put(n, resultado);

    return resultado;
    
    
  }



  public static void main (String[] args){


    System.out.println(climbStairs(5));
  }
}

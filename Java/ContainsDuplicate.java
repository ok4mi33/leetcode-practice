import java.util.HashSet;


public class ContainsDuplicate {

  // metodo que resuelve el problema
  public static boolean containsDuplicate(int[] nums) {

    // declaro el HashSet vacio
    HashSet<Integer> numerosVistos = new HashSet<>();

    // recorro el array (un solo loop, con i)
    for(int i = 0; i < nums.length; i++) {

      // si el HashSet ya contiene nums[i]
      if(numerosVistos.contains(nums[i])) {
        
        // trae el return
        return true;
      } else {
        numerosVistos.add(nums[i]);
      }
    }
    return false;
  }

  // metodo main, que llama a containsDuplicate y muestra el resultado
  public static void main(String[] args) {

    // declaro el array de prueba
    int[] nums = {1, 3, 4, 5};

    // llamo a containsDuplicate pasadole el array, guardo el resultado
    
    boolean resultado = containsDuplicate(nums);

    // imprimo el resultado
    System.out.println(resultado);
  }
}









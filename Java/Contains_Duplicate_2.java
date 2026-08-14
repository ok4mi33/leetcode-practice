
import java.util.HashMap;


public class Contains_Duplicate_2 {

  public static boolean numeros(int[] nums, int k) {
  
    // declaro un HashMap vacio (numero -> indice)
    HashMap<Integer, Integer> claves = new HashMap<>();

    // para cada numero del array (un solo loop, con i):
    for(int i = 0; i < nums.length; i++){

      // si el numero YA esta en el HahsMap
      if(claves.containsKey(nums[i])) {
        
        // calculo la distancia = | i - indice_guardado|

        int distancia = Math.abs(i - claves.get(nums[i]));

        // si esa distancia es <= k:

        if(distancia <= k) {
          return true;
        }

       
      
      }
       //(en cualquier caso, haya estado antes o no)

        // actualizo el HashMap: numero -> i (guardo/reemplazo el indice mas reciente)
        claves.put(nums[i], i);

    }
    return false; // recorri todo y nunca hubo un par valido)



  }

  public static void main(String[] args) {
    
    int[] nums = {1, 2, 3, 1};

    int k = 1;

    boolean resultado = numeros(nums, k);

    System.out.println(resultado);
    
  }


}

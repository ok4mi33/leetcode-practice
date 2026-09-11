import java.util.HashMap;

public class MaximumSubarray {

  static HashMap<Integer, Integer> calculo = new HashMap<>();

  public static int mejorTerminandoEn(int[] nums, int i) {

    // si i ya esta en calculo, devolver el valor guardado directamente
    if(calculo.containsKey(i)){
      return calculo.get(i);
    }

    // caso base: si i == 0, return nums[0]
    if(i == 0){
      return nums[0];
    }

    // calcular: Math.max(mejorTerminandoEn(nums, i-1) + nums[i], nums[i])
    int resultado = Math.max(mejorTerminandoEn(nums, i-1) + nums[i], nums[i]);

    calculo.put(i,resultado);
    return resultado;
  }

  public static int maxSubArray(int[] nums) {

    // aqui va la parte NUEVA que falta pensar en detalle:
    //
    // recorrer TODAS las posiciones (0 hasta nums.length-1),
    int maximoGlobal = mejorTerminandoEn(nums, 0);
    for(int i = 0; i < nums.length; i++) {

      int llamada = mejorTerminandoEn(nums, i); 

      maximoGlobal = Math.max(maximoGlobal, llamada);

      
      // con el maximo GLOBAL entre todos esos resultados
      // (no solo el de la ultima posicion, como en House Robber)

    }

      return maximoGlobal;

  }
  public static void main(String[] args) {
    int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    System.out.println(maxSubArray(nums));
  }
}

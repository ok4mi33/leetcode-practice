import java.util.HashMap;


public class HouseRobber {



  static HashMap<Integer, Integer> calculo = new HashMap<>();

  public static int mejorHasta(int[] nums, int i){

    if(calculo.containsKey(i)){
      return calculo.get(i);

    }

    if(i == 0) {

      return nums[0];
    }
    if(i == 1) {
      return Math.max(nums[0], nums[1]);
    }


    int resultado = Math.max(nums[i] + mejorHasta(nums, i-2), mejorHasta(nums, i-1));

    calculo.put(i, resultado);
    return resultado;
  }

  


  public static void main (String[] args) {

    int[] nums = {1, 2, 3, 1};

    System.out.println(mejorHasta(nums, 3));
  }
}

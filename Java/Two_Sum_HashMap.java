import java.util.HashMap; // declaracion: HashMap<TipoDeClave, TipoDeValor> nombre = new HashMap<>();
			  // .put(clave, valor)
			  // .containsKey(algo)
			  // .get(clave)



public class Two_Sum_HashMap {
	public static void main(String[] args) {
		
    // defino el array de numeros
    int[] nums = {3, 4, 3, 4 ,5, 6};

    // defino el target
    int target = 9;
    
    // declaro el HashMap vacio (clave = numer, valor = indice)
    HashMap<Integer, Integer> recorrido = new HashMap<>();
    
    // recorro el array una sola vez (loop)
    for(int i = 0; i < nums.length; i++){
    
          // calculo el complemento (target - numero actual)
          int complemento = target - nums[i];
          
          // pregunto si el complemento ya esta guardado en el HashMap
          if(recorrido.containsKey(complemento)){
          //
                  // si esta, imprimo el indice guardado (con .get) y el indice actual
                  System.out.println(recorrido.get(complemento) + "," + i);
                  
          // si no esta, guardo el numero actual con su indice en el HashMap
          } else { 
          recorrido.put(nums[i], i);
          
          
      }
    }
  }
}






public class Binary_Search {


  public static int Busqueda(int[] nums, int target) {


    // declaracion de variables


    // izquierda = 0
    int izquierda = 0;

    // derecha = nums.length -1
    int derecha = nums.length -1;

    

    // loops

    // mientras izquierda sea menor o igual que derecha:
    while(izquierda <= derecha){

      // medio = (izquierda + derecha) / 2
      int medio = (izquierda + derecha) / 2;

      // si nums[medio] == target:
      if (nums[medio] == target){

        return medio; // encontrado
      }
      if (target > nums[medio]){
        izquierda = medio + 1; // muevo izquierda hacia la derecha del medio (descarto la mitad izquierda)                     

      }
      if (target < nums[medio]){
        derecha = medio - 1; // muevo derecha hacia la izquierda del medio (descarto la mitad derecha)

      }
    }
    return -1; // recorri todo el rango posible y no lo encontre
    
    
  }

  public static void main(String[] args){

    int[] nums = {-1, 0, 3, 5, 9, 12};
    int target = 2;

    int resultado = Busqueda(nums, target);

    System.out.println(resultado);

  }
}


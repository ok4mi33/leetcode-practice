
import java.util.*;


public class CourseSchedule {

  // Atributos de clase: declarados arriba, sin valor todavia
  static List<List<Integer>> grafo;

  static int[] estado;

  // Metodo principal: aqui viven tus for sueltos
  public static boolean hayCiclo(int n, int[][] aristas) {

    // 1. Crear grafo y estado (ahora si existe `n`)
    grafo = new ArrayList<>();
    estado = new int[n];

    // 2. Llenar grafo con n listas vacias
    for(int i = 0; i < estado.length; i++){

      grafo.add(new ArrayList<>());
    }

    // 3. Cargar las aristas (ahora si existe `aristas`)
     for (int[] arista : aristas) {

      grafo.get(arista[0]).add(arista[1]);
    }

    // 4. Llamar a tieneCiclo desde cada nodo sin visitar
    for (int i = 0; i < n; i++) {
      if (estado[i] == 0) {
        if (tieneCiclo(i)) {
          return true;
        }
      }
    }

    return false;
  
  }
   
  // DFS: tu funcion tal cual, sin cambios 
   static boolean tieneCiclo(int nodo) {
    estado[nodo] = 1;

    for (int vecino : grafo.get(nodo)) {
      if (estado[vecino] == 1) {
        return true;
      }

      if (estado[vecino] == 0) {
        if (tieneCiclo(vecino)) {
          return true;
        }
      }
    }

    estado[nodo] = 2;

    return false;

  }


  


  public static void main(String[] args) {

    boolean resultado1 = hayCiclo(3, new int[][]{{0,1},{1,2},{2,0}});
    System.out.println("Caso 1: esperado=true, obtenido=" + resultado1);
    
    boolean resultado2 = hayCiclo(3, new int[][]{{0,1},{1,2}});
    System.out.println("Caso 2: esperado=false, obtenido=" + resultado2);
    
    boolean resultado3 = hayCiclo(4, new int[][]{{0,1},{0,2},{1,3},{2,3}});
    System.out.println("Caso 3: esperado=false, obtenido=" + resultado3);
    
    boolean resultado4 = hayCiclo(4, new int[][]{{0,1},{2,3},{3,2}});
    System.out.println("Caso 4: esperado=true, obtenido=" + resultado4);
    
    boolean resultado5 = hayCiclo(3, new int[][]{});
    System.out.println("Caso 5: esperado=false, obtenido=" + resultado5);
    
    boolean resultado6 = hayCiclo(1, new int[][]{{0,0}});
    System.out.println("Caso 6: esperado=true, obtenido=" + resultado6);
    
    boolean resultado7 = hayCiclo(2, new int[][]{{0,1},{1,0}});
    System.out.println("Caso 7: esperado=true, obtenido=" + resultado7);







    
  }
}

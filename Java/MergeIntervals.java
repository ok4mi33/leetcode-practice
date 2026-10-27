
import java.util.*;

public class MergeIntervals {

  public static int[][] merge(int[][] intervalos) {

    if (intervalos.length == 0) {
      return new int[][] {};
    }

    Arrays.sort(intervalos, (a,b) -> a[0] - b[0]);

    List<int[]> resultado = new ArrayList<>();
    resultado.add(intervalos[0]);

    for (int i = 1; i < intervalos.length; i++) {

      int[] nuevo = intervalos[i];
      int[] ultimo = resultado.get(resultado.size() - 1);

      if (nuevo[0] <= ultimo[1]) {
        ultimo [1] = Math.max(ultimo[1], nuevo[1]);

      } else {
        resultado.add(nuevo);
      }

    }
      return resultado.toArray(new int[resultado.size()][]);
  }

  public static void main(String[] args) {

    int[][] resultado1 = merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
    System.out.println("Caso 1: esperado=[[1, 6], [8, 10], [15, 18]], obtenido=" + Arrays.deepToString(resultado1));

    int[][] resultado2 = merge(new int[][]{{1,4},{4,5}});
    System.out.println("Caso 2: esperado=[[1, 5]], obtenido=" + Arrays.deepToString(resultado2));

    int[][] resultado3 = merge(new int[][]{{1,10},{2,5}});
    System.out.println("Caso 3: esperado=[[1, 10]], obtenido=" + Arrays.deepToString(resultado3));

    int[][] resultado4 = merge(new int[][]{{8,10},{1,3},{2,6}});
    System.out.println("Caso 4: esperado=[[1, 6], [8, 10]], obtenido=" + Arrays.deepToString(resultado4));

    int[][] resultado5 = merge(new int[][]{{1,3}});
    System.out.println("Caso 5: esperado=[[1, 3]], obtenido=" + Arrays.deepToString(resultado5));

    int[][] resultado6 = merge(new int[][]{});
    System.out.println("Caso 6: esperado=[], obtenido=" + Arrays.deepToString(resultado6));


  }
}

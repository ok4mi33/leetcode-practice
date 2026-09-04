import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class AverageLevelsBinaryTree {

  public static class TreeNode {

      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int val) {
        this.val = val;
      }
  }

  public static List<Double> averageOfLevels(TreeNode raiz) {

    // meto la raiz en la cola
    Queue<TreeNode> cola = new LinkedList<>();
    cola.offer(raiz);

    List<Double> resultado = new ArrayList<>();



    //mientras haya elementos en la cola:
    while (!cola.isEmpty()) {

    // cantidadEnNivel = cola.size()
    int cantidadEnNivel = cola.size();
    //
    // sumaNivel = 0
    int sumaNivel = 0;
    //
    //
    // repito "cantidadEnNivel" veces:
    for (int i = 0; i < cantidadEnNivel; i++) {
    //
    //
    // saco un nodo de la cola (poll)
      TreeNode actual = cola.poll();
    //
    // le sumo a sumaNivel el valor del nodo que acabo de sacar
      sumaNivel += actual.val;
    //
    // si el nodo tiene hijo izquierdo, lo meto a la cola
      if (actual.left != null) {
      cola.offer(actual.left);
      }
      if (actual.right != null) {
      cola.offer(actual.right);
      }
    //
    }
    // promedio = sumaNivel / cantidadEnNivel (como decimal)
    double promedio = (double) sumaNivel / cantidadEnNivel;
    //
    //
    // agrego ese promedio a la lista de resultados
    resultado.add(promedio);
    //
    //
    }
    //
    // devuelvo la lista de resultados
    return resultado;
    //
    //
   }



  
  public static void main(String[] args) {

    TreeNode raiz = new TreeNode(3);
    TreeNode izq = new TreeNode(9);
    TreeNode der = new TreeNode(20);

    raiz.left = izq;
    raiz.right = der;

    TreeNode derizq = new TreeNode(15);
    TreeNode derder = new TreeNode (7);

    der.left = derizq;
    der.right = derder;


    System.out.println(averageOfLevels(raiz));






  }
}




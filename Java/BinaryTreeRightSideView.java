import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


public class BinaryTreeRightSideView {

  public static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
      this.val = val;
    }

    
  }

  public static List<Integer> rightSideView(TreeNode raiz) {

      Queue<TreeNode> cola = new LinkedList<>();
      List<Integer> resultado = new ArrayList<>();

      cola.offer(raiz);

      while (!cola.isEmpty()) {

        int cantidadEnNivel = cola.size();

        for(int i = 0; i < cantidadEnNivel; i++) {

          TreeNode actual = cola.poll();

          
          if(i == cantidadEnNivel - 1) {
            resultado.add(actual.val);
          }
          
          if(actual.left != null) {
            cola.offer(actual.left);
          }
          if(actual.right != null) {
            cola.offer(actual.right);

          }
        }
      }

      return resultado;
    }

  public static void main(String[] args) {

    TreeNode raiz = new TreeNode(1);
    TreeNode izq = new TreeNode(2);
    TreeNode der = new TreeNode(3);

    raiz.left = izq;
    raiz.right = der;

    TreeNode izqder = new TreeNode(5);
    TreeNode derder = new TreeNode (4);

    izq.right = izqder;
    der.right = derder;


    System.out.println(rightSideView(raiz));



  }

  
}

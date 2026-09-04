import java.util.Queue;
import java.util.LinkedList;

public class BFSPrueba {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
      this.val = val;
    }
  }

  public static void main (String[] args) {

    Queue<TreeNode> numeros = new LinkedList<>();
    

    TreeNode raiz = new TreeNode(5);
    TreeNode izq = new TreeNode(3);
    TreeNode der = new TreeNode(8);

    raiz.left = izq;
    raiz.right = der;

    TreeNode derizq = new TreeNode(1);
    TreeNode derder = new TreeNode (4);

    der.left = derizq;
    der.right = derder;
    // meto raiz en la cola
    numeros.offer(raiz);

    while(!numeros.isEmpty()){

      TreeNode sacado = numeros.poll();

      System.out.println(sacado.val);

      if(sacado.left != null) {

        numeros.offer(sacado.left);
      }
      if(sacado.right != null) {

        numeros.offer(sacado.right);
      }

    }


  }

}

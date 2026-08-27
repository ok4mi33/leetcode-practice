
public class InvertBinaryTree {


  public static class TreeNode {

      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int val) {
        this.val = val;
      }
  }

  public static TreeNode invertirArbol(TreeNode nodo) {


    // si nodo es null (caso base):
    if(nodo == null) {

      return null;
    }


    // intercambio
    TreeNode temp = nodo.left;
    nodo.left = nodo.right;
    nodo.right = temp;


    // aplico la misma funcion a ambos subarboles, para invertir TODOS los niveles
    invertirArbol(nodo.left);
    invertirArbol(nodo.right);


    return nodo;



  }

  public static void main(String[] args) {

    TreeNode p = new TreeNode(4);

    TreeNode izq = new TreeNode(3);

    TreeNode der = new TreeNode(6);

    p.left = izq;

    p.right = der;

   TreeNode resultado = invertirArbol(p);

   System.out.println(resultado.left.val);

  }


}

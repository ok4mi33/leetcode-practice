

public class TreeNodePrueba {

  public static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
      this.val = val;
    }
  }

  public static void main (String[] args) {

    TreeNode raiz = new TreeNode(5);
    TreeNode izq = new TreeNode(3);
    TreeNode der = new TreeNode(8);

    raiz.left = izq;
    raiz.right = der;

    TreeNode izqizq = new TreeNode(1);
    TreeNode derder = new TreeNode(4);

    raiz.left.left = izqizq;
    raiz.left.right = derder;

    System.out.println(raiz.left.right.val);


  }
}

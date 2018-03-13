package za;

/**
 * Created by baozi on 2018/2/5.
 */
public class BinaryTreeNode {

    int value;

    BinaryTreeNode left;

    BinaryTreeNode right;

    static int treeDepth(BinaryTreeNode pRoot) {
        if (pRoot == null) {
            return 0;
        }
        int nLeft = treeDepth(pRoot.left);
        int right = treeDepth(pRoot.right);
        return nLeft > right ? nLeft + 1 : right + 1;
    }

    public static void main(String[] args) {
        System.out.println(treeDepth(new BinaryTreeNode()));
    }
}

import apple.laf.JRSUIUtils;

/**
 * Created by baozi on 13/03/2018.
 */
public class Solution2 {


    /**
     * 递归找寻相同的根节点，对找到相同的根节点进行相同性判断
     * @param root1
     * @param root2
     * @return
     */
    public static boolean HasSubtree(TreeNode root1,TreeNode root2) {

        boolean result = false;

        if (root1 != null && root2 != null) {
            if (root1.val == root2.val) {
                result = comperTree(root1, root2);
            }
            if (!result) {
                result = HasSubtree(root1.left, root2);
            }
            if (!result) {
                result = HasSubtree(root1.right, root2);
            }
        }
        return result;
    }

    public static boolean comperTree(TreeNode root1, TreeNode root2) {

        if (root2 == null) {
            return true;
        }

        if (root1 == null) {
            return false;
        }

        if (root1.val != root2.val) {
            return false;
        }

        return comperTree(root1.left, root2.left) && comperTree(root1.right, root2.right);
    }

    public void Mirror(TreeNode root) {

        if (root == null) {
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = right;
        root.right = left;

        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
    }





    public static void main(String[] args) {


        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(8);
        TreeNode treeNode3 = new TreeNode(7);
        TreeNode treeNode4 = new TreeNode(9);
        TreeNode treeNode5 = new TreeNode(2);
        TreeNode treeNode6 = new TreeNode(4);
        TreeNode treeNode7 = new TreeNode(7);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;

        treeNode5.left = treeNode6;
        treeNode5.right = treeNode7;


        TreeNode treeNode11 = new TreeNode(8);
        TreeNode treeNode22 = new TreeNode(9);
        TreeNode treeNode33 = new TreeNode(2);
        treeNode11.left = treeNode22;
        treeNode11.right = treeNode33;

        System.out.println(HasSubtree(treeNode1, treeNode11));

    }
}

class TreeNode {

    int val = 0;

    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}

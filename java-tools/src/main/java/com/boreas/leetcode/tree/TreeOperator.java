package com.boreas.leetcode.tree;

/**
 * @author boreas
 * @create 2022-03-16 15:11
 */
public class TreeOperator {
    /** [1,3,4,5,6,7,9] */

    /**
     * Successor 代表的是中序遍历序列的下一个节点。
     * @param root
     * @return
     */
    public TreeNode successor(TreeNode root) {
        root = root.right;
        while (root.left != null){
            root = root.left;
        }
        return root;
    }

    /**
     * Predecessor 代表的是中序遍历序列的前一个节点。
     * @param root
     * @return
     */
    public TreeNode predecessor(TreeNode root) {
        root = root.left;
        while (root.right != null) root = root.right;
        return root;
    }
}

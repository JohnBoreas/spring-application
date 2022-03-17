package com.boreas.leetcode.tree;

/**
 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，
 * 并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
 *
 * 一般来说，删除节点可分为两个步骤：
 *
 * 1、首先找到需要删除的节点；
 * 2、如果找到了，删除它。
 *
 * 输入：root = [5,3,6,2,4,null,7], key = 3
 * 输出：[5,4,6,2,null,null,7] or [5,2,6,null,4,null,7]
 * 解释：给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
 *
 * @author boreas
 * @create 2022-03-16 11:57
 */
public class TreeDeleteNode {

    public static void main(String[] args) {

    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        // delete from the right subtree
        if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else if (key < root.val) {
            // delete from the left subtree
            root.left = deleteNode(root.left, key);
        } else {
            // delete the current node
            // the node is a leaf
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) {
                // the node is not a leaf and has a right child
                root.val = successor(root);
                root.right = deleteNode(root.right, root.val);
            } else {
                // the node is not a leaf, has no right child, and has a left child
                root.val = predecessor(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }

    /**
     * 获取下一个
     */
    public int successor(TreeNode root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root.val;
    }
    /**
     * 获取前一个
     */
    public int predecessor(TreeNode root) {
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root.val;
    }
}

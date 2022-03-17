package com.boreas.leetcode.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的先序(preorder)，中序（inorder），后序(postorder)的遍历
 * @author boreas
 * @create 2022-03-16 12:56
 */
public class NodeOrder {

    /**
     * 先序遍历
     * 遍历过程如下：
     *
     * 访问根节点，
     * 访问当前节点的左子树，
     * 访问当前节点的右子树。
     * @param root
     * @param arr
     * @return
     */
    public LinkedList<Integer> preorder(TreeNode root, LinkedList<Integer> arr) {
        if (root == null) {
            return null;
        }
        arr.add(root.val);
        preorder(root.left, arr);
        preorder(root.right, arr);
        return arr;
    }
    /**
     * 中序遍历
     * 遍历过程如下：
     *
     * 访问左子树，
     * 访问根节点，
     * 访问右子树。
     * @param root
     * @param arr
     * @return
     */
    public LinkedList<Integer> inorder(TreeNode root, LinkedList<Integer> arr) {
        if (root == null) {
            return null;
        }
        inorder(root.left, arr);
        arr.add(root.val);
        inorder(root.right, arr);
        return arr;
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> arr = new ArrayList<>();
        Deque<TreeNode> stk = new LinkedList<TreeNode>();
        while (root != null && !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            arr.add(root.val);
            root = root.right;
        }
        return arr;
    }
    /**
     * 后序遍历
     * 遍历过程如下：
     *
     * 访问左子树，
     * 访问右子树，
     * 访问根节点
     * @param root
     * @param arr
     * @return
     */
    public LinkedList<Integer> postorder(TreeNode root, LinkedList<Integer> arr) {
        if (root == null) {
            return null;
        }
        postorder(root.left, arr);
        postorder(root.right, arr);
        arr.add(root.val);
        return arr;
    }
}

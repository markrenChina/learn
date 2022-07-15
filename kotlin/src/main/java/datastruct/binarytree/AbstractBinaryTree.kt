package datastruct.binarytree

import datastruct.TreeNode

abstract class AbstractBinaryTree : IBinaryTree {
    abstract override var root: IBinaryTreeNode?


    tailrec fun preorderTraversal(node: IBinaryTreeNode?,visit: (Any)->Unit) {
        node?.let { visit(it) } ?: return
        preorderTraversal(node.left,visit)
        preorderTraversal(node.right,visit)
    }

    tailrec fun inorderTraversal(node: IBinaryTreeNode?,visit: (Any)->Unit) {
        node?.let {
            inorderTraversal(it.left,visit)
            visit(it)
        } ?: return
        inorderTraversal(node.right,visit)
    }

    tailrec fun postorderTraversal(node: IBinaryTreeNode?,visit: (Any)->Unit) {
        node?: return
        postorderTraversal(node.left,visit)
        postorderTraversal(node.right,visit)
        visit(node)
    }
    //根 左 右
    override fun preorderTraversal(visit: (Any)->Unit) {
        preorderTraversal(root,visit)
    }

    //左 根 右
    override fun inorderTraversal(visit: (Any)->Unit) {
        inorderTraversal(root,visit)
    }

    //左 右 根
    override fun postorderTraversal(visit: (Any)->Unit) {
        postorderTraversal(root,visit)
    }
}
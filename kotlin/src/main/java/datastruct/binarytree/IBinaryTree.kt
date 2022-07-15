package datastruct.binarytree

import datastruct.Tree

interface IBinaryTree : Tree {
    /**
     * 前序遍历
     */
    fun preorderTraversal(visit: (Any)->Unit)

    /**
     * 中序遍历
     */
    fun inorderTraversal(visit: (Any)->Unit)
    /**
     * 后续遍历
     */
    fun postorderTraversal(visit: (Any)->Unit)

    class Test {
        companion object{
            fun printBinaryTree(tree: IBinaryTree) {
                tree.inorderTraversal { println(it) }
            }
        }
    }
}
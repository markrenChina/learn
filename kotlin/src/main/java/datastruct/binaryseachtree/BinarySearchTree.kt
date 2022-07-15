package datastruct.binaryseachtree

import datastruct.TreeNode
import datastruct.binarytree.AbstractBinaryTree
import datastruct.binarytree.BinaryTreeNode
import datastruct.binarytree.IBinaryTree
import datastruct.binarytree.IBinaryTreeNode

open class BinarySearchTree(override var root: IBinaryTreeNode? = null) : AbstractBinaryTree() {

    override fun getChild(parent: TreeNode, index: Int): TreeNode? = parent.getChildAt(index)

    private fun addNode(parent: IBinaryTreeNode, node: IBinaryTreeNode): Boolean {
        return if (parent < node) {
            if (parent.right == null) {
                parent.right =node
                true
            } else {
                addNode(parent.right!!, node)
            }
        } else if (parent == node) {
            false
        } else {
            if (parent.left == null) {
                parent.left = node
                true
            } else {
                addNode(parent.left!!, node)
            }
        }
    }

    open override fun add(node: TreeNode): Boolean {
        return if (node is IBinaryTreeNode) {
            root?.let {
                return addNode(it, node)
            } ?: kotlin.run { root = node; }
            true
        } else {
            false
        }
    }

    private fun findMax(parent: IBinaryTreeNode): IBinaryTreeNode{
        return parent.right?.let { findMax(it) } ?: parent
    }

    private fun deleteAndGetMax(parent: IBinaryTreeNode): IBinaryTreeNode? {
        var tmp: IBinaryTreeNode? = parent
        var tmpParent: IBinaryTreeNode? = null
        while (tmp != null){
            if (tmp.right != null){
                tmpParent = tmp
                tmp = tmp.right
            }else {
                tmpParent?.let {
                    tmpParent.right = tmp.left
                    tmp.left = null
                    return tmp
                }
                return tmp
            }
        }
        return null
    }

    private fun remove(parent: IBinaryTreeNode, node: IBinaryTreeNode): Boolean {
        var tmp: IBinaryTreeNode? = parent
        var tmpParent : IBinaryTreeNode = parent
        var isLeft: Boolean? = null
        while (tmp != null){
            if (tmp > node){
                tmpParent = tmp
                isLeft = true
                tmp = tmp.left
                continue
            }else if (tmp < node){
                tmpParent = tmp
                tmp = tmp.right
                isLeft =false;
                continue
            }else {
                //逻辑上不可添加相同的值，左右相等只能是双方都为null
                if (tmp.left == tmp.right){
                    isLeft?.let {
                        if (it) {
                            tmpParent.left = null
                        }else {
                            tmpParent.right = null
                        }
                        tmp = null
                        return true
                    }
                    root = null
                    return true
                } else if (tmp!!.left == null){
                    isLeft?.let {
                        if (it) {
                            tmpParent.left = tmp!!.right
                        }else {
                            tmpParent.right = tmp!!.right
                        }
                        tmp = null
                        return true
                    }
                    root = root!!.right
                    tmp = null
                    return true

                } else if (tmp!!.right == null){
                    isLeft?.let {
                        if (it) {
                            tmpParent.left = tmp!!.left
                        } else {
                            tmpParent.right = tmp!!.left
                        }
                        tmp = null
                        return true
                    }
                    root = root!!.left
                } else {
                    //左右都不为空，从左边找一个最大值，前驱，或者
                    val successor = deleteAndGetMax(tmp!!.left!!)!!
                    if (successor != tmp!!.left){
                        successor.left = tmp!!.left
                    }
                    successor.right = tmp!!.right
                    isLeft?.let {
                        if (it){
                            tmpParent.left = successor
                        }else {
                            tmpParent.right = successor
                        }
                        tmp = null
                        return true
                    }
                    tmp = null
                    root = successor
                    return true
                }
            }
        }
        return false
    }

    open override fun remove(node: TreeNode): Boolean {
        return if (node is IBinaryTreeNode) {
            root?.let {
                remove(it, node)
            } ?: false
        } else {
            false
        }
    }

    class Test{
        companion object{
            fun Test(){
                val one = BinaryTreeNode(1)
                val five = BinaryTreeNode(5)
                val eight = BinaryTreeNode(8)
                val six = BinaryTreeNode(6)
                val f_five = BinaryTreeNode(-5)
                val f_one= BinaryTreeNode(-1)
                val f_two= BinaryTreeNode(-2)
                val bst= BinarySearchTree()
                bst.add(one)
                bst.add(five)
                bst.add(eight)
                bst.add(f_five)
                bst.add(f_one)
                bst.add(f_two)
                bst.add(six)
                //bst.remove(one)
                IBinaryTree.Test.printBinaryTree(bst)
                println("***************")
                bst.remove(f_five)
                IBinaryTree.Test.printBinaryTree(bst)
                println("***************")
                bst.remove(eight)
                IBinaryTree.Test.printBinaryTree(bst)
            }
        }
    }
}

fun main(){
    BinarySearchTree.Test.Test()
}
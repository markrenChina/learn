package datastruct.binarytree

import datastruct.TreeNode

open class BinaryTreeNode(private val mValue : Any) :IBinaryTreeNode {
    override var left : IBinaryTreeNode?= null
    override var right : IBinaryTreeNode?= null
    private var listChild = listOf(left,right)


    override fun getChildCount(): Int {
        var count= 0
        left?.let { count++ }
        right?.let { count++ }
        return count
    }

    override fun getChildAt(index: Int): TreeNode? {
        return listChild[index]
    }

    override fun isLeaf(): Boolean {
        return left == null && right == null
    }

    override fun getValue(): Any {
        return mValue
    }

    override fun toString(): String {
        return mValue.toString()
    }

    open override fun compareTo(other: TreeNode): Int {
        return this.getValue().hashCode() - other.getValue().hashCode()
    }
}
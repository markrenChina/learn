package datastruct

interface TreeNode: Comparable<TreeNode> {

    /**
     * 返回子节点个数
     */
    fun getChildCount() : Int

    /**
     * 根据索引返回子节点
     * @param index 索引
     * @return 子孩子
     */
    fun getChildAt(index: Int) : TreeNode?

    /**
     * 是否是叶子节点
     */
    fun isLeaf():Boolean

    /**
     * 获取值
     */
    fun getValue(): Any

    override fun toString(): String
}
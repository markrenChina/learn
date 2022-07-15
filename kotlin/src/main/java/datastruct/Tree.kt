package datastruct

interface Tree {
    /**
     * 获取根节点
     */
    val root: TreeNode?

    /**
     * 获取parent节点下的
     * @param index 节点索引
     */
    fun getChild(parent: TreeNode, index: Int): TreeNode?


    /**
     * 添加
     */
    fun add(node: TreeNode) : Boolean

    /**
     * 移除
     */
    fun remove(node: TreeNode) : Boolean

}
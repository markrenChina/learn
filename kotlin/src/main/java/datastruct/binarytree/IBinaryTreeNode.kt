package datastruct.binarytree

import datastruct.TreeNode

interface IBinaryTreeNode : TreeNode {
    var left : IBinaryTreeNode?
    var right: IBinaryTreeNode?
}
//
// Created by Ushop on 2022/8/10.
//
#include "../leetcode-utils.hpp"

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Codec {
private:
    //template<typename TreeNode>
    void SerializeBinaryTree(TreeNode* node,stringstream& ss){
        if(!node){
            ss << "N,";
            return;
        }
        ss << node->val << ",";
        SerializeBinaryTree(node->left,ss);
        SerializeBinaryTree(node->right,ss);
    }
    bool readNumber(string& data,size_t& pos,int& number){
        stringstream ss;
        for (; pos < data.size();pos++) {
            if (data[pos] == 'N'){
                pos +=2;
                return false;
            }else if (data[pos] == ','){
                pos++;
                number = ::atoi(ss.str().c_str());
                return true;
            }else {
                ss << data[pos];
            }
        }
        return false;
    }

    //template<typename TreeNode,typename T>
    void DeserializeBinaryTree(TreeNode** node,string& data,size_t& pos){
        int number;
        if (readNumber(data,pos,number)){
            *node = new TreeNode(number);
            DeserializeBinaryTree(&((*node)->left),data,pos);
            DeserializeBinaryTree(&((*node)->right),data,pos);
        }
    }

public:

    // Encodes a tree to a single string.
    string serialize(TreeNode *root) {
        if (!root){
            return {};
        }
        stringstream ss;
        SerializeBinaryTree(root,ss);
        return ss.str();
    }

    // Decodes your encoded data to tree.
    TreeNode *deserialize(string data) {
        if (data.empty()){
            return nullptr;
        }
        TreeNode* root = new TreeNode(0);
        size_t pos = 0;
        DeserializeBinaryTree(&root,data,pos);
        return root;
    }
};

int main(){
    TreeNode* root = DeserializeBinaryTree<TreeNode,int>({3,1,4,INT32_MIN+1,2},INT32_MIN+1);
    Codec codec;
    string str = codec.serialize(root);
    TreeNode* bak = codec.deserialize(str);
    cout << str << endl;
}
//
// Created by Ushop on 2021/9/29.
//
#include <iostream>
#include <string>
#include <initializer_list>
#include <vector>

//简单地模拟JSON支持地几种数据类型
enum class jsonType {
    jsonTypeNull,
    jsonTypeInt,
    jsonTypeLong,
    jsonTypeDouble,
    jsonTypeBool,
    jsonTypeString,
    jsonTypeArray,
    jsonTypeObject
};


struct jsonType{
    jsonNode(const char* key, const char * value):
    m_type(jsonType::jsonTypeString),
    m_key(key),
    m_value(value){
        std::cout << "jsonNode contructor1 called." << std::endl;
    };

    jsonNode(const char* key, const double * value):
            m_type(jsonType::jsonTypeDouble),
            m_key(key),
            m_value(std::to_string(value)){
        std::cout << "jsonNode contructor2 called." << std::endl;
    };
    //省略其他构造函数

    jsonType m_type;
    std::string m_ket;
    //始终使用string类型保存值，避免浮点类型因为精度问题而显示不同地结果
    std::string m_value;
};

class json{
public:
    static json& array(std::initializer_list<jsonNode> nodes){
        m_json.m_nodes.clear();
        m_json.m_nodes.insert(m_json.m_nodes.end(),nodes.begin(),nodes.end());

        std::cout << "json::array() called." << std:: endl;
        return m_json;
    }

    json(){}
    ~json(){}

    std::string toString(){
        size_t size = m_nodes.size();
        for (int i = 0; i < size; ++i){
            switch(m_nodes[i].m_type){
                //根据类型组成一个Json字符串，代码省略
                case jsonType::jsonTypeDouble:break;
            }
        }
    }

private:
    std::vector<jsonNode> m_nodes;
    static json m_json;
};

json json::m_json;

int main(){
    json array_not_object = json::array({{"currency","USD"},{"value",42.99}});
    return 0;
}
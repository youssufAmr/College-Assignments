
#ifndef BSTNODE_H
#define BSTNODE_H
#include <iostream>
using namespace std;
template <class T>
class BSTNode
{
public:
    BSTNode()
    {
        left=NULL;
        right=NULL;
    }

    BSTNode(T value,BSTNode *l,BSTNode *r)
    {
        val=value;
        left =l;
        right=r;
    }
    void setright(BSTNode *r)
    {
        right = r;
    }
    void setleft(BSTNode *l)
    {
        left = l;
    }
    void setval(T v)
    {
        val = v;
    }
    BSTNode<T>* getright()
    {
        return right;
    }
    BSTNode<T>* getleft()
    {
        return left;
    }
    T getval()
    {
        return val;
    }
private:
    T val;
    BSTNode<T> *left,*right;
};

#endif // BSTNODE_H

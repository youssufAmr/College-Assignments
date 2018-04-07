#ifndef AVLBST_H
#define AVLBST_H
#include "bstnode.h"
#include <QByteArray>
#include <QProcess>
#include <QTextStream>
#include <QGraphicsScene>
#include <QGraphicsView>
#include <QThread>
#include <QLabel>
#include <cmath>
#include <fstream>
template <class T>
class AvlBST
{
public:
    AvlBST(QGraphicsScene* scene, QGraphicsView* view)
    {
        root=NULL;
        _scene = scene;
               _view = view;
    }

    AvlBST(BSTNode<T>* r)
    {
        root=r;
    }

    void Insert(T v)
    {
        BSTNode<T>* newone=new BSTNode<T>();
        newone->setval(v);
        if(root==NULL)
        {
            root=newone;
        }
        else
        {
            BSTNode<T> *here=root;
            while(true)
            {
                if(v > here->getval())
                {
                    if(here->getright() != NULL)
                    {
                        here=here->getright();
                    }
                    else
                    {
                        break;
                    }
                }
                else if(v < here->getval())
                {
                    if(here->getleft() != NULL)
                    {
                        here=here->getleft();
                    }
                    else
                    {
                        break;
                    }
                }
                else if(v == here->getval())
                {
                    break;
                }
            }
            if(v > here->getval())
            {
                here->setright(newone);
            }
            else if(v < here->getval())
            {
                here->setleft(newone);
            }
        }


        if(!isBalance(root))
        {

            bool isroot=false,isleft=false,isright=false;
            getunbalanced(root);
            BSTNode<T> *here=root;
            if(unbalanced!=here)
            {
                while(true)
                {
                    if(unbalanced->getval() < here->getval())
                    {
                        if(here->getleft()==unbalanced)
                        {
                            isleft=true;
                            break;
                        }
                        else
                        {
                            here=here->getleft();
                        }
                    }
                    else
                    {
                        if(here->getright()==unbalanced)
                        {
                            isright=true;
                            break;
                        }
                        else
                        {
                            here=here->getright();
                        }
                    }
                }

            }
            else
            {
                isroot=true;
            }

            if (unvalidBF < -1 )
            {

                if(  unbalanced->getleft()!=NULL)
                {
                    if( v < unbalanced->getleft()->getval())
                        unbalanced=right_rotation(unbalanced);
                    if(v > unbalanced->getleft()->getval())
                    {

                        unbalanced->setleft(left_rotation(unbalanced->getleft()));
                        unbalanced=right_rotation(unbalanced);
                    }
                }


            }
            else if (unvalidBF  > 1)
            {
                if(  unbalanced->getright()!=NULL)
                {
                    if( v > unbalanced->getright()->getval())
                        unbalanced=left_rotation(unbalanced);
                    if(v < unbalanced->getright()->getval())
                    {

                        unbalanced->setright(right_rotation(unbalanced->getright()));
                        unbalanced=left_rotation(unbalanced);
                    }
                }

            }



            if(isroot)
            {
                root=unbalanced;
            }
            else if(isleft)
            {
                here->setleft(unbalanced);
            }
            else if(isright)
            {
                here->setright(unbalanced);
            }
        }

    }

    void getunbalanced(BSTNode<T> *n)
    {
        if(n!=NULL)
        {
            int lheight=getHeight(n->getleft());
            int rheight=getHeight(n->getright());
            int balancefactor=rheight-lheight;

            if(abs(balancefactor)>1)
            {
                unbalanced= n;
                unvalidBF=balancefactor;
            }
            else
            {
                if(n->getleft()!=NULL)
                    getunbalanced(n->getleft());
                if(n->getright()!=NULL)
                    getunbalanced(n->getright());
            }
        }
    }

    bool isBalance(BSTNode<T>* Root)
    {
        if(Root==NULL)
        {
            return true;
        }
        int lheight=getHeight(Root->getleft());
        int rheight=getHeight(Root->getright());
        int balancefactor=rheight-lheight;
        if(abs(balancefactor)<=1 && isBalance(Root->getleft())&&isBalance(Root->getright()))
        {
            return true;
        }
        return false;
    }


    T Max(T Left,T Right )
    {
        if(Left>=Right)
        {
            return Left;
        }
        return Right;
    }

    int getHeight(BSTNode<T>*Node)
    {
        if(Node==NULL)
        {
            return 0;
        }
        int height=Max(getHeight(Node->getleft()),getHeight(Node->getright()));
        return 1+height;
    }
    BSTNode<T>* left_rotation(BSTNode<T>* node)
    {
        BSTNode<T> *myright,*leftOfTheright;
        myright=node->getright();
        leftOfTheright=myright->getleft();
        node->setright(leftOfTheright);
        myright->setleft(node);
        node=myright;
        return node;
        //cout<<node->getval()<<"  r "<<node->getright()->getval()<<"  l  "<<node->getleft()->getval()<<endl;
    }
    BSTNode<T>* right_rotation(BSTNode<T>* node)
    {
        BSTNode<T> *myleft,*rightOfTheleft;
        myleft=node->getleft();
        rightOfTheleft=myleft->getright();
        node->setleft(rightOfTheleft);

        myleft->setright(node);
        node=myleft;
        return node;
    }
    void printingInOrder(BSTNode<T> *n)
    {
        if(n==NULL)
        {
            return;
        }
        printingInOrder(n->getleft());
        cout<<n->getval()<<" ";

        printingInOrder(n->getright());
    }
    void printingPreOrder(BSTNode<T> *n)
    {
        if(n==NULL)
        {
            return;
        }
         cout<<n->getval()<<" ";
        printingPreOrder(n->getleft());
        printingPreOrder(n->getright());
    }
     void printingPostOrder(BSTNode<T> *n)
    {
        if(n==NULL)
        {
            return;
        }

        printingPostOrder(n->getleft());
        printingPostOrder(n->getright());
        cout<<n->getval()<<" ";
    }
    void print()
    {
        cout<<"Inorder => {";
        printingInOrder(root);
        cout<<" }"<<endl;
        cout<<"preorder => {";
        printingPreOrder(root);
        cout<<" }"<<endl;
        cout<<"Postorder => {";
        printingPostOrder(root);
        cout<<" }"<<endl;
    }

    bool isFound(T v)
    {
        BSTNode<T> *here=root;
        while(here!=NULL)
        {
            if(here->getval()==v)
            {
                return true;
            }
            if(v < here->getval())
                here=here->getleft();
            else
                here=here->getright();
        }
        return false;
    }

    void Delete(T v)
    {
        if(isFound(v))
        {
            BSTNode<T> *here=root,*deletednode,*prev=NULL;
            bool isright=false,isleft=false,isroot=false;
            while(here!=NULL)
            {
                if(here->getval()==v)
                {
                    deletednode=here;
                }
                if(v < here->getval())
                    here=here->getleft();
                else
                    here=here->getright();
            }
            if(deletednode !=root)
            {
                here=root;
                while(true)
                {
                    if(deletednode->getval() < here->getval())
                    {
                        if(here->getleft()==deletednode)
                        {
                            prev=here;
                            isleft=true;
                            break;
                        }
                        else
                        {
                            here=here->getleft();
                        }
                    }
                    else
                    {
                        if(here->getright()==deletednode)
                        {
                            prev=here;
                            isright=true;
                            break;
                        }
                        else
                        {
                            here=here->getright();
                        }
                    }
                }
            }
            else
            {
                isroot=true;
            }
            if(deletednode->getright()==NULL && deletednode->getleft()==NULL)
            {
                if(isright)
                    prev->setright(NULL);
                else if(isleft)
                    prev->setleft(NULL);
                else if(isroot)
                    root=NULL;
            }
            else if(deletednode->getright()==NULL)
            {
                deletednode=deletednode->getleft();
                if(isright)
                    prev->setright(deletednode);
                else if(isleft)
                    prev->setleft(deletednode);
                else if(isroot)
                    root=deletednode;
            }
            else if(deletednode->getleft()==NULL)
            {
                deletednode=deletednode->getright();
                if(isright)
                    prev->setright(deletednode);
                else if(isleft)
                    prev->setleft(deletednode);
                else if(isroot)
                    root=deletednode;
            }
            else
            {
                BSTNode<T> *suc,*psuc;
                psuc=deletednode;
                suc=deletednode->getleft();
                while(suc->getright()!=NULL)
                {
                    psuc=suc;
                    suc=suc->getright();
                }
                deletednode->setval(suc->getval());
                if(psuc!=deletednode)
                    psuc->setright(NULL);
                else
                    deletednode->setleft(NULL);
            }

            if(!isBalance(root))
            {

                isroot=false;
                isleft=false;
                isright=false;
                getunbalanced(root);
                here=root;
                if(unbalanced!=here)
                {
                    while(true)
                    {
                        if(unbalanced->getval() < here->getval())
                        {
                            if(here->getleft()==unbalanced)
                            {
                                isleft=true;
                                break;
                            }
                            else
                            {
                                here=here->getleft();
                            }
                        }
                        else
                        {
                            if(here->getright()==unbalanced)
                            {
                                isright=true;
                                break;
                            }
                            else
                            {
                                here=here->getright();
                            }
                        }
                    }

                }
                else
                {
                    isroot=true;
                }
                if (unvalidBF < -1 )
                {

                    if(  unbalanced->getleft()!=NULL)
                    {
                        if( getHeight(unbalanced->getleft()->getright()) < getHeight(unbalanced->getleft()->getleft()))
                            unbalanced=right_rotation(unbalanced);
                        else
                        {

                            unbalanced->setleft(left_rotation(unbalanced->getleft()));
                            unbalanced=right_rotation(unbalanced);
                        }
                    }


                }
                else if (unvalidBF  > 1)
                {
                    if(  unbalanced->getright()!=NULL)
                    {
                        if( getHeight(unbalanced->getright()->getleft()) < getHeight(unbalanced->getright()->getright()))
                            unbalanced=left_rotation(unbalanced);
                        else
                        {

                            unbalanced->setright(right_rotation(unbalanced->getright()));
                            unbalanced=left_rotation(unbalanced);
                        }
                    }

                }



                if(isroot)
                {
                    root=unbalanced;
                }
                else if(isleft)
                {
                    here->setleft(unbalanced);
                }
                else if(isright)
                {
                    here->setright(unbalanced);
                }
            }
        }
        else
        {
            cout<<"doesn't found"<<endl;
        }
    }
    void drawGraph(BSTNode<T> * p, QTextStream *stream) {
            if (p != NULL) {
                *stream << "\t\t" << "n" << p->getval() << "[label=\"" << p->getval() << "\"];" << endl;
                if (p->getleft() != NULL) {
                    *stream  << p->getval() << "--"  << p->getleft()->getval() << endl;
                    drawGraph(p->getleft(), stream);
                }else {
                *stream << "\t\t" << "n" << p->getval() << "leftNull" << "[style=\"filled\",label=\"NULL\"];" << endl;
               *stream << "\t\tn" << p->getval() << "--" << "n" << p->getval() << "leftNull" << endl;
                }

                if (p->getright() != NULL){
                     *stream  << p->getval()<< "--"  << p->getright()->getval() << endl;
                    drawGraph(p->getright(), stream);
                }else {
                *stream << "\t\t" << "n" << p->getval() << "rightNull" << "[style=\"filled\",label=\"NULL\"];" << endl;
                             *stream << "\t\tn" << p->getval() << "--" << "n" << p->getval() << "rightNull" << endl; }
            }
        }

        QByteArray prepareGraph() {
            QByteArray g_bArray = QByteArray();
            QTextStream stream(&g_bArray, QIODevice::ReadWrite);
             stream << "graph {" << endl;
            drawGraph(root, &stream);
            stream << "\t\n" << "}" << endl;
            return g_bArray;
        }

        void show() {
            QProcess* proc = new QProcess();
            QByteArray g_bArray = prepareGraph();
          proc->setProcessChannelMode(QProcess::MergedChannels);
            proc->start("dot", QStringList() << "-Tpng");
            proc->write(g_bArray);

            QByteArray data;
              QPixmap pixmap = QPixmap();
            while (proc->waitForReadyRead(200)){
                data.append(proc->readAll());
            }
           // data.append(proc->readAll());

            pixmap.loadFromData(data);
            _scene->clear();
            _scene->addPixmap(pixmap);
            this->_view->show();



        }
    BSTNode<T>* getroot(){return root;}

protected:

private:
    BSTNode<T> *root,*unbalanced;
    QGraphicsScene* _scene;
    QGraphicsView* _view;
    int unvalidBF;
};

#endif // AVLBST_H

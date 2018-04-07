#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QGraphicsScene>
#include <QMessageBox>
MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    scene = new QGraphicsScene;
       view = ui->graphicsView;
       view->setScene(scene);
      avl = new AvlBST<int>(scene,view);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
QString val = ui->plainTextEdit->toPlainText();
int value = val.toInt();
avl->Insert(value);
avl->show();
}

void MainWindow::on_pushButton_2_clicked()
{
    QString val = ui->plainTextEdit_2->toPlainText();
    int value = val.toInt();
    avl->Delete(value);
    avl->show();
}

void MainWindow::on_pushButton_3_clicked()
{
    QString val = ui->plainTextEdit_3->toPlainText();
    int value = val.toInt();
    bool found=avl->isFound(value);
    if(found)
        QMessageBox::information(this,"","found");
    else
         QMessageBox::information(this,"","sorry not found");
}

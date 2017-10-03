<?php

namespace App\Controller\Admin;

use Core\HTML\BootstrapForm;

class CategoryController extends AppController{

    public function __construct()
    {
        parent::__construct();
        $this->loadModel('post');
        $this->loadModel('category');
    }

    public function index(){
        $categories = $this->category->all();
        $this->render('admin.category.index', compact('categories'));

    }

    public function add(){
        if(!empty($_POST)){
            $result = $this->category->create([
                'title'         => $_POST['title']
            ]);
            if($result){
                header('Location: '.BASE_URL.'/admin/category/');
                exit;
            }
        }
        $form = new BootstrapForm($_POST);
        $this->render('admin.category.add', compact('form'));
    }

    public function edit(){
        if(!empty($_POST)){
            $result = $this->category->update($_GET['id'],[
                'title'         => $_POST['title']
            ]);
            if($result){
                header('Location: '.BASE_URL.'/admin/category/');
                exit;
            }
        }

        $category = $this->category->find($_GET['id']);
        $form = new BootstrapForm($category);
        $this->render('admin.category.edit', compact('form'));

    }

    public function delete(){
        if(!empty($_POST)){
            $result = $this->category->delete($_POST['id']);
            if($result){
                header('Location: '.BASE_URL.'/admin/category/');
                exit;
            }
        }
    }

}
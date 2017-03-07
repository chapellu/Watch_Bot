<?php


namespace App\Controller\Admin;

use Core\HTML\BootstrapForm;

class PostController extends AppController{

    public function __construct()
    {
        parent::__construct();
        $this->loadModel('post');
        $this->loadModel('category');

    }

    public function index(){
        $posts = $this->post->all();
        $categories = $this->category->all();
        $this->render('admin.post.index', compact('posts'));

    }

    public function view(){
        $post = $this->post->findWithCategory($_GET['id']);
        $this->render('post.view', compact('post'));
    }

    public function add(){
        if(!empty($_POST)){
            $result = $this->post->create([
                'title'         => $_POST['title'],
                'content'       => $_POST['content'],
                'category_id'   => $_POST['category_id']
            ]);
            if($result){
                header('Location: '.BASE_URL.'/admin/post/');
                exit;
            }
        }

        $categories = $this->category->liste('id','title');
        $form = new BootstrapForm($_POST);

        $this->render('admin.post.add', compact('categories', 'form'));
    }

    public function edit(){
        if(!empty($_POST)){
            $result = $this->post->update($_GET['id'],[
                'title'         => $_POST['title'],
                'content'       => $_POST['content'],
                'category_id'   => $_POST['category_id']
            ]);
            if($result){
                header('Location: '.BASE_URL.'/admin/post/');
                exit;
            }
        }

        $post = $this->post->find($_GET['id']);
        $categories = $this->category->liste('id','title');
        $form = new BootstrapForm($post);
        $this->render('admin.post.edit', compact('categories', 'form'));

    }

    public function delete(){
        if(!empty($_POST)){
            $result = $this->post->delete($_POST['id']);
            if($result){
                header('Location: '.BASE_URL.'/admin/post/');
                exit;
            }
        }
    }

}
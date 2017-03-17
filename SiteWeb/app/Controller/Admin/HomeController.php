<?php

namespace App\Controller\Admin;

class HomeController extends AppController{

    protected $layout = 'home';

    public function __construct()
    {
        parent::__construct();
        $this->loadModel('post');
        $this->loadModel('category');
    }

    public function index(){
        $posts = $this->post->getAllHomePosts();
        $this->render('index', compact('posts', 'categories'));
    }
}
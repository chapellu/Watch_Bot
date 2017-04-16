<?php

namespace App\Controller;

class HomeController extends AppController{

    protected $layout = 'home';

    public function __construct()
    {
        parent::__construct();
        $this->loadModel('post');
    }

    public function index(){
        $posts = $this->post->getAllHomePosts();
        $this->render('index', compact('posts'));
    }
}
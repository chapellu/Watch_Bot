<?php


namespace App\Controller;

use Core\Controller\Controller;
use \App;
use Core\HTML\BootstrapForm;

class GroupController extends AppController{
    protected $layout = 'default';
    public function __construct()
    {
        parent::__construct();
        $this->loadModel('post');
    }

    public function index(){
        $posts = $this->post->getAllMembers();
        $this->render('group.index', compact('posts'));
    }

    public function view(){
        $post = $this->post->findWithCategory($_GET['id']);
        $this->render('group.view', compact('post'));
    }


    
}
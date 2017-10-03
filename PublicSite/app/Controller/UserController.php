<?php

namespace App\Controller;
use \Core\Auth\DatabaseAuth;
use \Core\Url\Router;
use \App;

class UserController extends AppController{

    public function login(){
        $errors = false;
        if(!empty($_POST)){
            $auth = new DatabaseAuth(App::getInstance()->getDb());
            if($auth->login($_POST['login'], $_POST['password'])){
                foreach (Router::$prefixes as $k => $v) {
                    if($v === 'admin'){
                        header('Location: ../'.$k.'/');
                    }
                }
            } else {
                $errors = true;
            }
        }

        $form = new \Core\HTML\BootstrapForm($_POST);
        $this->render('user.login', compact('form','errors'));
    }

    public function register(){
        $this->render('user.register', array());
    }


}
<?php

namespace App\Controller;

use Core\Controller\Controller;
use Core\Auth\DatabaseAuth;

use \App;

class AppController extends Controller{

    protected $layout = 'default';
    public $isLogged = false;

    public function __construct()
    {
        $this->viewPath = ROOT .DS. 'app' .DS. 'Views' .DS;
        $this->layoutPath = ROOT .DS. 'app' .DS. 'Views' .DS. 'layout' .DS ;
        $auth = new DatabaseAuth(App::getInstance()->getDb());

        if($auth->logged()){
        	$this->isLogged = true;
        } else{
        	$this->isLogged = false;
        }
    }

    public function loadModel($model_name){
        $this->$model_name = App::getInstance()->getTable($model_name);
    }

    

}
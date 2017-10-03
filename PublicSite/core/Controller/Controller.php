<?php


namespace Core\Controller;

class Controller{


    protected $viewPath;
    protected $layout;
    protected $layoutPath;
    private $rendered 	= false;

    public function render($view, $var = []){
        if($this->rendered){return false;}
        $var['isLogged'] = $this->isLogged;
        if(strpos($view,'notrendered')===false){
            ob_start();
            extract($var);
            require $this->viewPath . str_replace('.',DS,$view).'.php';
            $content = ob_get_clean();

            require $this->viewPath . 'layout' .DS. $this->layout . '.php';
        } else{
            require $this->viewPath . str_replace('.',DS,$view).'.php';
        }
        $this->rendered = true;

    }

    public function forbidden(){
        header('HTTP.1.0 403 Forbidden');
        die('Accès interdit');
    }

    public function notFound(){
        header('HTTP.1.0 404 Not Found');
        die('Page introuvable');
    }



}
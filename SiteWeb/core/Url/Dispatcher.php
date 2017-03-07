<?php
/**
 * Created by PhpStorm.
 * User: alan
 * Date: 21/01/2017
 * Time: 22:01
 */

namespace Core\Url;


use App\Controller\AppController;
use Core\Controller\Controller;

class Dispatcher
{

    public function __construct(){
        $this->request = new Request();
        Router::parse($this->request->url, $this->request);
        $action = $this->request->action;
        var_dump($this->request);
        if($this->request->prefix){
            $controller = $this->loadController(true);
        } else {
            $controller = $this->loadController(false);
        }
        if(!in_array($action, array_diff(get_class_methods($controller),get_class_methods(new Controller())))){ //Permet d'affichier une vue en testant simplement les méthodes du controleur concernées et pas celles du super controller
            $this->error();
        }
        call_user_func_array(array($controller, $action),$this->request->params);
        
        $controller->$action();
    }

    public function error(){
        header("HTTP/1.0 404 Not Found");
        $controller = new AppController();
        //A faire$controller->Session=new Session();
        $controller->notFound();
        die();
    }

    public function loadController($admin)
    {
        $admin_folder = '';
        if($admin){
            $admin_folder = DS.'Admin';
        }
        $name = ucfirst($this->request->controller) . 'Controller';
        $root_controller = ROOT . DS . 'app' . DS . 'Controller' .$admin_folder. DS . $name . '.php';
        if (!file_exists($root_controller)) {
            $this->error();
        } else {
            if($admin){
                $name = 'App\\Controller\\Admin\\' . $name;
            } else {
                $name = 'App\\Controller\\' . $name;
            }
            $controller = new $name();
            return $controller;
        }

    }



}
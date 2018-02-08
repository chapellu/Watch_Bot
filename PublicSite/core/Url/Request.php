<?php
/**
 * Created by PhpStorm.
 * User: alan
 * Date: 21/01/2017
 * Time: 22:02
 */

namespace Core\Url;


class Request
{
    public $url;		//URL appellée par l'utilisateur
    public $page = 1;	//Par défaut
    public $prefix = false;
    public $data = false;

    function __construct(){
        $this->url = $_SERVER['REQUEST_URI'];
        
        if(!empty($_POST)){
            $this->data= array();
            foreach ($_POST as $k => $v) {
                $this->data[$k]=$v;
            }
        }
    }
}
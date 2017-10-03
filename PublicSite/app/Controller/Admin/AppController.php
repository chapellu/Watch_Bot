<?php



namespace App\Controller\Admin;

use \App;

class AppController extends App\Controller\AppController {

    public function __construct()
    {
        parent::__construct();
        if(!$this->isLogged){
            $this->forbidden();
        }
    }



}
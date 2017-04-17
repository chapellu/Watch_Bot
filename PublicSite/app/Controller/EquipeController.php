<?php

namespace App\Controller;

class EquipeController extends AppController{



    public function __construct()
    {
        parent::__construct();
    }

    public function index(){
        $this->render('equipe.index');
    }
}
<?php

namespace App\Controller;

class ProjectController extends AppController{



    public function __construct()
    {
        parent::__construct();
    }

    public function index(){
        $this->render('project.index');
    }
}
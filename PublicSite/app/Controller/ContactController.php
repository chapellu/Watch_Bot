<?php

namespace App\Controller;

class ContactController extends AppController{



    public function __construct()
    {
        parent::__construct();
    }

    public function index(){
        $this->render('contact.index');
    }
}
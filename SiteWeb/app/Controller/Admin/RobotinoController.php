<?php

namespace App\Controller\Admin;

use App\Controller\Admin\AppController;

class RobotinoController extends AppController {

    protected $layout = 'default';

    public function __construct()
    {
        parent::__construct();
    }

    public function index(){
        $errors = false;
        $form = new \Core\HTML\BootstrapForm($_POST);
        $this->render('admin.robotino.index', compact('form','errors'));
    }
}
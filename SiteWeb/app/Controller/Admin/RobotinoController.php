<?php

namespace App\Controller\Admin;

use App\Controller\Admin\AppController;

class RobotinoController extends AppController {

    protected $layout = 'robotino';

    public function __construct()
    {
        parent::__construct();
    }

    public function index(){
        $form = new \Core\HTML\BootstrapForm($_POST);
        $this->render('admin.robotino.index', compact('form'));
    }

    public function camera(){
        $this->render('admin.robotino.camera', compact(''));
    }
}
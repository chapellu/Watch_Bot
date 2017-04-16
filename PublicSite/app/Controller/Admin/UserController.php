<?php

namespace App\Controller\Admin;

class UserController extends AppController{

    public function logout(){
        unset($_SESSION['auth']);
        header('Location: '.BASE_URL.'/home/');
    }


}
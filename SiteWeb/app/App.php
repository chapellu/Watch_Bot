<?php

use Core\Config;
use Core\Database;


class App
{
    private static $_instance;
    private $db_instance;
    public $name_website = 'Watchbot';
    public $title = "APPRS2018 - Watchbot";
    public $flash;

    public static function getInstance()
    {
        if(is_null(self::$_instance)) {
            self::$_instance = new App();
            self::$_instance->flash['logs']='';
        }
        return self::$_instance;
    }

    public function getTable($name) {
        $class_name = '\\App\\Table\\' . ucfirst($name) . 'Table';
        return new $class_name($this->getDb());
    }

    public function getDb(){
        //Lit config et gènére db
        $config = Config::getInstance(ROOT . '\config\Configurations.php');
        if(is_null($this->db_instance)) {
            if(DEV == 1){
                $this->db_instance = new Database(
                    'localhost',
                    'watchbot',
                    'root',
                    '');
            } else{
                $this->db_instance = new Database(
                    'localhost',
                    'watchbot',
                    'root',
                    'app2018');
            }

        }
        return $this->db_instance;
    }

    public static function load(){
        session_start();
        require ROOT . '/app/Autoloader.php';
        App\Autoloader::register(); //Permet de générer et lancer automatiquement l'autoloading de classe
        require ROOT . '/core/Autoloader.php';
        Core\Autoloader::register(); //Permet de générer et lancer automatiquement l'autoloading de classe

        Core\Url\Router::prefix('admin','admin'); //Mot clé pour entrer dans le mode admin
    }

    public static function sendSocket($address, $port, $msg){
        error_reporting(E_ALL);
        set_time_limit(0);
        ob_implicit_flush();



        if (($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === false) {
            echo "socket_create() a échoué : raison :  " . socket_strerror(socket_last_error()) . "\n";
        } else {
            echo "socket_create() a réussi\n";
        }


        echo "Essai de connexion à '$address' sur le port '$port'...";
        $result = socket_connect($socket, $address, $port);
        if ($socket === false) {
            echo "socket_connect() a échoué : raison : ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
        } else {
            echo "socket_connect() a réussi\n";
        }

        echo "Envoi de la requête :".$msg;
        socket_write($socket, $msg, strlen($msg));

        echo "Fermeture du socket...";
        socket_close($socket);
        echo "Socket détruite\n\n";

    }
   
}
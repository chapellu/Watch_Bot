<?php
$debut = microtime(true);

define('DS',DIRECTORY_SEPARATOR);
define('ROOT', dirname(__DIR__));
//define('BASE_URL',dirname(dirname($_SERVER['SCRIPT_NAME'])));
define('BASE_URL', '192.168.118.28');
define('NUM_ROBOTINO','38');

require ROOT . '/app/App.php';

App::load();

new Core\Url\Dispatcher();



/*$page = explode('.', $page);

if($page[0] === 'admin'){
    $controller = '\\App\\Controller\\Admin\\'. ucfirst($page[1]) . 'Controller';
    $action = $page[2];
} else {
    $controller = '\\App\\Controller\\'. ucfirst($page[0]) . 'Controller';
    $action = $page[1];
}
$controller = new $controller();
$controller->$action();*/










?>


<div style="position:fixed;bottom:0;line-height: 30px; left:0;right:0;padd:10px">
    <?php
    //echo 'Page générée en '.round(microtime(true) - $debut,5).' s';
    ?>

</div>
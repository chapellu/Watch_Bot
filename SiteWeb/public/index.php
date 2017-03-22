<?php
$debut = microtime(true);

define('DS',DIRECTORY_SEPARATOR);
define('ROOT', dirname(__DIR__));
//define('BASE_URL',dirname(dirname($_SERVER['SCRIPT_NAME'])));
define('NUM_ROBOTINO','37');
define('BASE_URL', "http://193.48.125.196");


require ROOT . '/app/App.php';

App::load();

new Core\Url\Dispatcher();







?>

<!--
<div style="position:fixed;bottom:0;line-height: 30px; left:0;right:0;padd:10px">
    <?php
    //echo 'Page générée en '.round(microtime(true) - $debut,5).' s';
    ?>

</div>
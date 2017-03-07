<?php
/*$debut = microtime(true);

define('DS',DIRECTORY_SEPARATOR);
define('ROOT', dirname(__DIR__));
//define('BASE_URL',dirname(dirname($_SERVER['SCRIPT_NAME'])));
define('BASE_URL', "http://192.168.118.28");


require ROOT . '/app/App.php';

App::load();

new Core\Url\Dispatcher();*/


define('NUM_ROBOTINO','37');
echo '<meta http-equiv="Refresh" content="0; url=http://193.48.125.'.NUM_ROBOTINO.':50000?action='.$_GET['action'].'">';





?>

<!--
<div style="position:fixed;bottom:0;line-height: 30px; left:0;right:0;padd:10px">
    <?php
    //echo 'Page générée en '.round(microtime(true) - $debut,5).' s';
    ?>

</div>
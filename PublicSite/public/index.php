<?php
$debut = microtime(true);
ini_set('display_errors', 1);
error_reporting(E_ALL);
define('DS',DIRECTORY_SEPARATOR);
define('ROOT', dirname(__DIR__));
define('NUM_ROBOTINO','38');
define('DEV',0);
define('SERVEUR',1);

if(DEV == 1){
    define('BASE_URL','http://localhost/watchbot/SiteWeb');
    define('ROOT_SCRIPT', 'C:\wamp64\www\watchbot/scriptsD6T/');
} else{
    define('BASE_URL', 'http://193.48.125.196/PublicSite');
    define('ROOT_SCRIPT', '/var/www/html/Watch_Bot/scriptsD6T/');
}

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

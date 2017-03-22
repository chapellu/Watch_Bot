<div class="robotino col-sm-12">

    <div class="navbar-menu">
       <dl>
           <dt>Menu</dt>
           <dd>
               <ul>
                   <li><a href="#">Un truc</a></li>
                   <li><a href="#">Un deuxième truc</a></li>
               </ul>
           </dd>
       </dl>
    </div>

    <div class="carte">
        <img class="img_carte" src="<?= BASE_URL.'/public/img/carte.png';?>" alt="">
    </div>

    <div class="commandes">
        <form method="post" >
            <table>
                <thead><h2>Commandes mannuelles</h2></thead>
                <tr>
                    <td></td>
                    <td>
                        <?= $form->bouttonRobotino('avancer', 'boutonsRobotino');?>
                    </td>
                </tr>
                <tr>
                    <td>
                        <?= $form->bouttonRobotino('gauche', 'boutonsRobotino');?>
                    </td>
                    <td></td>
                    <td>
                        <?= $form->bouttonRobotino('droite', 'boutonsRobotino');?>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <?= $form->bouttonRobotino('reculer', 'boutonsRobotino');?>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div>
    <h3>Logs (à faire)</h3>
    <p>
<?php
var_dump($this);
\App::getInstance()->flash['logs'].='++';
var_dump(\App::getInstance()->flash['logs']);
if(isset($_GET['action'])){
    error_reporting(E_ALL);

    /* Interdit l'exécution infinie du script, en attente de connexion. */
    set_time_limit(0);

    /* Active le vidage implicite des buffers de sortie, pour que nous
     * puissions voir ce que nous lisons au fur et à mesure. */
    ob_implicit_flush();

    $address = '193.48.125.'.NUM_ROBOTINO;
    $port = 50000;


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

    $msg = "POST /?action=".$_GET['action']." HTTP/1.1\r\n\r\n";
    $msg .= "Connection: Close\r\n\r\n";
    $out = "";

    echo "Envoi de la requête :".$msg;
    socket_write($socket, $msg, strlen($msg));



    echo "Fermeture du socket...";
    socket_close($socket);
    echo "Socket détruite\n\n";

    header('Location: '.BASE_URL.'/admin/robotino');
    exit();
}


?>
    </p>
</div>


<div class="col-sm-12">

</div>

<div class="col-sm-12">
    <form method="post" >
        <table class="tableBoutonsRobotino">
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

<?php
if(isset($_GET['action'])){

    error_reporting(E_ALL);

    /* Lit l'adresse IP du serveur de destination */
    $address = 'http://193.48.125.'.NUM_ROBOTINO;
    $port = 50000;

    /* Crée un socket TCP/IP. */
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if ($socket === false) {
        echo "socket_create() a échoué : raison :  " . socket_strerror(socket_last_error()) . "\n";
    } else {
        echo "OK.\n";
    }

    echo "Essai de connexion à '$address' sur le port '$port'...";
    $result = socket_connect($socket, $address, $port);
    if ($socket === false) {
        echo "socket_connect() a échoué : raison : ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
    } else {
        echo "OK.\n";
    }

    $in = "POST / HTTP/1.1\n";
    $in .= "action=avancer\n";
    $in .= "Connection: Close\r\n\r\n";
    $out = '';

    echo "Envoi de la requête HTTP HEAD...";
    socket_write($socket, $in, strlen($in));
    echo "OK.\n";

    echo "Lire la réponse : \n\n";
    while ($out = socket_read($socket, 2048)) {
        echo $out;
    }

    echo "Fermeture du socket...";
    socket_close($socket);
    echo "OK.\n\n";


    //header('Location: '.BASE_URL.'/admin/robotino');
    //exit;
}
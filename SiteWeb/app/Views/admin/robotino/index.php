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
    echo "On rentre dans le if";die();

    /* Autorise l'exécution infinie du script, en attente de connexion. */
    set_time_limit(0);

    /* Active le vidage implicite des buffers de sortie, pour que nous
     * puissions voir ce que nous lisons au fur et à mesure. */
    ob_implicit_flush();

    $address = '193.48.125'.NUM_ROBOTINO;
    $port = 50000;

    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if ($socket === false) {
        echo "socket_create() a échoué : raison :  " . socket_strerror(socket_last_error()) . "\n";
    } else {
        echo "OK.\n";
    }

    echo "Essai de connexion à '$address' sur le port '$service_port'...\n";
    $result = socket_connect($socket, gethostbyname($address), $port);
    if ($socket === false) {
        echo "socket_connect() a échoué : raison : ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
    } else {
        echo "OK.\n";
    }
    echo "Envoie du message\n";
    $msg = "MARCHE BORDEL";
    socket_write($socket, $msg, strlen($msg));
    echo "Message envoyé.\n";

    echo "Fermeture du socket...";
    socket_close($socket);
    echo "OK.\n\n";
    header('Location: '.BASE_URL.'/admin/robotino');
    exit;
}
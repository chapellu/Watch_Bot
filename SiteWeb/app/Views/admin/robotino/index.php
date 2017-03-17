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

    /* Interdit l'exécution infinie du script, en attente de connexion. */
    set_time_limit(0);

    /* Active le vidage implicite des buffers de sortie, pour que nous
     * puissions voir ce que nous lisons au fur et à mesure. */
    ob_implicit_flush();

    $address = '193.48.125.'.NUM_ROBOTINO;
    $port = 50000;

    echo "Création socket---------";
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP) or die('Could not create socket : ' . socket_strerror(socket_last_error($socket)));
    echo "Socket créée---------";
    echo socket_strerror(socket_last_error($socket));
    socket_set_option($socket, SOL_SOCKET, SO_RCVTIMEO, array('sec' => 5, 'usec' => 0));
    socket_set_option($socket, SOL_SOCKET, SO_SNDTIMEO, array('sec' => 5, 'usec' => 0));


    echo 'Essai de connexion à '.$address.' sur le port '.$port.'...---------';
    $result = socket_connect($socket, $address, $port) or die('Can\'t bind socket'.socket_strerror(socket_last_error($socket)));
    echo "Connexion réussie---------";

    $msgsock = socket_accept($socket) or die('Can\'t accept socket'.socket_strerror(socket_last_error($socket)));;
    echo "Envoie du message---------";
    $msg = "MARCHE BORDEL";
    socket_write($msgsock, $msg, strlen($msg)) or die("Impossible d'envoyer le message");
    echo "Message envoyé.---------";

    echo "Fermeture du socket...---------";
    socket_close($msgsock);
    socket_close($socket);
    echo "OK.---------";
    header('Location: '.BASE_URL.'/admin/robotino');
    exit;
}


?>
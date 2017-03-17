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

    /* Autorise l'exécution infinie du script, en attente de connexion. */
    set_time_limit(0);

    /* Active le vidage implicite des buffers de sortie, pour que nous
     * puissions voir ce que nous lisons au fur et à mesure. */
    ob_implicit_flush();

    $address = '193.48.125.'.NUM_ROBOTINO;
    $port = 50000;

    echo "Création socket\n";
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP) or die("Could not create socket\n");
    echo "Socket créée\n";

    /*Fixe les timeout de lecture/Ecriture à 1 seconde*/
    socket_set_option($socket, SOL_SOCKET, SO_RCVTIMEO, array('sec' => 1, 'usec' => 0));
    socket_set_option($socket, SOL_SOCKET, SO_SNDTIMEO, array('sec' => 1, 'usec' => 0));


    echo 'Essai de connexion à '.gethostbyname($address).' sur le port '.$port.'...\n';
    $result = socket_connect($socket, gethostbyname($address), $port) or die("Could not connect to server\n");
    echo "Connexion réussie\n";


    echo "Envoie du message\n";
    $msg = "MARCHE BORDEL";
    socket_write($socket, $msg, strlen($msg)) or die("Impossible d'envoyer le message");
    echo "Message envoyé.\n";

    echo "Fermeture du socket...";
    socket_close($socket);
    echo "OK.\n\n";
    header('Location: '.BASE_URL.'/admin/robotino');
    exit;
}


?>
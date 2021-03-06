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

    $address = '193.48.125'.NUM_ROBOTINO;
    $port = 50000;

    if (($sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === false) {
        echo "socket_create() a échoué : raison : " . socket_strerror(socket_last_error()) . "\n";
    }

    if (socket_bind($sock, $address, $port) === false) {
        echo "socket_bind() a échoué : raison : " . socket_strerror(socket_last_error($sock)) . "\n";
    }

    if (socket_listen($sock, 5) === false) {
        echo "socket_listen() a échoué : raison : " . socket_strerror(socket_last_error($sock)) . "\n";
    }

    do {
        if (($msgsock = socket_accept($sock)) === false) {
            echo "socket_accept() a échoué : raison : " . socket_strerror(socket_last_error($sock)) . "\n";
            break;
        }
        /* Send instructions. */
        $msg = "Bienvenue sur le serveur de test PHP.\n" .
            "Pour quitter, tapez 'quit'. Pour éteindre le serveur, tapez 'shutdown'.\n";
        socket_write($msgsock, $msg, strlen($msg));

        do {
            if (false === ($buf = socket_read($msgsock, 2048, PHP_NORMAL_READ))) {
                echo "socket_read() a échoué : raison : " . socket_strerror(socket_last_error($msgsock)) . "\n";
                break 2;
            }
            if (!$buf = trim($buf)) {
                continue;
            }
            if ($buf == 'quit') {
                break;
            }
            if ($buf == 'shutdown') {
                socket_close($msgsock);
                break 2;
            }
            $talkback = "PHP: You said '$buf'.\n";
            socket_write($msgsock, $talkback, strlen($talkback));
            echo "$buf\n";
        } while (true);
        socket_close($msgsock);
    } while (true);

    socket_close($sock);

    //header('Location: '.BASE_URL.'/admin/robotino');
    //exit;
}
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">

        <title><?= App::getInstance()->title; ?></title>

        <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/bootstrap.min.css'; ?>"/>
        <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/fonts/font-awesome/css/font-awesome.min.css';?>"/>
        <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/style.css'; ?>"/>
        <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/footer.css'; ?>"/>
        <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/camera.css'; ?>"/>
    </head>

    <body>
        <?php
        require 'navbar.php';
        ?>

        <div class="container">
            <?= $content;?>
        </div>

        <?php
        require 'footer.php';
        ?>


        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="<?= BASE_URL.'/public/js/bootstrap.min.js';?>"></script>

        <!--Script pour la caméra-->
        <script src="https://raw.githubusercontent.com/dorukeker/gyronorm.js/master/dist/gyronorm.complete.min.js" async></script>
        <script src="https://rawgit.com/dorukeker/gyronorm.js/master/dist/gyronorm.complete.min.js" async></script>
        <script type="text/javascript" src="<?=BASE_URL.'/public/js/camera.js';?>"></script>

        <!--<script type="text/javascript">
            $(document).ready( function() {
                $("#log").load(<?='"'.BASE_URL.'/public/robotino/log.php"';?>);
            });
        </script>-->
        <script type="text/javascript">
        $(document).ready( function() {
            var auto_refresh = setInterval(function () {
                $("#log").load(<?='"'.BASE_URL.'/public/robotino/log.php"';?>).fadeIn("slow");
            }, 1000);
        });
        </script>
    </body>
</html>

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


        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="<?= BASE_URL.'/public/js/bootstrap.min.js';?>"></script>




        <script type="text/javascript">
            $(document).ready( function() {
                setInterval(function () {
                    $("#d6t").load(<?='"'.BASE_URL.'/public/robotino/detection.php"';?>).fadeIn("slow");
                    $("#log").load(<?='"'.BASE_URL.'/public/robotino/log.php"';?>).fadeIn("slow");
                }, 1000);
            });
        </script>
        <script>

            // create click events for all the elements based on class.
            var fsClass = document.getElementsByClassName("fullScreen");
            for (var i = 0; i < fsClass.length; i++) {
                fsClass[i].addEventListener("click", function (evt) { makeFullScreen(evt); }, false);
            }

            function makeFullScreen(evt) {
                // Test for each of the supported versions of full screen APIs and call
                // either requestFullscreen or cancelFullScreen (or exitFullScreen)
                //  Structure:
                //  Does the incoming target support requestFullscreen (or prefaced version)
                //  if (there is a fullscreen element)
                //      then cancel or exit
                //  else request full screen mode

                var divObj = evt.target;  //  get the target element

                if (divObj.requestFullscreen)
                    if (document.fullScreenElement) {
                        document.cancelFullScreen();
                    } else {
                        divObj.requestFullscreen();
                    }
                else if (divObj.msRequestFullscreen)
                    if (document.msFullscreenElement) {
                        document.msExitFullscreen();
                    } else {
                        divObj.msRequestFullscreen();
                    }
                else if (divObj.mozRequestFullScreen)
                    if (document.mozFullScreenElement) {
                        document.mozCancelFullScreen();
                    } else {
                        divObj.mozRequestFullScreen();
                    }
                else if (divObj.webkitRequestFullscreen)
                    if (document.webkitFullscreenElement) {
                        document.webkitCancelFullScreen();
                    } else {
                        divObj.webkitRequestFullscreen();
                    }
                //  stop bubbling so we don't get bounce back
                evt.stopPropagation();

            }

        </script>
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



    </body>
</html>

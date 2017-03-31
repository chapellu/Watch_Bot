<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Website of Polytech Annecy students">
    <meta name="author" content="APP-IAI, Watchbot2018">

    <title><?= App::getInstance()->title; ?></title>

    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/bootstrap.min.css'; ?>"/>
    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/fonts/font-awesome/css/font-awesome.min.css';?>"/>
    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/style.css'; ?>"/>
    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/footer.css'; ?>"/>



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
    <script src="<?= BASE_URL.'/public/js/jquery.js';?>"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script type="text/javascript" src="<?= BASE_URL.'/public/js/bootstrap.min.js';?>"></script>


  </body>
</html>

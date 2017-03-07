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
    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/footer.css'; ?>"/>
    <link type="text/css" rel="stylesheet" href="<?= BASE_URL.'/public/css/style.css';?>"/>


  </head>

  <body>

    

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <!--<div class="jumbotron">
      <div class="container">
        <h1>Hello, world!</h1>
        <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>
      </div>
    </div>-->
    <?php
      require 'navbar.php';
    ?>


        <?= $content;?>
        <!--<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>-->

          <!--<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>-->
  


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

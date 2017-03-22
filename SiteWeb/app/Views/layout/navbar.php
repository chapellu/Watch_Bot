<!-- Static navbar -->
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<?= BASE_URL.'/home/';?>"><?= App::getInstance()->name_website;?></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<?= BASE_URL.'/home/';?>">Accueil</a></li>
                <li><a href="<?= BASE_URL.'/group/';?>">Groupe</a></li>
                <?php if($isLogged): ?>               
                    <!--<li><a href="#">Contact</a></li>-->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li class="dropdown-header">Panneau administration</li>
                            <li><a href="<?= BASE_URL.'/admin/post/';?>">Gérer les articles</a></li>
                            <li><a href="<?= BASE_URL.'/admin/category/';?>">Gérer les catégories</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="<?= BASE_URL.'/admin/robotino/';?>">Déplacement robotino</a></li>
                            <li><a href="<?= BASE_URL.'/admin/robotino/camera';?>">Paramétrage caméra</a></li>
                        </ul>
                    </li>
                <?php endif; ?>
            </ul> 
            <ul class="nav navbar-nav navbar-right">
                <?php if(!$isLogged): ?>
                    <li><a href="<?= BASE_URL.'/user/login';?>">Connexion <span class="sr-only"></span></a></li>
                <?php else: ?>
                    <li><a href="<?= BASE_URL.'/admin/user/logout';?>">Déconnexion<span class="sr-only"></span></a></li>
                <?php endif;?>

            </ul>


            
        </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
</nav>

<!-- Static navbar -->
<nav class="navbar navbar-default navbar-fixed-top" id="navbar_home">
    <div class="container" id="container_or_not">
        <div class="navbar-header">
            <button id="toggle_button" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<?= BASE_URL.'/home/';?>"><strong><?= App::getInstance()->name_website;?></strong></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="<?= BASE_URL.'/home/';?>" id="nav_item1">Accueil</a>
                </li>
                <li>
					<a href="<?= BASE_URL.'/project/';?>" id="nav_item2">Projet</a>
				</li>				
				<li>
					<a href="<?= BASE_URL.'/equipe/';?>" id="nav_item3">Equipe</a>
				</li>		
				<li>
					<a href="<?= BASE_URL.'/contact/';?>" id="nav_item4">Contact</a>
				</li>
				
            </ul>
            <ul class="nav navbar-nav navbar-right" id="nav_item5">
               

            </ul>



        </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
</nav>

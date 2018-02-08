<!-- Carousel
================================================== -->
<div class="root overlay">
    <!--<img src="<?= BASE_URL.'/public/img/nao_home.jpg';?>" id="root_image" style="margin:0px auto;">-->
    <header id="header-home">
        <div class="container-fluid">
            <div class="col-md-10 col-md-offset-1">
                <h1 id="homeHeading">WATCHBOT</h1>
				<hr>
				<p>Restez détendus même quand vous n'êtes pas chez vous</p>
            </div>
        </div>
		<div class="container-fluid">
			<a class="btn btn-primary btn-xl" href="#Notre_projet">Découvrir</a>
		</div>
    </header>

</div>
<!-- /.carousel -->



<!-- Page Content -->
<div id="Notre_projet" class="home_div">
    <div class="title">
        Notre projet
    </div>
	<hr>
    <div class="content">
		<video controls autoplay loop>
			<source src="<?= BASE_URL . '/public/video/Vol.mp4" type="video/mp4'; ?>">
			Your browser does not support the video tag.
		</video>

        <img src="<?= BASE_URL . '/public/img/nao_robotino.jpg'; ?>" alt="">
    </div>
</div>

<!-- Section description -->
<div class="home_div colored">
    <div class="title">
        Description
    </div>
	<hr>
    <div class="content-row">
        <div class="text">
			WATCHBOT est un projet de télésurveillance robotisée.  
			L'objectif est de surveiller un bâtiment afin de détecter une intrusion avec des robots mobiles.
			Ainsi, la solution WATCHBOT est composée d'un robot majordome "Nao" et d'un robot de surveillance "Robotino". 						
			
		</div>
		<div class="content">
			<div class="description">
				<img src="<?= BASE_URL . '/public/img/Nao.jpg'; ?>" alt="">
				<span>
				"Bonjour, je m'appelle Nao. Je suis à votre service et vous tiendrais 
				informé en cas d'intrusion. Communiquez simplement avec moi via la parole, ou bien utilisez mon site web."
				</span>
			</div>
				
			<div class="description">
				<img src="<?= BASE_URL . '/public/img/Robotino.jpg'; ?>" alt="">
				<span>
				"Bonjour, je m'appelle Robotino. Je surveille votre maison en permanence lorsque vous n'êtes pas là. Si je détecte une intrusion,
				Nao vous la signalera tout de suite. Grâce à mon retour vidéo et au site web, prenez le contrôle à distance et communiquez 
				directement avec l'intrus."
				</span>
			</div>
		</div>
		
    </div>
</div>

<!-- Section fonctionnement -->
<div class="home_div">
    <div class="title">
        Fonctionnement
    </div>
	<hr>
    <div class="content-row">
		<div class="fonctionnement">
			<img src="<?= BASE_URL . '/public/img/Nao_transparent.png'; ?>" alt="">
			<span>
				Nao est un petit robot humanoïde à qui nous avons donné le rôle de majordome. 
				Il peut ainsi :
				<ul>
					<li>superviser Robotino</li>
					<li>faire passer les informations importantes grâce à une interface transparente</li>
					<li></li>
				</ul>
			</span>
		</div>
		<div class="fonctionnement">
			<span>
				<ul>
					<li>Surveille sur demande</li>
					<li>Ronde aléatoire maximisant la couverture</li>
					<li>Caméra embarquée et commande à distance</li>
				</ul>
			</span>
			<img src="<?= BASE_URL . '/public/img/Robotino_transparent.png'; ?>" alt="">
		</div>
		<div class="fonctionnement">
			<img src="<?= BASE_URL . '/public/img/nao_robotino.jpg'; ?>" alt="">
			<span>
			<ul>
				<li>Contrôle à distance du système</li>
				<li>Accès en direct à la vidéo</li>
			</ul>
			</span>
		</div>
    </div>
</div>

<!-- Section Ecole -->
<div class="home_div colored">
    <div class="title">
        Polytech Annecy-Chambéry
    </div>
	<hr>
    <div class="content-row">	
        <img id= "im_annecy" src="<?= BASE_URL . '/public/img/Annecy.jpg'; ?>" alt="">
		<div class="presentation_polytech">
			Le réseau Polytech, premier réseau français des écoles d'ingenieurs polytechniques des universités.<br>
			Celui-ci regroupe 14 écoles publiques proposant 12 domaines de formation. <br>
			Cela représente 70 000 ingénieurs en activité ainsi que 3 400 diplômés annuels.<br> 
			Le réseau garantit une formation de grande qualité pour préparer les jeunes diplômés à relever les défis de demain.<br>
			
			Polytech Annecy-Chambéry propose 3 spécialités d'enseignements :
			<ul>
				<li>Environnement - Bâtiment - Energie</li>
				<li>Instrumentation - Automatique - Informatique</li>
				<li>Mécanique - Matériaux</li>
			</ul>
			L'une des spécificités de notre formation Instrumentation - Automatique - Informatique (IAI) est l'Apprentissage par Problèmes et par Projet (APP).<br>
			L'objectif principal est de développer nos compétences au cours d'un projet sur trois ans.<br>
			WATCHBOT répond à la problématique posée qui porte sur la télésurveillance robotisée. 
		</div>
        <img id="logo_polytech" src="<?= BASE_URL . '/public/img/logo_transparent.png'; ?>" alt="">
    </div>
</div>

<div class="home_div">
    <div class="title">Qui sommes nous ?</div>
	<hr>
    <div class="content">
		<p>Nous venons tous de différents cursus et nous sommes chacun spécialisés dans des domaines. Voici notre équipe :</p>
		<div class="profiles">
			<a href="<?= BASE_URL.'/group/view/?name=jason.boileau';?>"><img src="<?= BASE_URL . '/public/img/team/jason.boileau.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=quentin.boutonnet';?>"><img src="<?= BASE_URL . '/public/img/team/quentin.boutonnet.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=sandrine.broutin';?>"><img src="<?= BASE_URL . '/public/img/team/sandrine.broutin.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=ludovic.chapelet';?>"><img src="<?= BASE_URL . '/public/img/team/ludovic.chapelet.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=alan.dupin';?>"><img src="<?= BASE_URL . '/public/img/team/alan.dupin.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=nicolas.java';?>"><img src="<?= BASE_URL . '/public/img/team/nicolas.java.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=damien.morel';?>"><img src="<?= BASE_URL . '/public/img/team/damien.morel.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=arnaud.mignotte';?>"><img src="<?= BASE_URL . '/public/img/team/arnaud.mignotte.jpg';?>" alt="..." class="img-circle img_profile"></a>
			<a href="<?= BASE_URL.'/group/view/?name=william.simon';?>"><img src="<?= BASE_URL . '/public/img/team/william.simon.jpg';?>" alt="..." class="img-circle img_profile"></a>
		</div>
    </div>
</div>
<!--
<?php foreach ($posts as $k):?>
    <div class="col-sr-7">
        <div class="content-post">
            <h2 class="title"><?= $k->title; ?></h2>
            <p class="date">Publié le <?= $k->date; ?></p>
            <p><?= $k->content; ?></p>
        </div>
    </div>
<?php endforeach; ?>
 <div class="col-sm-4">
     <h2>Contact Us</h2>
     <address>
         <strong>Start Bootstrap</strong>
         <br>3481 Melrose Place
         <br>Beverly Hills, CA 90210
         <br>
     </address>
     <address>
         <abbr title="Phone">P:</abbr>(123) 456-7890
         <br>
         <abbr title="Email">E:</abbr> <a href="mailto:#">name@example.com</a>
     </address>
 </div>

 <hr>

     <div class="col-sl-4">
         <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
         <h2>Marketing Box #1</h2>
         <p>These marketing boxes are a great place to put some information. These can contain summaries of what the company does, promotional information, or anything else that is relevant to the company. These will usually be below-the-fold.</p>
     </div>
     <div class="col-sm-4">
         <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
         <h2>Marketing Box #2</h2>
         <p>The images are set to be circular and responsive. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
     </div>
     <div class="col-sm-4">
         <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
         <h2>Marketing Box #3</h2>
         <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
     </div>
 </div>
 <!-- /.row -->





</div>
<!-- /.container -->

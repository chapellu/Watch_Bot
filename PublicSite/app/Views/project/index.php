<div class="home_div">
	<div class="title">
		Notre projet
	</div>
	<hr>
	<div class="content-row">
		<p class="intro" >L'Apprentissage par Problèmes et par Projet est un module spécifique de la 
		spécialité IAI (Instrumentation Automatique Informatique) de l'école Polytech Annecy-Chambéry. 
		Nous avons choisi le projet de Robotique de Service dont le thème est la télésurveillance robotisée. 
		L’objectif est de proposer un système sécurisant des bâtiments avec des robots autonomes. 
		Ceux-ci doivent pouvoir interagir entre eux pour communiquer des informations. 
		Pour cela, nous avons à disposition les robots Nao et Robotino. Le rôle de Nao est d'orchestrer 
		l'ensemble du projet en tant que majordome tandis que Robotino réalise la surveillance.
		</p>
		<p class="details_robots">
		Le projet est axé autour de plusieurs problématiques.
		Robotino se charge de patrouiller au sein de bâtiments et de détecter une intrusion. 
		Il doit pouvoir se déplacer dans un milieu inconnu, détecter des obstacles afin de les 
		contourner et réaliser une cartographie de la zone parcourue. 
		Cette dernière permettra de définir un parcours de surveillance dans un milieu connu.
		Nao est le lien entre Robotino et l'utilisateur. Quand Robotino détecte une intrusion, l'information 
		est envoyée à Nao qui prévient le propriétaire de la situation. 
		Celui-ci reçoit une photo de l'intrus et peut avoir un retour vidéo depuis le robot 
		Robotino et contrôler ce dernier à distance. 
		Afin de prendre en compte les différences entre les individus et rendre le système utilisable 
		par tous, il disposera d'une interface graphique permettant de communiquer 
		avec Nao.

		</p>
		
		<img class="schema" src="<?= BASE_URL . '/public/img/Synoptique.PNG'; ?>" alt="">
		<div class="content-row" id="project_details">
			<p class="description_schema">
			Le synoptique reprend le fonctionnement interne du projet et son interaction avec l’environnement 
			extérieur. Le système à proprement parlé est constitué de 4 éléments :</p>
			<ul>
				<li>Nao, interface entre l'utilisateur et le système</li>
				<li>Une interface Web, complétant l'interface utilisateur</li>
				<li>Robotino, robot utilisé pour surveiller une zone</li>
				<li>Une Raspberry Pi3, centre du système gérant l'intégralité du système</li>
			</ul>
			<p class="description_schema">
			Les interactions avec le système se font de plusieurs manières :</p>
			<ul>
				<li>Nao et l’utilisateur conversent de façon auditive et vocale</li>
				<li>La Raspberry Pi3 et l’utilisateur échangent des données par e-mails</li>
				<li>L’utilisateur peut accéder aux informations fournies par l’application ou prendre le contrôle de cette 
				dernière via une interface Web.</li>
			</ul>
		</div>
	</div>
</div>

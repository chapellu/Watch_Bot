<?php
namespace Core;

class Autoloader{
	
	static function register(){
		spl_autoload_register(array(__CLASS__, 'autoload'));
	}

	/**
	 * Inclue le fichier coorespond à la classe
	 */
	static function autoload($class)
    {
		//echo "----------------Classe=". $class;
		//echo " Namespace =" . __NAMESPACE__;
		if(strpos($class, __NAMESPACE__ . '\\') === 0) {
			$class = str_replace(__NAMESPACE__ . '\\', '', $class);
			//echo "----------------Classe1=". $class;
			//$class = str_replace('\\', '/', $class);
			//echo "----------------Classe2=". __DIR__ . '\\' . $class . '.php';
			require __DIR__ . '\\' . $class . '.php';
		}
	}


}



?>
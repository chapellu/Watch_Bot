<?php
/**
 * Created by PhpStorm.
 * User: alan
 * Date: 21/01/2017
 * Time: 22:02
 */

namespace Core\Url;


class Router
{
    static $routes = array();
    static $prefixes = array();

    static function parse($url,$request){
        $offset = 1; //Variable qui permet aux differents paramètres de bien prendre les bonnes infos
        $url = trim($url,'/'); //trim enlève les espaces en début et fin de chaine

        foreach (Router::$routes as $k => $v) {

            if(preg_match(($v['catcher']), $url, $match)){
                $request->controller=$v['controller'];
                $request->action=$v['action'];
                $request->params=array();
                foreach ($v['params'] as $i=>$w) {
                    $request->params[$i]=$match[$i];
                }
                return $request;
            }
        }

        $params = explode('/',$url);
        if(isset($params[$offset])){
            if(in_array($params[$offset],array_keys(self::$prefixes))){
                $request->prefix=self::$prefixes[$params[$offset]];
                array_shift($params); //Enlève le préfixe donc l'élement 0
            }
        }

        $request->controller=isset($params[$offset]) ? $params[$offset] : 'home';
        if(!isset($params[$offset+1]) || (strpos($params[$offset+1], '?') ===0)) {
            $request->action = 'index';
        }else{
            $request->action = $params[$offset+1];
        }

        $request->params=array_slice($params,$offset+2); //Maintenant qu'on a lu le controller on saute les deux premiers élements du tableau
        return true;
    }

    static function connect($redir,$origin){
        $r = array();

        $r['redir']=$redir;
        $r['origin']= preg_replace('/([a-z0-9]+):([^\/]+)/','${1}:(?P<${1}>${2})',$origin); //Récupère les différents paramètres dans un tableau
        //str_replace(search, replace, subject)
        $r['origin']='/'.str_replace('/','\/',$r['origin']).'/';
        $r['params']=array();

        $params=explode('/', $origin);
        foreach ($params as $k => $v) {
            if(strpos($v,':')){
                $p = explode(':',$v);
                $r['params'][$p[0]]=$p[1];
            }else{
                if($k==0){
                    $r['controller']=$v;
                }elseif ($k==1) {
                    $r['action']=$v;
                }
            }
        }

        $r['catcher']=$redir;
        foreach ($r['params'] as $k => $v) {
            //str_replace(search, replace, subject)
            $r['catcher']=str_replace(":$k", "(?P<$k>$v)", $r['catcher']);
        }
        $r['catcher']='/'.str_replace('/','\/',$r['catcher']).'/';
        self::$routes[]=$r;
    }

    static function url($url){
        foreach (self::$routes as $k => $v) {
            if(preg_match($v['origin'],$url,$match)){//match contient les différents paramètres à chaque paramètres de la config
                //Maintenant on va parcourir les paramètres de $match pour récupérer les id slug etc...
                foreach ($match as $k=>$w) {
                    if(!is_numeric($k)){
                        $v['redir']=str_replace(":$k",$w,$v['redir']);
                    }
                }
                return $v['redir'];
            }
        }
        foreach (self::$prefixes as $k => $v) {
            if(strpos($url, $v)===0){
                $url = str_replace($v, $k, $url);
            }
        }
        return BASE_URL.DS.$url;
    }


    /**
     * Fonctions pour admin et autres préfixes
     */

    static function prefix($url,$prefix){
        self::$prefixes[$url]=$prefix;
    }
}
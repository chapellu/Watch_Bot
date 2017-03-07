<?php


namespace App\Table;

use \Core\Table\Table;

class PostTable extends Table
{

    public function last(){
        return $this->query("
            SELECT post.id, post.title, post.content, post.date, category.title as category
            FROM post
            LEFT JOIN category ON post.category_id = category.id
            ORDER BY post.date DESC");


    }

    public function findWithCategory($id){
        return $this->query("
            SELECT post.id, post.title, post.content, post.date, category.title as categorie, post.category_id 
            FROM post
            LEFT JOIN category ON category_id = category.id
            WHERE post.id = ?", [$id], true);
    }
    
    public function lastByCategory($category_id){
        return $this->query("
            SELECT post.id, post.title, post.content, post.date, category.title as categorie
            FROM post
            LEFT JOIN category ON category_id = category.id
            WHERE post.category_id = ?
            ORDER BY post.date DESC", [$category_id]);
    }

    public function getAllHomePosts(){
        return $this->query("
            SELECT post.id, post.title, post.content, post.date, category.title as category
            FROM post
            LEFT JOIN category ON post.category_id = category.id
            WHERE category.title = ?
            ORDER BY post.date DESC", ['Accueil']);
    }

    public function getAllMembers(){
        return $this->query("
            SELECT post.id, post.title, post.content, post.date, category.title as category
            FROM post
            LEFT JOIN category ON post.category_id = category.id
            WHERE category.title = ?
            ORDER BY post.date DESC", ['Groupe']);    }

}
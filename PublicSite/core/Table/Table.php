<?php
/**
 * Created by PhpStorm.
 * User: alan
 * Date: 19/01/2017
 * Time: 12:43
 */

namespace Core\Table;

use Core\Database;


class Table
{
    protected $table;
    protected $db;

    public function __construct(Database $db)
    {
        $this->db = $db;
        //Récupère le nom de la table
        if(is_null($this->table)){
            $parts = explode('\\', get_class($this));
            $class_name = end($parts);
            $this->table = strtolower(str_replace('Table', '', $class_name));
        }
        
    }
    
    public function all(){
        return $this->query('SELECT * FROM ' . $this->table);
    }

    public function query($statement, $attributes = null, $one = false){
        if($attributes) {
            return $this->db->prepare(
                $statement,
                $attributes,
                str_replace('Table', 'Entity', get_class($this)),
                $one);
        } else {
            return $this->db->query(
                $statement,
                str_replace('Table', 'Entity', get_class($this)),
                $one);
        }
    }

    public function find($id){
        return $this->query("SELECT * FROM {$this->table} WHERE id = ?", [$id], true);
    }

    public function update($id, $fields){
        $sql_parts = [];
        $attributes = [];
        foreach ($fields as $k=>$v ){ //k nom du champ $v la valeur
            $sql_parts[] = "$k = ?";
            $attributes[] = $v; //va remplacer le ?
        }
        $attributes[] = $id;
        $sql = implode(', ', $sql_parts);
        return $this->query("UPDATE {$this->table} SET $sql WHERE id = ?", $attributes, true);
    }

    public function create($fields){
        $sql_parts = [];
        $attributes = [];
        foreach ($fields as $k=>$v ){ //k nom du champ $v la valeur
            $sql_parts[] = "$k = ?";
            $attributes[] = $v; //va remplacer le ?
        }
        $sql = implode(', ', $sql_parts);
        return $this->query("INSERT {$this->table} SET $sql", $attributes, true);
    }

    public function liste($key, $value){
        $records = $this->all();
        $return = [];
        foreach ($records as $k=>$v){
            $return[$v->$key] = $v->$value;
        }
        return $return;
    }

    public function delete($id){
        return $this->query("DELETE FROM {$this->table} WHERE id = ?", [$id], true);
    }

}


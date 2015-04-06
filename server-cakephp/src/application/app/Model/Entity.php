<?php

class Entity extends AppModel {

  var $name = 'Entity';
  
  public $hasMany = array(
  		'RelativePosition' => array(
  				'className'     => 'RelativePosition',
  				'foreignKey'    => 'center_entity_id',
  		)
  );

}

?>
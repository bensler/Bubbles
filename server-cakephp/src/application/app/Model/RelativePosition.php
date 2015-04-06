<?php

class RelativePosition extends AppModel {

  var $name = 'RelativePosition';
  
  public $belongsTo = array(
  		'Entity' => array(
  				'className'    => 'Entity',
  				'foreignKey'   => 'aux_entity_id'
  		)
  );

}

?>
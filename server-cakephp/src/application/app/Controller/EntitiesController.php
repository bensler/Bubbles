<?php
class EntitiesController extends AppController {

  var $name = 'Entities';
  
  var $uses = array('Entity', 'RelativePosition');
  
  public $components = array('RequestHandler');
  
  public function beforeFilter() {
   	$this->Auth->allow();
  }
  			
  function logout() {
   	$this->Auth->logout();
  	$this->setResult(NULL);
  }

  function login() {
  	$this->readRequest();
  	$this->Auth->logout();
  	$user = $this->Auth->identify($this->request, $this->response);
  	$this->request->data = array();
  	if ($user) {
  		$this->Auth->login($user);
  	}
  	$this->setResult($user ? $user : NULL);
  }
  
  function saveNodePosition($id) {
  	if ($this->loggedIn()) {
  		$requestData = $this->request->data;
  		
  		$positionData = $this->RelativePosition->find('first', array(
  				'conditions' => array(
  					'RelativePosition.center_entity_id' => $id,
  					'RelativePosition.aux_entity_id' => $requestData['targetEntity']
  				),
  				'recursive' => -1
  		));
  		$relativePosition = $positionData['RelativePosition'];
  		$positionId = $relativePosition['id'];
  		$this->RelativePosition->read(null, $positionId);
  		$this->RelativePosition->set('dx', $requestData['RelativePosition']['dx']);
  		$this->RelativePosition->set('dy', $requestData['RelativePosition']['dy']);
  		$this->RelativePosition->save();
  		$positionData = $this->RelativePosition->read(null, $positionId);
  		$relativePosition = $positionData['RelativePosition'];
  		$relativePosition['dx'] = intval($relativePosition['dx']);
  		$relativePosition['dy'] = intval($relativePosition['dy']);
  		
			$this->setResult($relativePosition);
    }
  }
  
  function getInitialDisplayMap() {
  	$this->getDisplayMap(null);
  }
  
  function getDisplayMap($id) {
  	if ($this->loggedIn()) {
  		$result = array();
  		$children = array();
  		
  		$raw = (($id != null)
  			? $this->Entity->find('first', array(
  					'conditions' => array('Entity.id' => $id),
  					'recursive' => 3
  			))
  			: $this->Entity->find('first', array(
  					'order' => array('Entity.id' => 'asc'),
  					'recursive' => 3
  				)) 
  		);

  		if (empty($raw)) {
				$result = $this->createMapEntry(null, null);
  		} else {
  			$entity = $raw['Entity'];
  			
  			foreach ($raw['RelativePosition'] as $relativePosition) {
  				array_push($children, $this->createRelationship($relativePosition));
  			}
				$result = $this->createMapEntry($entity, $children);
  		}
			$this->setResult($result);
  	}
  }
  
  private function createRelationship($dbRelPosition) {
  	$relativePosition = $this->copyArraySelectivly($dbRelPosition, array('dx', 'dy'));
  	$relativePosition['dx'] = intval($relativePosition['dx']);
  	$relativePosition['dy'] = intval($relativePosition['dy']);
  	$dbEntity = $dbRelPosition['Entity'];
  	
//   	[RelativePosition] => Array	(
//   			[0] => Array (
//   					[id] => 10
//   					[center_entity_id] => 4
//   					[aux_entity_id] => 1
//   					[dx] => 0
//   					[dy] => -100
//   			)
//   	)
  	 
  	return $this->createMapEntry(
  		$this->copyArraySelectivly($dbEntity, array('id', 'name', 'description')), 
  		$relativePosition
  	);
  }
  
  private function copyArraySelectivly($source, $keysToCopy) {
  	$copy = array();
  	foreach ($keysToCopy as $key) {
  		$copy[$key] = $source[$key];
  	}
		return $copy;
  }
  
  private function createMapEntry($key, $value) {
  	$entry = array();
  	$entry['key'] = $key;
  	$entry['value'] = $value;
  	return $entry;
	}
  
  private function loggedIn() {
  	$this->readRequest();
  	$loggedIn = $this->Auth->loggedIn();
 		$this->setResult(NULL);
  	return $loggedIn;
  }
  
  private function readRequest() {
  	$input = $this->request->input('json_decode');
  	if ($input == null) {
  		// no json input available when using browser to test actions 
  		$input = array();
  	}
  	$this->request->data = $this->objectToArray(array_pop($input));
  }
  
  private function setResult($resultData) {
  	$result = array('data' => $resultData);
  	$result['request'] = $this->request->data;
  	$result['loggedIn'] = $this->Auth->loggedIn();
  	$this->set('result', $result);
  	$this->set('_serialize', 'result');
  }
  
  private function objectToArray($object) {
  	if (!is_object($object) && !is_array($object)) {
  		return $object;
  	}
  	if (is_object($object)) {
  		$object = get_object_vars( $object );
  	}
  	return array_map(array(&$this, 'objectToArray'), $object);
  }

}
?>

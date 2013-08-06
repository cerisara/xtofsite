<?php
header('Content-type: text/json');


$url = 'http://talc1.loria.fr/users/cerisara/caldat.txt';
ini_set('default_socket_timeout',    120);   
$caldat = file_get_contents($url);

echo '[';
$separator = "";
$days = 16;
 //   echo '  { "date": "2013-03-19 17:30:00", "type": "meeting", "title": "Test Last Year", "description": "Lorem Ipsum dolor set", "url": "" },';
  //  echo '  { "date": "2013-03-23 17:30:00", "type": "meeting", "title": "Test Next Year", "description": "Lorem Ipsum dolor set", "url": "http://www.event3.com/" },';

$i = 1;
    echo $separator;
    $initTime = date("Y")."-".date("m")."-".date("d")." ".date("H").":00:00";

    $tok = strtok($caldat, ",\n");
    while ($tok !== false) {
      $date = $tok;
      $tok = strtok(",\n");
      $time = $tok;
      $tok = strtok(",\n");
      $type = $tok;
      $tok = strtok(",\n");
      $title = $tok;
      $tok = strtok(",\n");
      $who = $tok;
      $tok = strtok(",\n");
      
      if ($tok ==false) {
	echo '  { "date": "'.$date.' '.$time.'", "type": "'.$type.'", "title": "'.$title.'", "description": "'.$who.'", "url": "" }';
      } else {
	echo '  { "date": "'.$date.' '.$time.'", "type": "'.$type.'", "title": "'.$title.'", "description": "'.$who.'", "url": "" },';
      }
    }
    
    $separator = ",";

echo ']';
?>

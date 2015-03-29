<?php
  $xml = $_POST['xml'];
  $a = time();
  $fn = '/tmp/reps.'.$a.'.txt';
  file_put_contents($fn,$xml);
?>


<?php
date_default_timezone_set("America/New_York");
if ($handle = opendir('/var/sound')) {
    while (false !== ($fileName = readdir($handle))) {
        if (strpos($fileName,"431") != false && strpos($fileName,".wav") != false) {
		$time = str_replace(".wav","",$fileName);
		$newName = date('Y-m-d-h-i-s', $time).".wav";
		//echo $fileName." -> ".$newName."\n";
        	rename($fileName, $newName);
	}
    }
    closedir($handle);
}
?>

<?php

# log some data
$logFile = fopen("logger.txt", "a");

fwrite($logFile, "--------------------\nHTTP REQUEST HEADERS\n--------------------\n");
function parseRequestHeaders() {
    $headers = array();
    foreach($_SERVER as $key => $value) {
        if (substr($key, 0, 5) <> 'HTTP_') {
            continue;
        }
        $header = str_replace(' ', '-', ucwords(str_replace('_', ' ', strtolower(substr($key, 5)))));
        $headers[$header] = $value;
    }
    return $headers;
}
$headers = parseRequestHeaders();

foreach ($headers as $key => $value) {
    fwrite($logFile, $key . " -> " . $value . "\n");
}

fwrite($logFile, "\n--------------------\nGET VARIABLES\n--------------------\n");
foreach ($_GET as $key => $value) {
    fwrite($logFile, $key . " -> " . $value . "\n");
}

fwrite($logFile, "\n--------------------\nPOST VARIABLES\n--------------------\n");
foreach ($_POST as $key => $value) {
    fwrite($logFile, $key . " -> " . $value . "\n");
}

$uploadsDir = "./files/";
fwrite($logFile, "\n--------------------\nFILES\n--------------------\n");
foreach ($_FILES as $name => $file) {
    $savedFilename = $file["name"] . date("y-m-d@H:m:s_O",time());
    move_uploaded_file($file["tmp_name"], $uploadsDir . $savedFilename);
    fwrite($logFile, "File: " . $name . "\n");
    fwrite($logFile, "Location: " . $uploadsDir . $savedFilename . $name . "\n");
    fwrite($logFile, "    name -> " . $file["name"] . "\n");
    fwrite($logFile, "    type -> " . $file["type"] . "\n");
    fwrite($logFile, "    size -> " . $file["size"] . "\n");
    fwrite($logFile, "    error -> " . $file["error"] . "\n");
}

fwrite($logFile, "\n##################################################################################\n\n");

# close the file
fclose($logFile);

exit;
?>

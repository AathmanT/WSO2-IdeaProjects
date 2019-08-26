import ballerina/io;
import ballerina/log;


function process(io:WritableCharacterChannel dc) returns error? {

    string threadSize = "30";
    var writeCharResult = check dc.write("[b7a.runtime.scheduler]\nthreadpoolsize="+threadSize, 0);


    return;
}
function closeRc(io:ReadableCharacterChannel ch) {
    var cr = ch.close();
    if (cr is error) {
        log:printError("Error occured while closing the channel: ", err = cr);
    }
}
function closeWc(io:WritableCharacterChannel ch) {
    var cr = ch.close();
    if (cr is error) {
        log:printError("Error occured while closing the channel: ", err = cr);
    }
}public function main() {
    io:WritableCharacterChannel destinationChannel =
            new(io:openWritableFile("sampleResponse.txt"), "UTF-8");
    io:println("Started to process the file.");
    var result = process(destinationChannel);
    if (result is error) {
        log:printError("error occurred while processing chars ", err = result);
    } else {
        io:println("File processing complete.");
    }
    closeWc(destinationChannel);
}

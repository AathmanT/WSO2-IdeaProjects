import ballerina/http;
import ballerina/log;

service hello on new http:Listener(9092) {

    @http:ResourceConfig {
        methods: ["POST", "PUT", "GET"],
        path: "/"
    }
    resource function helloResource(http:Caller caller, http:Request req) {

        var result = caller->respond(req.getBodyParts());
        if (result is error) {
            log:printError("Error sending response", err = result);
        }
    }
}

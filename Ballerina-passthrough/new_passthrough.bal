import ballerina/http;
import ballerina/log;
http:Client clientEP = new("http://localhost:8688/EchoService");
service passthrough on new http:Listener(9090) {
    @http:ResourceConfig {
        path: "/"
    }
    resource function passthrough(http:Caller caller, http:Request req) {

        var clientResponse = clientEP->forward("/", req);

        if (clientResponse is http:Response) {

            var result = caller->respond(clientResponse);
            if (result is error) {
               log:printError("Error sending response", err = result);
            }
        } else {

            http:Response res = new;
            res.statusCode = 500;
            res.setPayload(<string>clientResponse.detail().message);
            var result = caller->respond(res);
            if (result is error) {
               log:printError("Error sending response", err = result);
            }
        }
    }
}
service hello on new http:Listener(9092) {

    @http:ResourceConfig {
        methods: ["POST", "PUT", "GET"],
        path: "/"
    }
    resource function helloResource(http:Caller caller, http:Request req) {

        var result = caller->respond("Hello World!");
        if (result is error) {
            log:printError("Error sending response", err = result);
        }
    }
}
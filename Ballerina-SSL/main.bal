import ballerina/http;
import ballerina/log;
http:ServiceEndpointConfiguration helloWorldEPConfig = {
    secureSocket: {
        keyStore: {
            path: "${ballerina.home}/bre/security/ballerinaKeystore.p12",
            password: "ballerina"
        },
        trustStore: {
            path: "${ballerina.home}/bre/security/ballerinaTruststore.p12",
            password: "ballerina"
        },
        protocol: {
            name: "TLS",
            versions: ["TLSv1.2", "TLSv1.1"]
        },
        ciphers: ["TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"],
        sslVerifyClient: "require"
    }
};
listener http:Listener helloWorldEP = new(9095, config = helloWorldEPConfig);@http:ServiceConfig {
    basePath: "/hello"
}
service helloWorld on helloWorldEP {    @http:ResourceConfig {
        methods: ["GET"],
        path: "/"
    }
    resource function sayHello(http:Caller caller, http:Request req) {
        var result = caller->respond("Successful");
        if (result is error) {
            log:printError("Error in responding", err = result);
        }
    }
}
http:ClientEndpointConfig clientEPConfig = {
    secureSocket: {
        keyStore: {
            path: "${ballerina.home}/bre/security/ballerinaKeystore.p12",
            password: "ballerina"
        },
        trustStore: {
            path: "${ballerina.home}/bre/security/ballerinaTruststore.p12",
            password: "ballerina"
        },
        protocol: {
            name: "TLS"
        },
        ciphers: ["TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"]
    }
};public function main() {
    http:Client clientEP = new("https://localhost:9095",
                                config = clientEPConfig);
    var resp = clientEP->get("/hello");
    if (resp is http:Response) {
        var payload = resp.getTextPayload();
        if (payload is string) {
            log:printInfo(payload);
        } else {
            log:printError(<string>payload.detail().message);
        }
    } else {
        log:printError(<string>resp.detail().message);
    }
}

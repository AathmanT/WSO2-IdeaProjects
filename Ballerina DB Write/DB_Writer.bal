import ballerina/io;
import ballerina/mysql;
import ballerina/sql;
mysql:Client testDB = new({
        host: "localhost",
        port: 3306,
        name: "testdb",
        username: "root",
        password: "root",
        dbOptions: { useSSL: false }
    });
jdbc:Client testDB = new({
    url: "jdbc:mysql://localhost:3306/testdb",
    username: "root",
    password: "root",
    dbOptions: { useSSL: false }
});
public function main() {
    //io:println("The update operation - Creating a table:");
    //var ret = testDB->update("CREATE TABLE student(id INT AUTO_INCREMENT,
    //                      age INT, name VARCHAR(255), PRIMARY KEY (id))");
    //handleUpdate(ret, "Create student table");

    io:println("\nThe update operation - Inserting data to a table");
    var ret = testDB->update("INSERT INTO student(age, name)
                          values (23, 'john')");
    handleUpdate(ret, "Insert to student table with no parameters");


    io:println("\nThe select operation - Select data from a table");
    var selectRet = testDB->select("SELECT * FROM student", ());
      if (selectRet is table<record {}>) {
        io:println("\nConvert the table into json");
        var jsonConversionRet = json.convert(selectRet);
        if (jsonConversionRet is json) {
            io:print("JSON: ");
            io:println(io:sprintf("%s", jsonConversionRet));
        } else {
            io:println("Error in table to json conversion");
        }
    } else {
        io:println("Select data from student table failed: "
                + <string>selectRet.detail().message);
    }
    //io:println("\nThe update operation - Drop student table");
    //ret = testDB->update("DROP TABLE student");
    //handleUpdate(ret, "Drop table student");
    //var stopRet = testDB.stop();
    //if (stopRet is error) {
    //    io:println(stopRet.detail().message);
    //}
}
function handleUpdate(sql:UpdateResult|error returned, string message) {
    if (returned is sql:UpdateResult) {
        io:println(message + " status: " + returned.updatedRowCount);
    } else {
        io:println(message + " failed: " + <string>returned.detail().message);
    }
}

var express = require("express");
var app = express();
app.listen(8080, () => {
 console.log("Server running on port 3000");
});

app.get("/json", (req, res, next) => {
    res.json(["Tony","Lisa","Michael","Ginger","Food"]);
 });

 app.get("/raw", (req, res, next) => {
    res.send("AHOJ")
 });


 app.get("/file", (req, res, next) => {
    res.attachment()
    res.sendFile("/tmp/academy_networking/simple-server/resources/image.jpeg")
 });

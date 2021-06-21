var express = require("express");

var index = 0;

var listOfPeople = [
  {
    name: "Sarah",
    surname: "Connor",
    favouriteDay: "favorite_day",
  },
];

var app = express();
app.listen(8080, () => {
  console.log("Server running on port 8080");
});

app.get("/json", (req, res, next) => {
  setTimeout(function () {
    index = (index + 1) % listOfPeople.length;
    res.json(listOfPeople[index]);
  }, 1500);
});

app.get("/raw", (req, res, next) => {
  setTimeout(function () {
    res.send("AHOJ");
  }, 1500);
});

app.get("/file", (req, res, next) => {
  setTimeout(function () {
    res.attachment();
  }, 1500);
});

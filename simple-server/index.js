var express = require("express");

var index = 0;

var listOfPeople = [
  {
    name: "Sarah",
    surname: "Connor",
    favouriteDay: "Friday",
  },
  {
    name: "Garfield",
    surname: "Davis",
    favouriteDay: "Monday",
  }
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
    res.send("This data comes from the server");
  }, 1500);
});


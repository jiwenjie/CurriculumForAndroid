var express = require('express');
var router = express.Router();
var path = require("path");
var sqlite3 = require('sqlite3');

/**
 * 
 * 对于以/开始的路径片段，path.join只是简单的将该路径片段进行拼接，
 * 而path.resolve将以/开始的路径片段作为根目录，在此之前的路径将会被丢弃，
 * 就像是在terminal中使用cd命令一样。
 * @type {sqlite3}
 */
var db = new sqlite3.Database(path.join(__dirname, "..", 'data.db'));

/* GET home page. 
	__dirname 表示当前文件所在的目录的绝对路径
	__filename 表示当前文件的绝对路径
	module.filename ==== __filename 等价
	sendFile表示为发送纯文本
*/
router.get('/', function (req, res, next) {
  res.sendFile(path.join(__dirname, '/../views/index.html'));
});

router.get('/json', function (req, res, next) {
  var param = req.query;
  if (param.hasOwnProperty("username")) {
    if (param.hasOwnProperty("register")) {
      register(res, req);
    }
    else {
      login(res, req);
    }
  }
  else if (param.hasOwnProperty("addCal")){
    if(checkLogin(req.session)){
      addCalendar(res, param);
    }
    else
      res.send("method error");
  }
  else
    res.send("method error");
});

function addCalendar(res, param){
  var sqlStr = "INSERT INTO 'schedule' (userId,start,end,comment,position,title,date) VALUES \
              ('" + param.user + "','" + param.start + "','" + param.end + "','" + param.comment + "','"
              + param.position + "','" + param.title + "','" + param.date + "')";
  console.log(sqlStr);
  db.run(sqlStr, function(err){
    if (err != null){
      res.send(JSON.stringify({ event: "addCal", result: false, msg: err}));
    }
    else{
      res.send(JSON.stringify({ event: "addCal", result: true }));
    }
  });
}

function checkLogin(session){
  if (session.username)
    return true;
  return false;
}

function login(res, req) {
  var username = req.query.username;
  var passowrd = req.query.password;
  var sqlStr = "SELECT * FROM 'user' WHERE name='" + username + "' AND password='" + passowrd + "'";
  console.log(sqlStr);
  db.all(sqlStr, function (err, rows) {
    if (err == null && rows.length == 1){
      req.session.username = username;
      res.send(JSON.stringify({ event: "login", result: true }));
    }
    else
      res.send(JSON.stringify({ event: "login", result: false, msg: err }));
  });
}

function register(res, req) {
  var username = req.query.username;
  var passowrd = req.query.password;
  var sqlStr = "INSERT INTO 'user' (name,password) VALUES ('" + username + "','" + passowrd + "')";
  console.log(sqlStr);
  db.run(sqlStr, function (err) {
    if (err != null) {
      req.session.username = username;
      req.session.save();
      res.send(JSON.stringify({ event: "register", result: false, msg: err }));
    }
    else {
      res.send(JSON.stringify({ event: "register", result: true }));
    }
  })
}

module.exports = router;

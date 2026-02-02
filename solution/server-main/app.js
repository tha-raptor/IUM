const createError = require('http-errors');
const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

const http = require('http');
const {Server} = require("socket.io");
const socketModule = require("./socket.io/socket.io");

const app = express();

const server = http.createServer(app);
const io = new Server(server);

socketModule.init(io);

const indexRoutes = require('./routes/index');
const port = 3000;

app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.json({limit: '5mb'}));
app.use(bodyParser.json());

app.use('/', indexRoutes);

app.use(function (req, res, next) {
    next(createError(404));
});

app.use((err, req, res, next) => {
    res.status(err.status || 500).json({
        error: {
            message: err.message || 'Internal Server Error'
        }
    });
});

server.listen(port, () => {
    console.log(`Server-main running on port: ${port}`);
});
module.exports = app;
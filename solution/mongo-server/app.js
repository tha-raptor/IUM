const swaggerJsDoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');
const express = require('express');
const app = express();
const reviewRoutes = require('./routes/index');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const path = require('path');

const port = 3001;

app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const swaggerDocument = require('./swagger.json');
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

mongoose.connect('mongodb://localhost:27017/TomatoesDB')
    .then(() => {
        console.log('TomatoesDB Connected');
        app.listen(port, () => console.log(`Mongo-Server running on port ${port}`));
    })
    .catch(err => console.error("Connection error:", err));

app.use('/', reviewRoutes);
module.exports = app;
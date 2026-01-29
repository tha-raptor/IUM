const express = require('express');
const router = express.Router();
const axios = require('axios');

router.get('/', (req, res) => {
    res.render('index', { title: 'DB Movie' });
});

router.get('/api/v1/movies/:id', async (req, res) => {
   const movieId = req.params.id;

   try {
       const response = await axios.get(`http://localhost:8080/api/v1/movies/${movieId}`);
       const movieData = response.data;

       res.json(movieData);
   }catch (error) {
       console.log(error);
       res.status(500).send(error);
   }
});

module.exports = router;
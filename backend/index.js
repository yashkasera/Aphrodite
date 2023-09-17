const express = require('express');
const app = express();
const dotenv = require('dotenv');
const { connectToDB } = require('./connection/db');
dotenv.config();
const port = process.env.PORT || 3000;
const url = process.env.DB_URL;
const router = require('./routes/achievements');

connectToDB(url).then(() => {console.log('Connected to DB')}).catch((err) => {console.log(err)});

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use('/api',router);

app.listen(port, () => {console.log(`Server started on port : ${port}`)});

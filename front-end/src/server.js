const express = require('express');
const path = require('path');
const app = express();
const port = 3000;

app.use('/static', express.static(path.resolve('build', 'static')));

app.get('*', (req, res) => res.sendFile(path.resolve('build', 'index.html')));

app.listen(port, () => console.log(`MAG app listening on port ${port}`));
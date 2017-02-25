var path = require('path');
var APP_DIR = path.resolve(__dirname, 'src/main/js');
var BUILD_DIR = path.resolve(__dirname, 'src/main/resources/static/built');
var config = {
    entry: APP_DIR + '/app.jsx',
    devtool: 'sourcemaps',
    output: {
        path: BUILD_DIR,
        filename: 'bundle.js'
    },
    module: {
        preLoaders: [
            {
                test: /\.jsx$|\.js$/,
                loader: 'eslint',
                exclude: /node_modules/
            }
        ],
        loaders: [
            {
                test: /\.jsx$|\.js$/,
                loader: 'babel',
                include: APP_DIR
            }
        ]
    },
    eslint: {
        failOnWarning: false,
        failOnError: true
    },
};

module.exports = config;
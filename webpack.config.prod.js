var path = require('path');
var webpack = require('webpack');
var APP_DIR = path.resolve(__dirname, 'src/main/js');
var BUILD_DIR = path.resolve(__dirname, 'src/main/resources/static/built');
var config = {
    entry: APP_DIR + '/index.js',
    devtool: 'sourcemaps',
    output: {
        path: BUILD_DIR,
        pathinfo: true,
        filename: 'bundle.js',
        publicPath: '/built'
    },
    context: APP_DIR,
    module: {
        rules: [
            {
                enforce: 'pre',
                test: /\.jsx$|\.js$/,
                loader: 'eslint-loader',
                exclude: /node_modules/
            },
            {
                test: /\.jsx$|\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            }
        ]
    },
    plugins: [
        new webpack.NamedModulesPlugin(),
        new webpack.NoEmitOnErrorsPlugin()
    ]
};

module.exports = config;
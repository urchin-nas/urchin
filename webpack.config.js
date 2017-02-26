var path = require('path');
var webpack = require('webpack');
var APP_DIR = path.resolve(__dirname, 'src/main/js');
var BUILD_DIR = path.resolve(__dirname, 'src/main/resources/static/built');
var config = {
    entry: [
        'react-hot-loader/patch',
        'webpack-dev-server/client?http://localhost:3000',
        'webpack/hot/only-dev-server',
        APP_DIR + '/index.js',
    ],
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
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NamedModulesPlugin(),
        new webpack.NoEmitOnErrorsPlugin(),
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
            },
            output: {
                comments: false,
            },
        })
    ],
    devServer: {
        host: 'localhost',
        port: 3000,
        hot: true,
        contentBase: path.resolve(__dirname, 'src/main/resources/static'),
        publicPath: '/built',
        proxy: {
            '/api': {
                target: 'http://localhost:8080'
            }
        }
    },
};

module.exports = config;
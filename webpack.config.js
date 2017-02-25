var path = require('path');
var webpack = require('webpack');
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
        rules: [
            {
                enforce: 'pre',
                test: /\.jsx$|\.js$/,
                loader: 'eslint-loader',
                exclude: /node_modules/
            },
            {
                test: /\.jsx$|\.js$/,
                loaders: ['react-hot-loader', 'babel-loader'],
                include: APP_DIR
            }
        ]
    },
    plugins: [
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
            },
            output: {
                comments: false,
            },
        }),
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoEmitOnErrorsPlugin()
    ],
    devServer: {
        contentBase: "./src/main/resources/static/",
        hot: true
    },
};

module.exports = config;
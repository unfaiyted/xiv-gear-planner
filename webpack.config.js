// for resolving the absolute path to our project
// necessary for webpack
const path = require('path');
const webpack = require('webpack');

module.exports = {
    // where our app "starts"
    // add the promise and fetch polyfill first
    entry: {
        main: ['promise-polyfill', 'whatwg-fetch', './src/main/resources/static/js/global.js'],
        player: './src/main/resources/static/js/player.js',
        gear: './src/main/resources/static/js/gear.js',
        static: './src/main/resources/static/js/static.js'


    },
    // where to put the transpiled javascript
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/built'),
        filename: '[name].js'
    },

    // babel config
    module: {
        rules: [
            {
                // any file that ends with '.js'
                test: /\.js$/,
                // except those in "node_modules"
                exclude: /node_modules/,
                // transform with babel
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env']
                    }
                }
            }
        ]
    },

    // allows us to see how the transpiled js relates to the untranspiled js
    devtool: 'source-map',

    devServer: {
        contentBase: path.join(__dirname, 'src/main/resources/static/js'),
        port: 3143,
        compress: true,
        watchContentBase: true,
        // send requests that start with "/api" to our api server
        proxy: {
            '/api': {
                target: 'http://localhost:9090',
                pathRewrite: {'^/api': ''}
            }
        }
    }
};

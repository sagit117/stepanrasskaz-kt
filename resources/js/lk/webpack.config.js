const path = require('path')
const { VueLoaderPlugin } = require("vue-loader")

module.exports = {
    entry: {
        account: './src/index.js',
    },
    output: {
        filename: '[name].js',
        path: path.resolve(__dirname, './dist'),
        clean: true
    },
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.s[ac]ss$/i,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    {
                        loader: 'sass-loader',
                        options: {
                            // indentedSyntax: true,
                            // sass-loader >= 8
                            sassOptions: {
                                // indentedSyntax: true
                            },
                            implementation: require("sass")
                        }
                    },
                ]
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.tsx?$/,
                loader: 'ts-loader',
                options: {
                    appendTsSuffixTo: [/\.vue$/],
                },
                exclude: /node_modules/,
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {
                        scss: 'vue-style-loader!css-loader!sass-loader', // <style lang="scss">
                        sass: 'vue-style-loader!css-loader!sass-loader?indentedSyntax' // <style lang="sass">
                    }
                }
            }
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js', '.css', '.vue', '.json'],
    },
    plugins: [
        new VueLoaderPlugin(),
    ],
}
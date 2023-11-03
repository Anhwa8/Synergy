const {configureWebpack} = require("vue-cli-plugin-vuetify/vue.config");
module.exports = {
	devServer: {
		port: 8080,
		proxy: "http://localhost:8080"
	},
	"pluginOptions": {
		"moment": {
			"locales": [
				"en",
				"ko",
			]
		},
	},
	"transpileDependencies": [
		"vuetify",
	],
}

import {defineConfig} from "cypress";

export default defineConfig({
    videosFolder: "cypress/videos",
    screenshotsFolder: "cypress/screenshots",
    fixturesFolder: "cypress/fixtures",
    video: false,
    viewportWidth: 1000,
    viewportHeight: 1500,

    e2e: {
        setupNodeEvents(on, config) {
            return require("./cypress/plugins/index.ts").default(on, config);
        },
        baseUrl: "http://localhost:4200",
    },
    component: {
        devServer: {
            framework: "angular",
            bundler: "webpack",
        },
        specPattern: "**/*.cy.ts",
    },
});

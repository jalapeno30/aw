// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function(config) {
  config.set({
    // base path, that will be used to resolve files and exclude
    basePath: '',

    // testing framework to use (jasmine/mocha/qunit/...)
    frameworks: ['jasmine'],
    preprocessors: {
      'app/views/**/*.html': ['ng-html2js'],
      'app/scripts/**/*.js': ['coverage']
    },

    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine',
      'karma-ng-html2js-preprocessor',
      'karma-coverage'
    ],

    // list of files / patterns to load in the browser
    files: [
      'app/bower_components/jquery/jquery.js',
      'app/bower_components/angular/angular.js',
      'app/bower_components/angular-mocks/angular-mocks.js',
      'app/bower_components/angular-resource/angular-resource.js',
      'app/bower_components/angular-cookies/angular-cookies.js',
      'app/bower_components/angular-sanitize/angular-sanitize.js',
      'app/bower_components/angular-route/angular-route.js',
      'app/bower_components/angular-bootstrap/ui-bootstrap.js',
      'app/bower_components/angular-local-storage/angular-local-storage.js',
      'app/bower_components/angular-translate/angular-translate.js',
      'app/bower_components/ng-table/ng-table.js',
      'app/bower_components/angular-block-ui/dist/angular-block-ui.js',
      'app/bower_components/bootstrap-daterangepicker/daterangepicker.js',
      'app/bower_components/ng-bs-daterangepicker/dist/ng-bs-daterangepicker.min.js',
      //'app/bower_components/microplugin/src/microplugin.js',
      //'app/bower_components/selectize/dist/js/selectize.js',
      'app/bower_components/angular-ui-select2/src/select2.js',
      'app/bower_components/angular-growl/build/angular-growl.js',
      'app/bower_components/angular-animate/angular-animate.js',
      //'app/scripts/*.js',
      'app/scripts/app.js',
      'app/scripts/config.js',
      'app/scripts/**/*.js',
      'app/**/*.html',
      'test/**/*.js',
    ],

    // list of files / patterns to exclude
    exclude: [],

    // web server port
    port: 9876,

    // level of logging
    // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: false,


    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: ['PhantomJS'],


    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: false,


    ngHtml2JsPreprocessor: {
      moduleName: 'templates'
    },

    reporters: ['progress', 'coverage'],

    // optionally, configure the reporter
    coverageReporter: {
      type : 'html',
      dir : 'coverage/'
    }
  });
};

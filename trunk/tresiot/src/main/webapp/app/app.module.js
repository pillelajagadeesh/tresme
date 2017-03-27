(function() {
    'use strict';

    angular
        .module('tresiotApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run)
		.run([
           '$log', '$rootScope', '$window', '$state', '$location',
           function($log, $rootScope, $window, $state, $location) {
               $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
            	   console.log(toState);
            	   if (toState.url == "/") {
            	        $('body').addClass('layout-top-nav').removeClass('sidebar-mini');
            	        $('.content-wrapper').addClass('home-container');
            	        $('.main-footer').addClass('home-footer');
            	    } 
            	  
               });
           }
        ])
    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();

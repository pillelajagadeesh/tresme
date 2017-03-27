(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('SensorgraphService', SensorgraphService);

    SensorgraphService.$inject = ['$http', 'DateUtils'];

    function SensorgraphService ($http, DateUtils) {
    	var service ={};
    	
    	service.sensorbargraph = function (data, callback){
    		$http.post('api/sensorbargraph',data).then(function(response){
        		callback(response.data);
        	});
    	};
    	
    	service.sensorlinegraph = function (data, callback){
    		$http.post('api/sensorlinegraph',data).then(function(response){
        		callback(response.data);
        	});
    	};
    	
    	service.getclienIdlist = function (callback){
    		$http.get('api/sensorclientidlist').then(function(response){
        		callback(response.data);
        	});
    	};
    	
    	service.getapplicationlist = function (callback){
    		$http.get('api/applicationlist').then(function(response){
    			
        		callback(response.data);
        	});
    	};
    	service.getsensorlist = function (callback){
    		$http.get('api/sensorlist').then(function(response){
    		
        		callback(response.data);
        	});
    	};
    	
    	
    	return service;
    }
})();
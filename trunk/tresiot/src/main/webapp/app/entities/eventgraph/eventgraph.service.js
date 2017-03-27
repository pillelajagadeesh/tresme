(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('EventgraphService', EventgraphService);

    EventgraphService.$inject = ['$http', 'DateUtils'];

    function EventgraphService ($http, DateUtils) {
    	var service ={};
    	
    	service.eventbargraph = function (data, callback){
    		$http.post('api/eventbargraph',data).then(function(response){
        		callback(response.data);
        	});
    	};
    	
    	service.getclienIdlist = function (callback){
    		$http.get('api/eventclientidlist').then(function(response){
        		callback(response.data);
        	});
    	};
    	service.getapplicationlist = function (callback){
    		$http.get('api/applicationlist').then(function(response){
    			
        		callback(response.data);
        	});
    	};
    	service.geteventlist = function (callback){
    		$http.get('api/eventlist').then(function(response){
    			
        		callback(response.data);
        	});
    	};
    	
    	return service;
    }
})();
(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('Applicationevent', Applicationevent);

    Applicationevent.$inject = ['$resource'];

    function Applicationevent ($resource) {
        var resourceUrl =  'api/applicationevents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('Sensordata', Sensordata);

    Sensordata.$inject = ['$resource'];

    function Sensordata ($resource) {
        var resourceUrl =  'api/sensordata/:id';

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

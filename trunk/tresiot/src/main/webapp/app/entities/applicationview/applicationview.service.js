(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('Applicationview', Applicationview);

    Applicationview.$inject = ['$resource'];

    function Applicationview ($resource) {
        var resourceUrl =  'api/applicationviews/:id';

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

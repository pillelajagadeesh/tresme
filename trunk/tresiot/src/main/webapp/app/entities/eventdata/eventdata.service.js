(function() {
    'use strict';
    angular
        .module('tresiotApp')
        .factory('Eventdata', Eventdata);

    Eventdata.$inject = ['$resource'];

    function Eventdata ($resource) {
        var resourceUrl =  'api/eventdata/:id';

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

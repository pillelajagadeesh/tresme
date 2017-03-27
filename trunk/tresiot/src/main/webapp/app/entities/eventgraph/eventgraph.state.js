(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eventgraph', {
            parent: 'entity',
            url: '/eventgraph',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Eventgraph'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eventgraph/eventgraph.html',
                    controller: 'EventGraphController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
       
       
    }

})();

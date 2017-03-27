(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sensordata', {
            parent: 'entity',
            url: '/sensordata?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Sensordata'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensordata/sensordata.html',
                    controller: 'SensordataController',
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
        .state('sensordata-detail', {
            parent: 'entity',
            url: '/sensordata/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Sensordata'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensordata/sensordata-detail.html',
                    controller: 'SensordataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sensordata', function($stateParams, Sensordata) {
                    return Sensordata.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sensordata',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sensordata-detail.edit', {
            parent: 'sensordata-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensordata/sensordata-dialog.html',
                    controller: 'SensordataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sensordata', function(Sensordata) {
                            return Sensordata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sensordata.new', {
            parent: 'sensordata',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensordata/sensordata-dialog.html',
                    controller: 'SensordataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                clientid: null,
                                value: null,
                                createdTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sensordata', null, { reload: 'sensordata' });
                }, function() {
                    $state.go('sensordata');
                });
            }]
        })
        .state('sensordata.edit', {
            parent: 'sensordata',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensordata/sensordata-dialog.html',
                    controller: 'SensordataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sensordata', function(Sensordata) {
                            return Sensordata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensordata', null, { reload: 'sensordata' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sensordata.delete', {
            parent: 'sensordata',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensordata/sensordata-delete-dialog.html',
                    controller: 'SensordataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sensordata', function(Sensordata) {
                            return Sensordata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensordata', null, { reload: 'sensordata' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

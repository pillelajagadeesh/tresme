(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eventdata', {
            parent: 'entity',
            url: '/eventdata?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Eventdata'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eventdata/eventdata.html',
                    controller: 'EventdataController',
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
        .state('eventdata-detail', {
            parent: 'entity',
            url: '/eventdata/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Eventdata'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eventdata/eventdata-detail.html',
                    controller: 'EventdataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Eventdata', function($stateParams, Eventdata) {
                    return Eventdata.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eventdata',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eventdata-detail.edit', {
            parent: 'eventdata-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eventdata/eventdata-dialog.html',
                    controller: 'EventdataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eventdata', function(Eventdata) {
                            return Eventdata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eventdata.new', {
            parent: 'eventdata',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eventdata/eventdata-dialog.html',
                    controller: 'EventdataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                clientid: null,
                                clientos: null,
                                devicemake: null,
                                appversion: null,
                                sessionduration: null,
                                location: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eventdata', null, { reload: 'eventdata' });
                }, function() {
                    $state.go('eventdata');
                });
            }]
        })
        .state('eventdata.edit', {
            parent: 'eventdata',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eventdata/eventdata-dialog.html',
                    controller: 'EventdataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eventdata', function(Eventdata) {
                            return Eventdata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eventdata', null, { reload: 'eventdata' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eventdata.delete', {
            parent: 'eventdata',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eventdata/eventdata-delete-dialog.html',
                    controller: 'EventdataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Eventdata', function(Eventdata) {
                            return Eventdata.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eventdata', null, { reload: 'eventdata' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('applicationevent', {
            parent: 'entity',
            url: '/applicationevent?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Applicationevents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/applicationevent/applicationevents.html',
                    controller: 'ApplicationeventController',
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
        .state('applicationevent-detail', {
            parent: 'entity',
            url: '/applicationevent/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Applicationevent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/applicationevent/applicationevent-detail.html',
                    controller: 'ApplicationeventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Applicationevent', function($stateParams, Applicationevent) {
                    return Applicationevent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'applicationevent',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('applicationevent-detail.edit', {
            parent: 'applicationevent-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationevent/applicationevent-dialog.html',
                    controller: 'ApplicationeventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Applicationevent', function(Applicationevent) {
                            return Applicationevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('applicationevent.new', {
            parent: 'applicationevent',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationevent/applicationevent-dialog.html',
                    controller: 'ApplicationeventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('applicationevent', null, { reload: 'applicationevent' });
                }, function() {
                    $state.go('applicationevent');
                });
            }]
        })
        .state('applicationevent.edit', {
            parent: 'applicationevent',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationevent/applicationevent-dialog.html',
                    controller: 'ApplicationeventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Applicationevent', function(Applicationevent) {
                            return Applicationevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('applicationevent', null, { reload: 'applicationevent' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('applicationevent.delete', {
            parent: 'applicationevent',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationevent/applicationevent-delete-dialog.html',
                    controller: 'ApplicationeventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Applicationevent', function(Applicationevent) {
                            return Applicationevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('applicationevent', null, { reload: 'applicationevent' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

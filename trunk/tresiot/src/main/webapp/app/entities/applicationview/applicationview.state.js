(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('applicationview', {
            parent: 'entity',
            url: '/applicationview?page&sort&search',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Applicationviews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/applicationview/applicationviews.html',
                    controller: 'ApplicationviewController',
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
        .state('applicationview-detail', {
            parent: 'entity',
            url: '/applicationview/{id}',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'Applicationview'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/applicationview/applicationview-detail.html',
                    controller: 'ApplicationviewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Applicationview', function($stateParams, Applicationview) {
                    return Applicationview.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'applicationview',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('applicationview-detail.edit', {
            parent: 'applicationview-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationview/applicationview-dialog.html',
                    controller: 'ApplicationviewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Applicationview', function(Applicationview) {
                            return Applicationview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('applicationview.new', {
            parent: 'applicationview',
            url: '/new',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationview/applicationview-dialog.html',
                    controller: 'ApplicationviewDialogController',
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
                    $state.go('applicationview', null, { reload: 'applicationview' });
                }, function() {
                    $state.go('applicationview');
                });
            }]
        })
        .state('applicationview.edit', {
            parent: 'applicationview',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationview/applicationview-dialog.html',
                    controller: 'ApplicationviewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Applicationview', function(Applicationview) {
                            return Applicationview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('applicationview', null, { reload: 'applicationview' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('applicationview.delete', {
            parent: 'applicationview',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/applicationview/applicationview-delete-dialog.html',
                    controller: 'ApplicationviewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Applicationview', function(Applicationview) {
                            return Applicationview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('applicationview', null, { reload: 'applicationview' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

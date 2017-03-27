(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationviewDetailController', ApplicationviewDetailController);

    ApplicationviewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Applicationview', 'Application'];

    function ApplicationviewDetailController($scope, $rootScope, $stateParams, previousState, entity, Applicationview, Application) {
        var vm = this;

        vm.applicationview = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:applicationviewUpdate', function(event, result) {
            vm.applicationview = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

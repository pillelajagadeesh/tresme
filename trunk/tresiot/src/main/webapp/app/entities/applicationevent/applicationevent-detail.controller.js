(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationeventDetailController', ApplicationeventDetailController);

    ApplicationeventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Applicationevent', 'Application'];

    function ApplicationeventDetailController($scope, $rootScope, $stateParams, previousState, entity, Applicationevent, Application) {
        var vm = this;

        vm.applicationevent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:applicationeventUpdate', function(event, result) {
            vm.applicationevent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

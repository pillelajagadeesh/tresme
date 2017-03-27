(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationDetailController', ApplicationDetailController);

    ApplicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Application'];

    function ApplicationDetailController($scope, $rootScope, $stateParams, previousState, entity, Application) {
        var vm = this;

        vm.application = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:applicationUpdate', function(event, result) {
            vm.application = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

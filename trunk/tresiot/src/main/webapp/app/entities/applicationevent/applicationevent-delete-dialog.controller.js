(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationeventDeleteController',ApplicationeventDeleteController);

    ApplicationeventDeleteController.$inject = ['$uibModalInstance', 'entity', 'Applicationevent'];

    function ApplicationeventDeleteController($uibModalInstance, entity, Applicationevent) {
        var vm = this;

        vm.applicationevent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Applicationevent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

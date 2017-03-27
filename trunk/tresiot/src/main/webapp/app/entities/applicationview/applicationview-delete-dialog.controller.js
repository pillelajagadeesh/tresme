(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationviewDeleteController',ApplicationviewDeleteController);

    ApplicationviewDeleteController.$inject = ['$uibModalInstance', 'entity', 'Applicationview'];

    function ApplicationviewDeleteController($uibModalInstance, entity, Applicationview) {
        var vm = this;

        vm.applicationview = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Applicationview.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

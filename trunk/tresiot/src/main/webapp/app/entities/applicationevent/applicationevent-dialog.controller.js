(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationeventDialogController', ApplicationeventDialogController);

    ApplicationeventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Applicationevent', 'Application'];

    function ApplicationeventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Applicationevent, Application) {
        var vm = this;

        vm.applicationevent = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applications = Application.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.applicationevent.id !== null) {
                Applicationevent.update(vm.applicationevent, onSaveSuccess, onSaveError);
            } else {
                Applicationevent.save(vm.applicationevent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tresiotApp:applicationeventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

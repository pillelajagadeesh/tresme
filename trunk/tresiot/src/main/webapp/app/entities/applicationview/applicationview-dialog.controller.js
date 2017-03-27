(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('ApplicationviewDialogController', ApplicationviewDialogController);

    ApplicationviewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Applicationview', 'Application'];

    function ApplicationviewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Applicationview, Application) {
        var vm = this;

        vm.applicationview = entity;
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
            if (vm.applicationview.id !== null) {
                Applicationview.update(vm.applicationview, onSaveSuccess, onSaveError);
            } else {
                Applicationview.save(vm.applicationview, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tresiotApp:applicationviewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

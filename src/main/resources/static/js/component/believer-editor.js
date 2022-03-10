(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, data) {},
    }

    function BelieverEditor(element, options)
    {
        this.believerId     = 0;
        this.element        = element;
        this.options        = $.extend( true, {}, defaults, options );

        this.load();
    }

    BelieverEditor.prototype = {
        load: function() {
            let instance = this;

            this.CompositionLayout(instance);

            $(instance.element).on('click', '.btn-save', function() {
                if(instance.believerId !== 0) {
                    ajaxPutRequest('/believer/' + instance.believerId, JSON.stringify({
                        believerId: instance.believerId,
                        believerName: $('#believerEditor_name').val(),
                        birthOfYear: $('#believerEditor_birth').val(),
                        lunarSolarType: $('#believerEditor_lunar_solar').val(),
                        address: $('#believerEditor_address').val(),
                    }), function () {
                        //OPTION CALLBACK
                        if (typeof instance.options.onSaveSuccess == 'function') {
                            instance.options.onSaveSuccess(instance.element, this);
                        }
                    }, null);
                }
            });

            $(instance.element).on('click', '.btn-delete', function () {
                if(instance.believerId !== 0) {
                    ajaxDeleteRequest('/believer/' + instance.believerId, null,
                        function () {
                            //OPTION CALLBACK
                            if (typeof instance.options.onDeleteSuccess == 'function') {
                                instance.options.onDeleteSuccess(instance.element, this);
                            }
                        }, null);
                }
            });
        },

        //Editor Layout Composition
        CompositionLayout: function(instance){
            let buttonGroup = $('<div/>', {
                class: 'btn-group mb-3 d-flex'
            });

            let deleteButton = $('<button/>', {
                class: 'btn btn-default btn-delete',
                text: '삭제 '
            });

            let deleteIcon = $('<i/>', {
                class: 'ti-trash'
            });

            deleteButton.append(deleteIcon);
            buttonGroup.append(deleteButton);

            let saveButton = $('<button/>', {
                class: 'btn btn-primary btn-save',
                text: '수정 '
            });

            let saveIcon = $('<i/>', {
                class: 'ti-save'
            });

            saveButton.append(saveIcon);
            buttonGroup.append(saveButton);

            $(instance.element).append(buttonGroup);

            let container = $('<div/>', {
                id: 'believer_editor_container',
                class: 'scrollable-auto-y',
                style: 'max-height: ' + instance.options.maxHeight + 'px'
            });

            let nameFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let nameLabel = $('<label/>', {
                text: '이름'
            });

            let nameInput = $('<input/>', {
                id: 'believerEditor_name',
                type: 'text',
                class: 'input-default form-control',
                maxlength: 10
            });

            nameFormGroup.append(nameLabel);
            nameFormGroup.append(nameInput);

            container.append(nameFormGroup);

            let birthFormGroup = $('<div/>', {
                class: 'form-group d-flex'
            });

            let birthGroup = $('<div/>', {
                class: 'd-flex flex-column m-r-10'
            })

            let birthLabel = $('<label/>', {
                text: '생년월일'
            });

            let birthInput = $('<input/>', {
                id: 'believerEditor_birth',
                type: 'text',
                class: 'input-default form-control',
                maxlength: 6
            });

            birthGroup.append(birthLabel);
            birthGroup.append(birthInput);

            birthFormGroup.append(birthGroup);

            let lunarSolarGroup = $('<div/>', {
                class: 'd-flex flex-column flex-1'
            })

            let lunarSolarLabel = $('<label/>', {
                text: '음력/양력'
            });

            let lunarSolarSelect = $('<select/>', {
                id: 'believerEditor_lunar_solar',
                class: 'input-default form-control',
                maxlength: 30
            });

            lunarSolarSelect.append(new Option('음력', 'LUNAR', true, true));
            lunarSolarSelect.append(new Option('양력', 'SOLAR', false, false));

            lunarSolarGroup.append(lunarSolarLabel);
            lunarSolarGroup.append(lunarSolarSelect);

            birthFormGroup.append(lunarSolarGroup);

            container.append(birthFormGroup);

            let addressFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let addressLabel = $('<label/>', {
                text: '주소'
            });

            let addressInput = $('<input/>', {
                id: 'believerEditor_address',
                type: 'text',
                class: 'input-default form-control'
            });

            addressFormGroup.append(addressLabel);
            addressFormGroup.append(addressInput);

            container.append(addressFormGroup);

            $(instance.element).append(container);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.believerId = rowData.believerId;

            $('#believerEditor_name').val(rowData.believerName);
            $('#believerEditor_birth').val(rowData.birthOfYear);
            $('#believerEditor_lunar_solar').val(rowData.lunarSolarType);
            $('#believerEditor_address').val(rowData.address);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.believerId = 0;

            $('#believer_editor_container input.form-control').each(function(index, el){
                $(el).val('');
            });

            $('#believer_editor_container select').each(function(index, el){
                $(el).val('');
            });
        }
    };

    $.fn.believerEditor = function( options ){
        let ret;

        if( !this.length ) {
            return;
        }

        ret = $.data(this, 'believer_editor', new BelieverEditor( this, options ) );

        return ret;
    };
}(jQuery));
(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, event) {},
        onAppendSuccess: function(element, event) {},
    }

    function FamilyEditor(element, options)
    {
        this.believerId             = 0;
        this.familyId               = 0;

        this.familyTypeElId         = 'familyEditor_familyType';
        this.familyTypeETCElId      = 'familyEditor_familyTypeETC';
        this.familyNameElId         = 'familyEditor_familyName';
        this.familyBirthElId        = 'familyEditor_birth';
        this.familyLunarSolarElId   = 'familyEditor_lunar_solar';

        this.element                = element;
        this.options                = $.extend( true, {}, defaults, options );

        this.load();
    }

    FamilyEditor.prototype = {
        load: function() {
            let instance = this;

            this.CompositionLayout(instance);

            $(instance.element).on('change', '#' + instance.familyTypeElId, function() {
                let selectValue = $(this).val();

                if(selectValue === 'ETC')
                    $('#' + instance.familyTypeElId).parent().next().removeClass('hidden');
                else{
                    $('#' + instance.familyTypeETCElId).val('');
                    $('#' + instance.familyTypeElId).parent().next().addClass('hidden');
                }
            });

            $(instance.element).on('click', '.btn-save', function() {
                if(instance.familyId !== 0) {
                    ajaxPutRequest('/family/' + instance.familyId, JSON.stringify({
                        familyType: $('#' + instance.familyTypeElId).val(),
                        etcValue: $('#' + instance.familyTypeETCElId).val(),
                        familyName: $('#' + instance.familyNameElId).val(),
                        birthOfYear: $('#' + instance.familyBirthElId).val(),
                        lunarSolarType: $('#' + instance.familyLunarSolarElId).val(),
                    }), function () {
                        //OPTION CALLBACK
                        if (typeof instance.options.onSaveSuccess == 'function') {
                            instance.options.onSaveSuccess(instance.element, this);
                        }
                    }, null);
                }
            });

            $(instance.element).on('click', '.btn-delete', function () {
                if(instance.familyId !== 0) {
                    ajaxDeleteRequest('/family/' + instance.familyId, null,
                        function () {
                            //OPTION CALLBACK
                            if (typeof instance.options.onDeleteSuccess == 'function') {
                                instance.options.onDeleteSuccess(instance.element, this);
                            }
                        }, null);
                }
            });

            $(instance.element).on('click', '.btn-reset', function () {
                instance.setEmpty();
            });

            $(instance.element).on('click', '.btn-append', function () {
                ajaxPostRequest('/family', JSON.stringify({
                    believerId: instance.believerId,
                    familyType: $('#' + instance.familyTypeElId).val(),
                    etcValue: $('#' + instance.familyTypeETCElId).val(),
                    familyName: $('#' + instance.familyNameElId).val(),
                    birthOfYear: $('#' + instance.familyBirthElId).val(),
                    lunarSolarType: $('#' + instance.familyLunarSolarElId).val(),
                }), function (){
                    //OPTION CALLBACK
                    if (typeof instance.options.onAppendSuccess == 'function') {
                        instance.setEmpty();
                        instance.options.onAppendSuccess(instance.element, this);
                    }
                }, null);
            });
        },

        //Editor Layout Composition
        CompositionLayout: function(instance){
            let buttonGroup = $('<div/>', {
                class: 'btn-group mb-3 d-flex group-update'
            });

            let deleteButton = $('<button/>', {
                class: 'btn btn-default btn-delete',
                text: '?????? '
            });

            let deleteIcon = $('<i/>', {
                class: 'ti-trash'
            });

            deleteButton.append(deleteIcon);
            buttonGroup.append(deleteButton);

            let saveButton = $('<button/>', {
                class: 'btn btn-primary btn-save',
                text: '?????? '
            });

            let saveIcon = $('<i/>', {
                class: 'ti-save'
            });

            saveButton.append(saveIcon);
            buttonGroup.append(saveButton);

            $(instance.element).append(buttonGroup);

            let appendButtonGroup = $('<div/>', {
                class: 'btn-group mb-3 d-flex group-append hidden'
            });

            let resetButton = $('<button/>', {
                class: 'btn btn-default btn-reset',
                text: '????????? '
            });

            let resetIcon = $('<i/>', {
                class: 'ti-reload'
            });

            resetButton.append(resetIcon);
            appendButtonGroup.append(resetButton);

            let appendButton = $('<button/>', {
                class: 'btn btn-primary btn-append',
                text: '?????? '
            });

            let appendIcon = $('<i/>', {
                class: 'ti-save'
            });

            appendButton.append(appendIcon);
            appendButtonGroup.append(appendButton);

            $(instance.element).append(appendButtonGroup);

            let container = $('<div/>', {
                id: 'family_editor_container',
                class: 'scrollable-auto-y',
                style: 'max-height: ' + instance.options.maxHeight + 'px'
            });

            let familyTypeFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let familyTypeLabel = $('<label/>', {
                text: '????????????'
            });

            let familyTypeSelect = $('<select/>', {
                id: this.familyTypeElId,
                class: 'input-default form-control'
            });

            familyTypeSelect.append(new Option('???', 'FATHER', true, true));
            familyTypeSelect.append(new Option('???', 'MOTHER', false, false));
            familyTypeSelect.append(new Option('???', 'SON', false, false));
            familyTypeSelect.append(new Option('???', 'DAUGHTER', false, false));
            familyTypeSelect.append(new Option('?????????', 'SPOUSE', false, false));
            familyTypeSelect.append(new Option('??????', 'BROTHER', false, false));
            familyTypeSelect.append(new Option('??????', 'FATHER_IN_LAW', false, false));
            familyTypeSelect.append(new Option('??????', 'MOTHER_IN_LAW', false, false));
            familyTypeSelect.append(new Option('??????', 'GRAND_SON', false, false));
            familyTypeSelect.append(new Option('??????', 'GRAND_DAUGHTER', false, false));
            familyTypeSelect.append(new Option('??????', 'ETC', false, false));

            familyTypeFormGroup.append(familyTypeLabel);
            familyTypeFormGroup.append(familyTypeSelect);

            container.append(familyTypeFormGroup);

            let familyTypeETCFormGroup = $('<div/>', {
                class: 'form-group hidden'
            });

            let familyTypeETCLabel = $('<label/>', {
                text: '?????? ????????????'
            });

            let familyTypeETCInput = $('<input/>', {
                id: this.familyTypeETCElId,
                class: 'input-default form-control'
            });

            familyTypeETCFormGroup.append(familyTypeETCLabel);
            familyTypeETCFormGroup.append(familyTypeETCInput);

            container.append(familyTypeETCFormGroup);

            let familyNameFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let familyNameLabel = $('<label/>', {
                text: '??????'
            });

            let familyNameInput = $('<input/>', {
                id: this.familyNameElId,
                class: 'input-default form-control',
                maxlength: 10
            });

            familyNameFormGroup.append(familyNameLabel);
            familyNameFormGroup.append(familyNameInput);

            container.append(familyNameFormGroup);

            let birthFormGroup = $('<div/>', {
                class: 'form-group d-flex'
            });

            let birthGroup = $('<div/>', {
                class: 'd-flex flex-column m-r-10'
            })

            let birthLabel = $('<label/>', {
                text: '????????????'
            });

            let birthInput = $('<input/>', {
                id: this.familyBirthElId,
                type: 'text',
                oninput: 'this.value = this.value.replace(/[^0-9.]/g, \'\').replace(/(\\..*)\\./g, \'$1\');',
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
                text: '??????/??????'
            });

            let lunarSolarSelect = $('<select/>', {
                id: this.familyLunarSolarElId,
                class: 'input-default form-control'
            });

            lunarSolarSelect.append(new Option('??????', 'LUNAR', true, true));
            lunarSolarSelect.append(new Option('??????', 'SOLAR', false, false));

            lunarSolarGroup.append(lunarSolarLabel);
            lunarSolarGroup.append(lunarSolarSelect);

            birthFormGroup.append(lunarSolarGroup);

            container.append(birthFormGroup);

            $(instance.element).append(container);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.familyId = rowData.familyId;

            $('#' + this.familyTypeElId).val(rowData.familyType).trigger('change');
            $('#' + this.familyTypeETCElId).val(rowData.etcValue);
            $('#' + this.familyNameElId).val(rowData.familyName);
            $('#' + this.familyBirthElId).val(rowData.birthOfYear);
            $('#' + this.familyLunarSolarElId).val(rowData.lunarSolarType);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.familyId = 0;

            $('#family_editor_container input.form-control').each(function(index, el){
                $(el).val('');
            });

            $('#family_editor_container select').each(function(index, el){
                $(el).val($(this).find('option').eq(0).val());
            });
        },

        showAppend: function(believerId){
            this.setEmpty();

            this.believerId = believerId;

            $('.group-append').removeClass('hidden');
            $('.group-update').addClass('hidden');
        },

        showUpdate: function(){
            this.setEmpty();

            $('.group-append').addClass('hidden');
            $('.group-update').removeClass('hidden');
        },
    };

    $.fn.familyEditor = function( options ){
        let ret;

        if( !this.length ) {
            return;
        }

        ret = $.data(this, 'family_editor', new FamilyEditor( this, options ) );

        return ret;
    };
}(jQuery));
(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, event) {},
        onAppendSuccess: function(element, event) {},
    }

    function PrayerEditor(element, options)
    {
        this.prayerTypeElId             = 'prayerEditor_prayerType';
        this.prayerBelieverNameElId     = 'prayerEditor_name';
        this.prayerStartDateElId        = 'prayerEditor_prayerStartDate';

        this.believerId                 = 0
        this.prayerId                   = 0;
        this.select2                    = null;
        this.datepicker                 = null;

        this.element        = element;
        this.options        = $.extend( true, {}, defaults, options );

        this.load();
    }

    PrayerEditor.prototype = {
        load: function() {
            let instance = this;

            this.CompositionLayout(instance);

            this.loadPrayerType();

            this.select2 = $('#' + instance.prayerBelieverNameElId).select2({
                width: '100%',
                theme: 'bootstrap4',
                language: {
                    noResults: function(){
                        return '등록된 신도가 없습니다.';
                    },
                    searching: function(){
                        return '조회 중....'
                    }
                },
                ajax:{
                    delay: 500,
                    type: 'GET',
                    url: function (params) {
                        return '/believers/' + params.term;
                    },
                    transport: function (params, success, failure) {
                        if(params.data.term !== undefined && params.data.term !== ''){
                            let request = $.ajax(params.url);

                            request.then(success);
                            request.fail(failure);

                            return request;
                        }
                    },
                    processResults: function (response) {
                        data = [];

                        $.each(response, function(index, el){
                            data.push({
                                id: el.believerId,
                                text: el.believerName + ' (생년월일: ' + el.birthOfYear + ')'
                            });
                        });

                        return {
                            results: data
                        };
                    }
                }
            });

            this.datepicker = $('#' + instance.prayerStartDateElId).datepicker({
                format: 'yyyy-mm-dd',
                startDate: '1900-01-01',
                endDate: '3000-12-31',
                todayHighlight: true,
                autoclose: true,
                language: 'ko'
            });

            $(instance.element).on('click', '.btn-save', function() {
                if(instance.prayerId !== 0) {
                    ajaxPutRequest('/prayer/' + instance.prayerId, JSON.stringify({
                        prayerStartDate: $('#' + instance.prayerStartDateElId).val(),
                        prayerTypeCodeId: $('#' + instance.prayerTypeElId).val(),
                        believerId: $('#' + instance.prayerBelieverNameElId).select2('data')[0].id
                    }), function () {
                        //OPTION CALLBACK
                        if (typeof instance.options.onSaveSuccess == 'function') {
                            instance.options.onSaveSuccess(instance.element, this);
                        }
                    }, null);
                }
            });

            $(instance.element).on('click', '.btn-delete', function () {
                if(instance.prayerId !== 0) {
                    ajaxDeleteRequest('/prayer/' + instance.prayerId, null,
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
                ajaxPostRequest('/prayer', JSON.stringify({
                    believerId: instance.believerId,
                    prayerTypeCodeId: $('#' + instance.prayerTypeElId).val(),
                    prayerStartDate: $('#' + instance.prayerStartDateElId).val()
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
            let updateButtonGroup = $('<div/>', {
                class: 'btn-group mb-3 d-flex group-update'
            });

            let deleteButton = $('<button/>', {
                class: 'btn btn-default btn-delete',
                text: '삭제 '
            });

            let deleteIcon = $('<i/>', {
                class: 'ti-trash'
            });

            deleteButton.append(deleteIcon);
            updateButtonGroup.append(deleteButton);

            let saveButton = $('<button/>', {
                class: 'btn btn-primary btn-save',
                text: '수정 '
            });

            let saveIcon = $('<i/>', {
                class: 'ti-save'
            });

            saveButton.append(saveIcon);
            updateButtonGroup.append(saveButton);

            $(instance.element).append(updateButtonGroup);

            let appendButtonGroup = $('<div/>', {
                class: 'btn-group mb-3 d-flex group-append'
            });

            let resetButton = $('<button/>', {
                class: 'btn btn-default btn-reset',
                text: '초기화 '
            });

            let resetIcon = $('<i/>', {
                class: 'ti-reload'
            });

            resetButton.append(resetIcon);
            appendButtonGroup.append(resetButton);

            let appendButton = $('<button/>', {
                class: 'btn btn-primary btn-append',
                text: '저장 '
            });

            let appendIcon = $('<i/>', {
                class: 'ti-save'
            });

            appendButton.append(appendIcon);
            appendButtonGroup.append(appendButton);

            $(instance.element).append(appendButtonGroup);

            let container = $('<div/>', {
                id: 'prayer_editor_container',
                class: 'scrollable-auto-y',
                style: 'max-height: ' + instance.options.maxHeight + 'px'
            });

            let nameFormGroup = $('<div/>', {
                class: 'form-group d-flex flex-column'
            });

            let nameLabel = $('<label/>', {
                text: '신도명'
            });

            let nameSelect = $('<select/>', {
                id: this.prayerBelieverNameElId,
                class: 'input-default form-control'
            });

            nameFormGroup.append(nameLabel);
            nameFormGroup.append(nameSelect);

            container.append(nameFormGroup);

            let prayerFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let prayerLabel = $('<label/>', {
                text: '기도종류'
            });

            let prayerSelect = $('<select/>', {
                id: this.prayerTypeElId,
                class: 'input-default form-control'
            });

            prayerFormGroup.append(prayerLabel);
            prayerFormGroup.append(prayerSelect);

            container.append(prayerFormGroup);

            let prayerStartDateFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let prayerStartDateLabel = $('<label/>', {
                text: '기도시작일'
            });

            let prayerStartDateInput = $('<input/>', {
                id: this.prayerStartDateElId,
                type: 'text',
                class: 'input-default form-control',
            });

            prayerStartDateInput.attr('autocomplete', 'off');

            prayerStartDateFormGroup.append(prayerStartDateLabel);
            prayerStartDateFormGroup.append(prayerStartDateInput);

            container.append(prayerStartDateFormGroup);

            $(instance.element).append(container);
        },

        //PrayerType Select Option Load
        loadPrayerType: function(){
            const instance = this;

            ajaxGetRequest('/code/P_PRAYER_TYPE', {}, function(data){
                $.each(data, function(index, el){
                    $('#' + instance.prayerTypeElId).append(new Option(el.codeName, el.codeId, false, false));
                });
            }, null);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.prayerId = rowData.prayerId;

            let select2Text = rowData.believerName + ' (생년월일: ' + rowData.birthOfYear + ')';
            this.select2.append(new Option(select2Text, rowData.believerId, false, true));

            $('#' + this.prayerTypeElId).val(rowData.codeId);

            this.datepicker.datepicker('update', rowData.prayerStartDate);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.prayerId = 0;

            $('#' + this.prayerTypeElId).each(function(index, el){
                $(el).val($(this).find('option').eq(0).val());
            });

            this.datepicker.datepicker('update', new Date());
        },

        showAppend: function(believerId, name, birthOfYear){
            this.setEmpty();

            this.believerId = believerId;

            let select2Text = name + ' (생년월일: ' + birthOfYear + ')';
            this.select2.append(new Option(select2Text, believerId, false, true));

            this.select2.prop('disabled', true);

            $('.group-append').removeClass('hidden');
            $('.group-update').addClass('hidden');
        },

        showUpdate: function(){
            this.setEmpty();

            this.select2.prop('disabled', false);

            $('.group-append').addClass('hidden');
            $('.group-update').removeClass('hidden');
        },
    };

    $.fn.prayerEditor = function( options ){
        let ret;

        if( !this.length ) {
            return;
        }

        ret = $.data(this, 'prayer_editor', new PrayerEditor( this, options ) );

        return ret;
    };
}(jQuery));
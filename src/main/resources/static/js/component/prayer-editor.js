(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, data) {},
    }

    function PrayerEditor(element, options)
    {
        this.prayerTypeElId         = 'prayerEditor_prayerType';
        this.prayerBelieverNameElId = 'prayerEditor_name';
        this.prayerStartDateElId         = 'prayerEditor_prayerStartDate';

        this.prayerId       = 0;
        this.select2        = null;
        this.datepicker     = null;

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
                        code: {codeId: $('#' + instance.prayerTypeElId).val() },
                        believer: {believerId: $('#' + instance.prayerBelieverNameElId).select2('data')[0].id }
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

            ajaxGetRequest('/code/C-1', {}, function(data){
                $.each(data, function(index, el){
                    $('#' + instance.prayerTypeElId).append(new Option(el.codeName, el.codeId, false, false));
                });
            }, null);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.prayerId = rowData.prayerId;

            let select2Text = rowData.believer.believerName + ' (생년월일: ' + rowData.believer.birthOfYear + ')';
            this.select2.append(new Option(select2Text, rowData.believer.believerId, false, true));

            $('#' + this.prayerTypeElId).val(rowData.code.codeId);

            this.datepicker.datepicker('update', rowData.prayerStartDate);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.prayerId = 0;

            $('#' + this.prayerTypeElId).val('');

            this.datepicker.datepicker('update', '');
        }
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
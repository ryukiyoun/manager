(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, data) {},
    }

    function ExpenditureEditor(element, options)
    {
        this.expenditureDateElId = 'expenditureEditor_expenditureDate'
        this.expenditureTypeElId = 'expenditureEditor_expenditureType'
        this.expenditureCashAmountElId = 'expenditureEditor_CashAmount'
        this.expenditureCardAmountElId = 'expenditureEditor_CardAmount'
        this.expenditureBankBookAmountElId = 'expenditureEditor_BankBookAmount'
        this.expenditureInstallmentElId = 'expenditureEditor_Installment'

        this.expenditureId       = 0;
        this.datepicker     = null;

        this.element        = element;
        this.options        = $.extend( true, {}, defaults, options );

        this.load();
    }

    ExpenditureEditor.prototype = {
        load: function() {
            let instance = this;

            this.CompositionLayout(instance);

            this.loadExpenditureType();

            this.datepicker = $('#' + instance.expenditureDateElId).datepicker({
                format: 'yyyy-mm-dd',
                startDate: '1900-01-01',
                endDate: '3000-12-31',
                todayHighlight: true,
                autoclose: true,
                language: 'ko'
            });

            $(instance.element).on('click', '.btn-save', function() {
                if(instance.expenditureId !== 0) {
                    ajaxPutRequest('/expenditure/' + instance.expenditureId, JSON.stringify({
                        code: {codeId: $('#' + instance.expenditureTypeElId).val() },
                        cashAmount: $('#' + instance.expenditureCashAmountElId).val(),
                        cardAmount: $('#' + instance.expenditureCardAmountElId).val(),
                        bankBookAmount: $('#' + instance.expenditureBankBookAmountElId).val(),
                        installment: $('#' + instance.expenditureInstallmentElId).val(),
                        expenditureDate: $('#' + instance.expenditureDateElId).val()
                    }), function () {
                        //OPTION CALLBACK
                        if (typeof instance.options.onSaveSuccess == 'function') {
                            instance.options.onSaveSuccess(instance.element, this);
                        }
                    }, null);
                }
            });

            $(instance.element).on('click', '.btn-delete', function () {
                if(instance.expenditureId !== 0) {
                    ajaxDeleteRequest('/expenditure/' + instance.expenditureId, null,
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
                id: 'expenditure_editor_container',
                class: 'scrollable-auto-y',
                style: 'max-height: ' + instance.options.maxHeight + 'px'
            });

            let expenditureTypeFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureTypeLabel = $('<label/>', {
                text: '수입종류'
            });

            let expenditureTypeSelect = $('<select/>', {
                id: this.expenditureTypeElId,
                class: 'input-default form-control'
            });

            expenditureTypeFormGroup.append(expenditureTypeLabel);
            expenditureTypeFormGroup.append(expenditureTypeSelect);

            container.append(expenditureTypeFormGroup);

            let expenditureCashAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureCashAmountLabel = $('<label/>', {
                text: '현금 결제액'
            });

            let expenditureCashAmountInput = $('<input/>', {
                id: this.expenditureCashAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            expenditureCashAmountFormGroup.append(expenditureCashAmountLabel);
            expenditureCashAmountFormGroup.append(expenditureCashAmountInput);

            container.append(expenditureCashAmountFormGroup);

            let expenditureCardAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureCardAmountLabel = $('<label/>', {
                text: '카드 결제액'
            });

            let expenditureCardAmountInput = $('<input/>', {
                id: this.expenditureCardAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            expenditureCardAmountFormGroup.append(expenditureCardAmountLabel);
            expenditureCardAmountFormGroup.append(expenditureCardAmountInput);

            container.append(expenditureCardAmountFormGroup);

            let expenditureBankBookAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureBankBookAmountLabel = $('<label/>', {
                text: '카드 결제액'
            });

            let expenditureBankBookAmountInput = $('<input/>', {
                id: this.expenditureBankBookAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            expenditureBankBookAmountFormGroup.append(expenditureBankBookAmountLabel);
            expenditureBankBookAmountFormGroup.append(expenditureBankBookAmountInput);

            container.append(expenditureBankBookAmountFormGroup);

            let expenditureInstallmentFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureInstallmentLabel = $('<label/>', {
                text: '할부개월'
            });

            let expenditureInstallmentInput = $('<input/>', {
                id: this.expenditureInstallmentElId,
                type: 'text',
                class: 'input-default form-control',
            });

            expenditureInstallmentFormGroup.append(expenditureInstallmentLabel);
            expenditureInstallmentFormGroup.append(expenditureInstallmentInput);

            container.append(expenditureInstallmentFormGroup);

            let expenditureDateFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let expenditureDateLabel = $('<label/>', {
                text: '결제일'
            });

            let expenditureDateInput = $('<input/>', {
                id: this.expenditureDateElId,
                type: 'text',
                class: 'input-default form-control',
            });

            expenditureDateInput.attr('autocomplete', 'off');

            expenditureDateFormGroup.append(expenditureDateLabel);
            expenditureDateFormGroup.append(expenditureDateInput);

            container.append(expenditureDateFormGroup);

            $(instance.element).append(container);
        },

        //IncomeType Select Option Load
        loadExpenditureType: function(){
            const instance = this;

            ajaxGetRequest('/code/C-1', {}, function(data){
                $.each(data, function(index, el){
                    $('#' + instance.expenditureTypeElId).append(new Option(el.codeName, el.codeId, false, false));
                });
            }, null);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.expenditureId = rowData.expenditureId;

            $('#' + this.expenditureTypeElId).val(rowData.code.codeId);

            $('#' + this.expenditureCashAmountElId).val(rowData.cashAmount);
            $('#' + this.expenditureCardAmountElId).val(rowData.cardAmount);
            $('#' + this.expenditureBankBookAmountElId).val(rowData.bankBookAmount);

            $('#' + this.expenditureInstallmentElId).val(rowData.installment);

            this.datepicker.datepicker('update', rowData.expenditureDate);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.expenditureId = 0;

            $('#expenditure_editor_container select').each(function(index, el){
                $(el).val('');
            });

            $('#expenditure_editor_container input.form-control').each(function(index, el){
                $(el).val('');
            });

            this.datepicker.datepicker('update', '');
        }
    };

    $.fn.expenditureEditor = function( options ){
        let ret;

        if( !this.length ) {
            return;
        }

        ret = $.data(this, 'expenditure_editor', new ExpenditureEditor( this, options ) );

        return ret;
    };
}(jQuery));
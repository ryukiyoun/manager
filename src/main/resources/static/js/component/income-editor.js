(function ($){
    let defaults = {
        maxHeight: 680,
        onSaveSuccess: function (element, event) {},
        onDeleteSuccess: function(element, data) {},
    }

    function IncomeEditor(element, options)
    {
        this.incomeDateElId = 'incomeEditor_incomeDate'
        this.incomeTypeElId = 'incomeEditor_incomeType'
        this.incomeCashAmountElId = 'incomeEditor_CashAmount'
        this.incomeCardAmountElId = 'incomeEditor_CardAmount'
        this.incomeBankBookAmountElId = 'incomeEditor_BankBookAmount'
        this.incomeInstallmentElId = 'incomeEditor_Installment'

        this.incomeId       = 0;
        this.datepicker     = null;

        this.element        = element;
        this.options        = $.extend( true, {}, defaults, options );

        this.load();
    }

    IncomeEditor.prototype = {
        load: function() {
            let instance = this;

            this.CompositionLayout(instance);

            this.loadIncomeType();

            this.datepicker = $('#' + instance.incomeDateElId).datepicker({
                format: 'yyyy-mm-dd',
                startDate: '1900-01-01',
                endDate: '3000-12-31',
                todayHighlight: true,
                autoclose: true,
                language: 'ko'
            });

            $(instance.element).on('click', '.btn-save', function() {
                if(instance.incomeId !== 0) {
                    ajaxPutRequest('/income/' + instance.incomeId, JSON.stringify({
                        code: {codeId: $('#' + instance.incomeTypeElId).val() },
                        cashAmount: $('#' + instance.incomeCashAmountElId).val(),
                        cardAmount: $('#' + instance.incomeCardAmountElId).val(),
                        bankBookAmount: $('#' + instance.incomeBankBookAmountElId).val(),
                        installment: $('#' + instance.incomeInstallmentElId).val(),
                        incomeDate: $('#' + instance.incomeDateElId).val()
                    }), function () {
                        //OPTION CALLBACK
                        if (typeof instance.options.onSaveSuccess == 'function') {
                            instance.options.onSaveSuccess(instance.element, this);
                        }
                    }, null);
                }
            });

            $(instance.element).on('click', '.btn-delete', function () {
                if(instance.incomeId !== 0) {
                    ajaxDeleteRequest('/income/' + instance.incomeId, null,
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
                id: 'income_editor_container',
                class: 'scrollable-auto-y',
                style: 'max-height: ' + instance.options.maxHeight + 'px'
            });

            let incomeTypeFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeTypeLabel = $('<label/>', {
                text: '수입종류'
            });

            let incomeTypeSelect = $('<select/>', {
                id: this.incomeTypeElId,
                class: 'input-default form-control'
            });

            incomeTypeFormGroup.append(incomeTypeLabel);
            incomeTypeFormGroup.append(incomeTypeSelect);

            container.append(incomeTypeFormGroup);

            let incomeCashAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeCashAmountLabel = $('<label/>', {
                text: '현금 결제액'
            });

            let incomeCashAmountInput = $('<input/>', {
                id: this.incomeCashAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            incomeCashAmountFormGroup.append(incomeCashAmountLabel);
            incomeCashAmountFormGroup.append(incomeCashAmountInput);

            container.append(incomeCashAmountFormGroup);

            let incomeCardAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeCardAmountLabel = $('<label/>', {
                text: '카드 결제액'
            });

            let incomeCardAmountInput = $('<input/>', {
                id: this.incomeCardAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            incomeCardAmountFormGroup.append(incomeCardAmountLabel);
            incomeCardAmountFormGroup.append(incomeCardAmountInput);

            container.append(incomeCardAmountFormGroup);

            let incomeBankBookAmountFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeBankBookAmountLabel = $('<label/>', {
                text: '카드 결제액'
            });

            let incomeBankBookAmountInput = $('<input/>', {
                id: this.incomeBankBookAmountElId,
                type: 'text',
                class: 'input-default form-control',
            });

            incomeBankBookAmountFormGroup.append(incomeBankBookAmountLabel);
            incomeBankBookAmountFormGroup.append(incomeBankBookAmountInput);

            container.append(incomeBankBookAmountFormGroup);

            let incomeInstallmentFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeInstallmentLabel = $('<label/>', {
                text: '할부개월'
            });

            let incomeInstallmentInput = $('<input/>', {
                id: this.incomeInstallmentElId,
                type: 'text',
                class: 'input-default form-control',
            });

            incomeInstallmentFormGroup.append(incomeInstallmentLabel);
            incomeInstallmentFormGroup.append(incomeInstallmentInput);

            container.append(incomeInstallmentFormGroup);

            let incomeDateFormGroup = $('<div/>', {
                class: 'form-group'
            });

            let incomeDateLabel = $('<label/>', {
                text: '결제일'
            });

            let incomeDateInput = $('<input/>', {
                id: this.incomeDateElId,
                type: 'text',
                class: 'input-default form-control',
            });

            incomeDateInput.attr('autocomplete', 'off');

            incomeDateFormGroup.append(incomeDateLabel);
            incomeDateFormGroup.append(incomeDateInput);

            container.append(incomeDateFormGroup);

            $(instance.element).append(container);
        },

        //IncomeType Select Option Load
        loadIncomeType: function(){
            const instance = this;

            ajaxGetRequest('/code/C-1', {}, function(data){
                $.each(data, function(index, el){
                    $('#' + instance.incomeTypeElId).append(new Option(el.codeName, el.codeId, false, false));
                });
            }, null);
        },

        //Editor Input Setting Data
        setValues: function(rowData){
            this.incomeId = rowData.incomeId;

            $('#' + this.incomeTypeElId).val(rowData.code.codeId);

            $('#' + this.incomeCashAmountElId).val(rowData.cashAmount);
            $('#' + this.incomeCardAmountElId).val(rowData.cardAmount);
            $('#' + this.incomeBankBookAmountElId).val(rowData.bankBookAmount);

            $('#' + this.incomeInstallmentElId).val(rowData.installment);

            this.datepicker.datepicker('update', rowData.incomeDate);
        },

        //Reset Editor Data
        setEmpty: function(){
            this.incomeId = 0;

            $('#income_editor_container select').each(function(index, el){
                $(el).val('');
            });

            $('#income_editor_container input.form-control').each(function(index, el){
                $(el).val('');
            });

            this.datepicker.datepicker('update', '');
        }
    };

    $.fn.incomeEditor = function( options ){
        let ret;

        if( !this.length ) {
            return;
        }

        ret = $.data(this, 'income_editor', new IncomeEditor( this, options ) );

        return ret;
    };
}(jQuery));
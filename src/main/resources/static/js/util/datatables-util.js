let defaultDataTablesOption = {
    orderCellsTop: true,
    language: {
        emptyTable: '저장된 데이터가 없습니다.',
        lengthMenu: '페이지당 _MENU_ 개씩 보기',
        info: '현재 _START_ ~ _END_ / _TOTAL_건',
        infoEmpty: '데이터 없음',
        infoFiltered: '( _MAX_건의 데이터에서 필터링됨 )',
        search: '검색: ',
        zeroRecords: '일치하는 데이터가 없어요.',
        loadingRecords: '로딩중...',
        processing:     '잠시만 기다려 주세요...',
        paginate: {
            next: '다음',
            previous: '이전'
        }
    },
    scrollY:'480px',
    initComplete: function () {
        let api = this.api();

        api.columns().eq(0).each(function (colIdx) {
            let cell = $('.filters th').eq($(api.column(colIdx).header()).index());

            cell.addClass('p-r-8');

            let searchInput = $('<input/>', {
                type: 'text',
                class: 'input-default form-control input-sm',
                style: 'padding-right: 0px;',
                placeholder: '검색어 입력'
            });

            $(cell).append(searchInput);

            $('input', $('.filters th').eq($(api.column(colIdx).header()).index())).off('keyup change')
                .on('keyup change', function (e) {
                    e.stopPropagation();

                    let regexr = '({search})';

                    api
                        .column(colIdx)
                        .search(
                            this.value != ''
                                ? regexr.replace('{search}', '(((' + this.value + ')))')
                                : '',
                            this.value != '',
                            this.value == ''
                        )
                        .draw();

                    api.rows('.selected').deselect();
                });
        });
    }
}
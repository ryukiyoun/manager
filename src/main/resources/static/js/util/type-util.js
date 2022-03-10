function lunarSolarConvert(data) {
    if (data === 'LUNAR')
        return '음력';
    else
        return '양력';
}

function paymentTYpeConvert(data) {
    if (data === 'TEMPLE')
        return '사찰';
    else
        return '신도';
}

function familyTypeConvert(data) {
    switch (data){
        case 'FATHER' :
            return '부';
        case 'MOTHER' :
            return '모';
        case 'SON' :
            return '자';
        case 'DAUGHTER' :
            return '녀';
        case 'SPOUSE' :
            return '배우자';
        case 'BROTHER' :
            return '동생';
        case 'FATHER_IN_LAW' :
            return '장인';
        case 'MOTHER_IN_LAW' :
            return '장모';
        case 'GRAND_SON' :
            return '손자';
        case 'GRAND_DAUGHTER' :
            return '손녀';
        case 'ETC' :
            return '기타';
    }
}
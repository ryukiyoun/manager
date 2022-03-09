function lunarSolarConvert(object, key, appendKey) {
    if (object[key] === 'LUNAR')
        object[appendKey] = '음력';
    else
        object[appendKey] = '양력';
}

function familyTypeConvert(object, key, appendKey) {
    switch (object[key]){
        case 'FATHER' :
            object[appendKey] = '부';
            break;
        case 'MOTHER' :
            object[appendKey] = '모';
            break;
        case 'SON' :
            object[appendKey] = '자';
            break;
        case 'DAUGHTER' :
            object[appendKey] = '녀';
            break;
        case 'SPOUSE' :
            object[appendKey] = '배우자';
            break;
        case 'BROTHER' :
            object[appendKey] = '동생';
            break;
        case 'FATHER_IN_LAW' :
            object[appendKey] = '장인';
            break;
        case 'MOTHER_IN_LAW' :
            object[appendKey] = '장모';
            break;
        case 'GRAND_SON' :
            object[appendKey] = '손자';
            break;
        case 'GRAND_DAUGHTER' :
            object[appendKey] = '손녀';
            break;
        case 'ETC' :
            object[appendKey] = '기타';
            break;
    }
}
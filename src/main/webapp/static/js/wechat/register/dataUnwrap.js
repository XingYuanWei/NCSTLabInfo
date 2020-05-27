
// 用于 sweetalert 的专业数据提示信息以及 verify 的数据校验信息
// [ {'id': professionId, 'value': professionName, 'collegeId': collegeId } ]
var professionSuggestions = [];

// [{
//     'id': collegeId,
//     'value': collegeName,
//     'professionArr': [{
//         id: professionId,
//         value: professionId,
//         collegeId: collegeId
//     }]
// }]
var collegesDataArr = [];

// [ {'id': specialityId, 'value': specialityName} ]
var specialitySuggestions = [];

// [ {'id': interestId, 'value': interestName, 'domainId': domainId } ]
var interestSuggestions = [];

// [{
//     'id': domainId,
//     'value': domainName,
//     'interestArr': [{
//         id: interestId,
//         value: interestId,
//         domainId: domainId
//     }]
// }]
var domainsDataArr = [];

$(function () {
    // colleges 数据解析
    var colleges = $('#colleges_data').find('data:has(data)').toArray();
    $.each(colleges, function (i, college) {
        var $college = $(college);
        var collegesData = {};
        var currProfessionArr = [];
        $.each($college.find('data'), function (i, profession) {
            var $profession = $(profession);
            var item_profession = {id: $profession.attr('id'), value: $profession.attr('value'),
                collegeId: $college.attr('id')};
            currProfessionArr.push(item_profession);
        });
        professionSuggestions = professionSuggestions.concat(currProfessionArr);
        collegesData['id'] = $college.attr('id');
        collegesData['value'] = $college.attr('value');
        collegesData['professionArr'] = currProfessionArr;
        collegesDataArr.push(collegesData);
    });

    // specialities 数据解析
    var specialities = $('#specialities_data').find('data').toArray();
    $.each(specialities, function (i, speciality) {
        var $speciality = $(speciality);
        var item_speciality = {id: $speciality.attr('id'), value: $speciality.attr('value')};
        specialitySuggestions.push(item_speciality);
    });

    // domains 数据解析
    var domains = $('#domains_data').find('data:has(data)').toArray();
    $.each(domains, function (i, domain) {
        var $domain = $(domain);
        var domainsData = {};
        var currInterestArr = [];
        $.each($domain.find('data'), function (i, interest) {
            var $interest = $(interest);
            var item_interest = {id: $interest.attr('id'), value: $interest.attr('value'),
                professionId: $domain.attr('id') };
            currInterestArr.push(item_interest);
        });
        interestSuggestions = interestSuggestions.concat(currInterestArr);
        domainsData['id'] = $domain.attr('id');
        domainsData['value'] = $domain.attr('value');
        domainsData['interestArr'] = currInterestArr;
        domainsDataArr.push(domainsData);
    })
});

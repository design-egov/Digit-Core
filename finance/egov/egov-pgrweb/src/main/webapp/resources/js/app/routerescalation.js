/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) 2016  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *          1) All versions of this program, verbatim or modified must carry this
 *             Legal Notice.
 *
 *          2) Any misrepresentation of the origin of the material is prohibited. It
 *             is required that all modified versions of this material be marked in
 *             reasonable ways as different from the original version.
 *
 *          3) This license does not grant any rights to any user of the program
 *             with regards to rights under trademark law for use of the trade names
 *             or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */


$(document).ready(function () {

    $('#category').change(function () {
        $.ajax({
            url: "/pgr/complaint/officials/complainttypes-by-category",
            type: "GET",
            data: {
                categoryId: $('#category').val()
            },
            dataType: "json",
            success: function (response) {
                var complainttype = $('#complainttype')
                complainttype.find("option:gt(0)").remove();
                $.each(response, function (index, value) {
                    complainttype.append($('<option>').text(value.name).attr('value', value.id));
                });
            }
        })
    });

    var position = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/pgr/complaint/router/position?positionName=%QUERY',
            dataType: "json",
            filter: function (data) {
                // Map the remote source JSON array to a JavaScript object array
                return $.map(data, function (pos) {
                    return {
                        name: pos.name,
                        value: pos.id
                    };
                });
            }
        }
    });

    position.initialize();

    var router_pos_typeahead = $('#routerposition').typeahead({
        hint: false,
        highlight: true,
        minLength: 3
    }, {
        displayKey: 'name',
        source: position.ttAdapter()
    });

    typeaheadWithEventsHandling(router_pos_typeahead, '#positionId');

    $('#btnsearch').click(function (e) {
        onSubmitEvent(e);
        return false;
    });
});


function onSubmitEvent(event) {
    $('.report-section').removeClass('display-hide');
    event.preventDefault();
    $("#routerescalationtbl")
        .dataTable({
            type: 'GET',
            responsive: true,
            destroy: true,
            ajax: {
                url: "/pgr/routerescalation/search-resultList?" + $("#routerescalationform").serialize()
            },
            "autoWidth": false,
            "bDestroy": true,
            "sDom": "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-md-3 col-xs-12'i><'col-md-3 col-xs-6 col-right'l><'col-xs-12 col-md-3 col-right'<'export-data'T>><'col-md-3 col-xs-6 text-right'p>>",
            "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            "oTableTools": {
                "sSwfPath": "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
                "aButtons": ["xls", "pdf", "print"]
            },
            columns: [
                {
                    "data": "complainttype",
                    "sTitle": "Grievance Type"
                }, {
                    "data": "ward",
                    "sTitle": "Ward"
                }, {
                    "data": "routedto",
                    "sTitle": "Routed To"
                }, {
                    "data": "firstescpos",
                    "sTitle": "First Escalation"
                }, {
                    "data": "secondescpos",
                    "sTitle": "Second Escalation"
                }, {
                    "data": "thirdescpos",
                    "sTitle": "Third Escalation"
                }],
            "aaSorting": [[0, 'asc']]
        });
    jQuery('.loader-class').modal('hide');
}
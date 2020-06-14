$(document).ready(function () {
    console.log("ready!");

    $.ajax({
        url: "https://api.rootnet.in/covid19-in/stats/latest",
        type: "GET",
        success: function (response) {
            var confirmedCases = parseInt(response.data.summary.confirmedCasesIndian);
            var discharged = parseInt(response.data.summary.discharged);
            var deaths = parseInt(response.data.summary.deaths);
            var activecases = confirmedCases - discharged - deaths;

            change($("#confirmedcases"), confirmedCases);
            change($("#recoveredcases"), discharged);
            change($("#activecases"), activecases);
            change($("#deaths"), deaths);

            $.each(response.data.regional, function (i, item) {
                $('<tr>').attr('id', i)
                    .append($('<td>').text(item.loc))
                    .append($('<td>').text(item.totalConfirmed))
                    .append($('<td>').text(item.discharged))
                    .append($('<td>').text(deaths))
                    .appendTo('#statelist');

            });

        },
        error: function (e) {
            console.log("error -", e);
        }
    });

    var toDate = filterDate(0);
    var fromDate = filterDate(1);

    $.ajax({
        url: "https://api.covid19api.com/country/India?from=" + fromDate + "&to=" + toDate,
        type: "GET",
        success: function (response) {
            //console.log(response);
            var chartData = [];
            var chartLabel = [];
            $.each(response, function (i, item) {
                chartData.push(item.Confirmed);
                chartLabel.push(i);
            });

            var ctx = document.getElementById('myChart');

            var myLineChart = new Chart(ctx, {
                type: 'line',
                data: {
                    datasets: [{
                        label: 'Last 30 days Confirmed cases',
                        data: chartData,
                        backgroundColor: "rgba(76,175,80,0.5)",
                        pointBackgroundColor: "black",
                    }],
                    labels: chartLabel
                },
                options: {
                    responsive: true,
                    legend: {
                        labels: {
                            // This more specific font property overrides the global property
                            fontColor: 'black'
                        }
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                fontColor: "black"
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                fontColor: "black"
                            }
                        }]
                    },
                    maintainAspectRatio: false
                }
            });

        },
        error: function (e) {
            console.log("error -", e);
        }
    })
});

function change(element, current) {
    element.each(function () {
        $(this).prop('Counter', 0).animate({
            Counter: current
        }, {
            duration: 2000,
            easing: 'swing',
            step: function (now) {
                $(this).text(Math.ceil(now));
            }
        });
    });
}

function filterDate(num) {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1 - num;
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }

    return yyyy + "-" + mm + "-" + dd;
}

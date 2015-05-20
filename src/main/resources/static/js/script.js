$(document).ready(function () {
    var year = (new Date).getFullYear();
    var month = (new Date).getMonth();
    var dateToday = new Date();
    var dateYesterday = new Date();
    dateYesterday.setDate(dateToday.getDate() - 1);
    var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    var monthName = monthNames[month];
    var dateStringToday = dateToday.getFullYear() + "-" + (dateToday.getMonth() + 1) + "-" + dateToday.getDate();
    var dateStringYesterday = dateYesterday.getFullYear() + "-" + (dateYesterday.getMonth() + 1) + "-" + dateYesterday.getDate();

    // Display today's trains
    $.get("sound/trains?date=" + dateStringToday, function (data) {
        displayTodaysTrains(data);
    });

    /**
     * Display today's trains
     * @param data train data
     */
    function displayTodaysTrains(data) {
        var trainsToday = $('#trains-today');
        var trainsTodayTotal = $('#trains-today-total');

        $.each(data, function (item) {
            var timeString = formatTime(data[item].timeMin);
            trainsToday.append("<div class='trains-time'>" + timeString + "</div>");
            trainsTodayTotal.html(data.length);
        });
    }

    // Display yesterday's trains
    $.get("sound/trains?date=" + dateStringYesterday, function (data) {
        displayYesterdaysTrains(data);
    });

    /**
     * Display yesterday's trains
     * @param data train data
     */
    function displayYesterdaysTrains(data) {
        var trainsYesterday = $('#trains-yesterday');
        var trainsYesterdayTotal = $('#trains-yesterday-total');

        $.each(data, function (item) {
            var timeString = formatTime(data[item].timeMin);
            trainsYesterday.append("<div class='trains-time'>" + timeString + "</div>");
            trainsYesterdayTotal.html(data.length);
        });
    }

    /**
     * Display the daily total
     */
    $.get("sound/trains/bymonth?month=" + (month + 1) + "&year=" + year, function (rawData) {
        var chart = {
            "type": "serial",
            "path": "http://www.amcharts.com/lib/3/",
            "categoryField": "category",
            "startDuration": 1,
            "theme": "light",
            "categoryAxis": {
                "gridPosition": "start"
            },
            "trendLines": [],
            "graphs": [
                {
                    "balloonText": "[[value]]",
                    "fillAlphas": 1,
                    "id": "AmGraph-1",
                    "title": "Trains",
                    "type": "column",
                    "valueField": "column-1",
                    "colorField": "columnColor",
                    "lineColorField": "lineColor"
                }
            ],
            "guides": [],
            "valueAxes": [
                {
                    "id": "ValueAxis-1",
                    "stackType": "regular",
                    "title": ""
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "Train Count for " + monthName
                }
            ],

            "dataProvider": []
        };

        $.each(rawData, function (key) {
            var date = new Date(rawData[key][0] + 'T00:00:00');
            var dayName = dayNames[date.getDay()];

            chart['dataProvider'].push({
                "category": dayName + ' - ' + rawData[key][0].substr(-2, 2),
                "column-1": rawData[key][1],
                "columnColor": dayName == 'Sat' || dayName == 'Sun' ? "lightcoral" : null,
                "lineColor": dayName == 'Sat' || dayName == 'Sun' ? "lightcoral" : null
            });
        });

        AmCharts.makeChart("this-month", chart);
    });

    /**
     * Display the monthly total
     */
    $.get("sound/trains/byyear?year=" + year, function (data) {
        var yearlyChart = {
            "type": "serial",
            "path": "http://www.amcharts.com/lib/3/",
            "categoryField": "category",
            "startDuration": 1,
            "theme": "light",
            "categoryAxis": {
                "gridPosition": "start"
            },
            "trendLines": [],
            "graphs": [
                {
                    "balloonText": "[[value]]",
                    "fillAlphas": 1,
                    "id": "AmGraph-1",
                    "title": "Trains",
                    "type": "column",
                    "valueField": "column-1"
                }
            ],
            "guides": [],
            "valueAxes": [
                {
                    "id": "ValueAxis-1",
                    "stackType": "regular",
                    "title": ""
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "Train Count for " + year
                }
            ], "dataProvider": []
        };

        $.each(data, function (key) {
            yearlyChart.dataProvider.push({
                'category': monthNames[data[key][0].substr(5, 2) - 1],
                'column-1': data[key][1]
            });
        });

        AmCharts.makeChart("this-year", yearlyChart);
    });


    /**
     * Format a time like 16:14 to 4:14pm or 9:33 to 9:33am
     * @param time
     */
    function formatTime(time) {
        var hour = time.substr(0, 2);
        var minute = time.substr(3, 2);
        var ret;

        if (hour == 0) {
            ret = "12:" + minute + "am";
        }
        else if (hour == 12) {
            ret = hour + ":" + minute + "pm";
        }
        else if (hour > 12) {
            hour -= 12;
            ret = hour + ":" + minute + "pm";
        } else {
            ret = hour * 1 + ":" + minute + "am";
        }
        if (hour < 10) {
            ret = "&nbsp;" + ret;
        }
        return ret;
    }

});
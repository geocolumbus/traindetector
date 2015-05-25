$(document).ready(function () {
    var year = (new Date).getFullYear();
    var month = (new Date).getMonth();
    var dateToday = new Date();
    var dateYesterday = new Date();
    dateYesterday.setDate(dateToday.getDate() - 1);
    var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var dayNames = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
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
    $.get("sound/trains/days?days=60", function (rawData) {
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
                    "title": "Number of Trains"
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "Daily Totals"
                }
            ],

            "dataProvider": []
        };

        $.each(rawData, function (key) {
            var date = new Date(rawData[key][0] + 'T00:00:01-05:00');
            var dayName = dayNames[date.getDay()];
            var weekendColor = dayName == 'Sa' || dayName == 'Su' ? "lightcoral" : null;

            chart['dataProvider'].push({
                "category": dayName + ' ' + rawData[key][0].substr(-5, 2).replace(/^0/, "") + '-' + rawData[key][0].substr(-2, 2),
                "column-1": rawData[key][1],
                "columnColor": weekendColor,
                "lineColor": weekendColor
            });
        });

        AmCharts.makeChart("this-month", chart);
    });

    /**
     * Display the monthly total
     */
    $.get("sound/trains/months?months=24", function (data) {
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
                    "title": "Number of Trains"
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "Monthly Totals"
                }
            ], "dataProvider": []
        };

        $.each(data, function (key) {
            yearlyChart.dataProvider.push({
                'category': monthNames[data[key][0].substr(5, 2) - 1],
                'column-1': data[key][1],
                'columnColor':'tan',
                'lineColor':'tan'
            });
        });

        AmCharts.makeChart("this-year", yearlyChart);
    });


    /**
     * Display the daily distribution
     */
    $.get("sound/trains/daydist", function (data) {
        var distributionDayChart = {
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
                    "title": "Number of Trains"
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "By Day of Week"
                }
            ], "dataProvider": []
        };

        $.each(data, function (key) {

            var cleanData = [
                ['Monday'],
                ['Tuesday'],
                ['Wednesday'],
                ['Thursday'],
                ['Friday'],
                ['Saturday'],
                ['Sunday']
            ];
            var weekendColor = key > 4 ? "lightcoral" : null;

            $.each(data, function (dataIndex) {
                $.each(cleanData, function (item) {
                    if (cleanData[item][0] == data[dataIndex][0]) {
                        cleanData[item].push(data[dataIndex][1]);
                        return false;
                    }
                });
            });

            distributionDayChart.dataProvider.push({
                'category': cleanData[key][0],
                'column-1': cleanData[key][1],
                "columnColor": weekendColor,
                "lineColor": weekendColor
            });
        });

        AmCharts.makeChart("distribution-day", distributionDayChart);
    });

    /**
     * Display the hourly distribution
     */
    $.get("sound/trains/hourdist", function (data) {
        var distributionDayChart = {
            "type": "serial",
            "path": "http://www.amcharts.com/lib/3/",
            "categoryField": "category",
            "startDuration": 1,
            "theme": "light",
            "categoryAxis": {
                "gridPosition": "start",
                "title": "Hour"
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
                    "title": "Number of Trains"
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "By Hour of Day"
                }
            ], "dataProvider": []
        };

        $.each(data, function (key) {
            var dayColor = key < 7 || key > 18 ? 'black' : null;

            distributionDayChart.dataProvider.push({
                'category': data[key][0],
                'column-1': data[key][1],
                "columnColor": dayColor,
                "lineColor": dayColor
            });
        });

        AmCharts.makeChart("distribution-hour", distributionDayChart);
    });

    /**
     * Display minutes between trains
     */
    $.get("sound/trains/minutesbetweentrains", function (data) {
        var minutesChart = {
            "type": "serial",
            "path": "http://www.amcharts.com/lib/3/",
            "categoryField": "category",
            "startDuration": 1,
            "theme": "light",
            "categoryAxis": {
                "gridPosition": "start",
                "title": "Minutes"
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
                /*
                {
                    "balloonText": "[[value]]",
                    "fillAlphas": 0,
                    "id": "AmGraph-2",
                    "title": "Poisson",
                    "valueField": "column-2",
                    "lineColor":"red"
                }
                */
            ],
            "guides": [],
            "valueAxes": [
                {
                    "id": "ValueAxis-1",
                    "stackType": "regular",
                    "title": "Number of Trains"
                }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "id": "Title-1",
                    "size": 15,
                    "text": "Minutes Between Trains"
                }
            ], "dataProvider": []
        };

        $.each(data, function (key) {
            minutesChart.dataProvider.push({
                'category': key,
                'column-1': data[key].minutes
                //'column-2': data[key].poisson
            });
        });

        AmCharts.makeChart("minutes-between-trains", minutesChart);
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
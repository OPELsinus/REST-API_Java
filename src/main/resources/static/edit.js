var type;
var amount;
var creation_date;
var export_date;

function edit(id) {
	document.getElementById('buttonsBlockShow' + id).style.visibility = 'visible';
	document.getElementById('show' + id).style.visibility = 'visible';
	document.getElementById('cancel' + id).style.visibility = 'visible';
	document.getElementById('delete' + id).style.visibility = 'visible';

	document.getElementById('edit' + id).style.visibility = 'hidden';

	//document.getElementById('wasteIdSpan' + id).textContent = '';
	type = document.getElementById('wasteTypeSpan' + id).innerHTML;
	amount = document.getElementById('wasteAmountSpan' + id).innerHTML;
	if (document.getElementById('wasteCreationDateSpan' + id))
		creation_date = document.getElementById('wasteCreationDateSpan' + id).innerHTML;
	if (document.getElementById('wasteExportDateSpan' + id))
		export_date = document.getElementById('wasteExportDateSpan' + id).innerHTML;
	console.log(type);
	console.log(amount);
	console.log(creation_date);
	console.log(export_date);
	document.getElementById('wasteTypeSpan' + id).textContent = '';
	document.getElementById('wasteAmountSpan' + id).textContent = '';
	if (document.getElementById('wasteCreationDateSpan' + id)) document.getElementById('wasteCreationDateSpan' + id).textContent = '';
	if (document.getElementById('wasteExportDateSpan' + id)) document.getElementById('wasteExportDateSpan' + id).textContent = '';

	//document.getElementById('id' + id).type = 'text';
	document.getElementById('wasteType' + id).type = 'text';
	document.getElementById('wasteAmount' + id).type = 'text';
	document.getElementById('creationDate' + id).type = 'date';
	document.getElementById('exportDate' + id).type = 'date';
}

function cancel(id) {
	console.log(type);
	console.log(amount);
	console.log(creation_date);
	console.log(export_date);

	document.getElementById('wasteTypeSpan' + id).textContent = type;
	document.getElementById('wasteAmountSpan' + id).textContent = amount;
	if (document.getElementById('wasteCreationDateSpan' + id))
		document.getElementById('wasteCreationDateSpan' + id).textContent = creation_date;
	if (document.getElementById('wasteExportDateSpan' + id))
		document.getElementById('wasteExportDateSpan' + id).textContent = export_date;

	//document.getElementById('id' + id).type = 'text';
	document.getElementById('wasteType' + id).type = 'hidden';
	document.getElementById('wasteAmount' + id).type = 'hidden';
	document.getElementById('creationDate' + id).type = 'hidden';
	document.getElementById('exportDate' + id).type = 'hidden';

	document.getElementById('buttonsBlockShow' + id).style.visibility = 'hidden';
	document.getElementById('show' + id).style.visibility = 'hidden';
	document.getElementById('cancel' + id).style.visibility = 'hidden';
	document.getElementById('delete' + id).style.visibility = 'hidden';
	document.getElementById('edit' + id).style.visibility = 'visible';
}

function getOnlyMine(owner) {
	console.log(owner);
	if (document.getElementById('getonlymine').checked) {
		for (var i = 1; i < 1000; i++) {
			try {
				if (document.getElementById('ownerSpan' + i).innerHTML != owner) {
					$('#tr' + i).hide();
				}
				else {
					$('#tr' + i).show();
				}
			}
			catch {
				continue;
			}	
		}
	}

	else {
		window.location.reload();
	}
}

function getOnlyMineBySelect(owner) {
	console.log(owner);
	if (owner != 'no') {
		for (var i = 1; i < 1000; i++) {
			try {
				if (document.getElementById('ownerSpan' + i).innerHTML != owner) {
					$('#tr' + i).hide();
				}
				else {
					$('#tr' + i).show();
				}
			}
			catch {
				continue;
			}	
		}
	}

	else {
		window.location.reload();
	}
}

function getDateDiff(days, type) {
	// window.location.reload();

	Date.prototype.toISODateString = function () {
	  return this.toISOString().substr(0,10);
	};

	Date.prototype.toDateFromDays = function (days) {
	  days = parseInt(days) || 0;
	  var newDate = new Date(this.getTime());
	  newDate.setDate(this.getDate() + days);
	  return newDate;
	};
	var currentTime = new Date();

	for (var i = 1; i < 1000; i++) {
		try{
			date = document.getElementById(type + i).innerHTML;
			dateDiff = Math.floor((Date.parse(currentTime) - Date.parse(date)) / 86400000);
			if (dateDiff > days) {
				$('#tr' + i).hide();
			}
			else {
				$('#tr' + i).show();
			}
		}
		catch {
			continue;
		}
	}
}

function getWasteByTypeOrAmount(value, type) {
	if (value != 'no') {
		for (var i = 1; i < 1000; i++) {
			try {
				if (document.getElementById(type + i).innerHTML != value) {
					$('#tr' + i).hide();
				}
				else {
					$('#tr' + i).show();
				}
			}
			catch {
				continue;
			}	
		}
	}

	else {
		//window.location.reload();
	}

	for (var i = 1; i < 10; i++) {
		console.log($('#tr' + i).is(":visible"));
	}

}

window.onload = function() {
  getTotalPages();
};

function getTotalPages() {
 	var totalPages = document.getElementById("totalPages").innerHTML;
 	var mainUrl = window.location.href;
 	console.log(window.location.href);
 	try {
 		console.log(window.location.href.split('?')[1].split('&'));
 	}

 	catch {
 		mainUrl += "?page=0";
 	}
 	var isPage = 0;
    for (var i = 0; i < mainUrl.split('?')[1].split('&').length; i++){
    	if (mainUrl.split('?')[1].split('&')[i].includes('page'))
    		isPage = i;
    	
    }
    
    var curPage = mainUrl.split('?')[1].split('&')[isPage].split('=')[1];
    console.log(curPage);
    console.log(totalPages);
    if (parseInt(curPage) >= 2) {
    	if (parseInt(curPage) < parseInt(totalPages) - 1) {
	    	    	
	    	var url = getNewURL(parseInt(curPage), isPage, mainUrl);
	    	document.getElementById('page1Text').innerHTML = parseInt(curPage);
	    	document.getElementById('page1Text').href = url;

	    	var url = getNewURL(parseInt(curPage) + 1, isPage, mainUrl);
	    	document.getElementById('page2Text').innerHTML = parseInt(curPage) + 1;
	    	document.getElementById('page2Text').href = url;
	    	
	    	var url = getNewURL(parseInt(curPage) + 2, isPage, mainUrl);
	    	document.getElementById('page3Text').innerHTML = parseInt(curPage) + 2;
	    	document.getElementById('page3Text').href = url;
    	}
    	else {
    		var url = getNewURL(parseInt(curPage), isPage, mainUrl);
    		document.getElementById('page1Text').innerHTML = parseInt(curPage);
	    	document.getElementById('page1Text').href = url;
	    	
	    	var url = getNewURL(parseInt(curPage) + 1, isPage, mainUrl);
	    	document.getElementById('page2Text').innerHTML = parseInt(curPage) + 1;
	    	document.getElementById('page2Text').href = window.location.href;
	    	
	    	document.getElementById('page3').style.display = 'none';
    	}
    }

    else {
    	if (parseInt(totalPages) >= 3) {
    		var url = getNewURL(1, isPage, mainUrl);
    		console.log(url);
    		document.getElementById('page1Text').innerHTML = 1;
	    	document.getElementById('page1Text').href = url;
	    	
	    	var url = getNewURL(2, isPage, mainUrl);
	    	document.getElementById('page2Text').innerHTML = 2;
	    	document.getElementById('page2Text').href = url;
	    	
	    	var url = getNewURL(3, isPage, mainUrl);
	    	document.getElementById('page3Text').innerHTML = 3;
	    	document.getElementById('page3Text').href = url;
    	}
    	
    	if (parseInt(totalPages) == 2) {
    		var url = getNewURL(1, isPage, mainUrl);
    		document.getElementById('page1Text').innerHTML = 1;
	    	document.getElementById('page1Text').href = url;
	    	
	    	var url = getNewURL(2, isPage, mainUrl);
	    	document.getElementById('page2Text').innerHTML = 2;
	    	document.getElementById('page2Text').href = url;
	    	
	    	document.getElementById('page3').style.display = 'none';
    	}
    	if (parseInt(totalPages) == 1) {
    		var url = getNewURL(1, isPage, mainUrl);
    		document.getElementById('page1Text').innerHTML = 1;
	    	document.getElementById('page1Text').href = url;
	    	
	    	document.getElementById('page2').style.display = 'none';
	    	
	    	document.getElementById('page3').style.display = 'none';
    	}
    }

}

function getNewURL(curPage, isPage, mainUrl) {
	var pageUrl = "page=" + parseInt(curPage - 1);

	var url = mainUrl.split('?')[0] + '?';

	for (var i = 0; i < mainUrl.split('?')[1].split('&').length; i++){
		if (i == isPage) url += pageUrl;
		else url += mainUrl.split('?')[1].split('&')[i];

		if (i != mainUrl.split('?')[1].split('&').length - 1) url += '&';
    }
	return url;
}

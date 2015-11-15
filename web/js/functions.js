/*funkce meni vzajemne mezi sebou hodnoty poli:
* prijezd (dataFrom), odjezd (dateTo) a pocet dnu (numDays).
* podle typu volani (from, to, num) dopocita zbytek tak, 
* ze pokud se menilo datum meni pocet noci a pokud 
* pocet noci meni se dateTo
*
* @autor Jiri Stepan
*/
function changeDate(type){
	dateFromField=document.getElementById('dateFromField');
	dateToField=document.getElementById('dateToField');
	numDaysField=document.getElementById('numDaysField');
	
	// ziskame hodnoty typu Date
	//dateTo=getDate(dateToField.value);	

	if (type=='num'){
		dateFrom=getDate(dateFromField.value);
		numDays=parseInt(numDaysField.value);

		dateTo=new Date()
		dateTo.setTime(dateFrom.getTime()+numDays*86400000);
		
		dateString=getDateStr(dateTo)
		dateToField.value=dateString;	
	}
	if (type=='to'){
		dateFrom=getDate(dateFromField.value);
		dateTo=getDate(dateToField.value);
		
		numDays=(dateTo.getTime()-dateFrom.getTime())/(86400000); //convert from miliseconds to days
		
		numDaysField.value=numDays;	
	}
	
	if (type=='from'){
		dateTo=getDate(dateToField.value);
		numDays=parseInt(numDaysField.value);

		dateFrom.setDate(dateFrom.getDate()-numDays);
		
		dateToField.value=getDateStr(dateFrom);	
		
	}
}

// vrati objekt typu Date ze stringu typu 23.12.2003
function getDate(dateStr){
	//var now = new Date();
	//var toDay = new Date(now.getYear(), now.getMonth(), now.getDate());
	//alert('getDate:'+dateStr);
	if (dateStr=='') return new Date();
	dateArray = dateStr.split(".");
	day = dateArray[0];
	month = dateArray[1]-1; 
	year = dateArray[2];
	//TODO: kontrolovat format a pripadne doplnit chybejici rok
	return (new Date(year, month, day));
	
}

//inverzni fce vraci z Date zapis 23.12.2003
function getDateStr(date){
	return date.getDate()+'.'+(date.getMonth()+1)+'.'+date.getFullYear()	
}

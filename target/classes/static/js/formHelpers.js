function enableDisableToDateInput(src, idElem){
				checked = src.checked;
				
				document.getElementById(idElem).disabled=checked;
				if(checked === true){
					src.value = "Y";
				}else{
					src.value = "N";
					
				}
				document.getElementById(idElem).value="";	
}
	
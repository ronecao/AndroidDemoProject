// JavaScript Document
function initFramework()
{
	window.EMVIO.initFramework('phQP9iBIEgl4nEFi8vQmFf25q6m8PieC')
}

function processSale(transDict)
{
	window.EMVIO.processSale(transDict);
}
function processAuthorization(transDict)
{
	window.EMVIO.processAuthorization(transDict);
}
function processCapture(transDict)
{
	window.EMVIO.processCapture(transDict);
}
function processVoid(transDict)
{
	window.EMVIO.processVoid(transDict);
}
function processReturn(transDict)
{
	window.EMVIO.processReturn(transDict);
}
function phoneReferral(result,authcode)
{
	window.EMVIO.phoneReferral(result,authcode);
}

function confirmCardinfoResult(result)
{
	window.EMVIO.confirmCardinfoResult(result);
}
function getPinpads()
{
	window.EMVIO.getPinpads();
}
function selectPinpad(name)
{
	window.EMVIO.selectPinpad(name);
}
function connectPinpad()
{
	window.EMVIO.connectPinpad();
}
function TMSupdate()
{
	window.EMVIO.TMSupdate();
}
function signatureVerified(result)
{
	window.EMVIO.signatureVerified(result);
}



/*
delgate functions please implement them as need
*/
function OninitCompleted(arg){
	 document.getElementById('replaceme').innerHTML=arg;
	
            
 } 

function OntransactionMessageUpdate(msg)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/>"+msg;
}
function OnconfirmCardinfo(cardinfo)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/> cardinfo:"+cardinfo;
	confirmCardinfoResult('true');
}
function OntransactionSaleCompleted(result)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/>"+result;
}
function OntransAuthorizationCompleted (result)
{
content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/> OntransAuthorizationCompleted:"+result;
}
function OnpinPadConnectionCompleted(result)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/> connection completed:"+result;
}
function OnverifySignature(verify)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/>verifySignature:"+verify+"<br/> ";
	signatureVerified("true");
}
function OnvoiceRefferal(verify,phone)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/>OnvoiceRefferal:"+verify+phone+"<br/> ";
}
function OngetPinpadCompleted(result)
{
	content = document.getElementById('replaceme').innerHTML
	 document.getElementById('replaceme').innerHTML= content +"<br/>OngetPinpadCompleted:"+result+"<br/> ";
	selectPinpad("Miura 865");
}

function OnTMSupdateCompleted(result)
{
	content = document.getElementById('replaceme').innerHTML
	document.getElementById('replaceme').innerHTML= content +"<br/>OnTMSupdateCompleted:"+result+"<br/> ";
}

function OntransactionCaptureCompleted(result)
{
content = document.getElementById('replaceme').innerHTML
	document.getElementById('replaceme').innerHTML= content +"<br/>OntransactionCaptureCompleted:"+result+"<br/> ";
}
function OntransactionVoidCompleted(result)
{
content = document.getElementById('replaceme').innerHTML
	document.getElementById('replaceme').innerHTML= content +"<br/>OntransactionVoidCompleted:"+result+"<br/> ";
}
function OntransactionReturnCompleted(result)
{
content = document.getElementById('replaceme').innerHTML
	document.getElementById('replaceme').innerHTML= content +"<br/>OntransactionReturnCompleted:"+result+"<br/> ";
}


function gradeit(){
for (q=0;q<totalquestions;q++){
	var thequestion=eval("document.myquiz.question"+q)
	for (c=0;c<thequestion.length;c++){
		if (thequestion[c].checked==true)
		   actualchoices[q]=thequestion[c].value
		else
		   actualchoices[q]="unanswered"
	}
}

// TODO: send the actualchoices array by email

alert("Thank you ! Your answers have been taken into account")
window.location="../../index.html"
}


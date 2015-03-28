
function gradeit(){
	var username=eval("document.myquiz.username").value
for (q=0;q<totalquestions;q++){
	var thequestion=eval("document.myquiz.question"+q)
		   actualchoices[q]="unanswered"
	for (c=0;c<thequestion.length;c++){
		if (thequestion[c].checked==true)
		   actualchoices[q]=thequestion[c].value
	}
}
  var xxml='<user>'+username+'</user>\n';
  xxml+='<reps>\n';
  for (q=0;q<totalquestions;q++) {
    xxml+='<q>'+q+'</q>';
    xxml+='<r>'+actualchoices[q]+'</r>\n';
  }
  xxml+='</reps>\n';
  $.post('http://127.0.0.1/xtof/siteperso/repqcm.php', {xml: xxml });

// TODO: send the actualchoices array by email

alert("Thank you ! Your answers have been taken into account")
window.location="../../index.html"
}


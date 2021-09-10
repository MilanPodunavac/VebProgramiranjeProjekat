
function encodeImageFileAsURL() {

    var filesSelected = document.getElementById("inputFile").files;
    if (filesSelected.length > 0) {
      var fileToLoad = filesSelected[0];

      var fileReader = new FileReader();

      fileReader.onload = function(fileLoadedEvent) {
        var srcData = fileLoadedEvent.target.result; // <--- data: base64

        var newImage = document.createElement('img');
        newImage.src = srcData;

        document.getElementById("imgTest").innerHTML = newImage.outerHTML;
        alert("Converted Base64 version is " + document.getElementById("imgTest").innerHTML);
        console.log("Converted Base64 version is " + srcData);

		$.post({
			url: "rest/Image64Service/addImage",
			data: JSON.stringify({id: "0", base64Data: srcData}),
			contentType: 'application/json',
			complete: function(){
				alert("image saved");
			}
		})
		
		
      }
      fileReader.readAsDataURL(fileToLoad);
    }
}

function showImage(){
	$.get({
		url: "rest/Image64Service/getImageData?id=0000000001",
		complete: function(message){
			let newSrcData = message.responseText;
			
			var newnewImage = document.createElement('img');
			newnewImage.src = newSrcData;
			document.getElementById("imgGet").innerHTML = newnewImage.outerHTML;
		}
	})
}


# Webcam Capture Service

## Reference

see [sarxos' webcam-capture](http://en.wikipedia.org/wiki/Markdown)

> The concepts in this library was used to create this motion activated webcam scanner service

## Usage

build and run the jar:

    $ mvn package
    $ java -jar target\webcam-capture-service-0.0.1-SNAPSHOT.jar
    
open [stream] (http://localhost:8321) in browser

## Integration

the server accepts connections on port 8321, to show the stream in a html page use the following code:

	<canvas id="webcam-capture" width="1920" height="1080"></canvas>
	
	<script>
	
		var canvas = document.getElementById('webcam-capture');
		var context = canvas.getContext('2d');
		var imageObj = new Image();

		imageObj.onload = function() {
			context.drawImage(imageObj, 0, 0);
		};
		
		var delay = 1000 / 25; // 25 fps

		var capture = function() {

			imageObj.src = 'http://localhost:8321';
			setTimeout(capture, delay);
		}

		setTimeout(capture, delay);
	</script>
